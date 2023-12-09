package net.jqwik.engine.properties.arbitraries;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;

import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.arbitraries.exhaustive.*;
import net.jqwik.engine.properties.arbitraries.randomized.*;
import net.jqwik.engine.properties.shrinking.*;

import org.jspecify.annotations.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class DefaultRandoopArbitrary<T> implements RandoopArbitrary<T>{
	Class<T> clazz;
	public DefaultRandoopArbitrary(Class<T> clazz){this.clazz = clazz;}

	@Override
	public RandomGenerator<T> generator(int genSize) { return RandomGenerators.randoop(clazz);}

	@Override
	public Optional<ExhaustiveGenerator<T>> exhaustive(long maxNumberOfSamples) {
		return Optional.empty();
		//TODO: VER QUE HACE;
		// return ExhaustiveGenerators.stack(elementArbitrary, minSize, maxSize(), uniquenessExtractors, maxNumberOfSamples);
	}

	private Arbitrary<T> arbitrary() {
		// if (partsWithSize.isEmpty()) {
		return defaultArbitrary();
		// }
		// if (partsWithSize.size() == 1) {
		// 	return partsWithSize.get(0).get2();
		// }
	}

	private Arbitrary<T> defaultArbitrary() {
		return new DefaultRandoopArbitrary<T>(this.clazz);
	}

	//TODO: no se que hace esto
	@Override
	public EdgeCases<T> edgeCases(int maxEdgeCases) {
		return EdgeCasesSupport.fromShrinkables(listOfEdgeCases());
	}

	private List<Shrinkable<T>> listOfEdgeCases() {
		return new LinkedList<Shrinkable<T>>();
	}


	@Override
	public RandoopArbitrary<T> all() {
		return new DefaultRandoopArbitrary<T>(this.clazz);
	}
}
