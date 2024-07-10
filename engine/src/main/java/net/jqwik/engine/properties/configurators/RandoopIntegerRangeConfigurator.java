package net.jqwik.engine.properties.configurators;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.api.configurators.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.providers.*;

public class RandoopIntegerRangeConfigurator extends ArbitraryConfiguratorBase {

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, IntRange range) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			return randoopArbitrary.greaterOrEqual(range.min()).lessOrEqual(range.max()!=Integer.MAX_VALUE ? range.max() : 10);
		}
		return arbitrary;
	}
}
