package experiments;

import examples.datastructure.pila.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.assertj.core.api.*;

public class PilasTest {

	@Property(tries = 100)
	void pilasTest(
		@ForAll @IntRange(min = 110,max = 120) PilaSobreListasEnlazadas stack) {
		System.out.println(stack);
		Assume.that(stack.length() > 4);
		int previousSize = stack.length();
		stack.pop();
		Assertions.assertThat(stack.length()).isEqualTo(previousSize-1);
	}

}
