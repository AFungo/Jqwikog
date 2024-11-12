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

package experiments.randoopTest.junitquickcheck.geom;

import examples.datastructure.pila.*;
import junitquickcheck.geom.*;

import net.jqwik.api.*;

import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;

public class PolygonPropertiesTest {

	public static boolean polygonIsConvex(Object o){
		if(o == null)
			return false;
		Polygon polygon = (Polygon) o;
		return polygon.convex();
	}

	@Property
	public void convexity(
		@AssumeMethod(className = PolygonPropertiesTest.class, methodName = "polygonIsConvex")
        @ForAll Polygon polygon,
		@ForAll Point p,
		@ForAll Point q,
		@ForAll double alpha) {


		Assume.that(polygon.contains(p));
		Assume.that(polygon.contains(q));

        Point r =
            new Point(
                alpha * p.x + (1 - alpha) * q.x,
                alpha * p.y + (1 - alpha) * q.y);

		Assertions.assertThat(polygon.contains(r)).isTrue();
    }
}
