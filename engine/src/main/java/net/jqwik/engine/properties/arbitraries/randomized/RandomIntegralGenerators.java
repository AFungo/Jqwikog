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

	public static <T> RandomGenerator<T> randoop(Class<T> clazz){
		int seed = 0;//(int)(random()*1000);
		RandoopObjectGenerator rog = new RandoopObjectGenerator(clazz);
		rog.setSeed(seed);
		rog.addFlag(new LiteralsFileFlag("/home/augusto/Documents/tesis/randoopObjectGenerator/literals/lits.txt"));
		List<Object> obj = rog.generateObjects(20);
		// for (Object o: obj) {
		// 	System.out.println(o);
		// }
		return random -> {
			if(obj.isEmpty()){
				rog.setSeed(seed+1);
				obj.addAll(rog.generateObjects(20));
				// obj = rog.generateObjects(20);
			}
			return Shrinkable.unshrinkable((T) obj.remove(0));
		};
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
