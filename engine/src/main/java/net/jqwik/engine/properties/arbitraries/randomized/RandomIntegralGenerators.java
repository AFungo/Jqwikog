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

	public static <T> RandomGenerator<T> randoop(Class<T> clazz, int genSize, RandomDistribution distribution){
		RandomNumericGenerator numericGenerator =
			distribution.createGenerator(genSize, BigInteger.valueOf(1), BigInteger.valueOf(1000), BigInteger.valueOf(500));//TODO: here center doesnt cares, because we use normaldistribution
		RandoopObjectGenerator rog = new RandoopObjectGenerator(clazz);
		// rog.setSeed(seed);
		rog.addFlag(new LiteralsFileFlag("/home/augusto/Documents/tesis/randoopObjectGenerator/literals/lits.txt"));
		List<Object> obj = new LinkedList<>();// = rog.generateObjects(genSize);
		// for (Object o: obj) {
		// 	System.out.println(o);
		// }
		return random -> {
			if(obj.isEmpty()){
				int seed = numericGenerator.next(random).intValue();//(int)(random()*1000);
				rog.setSeed(seed);
				/*
				* TODO: aca tengo un problema, ya que genero el tamaño de la muestra pero no siempre es necesario,
				 * ya que a veces si es randoop esta generando objetos para una parametizacion
				 * por ejemplo List<Date> (randoop genera para Date) no es necesario el tamaño total de la muestra,
				 * necesitaria el tamaño total si generara para List, pero no se cuantas muestras son las necesarias...
				 * */
				obj.addAll(rog.generateObjects(genSize));
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
