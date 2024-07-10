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

public class DefaultRandoopArbitrary<T> extends TypedCloneable implements RandoopArbitrary<T>{

	Class<T> clazz;
	List<Class<?>> parameterizedClasses;
	Integer minIntegerRange;
	Integer maxIntegerRange;

	public DefaultRandoopArbitrary(Class<T> clazz){
		this.clazz = clazz;
		this.parameterizedClasses = new ArrayList<>();
	}
	public DefaultRandoopArbitrary(Class<T> clazz, List<Class<?>> parameterizedClasses){
		this.clazz = clazz;
		this.parameterizedClasses = parameterizedClasses;
	}

	@Override
	public RandomGenerator<T> generator(int genSize) {
		return RandomGenerators.randoop(clazz, parameterizedClasses, minIntegerRange, maxIntegerRange);
	}

	@Override
	public Optional<ExhaustiveGenerator<T>> exhaustive(long maxNumberOfSamples) {
		return Optional.empty();
	}

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

	@Override
	public RandoopArbitrary<T> greaterOrEqual(int min) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.minIntegerRange = min;
		return clone;
	}

	@Override
	public RandoopArbitrary<T> lessOrEqual(int max) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.maxIntegerRange = max;
		return clone;
	}
}
