package net.jqwik.engine.properties.arbitraries;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;

import net.jqwik.engine.*;
import net.jqwik.engine.properties.arbitraries.randomized.*;

import randoop.main.*;

import java.util.*;
import java.util.function.*;

public class DefaultRandoopArbitrary<T> extends TypedCloneable implements RandoopArbitrary<T>{

	Class<T> clazz;
	List<Class<?>> parameterizedClasses;

	Set<Integer> intLiterals = new HashSet<>();

	Set<Class<?>> dependencies = new HashSet<>();

	Function<Object, Boolean> assume;

	List<String> methods = new ArrayList<>();

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
		Random r = SourceOfRandomness.current();
		int seed = r.nextInt();
		RandoopObjectGenerator rog = parameterizedClasses.isEmpty()?
										 new RandoopObjectGenerator(clazz, seed): new RandoopObjectGenerator(clazz, parameterizedClasses, seed);

		if(!intLiterals.isEmpty()){
			rog.setCustomIntegers(intLiterals);
		}

		if(!dependencies.isEmpty()){
			rog.setNecessaryClasses(dependencies);
		}

		if(assume != null){
			rog.setAssume(assume);
		}

		if(!methods.isEmpty()){
			rog.setMethodsToUse(methods);
		}

		return RandomGenerators.randoop(rog);
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

	@Override
	public RandoopArbitrary<T> setAssume(Function<Object, Boolean> function) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.assume = function;
		return clone;
	}

	@Override
	public RandoopArbitrary<T> setMethodsToUse(String[] methods) {
		DefaultRandoopArbitrary<T> clone = typedClone();
		clone.methods.addAll(Arrays.asList(methods));
		return clone;
	}
}
