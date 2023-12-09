package examples.packageWithProperties;

import java.util.*;
import java.util.stream.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.junit.jupiter.api.*;
import randoop.main.*;
import randoop.main.randoopflags.*;

import static java.lang.Math.*;

public class AnnotatedPropertiesTests {

	@Property
	boolean allIntegersAndNulls(@ForAll @WithNull Integer anInt) {
		return anInt != null;
	}

	@Property(tries = 10)
	boolean aListWithNullIntegers(@ForAll List<@WithNull(value = 0.5) Integer> aList) {
		return aList.stream().allMatch(anInt -> anInt != null);
	}

	@Property(tries = 100)
	boolean integersAreWithinBounds(@ForAll @IntRange(min = -10, max = 10) int anInt) {
		return anInt >= -10 && anInt <= 10;
	}

	@Property(tries = 10)
	void defaultStrings(@ForAll @StringLength(max = 13) String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void stringsByChars(@ForAll @Chars({ 'a', 'b', ' ' }) String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void stringsWithSeveralChars(@ForAll @Chars({ 'a', 'b', ' ' }) @CharRange(from = '0', to = '9') String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void charsWithRange(@ForAll @CharRange(from = 'f', to = 'q') char aChar) {
		System.out.println(String.format("[%s]", aChar));
	}

	@Property(tries = 50)
	void numericStrings(@ForAll @NumericChars String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void alphaNumericStrings(@ForAll @NumericChars @AlphaChars String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void stringsByFromTo(@ForAll @CharRange(from = 'a', to = 'Z') String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 50)
	void stringsCombined(@ForAll @CharRange(from = '0', to = '9') @Chars({ 'a', 'b' }) String aRandomString) {
		System.out.println(String.format("[%s]", aRandomString));
	}

	@Property(tries = 10)
	void aListWithMaxSize(@ForAll @Size(max = 15) @StringLength(max = 4) @Chars({ 'x', 'y', 'z' }) List<String> listOfStrings) {
		System.out.println(String.format("%s", listOfStrings));
	}

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

	@Property(tries=100)
	void aSetWithMaxSize(@ForAll @Size(max = 15) @IntRange(min = 1, max = 41) Set<Integer> setOfIntegers) {
		System.out.println(String.format("%s", setOfIntegers));
	}
}
