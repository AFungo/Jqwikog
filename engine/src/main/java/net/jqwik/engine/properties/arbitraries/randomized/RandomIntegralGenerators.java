package net.jqwik.engine.properties.arbitraries.randomized;

import java.math.*;
import java.util.*;

import net.jqwik.api.*;
import net.jqwik.api.RandomDistribution.*;
import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.shrinking.*;

import randoop.main.*;
import randoop.main.randoopflags.*;

import static java.lang.Math.*;

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

	public static <T> RandomGenerator<T> randoop(Class<T> clazz, List<Class<?>> parameterizedClasses, int genSize, RandomDistribution distribution){
		RandomNumericGenerator numericGenerator =
			distribution.createGenerator(genSize, BigInteger.valueOf(1), BigInteger.valueOf(1000), BigInteger.valueOf(500));//TODO: here center doesnt cares, because we use normaldistribution

		RandoopObjectGenerator rog = parameterizedClasses.isEmpty()?
										 new RandoopObjectGenerator(clazz): new RandoopObjectGenerator(clazz, parameterizedClasses);
		//TODO: Ver el tema del random para generar (recordar que jqkwik va cambiando el random)
		//ademas sospecho que al ponerlo asi siempre me va a dar los mismos resultados
		rog.setSeed(100);//numericGenerator.next(100).intValue());
		return random -> Shrinkable.unshrinkable(((T) rog.generateOneObject()));
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
