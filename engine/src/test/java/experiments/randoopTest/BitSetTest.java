package experiments.randoopTest;

import examples.datastructure.set.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.assertj.core.api.*;

public class BitSetTest {

	@Property(tries = 100)
	public void flipTest(@ForAll BitSet bitSet,
						 @ForAll @IntRange(min=1, max=10) int index){
		Assume.that(index < bitSet.length());
		boolean value = bitSet.get(index);
		bitSet.flip(index);

		Assertions.assertThat(!value).isEqualTo(bitSet.get(index));
	}

}
