[![Maven Central](https://maven-badges.herokuapp.com/maven-central/net.jqwik/jqwik/badge.svg)](https://maven-badges.herokuapp.com/maven-central/net.jqwik/jqwik)
[![Javadocs](http://javadoc.io/badge/net.jqwik/jqwik-api.svg)](https://jqwik.net/docs/current/javadoc/index.html)
[![CI Status](https://github.com/jqwik-team/jqwik/workflows/CI/badge.svg?branch=main)](https://github.com/jqwik-team/jqwik/actions)

# jqwik

An alternative 
[test engine for the JUnit 5 platform](https://junit.org/junit5/docs/current/user-guide/#launcher-api-engines-custom)
that focuses on Property-Based Testing.


## See the [jqwik website](http://jqwik.net) for further details and documentation.

## Quick Start
JAVA version = openjdk 11.0.22 2024-01-16
             = openjdk version "1.8.0_392"

Build = ./gradlew build -x engine:test -x kotlin:test
Run randoop class test = ./gradlew :engine:test --tests examples.randoopTest.RandoopTest
Run a single randoop test = ./gradlew :engine:test --tests examples.randoopTest.RandoopTest.testName

./gradlew build -x engine:test -x kotlin:test -x time:test -x web:test -x api:javadoc
./gradlew :engine:compileTestJava
docker build -t myproject .
docker run -it myproject

docker run -it -v ~/fungo/JqwikWR/:/app/experimentos.txt myproject bash -c "./gradlew build -x engine:test -x kotlin:test -x time:test -x web:test -x api:javadoc && ./gradlew engine:compileTestJava && python3 script_config.py > /app/experimentos.txt"

docker run -it -v ~/fungo/JqwikWR/experimentos.txt:/app/experimentos.txt myproject bash -c "./gradlew build -x engine:test -x kotlin:test -x time:test -x web:test -x api:javadoc && ./gradlew engine:compileTestJava && python3 script_config.py > /app/experimentos.txt"

scp investigador@192.168.0.144:~/nolasco/alloy-mr-reducer/output/SingleNode/allow_epa_loops_true/EPA_AWARE/1000/0/mrs.txt ~/Documents/tesis/EPA-Nolasco