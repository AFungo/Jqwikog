package net.jqwik.api.arbitraries;

import net.jqwik.api.*;

import org.apiguardian.api.*;

import java.util.*;
import java.util.function.*;

import static org.apiguardian.api.API.Status.*;

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

	/**
	 * Set the allowed lower {@code min} (included) and upper {@code max} (included) bounder of generated numbers.
	 */
	default RandoopArbitrary<T> between(int min, int max) {
		return greaterOrEqual(min).lessOrEqual(max);
	}

	/**
	 * Set the allowed lower {@code min} (included) bounder of generated numbers.
	 */
	RandoopArbitrary<T> greaterOrEqual(int min);

	/**
	 * Set the allowed upper {@code max} (included) bounder of generated numbers.
	 */
	RandoopArbitrary<T> lessOrEqual(int max);
}
