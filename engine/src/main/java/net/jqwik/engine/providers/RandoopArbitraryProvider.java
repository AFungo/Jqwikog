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
		return true;//NOTE: i put false because we only want use randoop when we don't have any option  always randoop can provide :)
	}

	@Override
	public Set<Arbitrary<?>> provideFor(TypeUsage targetType, SubtypeProvider subtypeProvider) {
		//NOTE: HERE we can parameterize randoop
		List<TypeUsage> types = targetType.getTypeArguments();
		List<Class<?>> clazzes = types.stream().map(TypeUsage::getRawType).collect(Collectors.toList());
		// Set<List<Arbitrary<?>>> t = subtypeProvider.resolveAndCombine(types.toArray(new TypeUsage[0])).collect(CollectorsSupport.toLinkedHashSet());
				   // .map(arbitraries -> {
					//    Arbitrary<?> keyArbitrary = arbitraries.get(0);
					//    Arbitrary<?> valueArbitrary = arbitraries.get(1);
					//    return Arbitraries.maps(keyArbitrary, valueArbitrary);
				   // })
				   // .collect(CollectorsSupport.toLinkedHashSet());
	// }
		//FIXME: If we see another implementations there first call the api and the api calls the constructor. Maybe we need to do it like that
		//NOTE: We can add the list of types there,
		if(clazzes.isEmpty())
			return Collections.singleton(new DefaultRandoopArbitrary<>(targetType.getRawType()));
		else
			return Collections.singleton(new DefaultRandoopArbitrary<>(targetType.getRawType(), clazzes));
	}

	@Override
	public int priority() {
		return -1;
	}
}
