package experiments.randoopTest.junitquickcheck.dummy;


import junitquickcheck.dummy.*;

import net.jqwik.api.*;

import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;

import java.util.*;

public class AGeneratorTest {
    @Property
	public void listAreCorrectlyGenerated(@ForAll @Deps(classes = {B.class, ArrayList.class}) A a) {
		Assume.that(a != null);
        a.getListOfB().forEach(b ->
								   Assertions.assertThat(b).isInstanceOf(B.class));
    }
}
