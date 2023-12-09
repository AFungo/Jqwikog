package net.jqwik.engine.properties.shrinking;

import net.jqwik.api.*;
import net.jqwik.engine.properties.*;

import java.util.*;

public class ShrinkableStack<E> extends ShrinkableContainer<Stack<E>, E>{

	ShrinkableStack(Stack<Shrinkable<E>> elements, int minSize, int maxSize) {
		this(elements, minSize, maxSize, Collections.emptySet(), null);
	}

	public ShrinkableStack(
		List<Shrinkable<E>> elements,
		int minSize, int maxSize,
		Collection<FeatureExtractor<E>> uniquenessExtractors,
		Arbitrary<E> elementArbitrary
	) {
		super(elements, minSize, maxSize, uniquenessExtractors, elementArbitrary);
	}

	/**
	 * Here is the magic, here return your "Random" stack for test
	 * @param shrinkables
	 * @return
	 */
	@Override
	Stack<E> createValue(List<Shrinkable<E>> shrinkables) {
		// Using loop instead of stream to make stack traces more readable
		Stack<E> values = new Stack<>();
		for (Shrinkable<E> shrinkable : shrinkables) {
			values.push(shrinkable.value());
		}
		return values;
	}

	@Override
	Shrinkable<Stack<E>> createShrinkable(List<Shrinkable<E>> shrunkElements) {
		Stack<Shrinkable<E>> values = new Stack<>();
		for (Shrinkable<E> shrinkable : shrunkElements) {
			values.push(shrinkable);
		}
		return new ShrinkableStack<>(values, minSize, maxSize, uniquenessExtractors, elementArbitrary);
	}
}
