// The script generates a random subset of valid jdk, os, timezone, and other axes.
// You can preview the results by running "node matrix.js"
// See https://github.com/vlsi/github-actions-random-matrix
let fs = require('fs');
let os = require('os');
let {MatrixBuilder} = require('./matrix_builder');
const matrix = new MatrixBuilder();

// Some filter conditions might become unsatisfiable, and by default
// the matrix would ignore that.
// For instance, SCRAM requires PostgreSQL 10+, so if you ask
// matrix.generateRow({pg_version: '9.0'}), then it won't generate a row
// That behaviour is useful for PR testing. For instance, if you want to test only SCRAM=yes
// cases, then just comment out "value: no" in SCRAM axis, and the matrix would yield the matching
// parameters.
// However, if you add new testing parameters, you might want un-comment the following line
// to notice if you accidentally introduce unsatisfiable conditions.
// matrix.failOnUnsatisfiableFilters(true);

matrix.addAxis({
  name: 'java_distribution',
  values: [
    'corretto',
    'liberica',
    'microsoft',
    'oracle',
    'temurin',
    'zulu',
  ]
});

// TODO: support different JITs (see https://github.com/actions/setup-java/issues/279)
matrix.addAxis({name: 'jit', title: '', values: ['hotspot']});

// See the supported versions at https://foojay.io/almanac/java-17/
matrix.addAxis({
  name: 'java_version',
  title: x => 'Java ' + x,
  // Strings allow versions like 18-ea
  values: [
    '8',
    '11',
    '17',
    '19',
  ]
});

matrix.addAxis({
  name: 'tz',
  title: x => x,
  values: [
    'America/New_York',
    'Pacific/Chatham',
    'UTC'
  ]
});

matrix.addAxis({
  name: 'os',
  title: x => x.replace('-latest', ''),
  values: [
    'ubuntu-latest',
    // We use docker-compose for launching PostgreSQL
    'windows-latest',
    'macos-latest',
  ]
});

// Test cases when Object#hashCode produces the same results
// It allows capturing cases when the code uses hashCode as a unique identifier
matrix.addAxis({
  name: 'hash',
  values: [
    {value: 'regular', title: '', weight: 42},
    {value: 'same', title: 'same hashcode', weight: 1}
  ]
});

matrix.addAxis({
  name: 'locale',
  title: x => x.language + '_' + x.country,
  values: [
    {language: 'de', country: 'DE'},
    {language: 'fr', country: 'FR'},
    {language: 'ru', country: 'RU'},
    {language: 'tr', country: 'TR'},
  ]
});

matrix.setNamePattern([
    'java_version', 'java_distribution', 'hash', 'os',
    'tz', 'locale',
]);

// Microsoft Java has no distribution for 8, 18, 19
matrix.exclude({java_distribution: 'microsoft', java_version: '8'});
matrix.exclude({java_distribution: 'microsoft', java_version: '18'});
matrix.exclude({java_distribution: 'microsoft', java_version: '19'});
// Oracle supports 17+ only
matrix.exclude({java_distribution: 'oracle', java_version: ['8', '11', '19']});
// TODO: remove when compileJava with "same hashcode" issues are resolved
// See https://bugs.openjdk.org/browse/JDK-8288590 is resolved
// See https://github.com/jqwik-team/jqwik/pull/460#issuecomment-1428261036
matrix.exclude({hash: {value: 'same'}});

// The most rare features should be generated the first
// For instance, we have a lot of PostgreSQL versions, so we generate the minimal the first
// It would have to generate other parameters, and it might happen it would cover "most recent Java" automatically
// Ensure at least one job with "same" hashcode exists
// TODO: un-comment once "same hashcode" issues are resolved
// matrix.generateRow({hash: {value: 'same'}});
// Ensure there will be at least one job with minimal supported Java
matrix.generateRow({java_version: matrix.axisByName.java_version.values[0]});
// Ensure there will be at least one job with the latest Java
matrix.generateRow({java_version: matrix.axisByName.java_version.values.slice(-1)[0]});
// Ensure at least one Windows and at least one Linux job is present (macOS is almost the same as Linux)
// matrix.generateRow({os: 'windows-latest'});
matrix.generateRow({os: 'ubuntu-latest'});

const include = matrix.generateRows(process.env.MATRIX_JOBS || 5);
if (include.length === 0) {
  throw new Error('Matrix list is empty');
}
include.sort((a, b) => a.name.localeCompare(b.name, undefined, {numeric: true}));
include.forEach(v => {
    // Pass locale via Gradle arguments in case it won't be inherited from _JAVA_OPTIONS
    // In fact, _JAVA_OPTIONS is non-standard and might be ignored by some JVMs
    let gradleArgs = [
        `-Duser.country=${v.locale.country}`,
        `-Duser.language=${v.locale.language}`,
    ];
    v.extraGradleArgs = gradleArgs.join(' ');
});
include.forEach(v => {
  let jvmArgs = [];

  if (v.hash.value === 'same') {
    jvmArgs.push('-XX:+UnlockExperimentalVMOptions', '-XX:hashCode=2');
  }
  // Pass locale via _JAVA_OPTIONS so all the forked processes inherit it
  jvmArgs.push(`-Duser.country=${v.locale.country}`);
  jvmArgs.push(`-Duser.language=${v.locale.language}`);
  if (v.jit === 'hotspot' && Math.random() > 0.5) {
    // The following options randomize instruction selection in JIT compiler
    // so it might reveal missing synchronization in TestNG code
    v.name += ', stress JIT';
    jvmArgs.push('-XX:+UnlockDiagnosticVMOptions');
    if (v.java_version >= 8) {
      // Randomize instruction scheduling in GCM
      // share/opto/c2_globals.hpp
      jvmArgs.push('-XX:+StressGCM');
      // Randomize instruction scheduling in LCM
      // share/opto/c2_globals.hpp
      jvmArgs.push('-XX:+StressLCM');
    }
    if (v.java_version >= 16) {
      // Randomize worklist traversal in IGVN
      // share/opto/c2_globals.hpp
      jvmArgs.push('-XX:+StressIGVN');
    }
    if (v.java_version >= 17) {
      // Randomize worklist traversal in CCP
      // share/opto/c2_globals.hpp
      jvmArgs.push('-XX:+StressCCP');
    }
  }
  v.testExtraJvmArgs = jvmArgs.join(' ');
  delete v.hash;
});

console.log(include);

let filePath = process.env['GITHUB_OUTPUT'] || '';
if (filePath) {
    fs.appendFileSync(filePath, `matrix<<MATRIX_BODY${os.EOL}${JSON.stringify({include})}${os.EOL}MATRIX_BODY${os.EOL}`, {
        encoding: 'utf8'
    });
}
