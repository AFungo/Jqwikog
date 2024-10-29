package experiments;

import chess.*;
import examples.datastructure.list.*;
import examples.datastructure.ncl.*;

import examples.datastructure.pila.*;
import examples.datastructure.set.*;

import examples.datastructure.set.BitSet;

import examples.datastructure.trees.*;

import examples.jgrapht.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

// import org.junit.jupiter.api.*;
import net.jqwik.api.randoop.*;

import net.jqwik.api.randoop.AssumeMethod;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.trie.*;
import org.assertj.core.api.Assertions;
import org.graphstream.algorithm.*;
import org.graphstream.algorithm.coloring.*;
import org.graphstream.graph.implementations.*;
import org.jgrapht.alg.connectivity.*;
import org.jgrapht.alg.interfaces.*;
import org.jgrapht.alg.spanning.*;
import org.jgrapht.graph.*;

import java.util.*;
import java.util.stream.*;

public class RandoopExperiments {



	public static boolean oddSize(Object o){
		PilasTuple pilasTuple = (PilasTuple) o;
		PilaSobreListasEnlazadas pila = pilasTuple.getPila();
		int size = pila.length();
		return size  % 2 != 0;
	}

	@Property(tries = 100)
	void pilasTupleTest(
		@ForAll @Deps(classes = {PilaSobreListasEnlazadas.class})
		@AssumeMethod(className = RandoopExperiments.class, methodName = "oddSize")
		@UseMethods(methods = {"setPilas", "setNumber", "applyPush"})
		@IntRange(min = 110,max = 120) PilasTuple tuple)
	{
		System.out.println(tuple);
	}







	@Property(tries = 100)
	public void sortedListTest(@ForAll @IntRange(min=20, max=30) SimpleList list){
		Assume.that(list.size() > 2);
		Assume.that(list.isSorted());
		System.out.println(list);
		for (int i = 0; i < list.size()-1; i++) {
			Assertions.assertThat(list.get(i)).isLessThanOrEqualTo(list.get(i+1));
		}
	}


}