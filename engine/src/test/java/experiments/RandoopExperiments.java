package experiments;

import examples.datastructure.list.*;
import examples.datastructure.ncl.*;

import examples.datastructure.pila.*;
import examples.datastructure.set.*;

import examples.datastructure.set.BitSet;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

// import org.junit.jupiter.api.*;
import net.jqwik.api.randoop.*;

import org.assertj.core.api.Assertions;

import java.util.*;

public class RandoopExperiments {

	@Property(tries = 100)
	void pilasTest(
		@ForAll @IntRange(min = 110,max = 120) PilaSobreListasEnlazadas stack) {
		System.out.println(stack);
		Assume.that(stack.length() > 4);
		int previousSize = stack.length();
		stack.pop();
		Assertions.assertThat(stack.length()).isEqualTo(previousSize-1);
	}


	@Property(tries = 100)
	void pilasTupleTest(
		@ForAll @Deps(classes = {PilaSobreListasEnlazadas.class}) @IntRange(min = 110,max = 120) PilasTuple tuple) {
		System.out.println(tuple);
	}

	@Property(tries = 100)
	public void nclTest(@ForAll NodeCachingLinkedList ncl,
						@ForAll @IntRange(min=1, max=4) Integer indexToRemove){
		Assume.that(ncl.size() > indexToRemove);
		Assume.that(!ncl.cacheIsFull());
		int beforeSize = ncl.cacheSize();
		ncl.removeIndex(indexToRemove);
		int afterSize = ncl.cacheSize();
		Assertions.assertThat(afterSize-1).isEqualTo(beforeSize);
	}

	@Property(tries=100)
	public void flipTest(@ForAll BitSet bitSet,
						 @ForAll @IntRange(min=1, max=10) int index){
		Assume.that(index < bitSet.length());
		boolean value = bitSet.get(index);
		bitSet.flip(index);

		Assertions.assertThat(!value).isEqualTo(bitSet.get(index));
	}

	@Property(tries = 100)
	public void sortedListTest(@ForAll @IntRange(min=20, max=30) SimpleList list){
		Assume.that(list.size() > 2);
		Assume.that(list.isSorted());
		System.out.println(list);
		for (int i = 0; i < list.size()-1; i++) {
			Assertions.assertThat(list.get(i)).isLessThanOrEqualTo(list.get(i+1));
		}
	}
}