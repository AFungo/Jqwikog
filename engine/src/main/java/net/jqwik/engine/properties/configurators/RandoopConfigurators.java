package net.jqwik.engine.properties.configurators;

import net.jqwik.api.*;
import net.jqwik.api.arbitraries.*;
import net.jqwik.api.configurators.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.randoop.*;

import java.util.*;

public class RandoopConfigurators extends ArbitraryConfiguratorBase {

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, IntRange range) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			return randoopArbitrary.setIntegersLiterals(range.min(), range.max());
					   //greaterOrEqual(range.min()).lessOrEqual(range.max()!=Integer.MAX_VALUE ? range.max() : 10);
		}
		return arbitrary;
	}

	public Arbitrary<?> configure(Arbitrary<?> arbitrary, Classes classes) {
		if (arbitrary instanceof RandoopArbitrary) {
			RandoopArbitrary<?> randoopArbitrary = (RandoopArbitrary<?>) arbitrary;
			return randoopArbitrary.setNecessaryClasses(new HashSet<>(Arrays.asList(classes.classes())));
		}
		return arbitrary;
	}
}
