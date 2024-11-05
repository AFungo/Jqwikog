package experiments;

import examples.datastructure.list.*;

import examples.datastructure.pila.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import net.jqwik.api.randoop.*;

import net.jqwik.api.randoop.AssumeMethod;

import org.assertj.core.api.Assertions;

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