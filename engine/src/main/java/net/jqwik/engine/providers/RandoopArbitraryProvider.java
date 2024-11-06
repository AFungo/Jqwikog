package net.jqwik.engine.providers;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.api.providers.*;
import net.jqwik.api.support.*;
import net.jqwik.engine.properties.arbitraries.*;

import java.util.*;
import java.util.stream.*;

public class RandoopArbitraryProvider<T> implements ArbitraryProvider {

	@Override
	public boolean canProvideFor(TypeUsage targetType) {
		return true;
	}

	@Override
	public Set<Arbitrary<?>> provideFor(TypeUsage targetType, SubtypeProvider subtypeProvider) {
		List<TypeUsage> types = targetType.getTypeArguments();
		List<Class<?>> clazzes = types.stream().map(TypeUsage::getRawType).collect(Collectors.toList());
		if(clazzes.isEmpty())
			return Collections.singleton(new DefaultRandoopArbitrary<>(targetType.getRawType()));
		else
			return Collections.singleton(new DefaultRandoopArbitrary<>(targetType.getRawType(), clazzes));
	}

	private Integer priority = -1;

	@Override
	public int priority() {
		return priority;
	}
}
