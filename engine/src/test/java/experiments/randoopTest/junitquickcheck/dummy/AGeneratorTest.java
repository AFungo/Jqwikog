package experiments.randoopTest.junitquickcheck.dummy;


import junitquickcheck.dummy.*;

import junitquickcheck.geom.*;

import net.jqwik.api.*;

import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;

import java.util.*;

public class AGeneratorTest {

	public static boolean listOfBNotNull(Object o){
		if(o == null)
			return false;
		A a = (A) o;
		return a.getListOfB() != null;
	}

    @Property
	public void listAreCorrectlyGenerated(@ForAll @AssumeMethod(className = AGeneratorTest.class, methodName = "listOfBNotNull") @Deps(classes = {B.class, ArrayList.class}) A a) {
		Assume.that(a != null);
        a.getListOfB().forEach(b ->
								   Assertions.assertThat(b).isInstanceOf(B.class));
    }
}
