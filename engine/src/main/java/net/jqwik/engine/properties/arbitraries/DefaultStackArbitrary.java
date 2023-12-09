package net.jqwik.engine.properties.arbitraries;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;

import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.arbitraries.exhaustive.*;
import net.jqwik.engine.properties.shrinking.*;

import org.jspecify.annotations.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class DefaultStackArbitrary<T> extends MultivalueArbitraryBase<T, Stack<T>> implements StackArbitrary<T>{

	public DefaultStackArbitrary(Arbitrary<T> elementArbitrary){
		super(elementArbitrary);
	}

	@Override
	protected Iterable<T> toIterable(Stack<T> streamable) {
		return streamable;
	}

	@Override
	public RandomGenerator<Stack<T>> generator(int genSize) {
		return createStackGenerator(genSize, false);
	}

	@Override
	public RandomGenerator<Stack<T>> generatorWithEmbeddedEdgeCases(int genSize) {
		return createStackGenerator(genSize, true);
	}

	@Override
	public Optional<ExhaustiveGenerator<Stack<T>>> exhaustive(long maxNumberOfSamples) {
		return Optional.empty();
		//TODO: VER QUE HACE;
		// return ExhaustiveGenerators.stack(elementArbitrary, minSize, maxSize(), uniquenessExtractors, maxNumberOfSamples);
	}

	@Override
	public EdgeCases<Stack<T>> edgeCases(int maxEdgeCases) {
		return edgeCases((elements, minSize1) -> new ShrinkableStack<>(elements, minSize1, maxSize(), uniquenessExtractors, elementArbitrary), maxEdgeCases);
	}

	@Override
	public StackArbitrary<T> ofMaxSize(int maxSize) {
		return (StackArbitrary<T>) super.ofMaxSize(maxSize);
	}

	@Override
	public StackArbitrary<T> ofMinSize(int minSize) {
		return (StackArbitrary<T>) super.ofMinSize(minSize);
	}

	@Override
	public StackArbitrary<T> withSizeDistribution(RandomDistribution distribution) {
		return (StackArbitrary<T>) super.withSizeDistribution(distribution);
	}

	// // TODO: Remove duplication with DefaultSetArbitrary.mapEach()
	// @Override
	// public <U> Arbitrary<List<U>> mapEach(BiFunction<List<T>, T, U> mapper) {
	// 	return this.map(elements -> elements.stream()
	// 										.map(e -> mapper.apply(elements, e))
	// 										.collect(Collectors.toList()));
	// }
	//
	// // TODO: Remove duplication with DefaultSetArbitrary.flatMapEach()
	// @Override
	// public <U> Arbitrary<List<U>> flatMapEach(BiFunction<List<T>, T, Arbitrary<U>> flatMapper) {
	// 	return this.flatMap(elements -> {
	// 		List<Arbitrary<U>> arbitraries =
	// 			elements.stream()
	// 					.map(e -> flatMapper.apply(elements, e))
	// 					.collect(Collectors.toList());
	// 		return Combinators.combine(arbitraries).as(ArrayList::new);
	// 	});
	// }

	@Override
	public StackArbitrary<@Nullable T> uniqueElements(Function<@Nullable T, Object> by) {
		FeatureExtractor<T> featureExtractor = by::apply;
		return (StackArbitrary<T>) super.uniqueElements(featureExtractor);
	}

	@Override
	public StackArbitrary<T> uniqueElements() {
		return (StackArbitrary<T>) super.uniqueElements();
	}
}
