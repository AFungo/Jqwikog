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
}
