package net.jqwik.api.arbitraries;

import net.jqwik.api.*;


import java.util.*;
import java.util.function.*;

public interface RandoopArbitrary<T> extends Arbitrary<T>{
	/**
	 * Allow all unicode chars to show up in generated values.
	 *
	 * <p>
	 * Resets previous settings.
	 * </p>
	 *
	 * @return new instance of arbitrary
	 */
	RandoopArbitrary<T> all();

	RandoopArbitrary<T> setIntegersLiterals(int min, int max);

	RandoopArbitrary<T> setStringsLiterals(Set<String> strings);

	RandoopArbitrary<T> setDependencies(Set<Class<?>> dependencies);

	RandoopArbitrary<T> setAssume(Function<Object, Boolean> function);

	RandoopArbitrary<T> setMethodsToUse(String[] methods);
}
