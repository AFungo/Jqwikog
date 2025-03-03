/*
 The MIT License

 Copyright (c) 2010-2021 Paul R. Holser, Jr.

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package experiments.randoopTest.junitquickcheck.money;

import junitquickcheck.money.*;

import net.jqwik.api.*;

import org.assertj.core.api.*;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_DOWN;
import static java.math.RoundingMode.HALF_UP;
import java.math.BigDecimal;

public class DollarsAndCentsPropertiesTest {
    @Property(tries = 100)
	public void roundingDown(@ForAll BigDecimal d) {
        BigDecimal[] pieces = d.divideAndRemainder(ONE);
        BigDecimal integral = pieces[0];
        BigDecimal fractional = pieces[1];

		Assume.that(
			fractional.compareTo(new BigDecimal("0.5")) <= 0
		);

        DollarsAndCents money = new DollarsAndCents(d);

		Assertions.assertThat(
            integral.add(fractional).setScale(2, HALF_DOWN)).isEqualTo(
            money.toBigDecimal());
    }

    @Property(tries = 100)
	public void roundingUp(@ForAll BigDecimal d) {
        BigDecimal[] pieces = d.divideAndRemainder(ONE);
        BigDecimal integral = pieces[0];
        BigDecimal fractional = pieces[1];

		// Assertions.assertThat(assumeThat(fractional, greaterThan(new BigDecimal("0.5")));

        DollarsAndCents money = new DollarsAndCents(d);

        Assertions.assertThat(integral.add(fractional).setScale(2, HALF_UP)).
				  isEqualTo(money.toBigDecimal());
    }
}
