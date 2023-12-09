package net.jqwik.engine.providers;

import net.jqwik.api.*;

import java.util.*;

public class StackArbitraryProvider extends AbstractCollectionArbitraryProvider{

	@Override
	protected Class<?> getProvidedType() {
		return Stack.class;
	}

	@Override
	protected Arbitrary<?> create(Arbitrary<?> innerArbitrary) {
		return innerArbitrary.stack();
	}
}
