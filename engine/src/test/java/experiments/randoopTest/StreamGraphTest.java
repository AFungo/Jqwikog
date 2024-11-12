package experiments.randoopTest;

import experiments.*;

import net.jqwik.api.*;
import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;
import org.graphstream.algorithm.coloring.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.geom.*;

import static java.lang.Math.*;

public class StreamGraphTest {

	@Property
	void testVector2DotProduct(@ForAll @UseMethods(methods = "set") Vector2 vector1,
							   @ForAll @UseMethods(methods = "set") Vector2 vector2) {
		Assertions.assertThat(vector1.dotProduct(vector2)).
				  isEqualTo(vector2.dotProduct(vector1));
	}

	@Property
	void testVector2Normalize(@ForAll @UseMethods(methods = "set") Vector2 vector1) {
		double x = vector1.x();
		double y = vector1.y();
		Assertions.assertThat(vector1.normalize()).
				  isEqualTo(sqrt(x*x+y*y));
	}

	public static boolean graphSize(Object o){
		// if(o == null) return false;
		DefaultGraph graph = (DefaultGraph) o;
		return graph.getEdgeCount() > 4;
	}

	@Property
	void testWelshPowellColoring(@ForAll
								 @AssumeMethod(className = RandoopExperiments.class, methodName = "graphSize")
								 @RandoopStrings(strings = {"hola", "chau", "mundo", "hello", "bay"})
								 @UseMethods(methods = {"addEdge", "addNode"})
								 DefaultGraph graph) {
		Assume.that(graph != null);
		WelshPowell alg = new WelshPowell();
		alg.init(graph);
		alg.compute();
		int colorsUsed = alg.getChromaticNumber();
		boolean isProperlyColored = graph.edges().allMatch(edge -> {
			if(edge.getNode0().equals(edge.getNode1())) return true;
			int color1 = (int) edge.getNode0().getAttribute("WelshPowell.color");
			int color2 = (int) edge.getNode1().getAttribute("WelshPowell.color");
			return color1 != color2;
		});

		Assertions.assertThat(isProperlyColored).isTrue();
		// System.out.println("Colors used: " + colorsUsed + " edges: " + graph.getEdgeCount() + " nodes: " + graph.getNodeCount());
	}
}
