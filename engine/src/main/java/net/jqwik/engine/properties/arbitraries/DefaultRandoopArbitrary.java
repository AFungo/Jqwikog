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

	Set<Integer> intLiterals = new HashSet<>();

	Set<Class<?>> necessaryClasses = new HashSet<>();

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
		return RandomGenerators.randoop(clazz, parameterizedClasses, intLiterals, necessaryClasses);
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
		return new LinkedList<>();
	}

	@Override
	public RandoopArbitrary<T> all() {
		return new DefaultRandoopArbitrary<>(this.clazz);
	}

	@Override
	public RandoopArbitrary<T> setIntegersLiterals(int min, int max) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.intLiterals = Arbitraries.integers().between(min, max).
									   set().sample();
		return clone;
	}

	@Override
	public RandoopArbitrary<T> setNecessaryClasses(Set<Class<?>> classes) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.necessaryClasses.addAll(classes);
		return clone;
	}
}
