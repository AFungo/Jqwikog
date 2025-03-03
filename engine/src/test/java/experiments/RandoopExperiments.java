package experiments;

import examples.datastructure.list.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.assertj.core.api.Assertions;

public class RandoopExperiments {


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