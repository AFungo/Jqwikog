package net.jqwik.properties.stateful;

import java.util.*;

import net.jqwik.api.*;
import net.jqwik.api.stateful.*;

public interface NActionGenerator<M> {

	Action<M> next(M model);

	List<Shrinkable<Action<M>>> generated();
}
