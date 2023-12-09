package net.jqwik.engine.providers;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.api.providers.*;
import net.jqwik.engine.properties.arbitraries.*;

import java.util.*;

public class RandoopArbitraryProvider<T> implements ArbitraryProvider {

	@Override
	public boolean canProvideFor(TypeUsage targetType) {
		return false;//TODO: i put false because we only want use randoop when we don't have any option  always randoop can provide :)
	}

	@Override
	public Set<Arbitrary<?>> provideFor(TypeUsage targetType, SubtypeProvider subtypeProvider) {
		return Collections.singleton(new DefaultRandoopArbitrary<>(targetType.getRawType()));
	}
}
