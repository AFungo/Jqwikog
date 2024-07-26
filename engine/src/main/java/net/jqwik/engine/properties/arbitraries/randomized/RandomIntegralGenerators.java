package net.jqwik.engine.properties.arbitraries.randomized;

import java.math.*;
import java.util.*;

import net.jqwik.api.*;
import net.jqwik.api.RandomDistribution.*;
import net.jqwik.engine.*;
import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.shrinking.*;
import randoop.main.*;

public class RandomIntegralGenerators {

	public static RandomGenerator<BigInteger> bigIntegers(
		int genSize,
		BigInteger min,
		BigInteger max,
		BigInteger shrinkingTarget,
		RandomDistribution distribution
	) {
		Range<BigInteger> range = Range.of(min, max);

		checkTargetInRange(range, shrinkingTarget);

		if (range.isSingular()) {
			return ignored -> Shrinkable.unshrinkable(range.min);
		}

		RandomNumericGenerator numericGenerator =
			distribution.createGenerator(genSize, range.min, range.max, shrinkingTarget);

		return random -> {
			BigInteger value = numericGenerator.next(random);
			return new ShrinkableBigInteger(
				value,
				range,
				shrinkingTarget
			);
		};
	}

	public static <T> RandomGenerator<T> randoop(Class<T> clazz,
												 List<Class<?>> parameterizedClasses,
												 Set<Integer> integersLiterals,
												 Set<Class<?>> necessaryClasses){
		Random r = SourceOfRandomness.current();
		int seed = r.nextInt();
		RandoopObjectGenerator rog = parameterizedClasses.isEmpty()?
										 new RandoopObjectGenerator(clazz, seed): new RandoopObjectGenerator(clazz, parameterizedClasses, seed);

		if(!integersLiterals.isEmpty()){
			rog.setCustomIntegers(integersLiterals);
		}

		if(!necessaryClasses.isEmpty()){
			rog.setNecessaryClasses(necessaryClasses);
		}
		return random -> Shrinkable.unshrinkable(((T) rog.generate()));
	}
	private static void checkTargetInRange(Range<BigInteger> range, BigInteger value) {
		if (!range.includes(value)) {
			String message = String.format("Shrinking target <%s> is outside allowed range %s", value, range);
			throw new JqwikException(message);
		}
	}

	public static BigInteger defaultShrinkingTarget(Range<BigInteger> range) {
		if (range.includes(BigInteger.ZERO)) {
			return BigInteger.ZERO;
		}
		if (range.max.compareTo(BigInteger.ZERO) < 0) return range.max;
		if (range.min.compareTo(BigInteger.ZERO) > 0) return range.min;
		throw new RuntimeException("This should not be possible");
	}
}
