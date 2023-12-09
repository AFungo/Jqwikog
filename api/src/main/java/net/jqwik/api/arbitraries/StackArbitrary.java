package net.jqwik.api.arbitraries;

import net.jqwik.api.*;

import org.apiguardian.api.*;

import java.util.*;
import java.util.function.*;

import static org.apiguardian.api.API.Status.*;

public interface StackArbitrary<T> extends Arbitrary<Stack<T>>, StreamableArbitrary<T, Stack<T>>{
	/**
	 * Fix the size to {@code size}.
	 *
	 * @param size The size of the generated set
	 * @return new arbitrary instance
	 */
	@Override
	default StackArbitrary ofSize(int size) {
		return this.ofMinSize(size).ofMaxSize(size);
	}

	/**
	 * Set lower size boundary {@code minSize} (included).
	 *
	 * @param minSize The minimum size of the generated set
	 * @return new arbitrary instance
	 */
	StackArbitrary ofMinSize(int minSize);

	/**
	 * Set upper size boundary {@code maxSize} (included).
	 *
	 * @param maxSize The maximum size of the generated set
	 * @return new arbitrary instance
	 */
	StackArbitrary ofMaxSize(int maxSize);
}
