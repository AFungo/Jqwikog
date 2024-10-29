package examples.jgrapht;

import java.util.*;
import java.util.function.*;

public class MySuplier implements Supplier<Integer> {

	Integer value;

	public MySuplier(Integer value){
		this.value = value;
	}

	@Override
	public Integer get() {
		return value;
	}

	@Override
	public boolean equals(Object o){
		// If the object is compared with itself then return true
		if (o == this) {
			return true;
		}

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
		if (!(o instanceof MySuplier)) {
			return false;
		}
		return ((MySuplier) o).value.equals(this.value);
	}
}
