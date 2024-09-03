package net.jqwik.engine.properties.arbitraries;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;

import net.jqwik.engine.*;
import net.jqwik.engine.properties.arbitraries.randomized.*;

import java.util.*;
import java.util.function.*;

public class DefaultRandoopArbitrary<T> extends TypedCloneable implements RandoopArbitrary<T>{

	Class<T> clazz;
	List<Class<?>> parameterizedClasses;

	Set<Integer> intLiterals = new HashSet<>();

	Set<Class<?>> dependencies = new HashSet<>();

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
		return RandomGenerators.randoop(clazz, parameterizedClasses, intLiterals, dependencies);
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
	public RandoopArbitrary<T> setDependencies(Set<Class<?>> dependencies) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.dependencies.addAll(dependencies);
		return clone;
	}

	Function<Object, Boolean> assume;
	@Override
	public RandoopArbitrary<T> setAssume(Function<Object, Boolean> function) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.assume = function;
		return clone;
	}
}
