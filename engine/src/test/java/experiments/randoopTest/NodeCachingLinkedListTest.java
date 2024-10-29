package experiments.randoopTest;

import examples.datastructure.ncl.*;

import net.jqwik.api.*;
import net.jqwik.api.constraints.*;

import org.assertj.core.api.*;

public class NodeCachingLinkedListTest {

	@Property(tries = 100)
	public void nclTest(@ForAll NodeCachingLinkedList ncl,
						@ForAll @IntRange(min=1, max=4) Integer indexToRemove){
		Assume.that(ncl.size() > indexToRemove);
		Assume.that(!ncl.cacheIsFull());
		int beforeSize = ncl.cacheSize();
		ncl.removeIndex(indexToRemove);
		int afterSize = ncl.cacheSize();
		Assertions.assertThat(afterSize-1).isEqualTo(beforeSize);
	}

}
