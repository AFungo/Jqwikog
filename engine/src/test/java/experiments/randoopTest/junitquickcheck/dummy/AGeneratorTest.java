package experiments.randoopTest.junitquickcheck.dummy;


import junitquickcheck.dummy.*;

import net.jqwik.api.*;

import org.assertj.core.api.*;

public class AGeneratorTest {
    @Property(tries = 10)
	public void listAreCorrectlyGenerated(@ForAll A a) {
        a.getListOfB().forEach(b ->
								   Assertions.assertThat(b).isInstanceOf(B.class));
    }
}
