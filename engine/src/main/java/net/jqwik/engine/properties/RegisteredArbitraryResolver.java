package net.jqwik.engine.properties;

import java.util.*;

import net.jqwik.api.*;
import net.jqwik.api.providers.*;
import net.jqwik.api.providers.ArbitraryProvider.*;
import net.jqwik.engine.properties.arbitraries.*;

public class RegisteredArbitraryResolver {

	private final List<ArbitraryProvider> registeredProviders = new ArrayList<>();

	public RegisteredArbitraryResolver(Collection<ArbitraryProvider> registeredProviders) {
		this.registeredProviders.addAll(registeredProviders);
		this.registeredProviders.addAll(DefaultArbitraries.getDefaultProviders());
	}

	public Set<Arbitrary<?>> resolve(TypeUsage targetType, SubtypeProvider subtypeProvider) {
		int currentPriority = Integer.MIN_VALUE;
		Set<Arbitrary<?>> fittingArbitraries = new LinkedHashSet<>();
		for (ArbitraryProvider provider : registeredProviders) {
			if (provider.canProvideFor(targetType)) {
				if (provider.priority() < currentPriority) {
					continue;
				}
				if (provider.priority() > currentPriority) {
					fittingArbitraries.clear();
					currentPriority = provider.priority();
				}
				Set<Arbitrary<?>> arbitraries = provider.provideFor(targetType, subtypeProvider);
				fittingArbitraries.addAll(arbitraries);
			}
		}
		if (fittingArbitraries.isEmpty()) {
			//TODO: aca se usa randoop cuando no encuentra ningun otro generador es la ultima opcion, por ahora ;)
			Class<?> clazz = targetType.getRawType();//parameter.getRawParameter().getType();
			fittingArbitraries.add(new DefaultRandoopArbitrary<>((Class<?>) clazz));
			System.out.println("We going to use randoop :)");
		}
		return fittingArbitraries;
	}

}
