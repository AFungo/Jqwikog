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
	void charTest(@ForAll Stack c) {
		System.out.println(c);
	}

	@Property(tries = 10)
	void myOwnTest(@ForAll @MyOwnConstraint(from = 0, to = 10) Stack<String> stackOfStrings) {
		System.out.println(String.format("%s", stackOfStrings));
	}


	@Property(tries = 100)
	void stackTest(@ForAll Stack<Date> stack) {
		Assume.that(!stack.isEmpty());
		System.out.println(stack);
		int previusSize = stack.size();
		stack.peek();
		Assertions.assertEquals(stack.size(), previusSize);
	}

	@Property(tries = 100)
	void dateTest(@ForAll Date date) {
		// Assume.that(!stack.isEmpty());
		System.out.println(date);
		Assertions.assertFalse(date.after(date));
	}

	@Property(tries = 100)
	void bitSetTest(@ForAll BitSet set) {
		Assume.that(!set.isEmpty());
		System.out.println(set);
		set.clear();
		Assertions.assertTrue(set.isEmpty());
	}
	@Provide("stackProvider")
	Arbitrary<Stack<Object>> stackProvider(){
		int seed = (int)(random()*1000);
		RandoopObjectGenerator rog = new RandoopObjectGenerator(Stack.class);
		rog.setSeed(seed);
		rog.addFlag(new LiteralsFileFlag("/home/augusto/Documents/tesis/randoopObjectGenerator/literals/lits.txt"));
		List<Object> obj = rog.generateObjects(3);
		for (Object o: obj) {
			System.out.println(o);
		}
		System.out.println("----------------------------------\n");
		return Arbitraries.of(obj.stream().map(a -> (Stack<Object>) a).collect(Collectors.toList()));
		// return Arbitraries.of();
	}
}
