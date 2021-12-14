package net.jqwik.engine.execution;

import java.math.*;
import java.util.*;

import org.mockito.*;

import net.jqwik.api.*;
import net.jqwik.api.lifecycle.*;
import net.jqwik.engine.properties.*;
import net.jqwik.engine.properties.shrinking.*;
import net.jqwik.testing.*;

import static org.assertj.core.api.Assertions.*;

import static net.jqwik.api.lifecycle.TryExecutionResult.Status.*;

@SuppressLogging
class AfterFailureParametersGeneratorTests {

	ParametersGenerator generator = new ParametersGenerator() {
		int index = 0;

		@Override
		public boolean hasNext() {
			return index < 25;
		}

		@Override
		public List<Shrinkable<Object>> next(TryLifecycleContext context) {
			return Arrays.asList(shrinkableInt(++index));
		}

		private Shrinkable<Object> shrinkableInt(int anInt) {
			Range<BigInteger> range = Range.of(BigInteger.ZERO, BigInteger.valueOf(1000));
			BigInteger value = BigInteger.valueOf(anInt);
			return new ShrinkableBigInteger(value, range, BigInteger.ZERO)
				.map(BigInteger::intValueExact)
				.asGeneric();
		}

		@Override
		public int edgeCasesTotal() {
			return 0;
		}

		@Override
		public int edgeCasesTried() {
			return 0;
		}

		@Override
		public GenerationInfo generationInfo(String randomSeed) {
			return new GenerationInfo(randomSeed, index);
		}

		@Override
		public void reset() {
			index = 0;
		}
	};

	TryLifecycleContext context = Mockito.mock(TryLifecycleContext.class);

	@Example
	void afterFailureMode_PreviousSeed_plainGeneratorIsUsed() {
		ParametersGenerator afterFailureGenerator = new AfterFailureParametersGenerator(
			AfterFailureMode.PREVIOUS_SEED,
			new GenerationInfo("42", 715),
			generator
		);

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(1);
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(2);

		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(new GenerationInfo("42", 2));
	}

	@Example
	void afterFailureMode_SampleFirst_generationIndex0_plainGeneratorIsUsed() {
		ParametersGenerator afterFailureGenerator = new AfterFailureParametersGenerator(
			AfterFailureMode.SAMPLE_FIRST,
			new GenerationInfo("42", 0),
			generator
		);

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(1);
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(2);

		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(new GenerationInfo("42", 2));
	}

	@Example
	void afterFailureMode_SampleFirst_generateSampleFirst_thenUsePlainGenerator() {
		ParametersGenerator afterFailureGenerator = new AfterFailureParametersGenerator(
			AfterFailureMode.SAMPLE_FIRST,
			new GenerationInfo("42", 13),
			generator
		);

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(13);
		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(new GenerationInfo("42", 13));

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(1);
		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(new GenerationInfo("42", 1));
	}

	@Example
	void afterFailureMode_SampleOnly_generateSampleOnly() {
		// Generate 13, then shrink to 2
		GenerationInfo generationInfo = new GenerationInfo("42", 13)
			.appendShrinkingSequence(Arrays.asList(SATISFIED, SATISFIED, FALSIFIED));

		ParametersGenerator afterFailureGenerator = new AfterFailureParametersGenerator(
			AfterFailureMode.SAMPLE_ONLY,
			generationInfo,
			generator
		);

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(2);
		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(generationInfo);

		assertThat(afterFailureGenerator.hasNext()).isFalse();
	}

	@Example
	void afterFailureMode_SampleCannotBeGenerated_thenUsePlainGenerator() {
		ParametersGenerator afterFailureGenerator = new AfterFailureParametersGenerator(
			AfterFailureMode.SAMPLE_ONLY,
			new GenerationInfo("42", 133),
			generator
		);

		assertThat(afterFailureGenerator.hasNext()).isTrue();
		assertThat(afterFailureGenerator.next(context).get(0).value()).isEqualTo(1);
		assertThat(afterFailureGenerator.generationInfo("42"))
			.isEqualTo(new GenerationInfo("42", 1));

		assertThat(afterFailureGenerator.hasNext()).isTrue();
	}

}