package examples.randoopTest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.junit.jupiter.api.*;
import randoop.main.*;
import randoop.main.randoopflags.*;

import java.util.*;
import java.util.stream.*;

import static java.lang.Math.*;

public class RandoopTest {

	@Property(tries = 10)
	void stackSizeTest(@ForAll Stack stack) {
		Assume.that(!stack.isEmpty());
		System.out.println("Size = " + stack.size() + " " + stack);
		int previousSize = stack.size();
		stack.peek();
		Assertions.assertEquals(stack.size(), previousSize);
	}

	@Property(tries = 100)
	void dateAfterTest(@ForAll Date date) {
		System.out.println(date);
		Assertions.assertFalse(date.after(date));
	}

	@Property(tries = 100)
	void bitSetClearTest(@ForAll BitSet set) {
		Assume.that(!set.isEmpty());
		System.out.println(set);
		set.clear();
		Assertions.assertTrue(set.isEmpty());
	}

	@Property(tries = 100)
	void priorityQueueTest(@ForAll PriorityQueue<Date> pq) {
		System.out.println(pq);
		pq.clear();
		Assertions.assertTrue(pq.isEmpty());
	}

	@Property(tries = 100)
	void treeSetTest(@ForAll TreeSet<Date> ts) {
		System.out.println(ts);
		ts.clear();
		Assertions.assertTrue(ts.isEmpty());
	}


	@Property(tries = 100)
	void localeTest(@ForAll Locale locale) {
		System.out.println(locale);
		Assertions.assertFalse(locale.getLanguage().isEmpty());
	}

	//tes list de date
	/**
	 * ----- Que pasa con la instanciacion la cantidad (ya tengo forma de resolverlo, creo. Esta explicado en RandomIntegralGenerators)
	 *como puede randoop aprender de la info previa //No se si aprende, tiene una lista de metodos que puede ir usando con los objetos
	 * ----- ver como instancias clases en randoop y que me genere objetos de ese tipo
	 * arreglar las path en los repos
	 * ------ sacar stack del repo
	 * ------ sacar myownconstraint
	 *
	 */
}
