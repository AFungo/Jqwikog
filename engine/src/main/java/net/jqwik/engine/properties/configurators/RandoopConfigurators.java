package net.jqwik.engine.properties.configurators;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.api.configurators.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.randoop.*;
import net.jqwik.api.randoop.AssumeMethod;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

public class RandoopConfigurators extends ArbitraryConfiguratorBase {

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, IntRange range) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			return randoopArbitrary.setIntegersLiterals(range.min(), range.max());
					   //greaterOrEqual(range.min()).lessOrEqual(range.max()!=Integer.MAX_VALUE ? range.max() : 10);
		}
		return arbitrary;
	}

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, Deps dependencies) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			return randoopArbitrary.setDependencies(new HashSet<>(Arrays.asList(dependencies.classes())));
		}
		return arbitrary;
	}

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, AssumeMethod assumeMethod) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			Function<Object, Boolean> function = searchFunction(assumeMethod.className(), assumeMethod.methodName());
			if(function != null) {
				return randoopArbitrary.setAssume(function);
			}
		}
		return arbitrary;
	}

	public static Function<Object, Boolean> searchFunction(Class<?> clazz, String functionName) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().equals(functionName) && method.getReturnType() == boolean.class) {
				try {
					// Assuming the function is static, otherwise you'll need an instance of clazz
					return (Object o) -> {
						try {
							return (Boolean) method.invoke(null, o);
						} catch (IllegalAccessException e) {
							throw new RuntimeException(e);
						} catch (InvocationTargetException e) {
							throw new RuntimeException(e);
						}
					};
				} catch (Exception e) {
					throw new IllegalArgumentException("No encontre la funcion");
				}
			}
		}
		return null;
	}
}
