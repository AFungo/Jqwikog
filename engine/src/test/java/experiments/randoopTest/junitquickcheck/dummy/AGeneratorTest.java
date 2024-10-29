package experiments.junitquickcheck.dummy;


import junitquickcheck.dummy.*;

import net.jqwik.api.*;

import org.assertj.core.api.*;

public class AGeneratorTest {
    @Property
	public void listAreCorrectlyGenerated(A a) {
        a.getListOfB().forEach(b ->
								   Assertions.assertThat(b).isInstanceOf(B.class));
    }
}
