package experiments.randoopTest;

import net.jqwik.api.*;

import org.junit.jupiter.api.*;

import java.util.*;

public class DateTest {

	@Property(tries = 100)
	void dateAfterTest(@ForAll Date date) {
		Assertions.assertFalse(date.after(date));
	}

}
