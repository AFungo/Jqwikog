package net.jqwik.engine.properties;

import java.util.function.*;

public class Range<T extends Comparable<T>> {

	public static <T extends Comparable<T>> Range<T> of(T min, T max) {
		return Range.of(min, true, max, true);
	}

	public static <T extends Comparable<T>> Range<T> of(T min, boolean minIncluded, T max, boolean maxIncluded) {
		if (min.compareTo(max) > 0)
			throw new IllegalArgumentException(String.format("Min value [%s] must not be greater that max value [%s].", min, max));
		if (min.compareTo(max) == 0 && (!minIncluded || !maxIncluded))
			throw new IllegalArgumentException(
				String.format("If min value [%s] is equal to max value [%s] borders must be included.", min, max)
			);
		return new Range<>(min, minIncluded, max, maxIncluded);
	}

	public final T min;
	public final boolean minIncluded;
	public final T max;
	public final boolean maxIncluded;

	private Range(T min, boolean minIncluded, T max, boolean maxIncluded) {
		this.min = min;
		this.minIncluded = minIncluded;
		this.max = max;
		this.maxIncluded = maxIncluded;
	}

	public boolean isSingular() {
		return min.compareTo(max) == 0 && minIncluded && maxIncluded;
	}

	public boolean includes(T value) {
		int minCompare = value.compareTo(min);
		int maxCompare = value.compareTo(max);
		if (minCompare < 0 || maxCompare > 0) {
			return false;
		}
		if (!minIncluded && minCompare == 0) {
			return false;
		}
		return maxIncluded || maxCompare != 0;
	}

	public void ifIncluded(T value, Consumer<T> consumer) {
		if (includes(value)) {
			consumer.accept(value);
		}
	}

	public <U extends Comparable<U>> Range<U> map(Function<T, U> mapper) {
		return Range.of(mapper.apply(min), minIncluded, mapper.apply(max), maxIncluded);
	}

	public Range<T> withMin(T newMin, boolean newMinIncluded) {
		return Range.of(newMin, newMinIncluded, max, maxIncluded);
	}

	public Range<T> withMax(T newMax, boolean newMaxIncluded) {
		return Range.of(min, minIncluded, newMax, newMaxIncluded);
	}

	@Override
	public String toString() {
		char leftBracket = minIncluded ? '[' : ']';
		char rightBracket = maxIncluded ? ']' : '[';
		return String.format("%s%s..%s%s", leftBracket, min, max, rightBracket);
	}
}
