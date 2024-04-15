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
		// rog.setSeed(seed);
		// rog.addFlag(new LiteralsFileFlag("/home/augusto/Documents/tesis/randoopObjectGenerator/literals/lits.txt"));
		List<Object> obj = new LinkedList<>();// = rog.generateObjects(genSize);
		// for (Object o: obj) {
		// 	System.out.println(o);
		// }
		return random -> {
			if(obj.isEmpty()){
				int seed = numericGenerator.next(random).intValue();//(int)(random()*1000);
				rog.setSeed(seed);
				/**
				 * Note: Ahora lo entiendo de otra manera, si comparo con el generador de biginteger (que es el generador de objetos por defecto
				 * de jqkiq es decir todos los objetos no parameterizados que puede generar jqkiw nacen de big intger) la demanda de cuantos
				 * objetos necesito esta en otro lado, aca deberia elegir un numero a conveniencia para decirle a randoop che generame estos
				 * y en caso de necesitar mas me van a seguir pidendo hasta suplir la demanda
				 * deberia ser un numero estrategicamente elegido para que no sea lento el proceso de generacion, ni muy chico ni demasiado
				 * grande para no generar elemntos porque si
				 * posible problema es que si el numero es my chico como que randoop no "apende", e decir volveria a generar casos muy bases
				 * con una nueva semilla / tambien deberia analizar si debo cambir la semilla o no, porque podria seguir generando desde el ultimo punto que deje de generar
				 */
				obj.addAll(rog.generateObjects(100));//A lo mejor una buena estrategia es generar en base a la couta como hacer una formula o algo asi... tal vez identificar de alguna manera es una parametrizacion o no
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
