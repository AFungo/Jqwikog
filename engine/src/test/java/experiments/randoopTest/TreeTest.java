package experiments.randoopTest;

import examples.datastructure.trees.*;
import experiments.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;
import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;

import java.util.*;

/*
 *Experimentos extraidos de JQF
 * https://rohan.padhye.org/files/jqf-issta19.pdf
 */
public class TreeTest {

	public static boolean noElementsFrom0to100InTree(Object o){
		BinaryTree b = (BinaryTree) o;
		for(int i = 0; i <= 100; i++){
			if(b.contains(i)){
				return false;
			}
		}
		return true;
	}

	@Property
	public void treeContainsTest(
		@ForAll @IntRange(min = 200, max = 300) @AssumeMethod(className = TreeTest.class, methodName = "noElementsFrom0to100InTree")
			@UseMethods(methods = {"insert"})
		BinaryTree b,
		@ForAll @Size(max=100) Set<@IntRange(min=0, max=100) Integer> elements,
		@ForAll @Size(max=100) Set<@IntRange(min=0, max=100) Integer> queries
	) {
		for (Integer e : elements) {
			b.insert(e);
		}

		for (int q : queries) {
			Assertions.assertThat(elements.contains(q)).isEqualTo(b.contains(q));
		}
	}

}
