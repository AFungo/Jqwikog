package net.jqwik.api.arbitraries;

import net.jqwik.api.*;


import java.util.*;

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

	RandoopArbitrary<T> setDependencies(Set<Class<?>> dependencies);
}
