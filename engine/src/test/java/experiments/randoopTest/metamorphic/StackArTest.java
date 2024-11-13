package experiments.randoopTest.metamorphic;

import examples.epa.*;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.randoop.AssumeMethod;
import org.assertj.core.api.Assertions;
import randoop.com.google.gson.Gson;


public class StackArTest {

	public static boolean EPAPrecondition(Object o){
		if(o == null){
			return false;
		}
		StackAr s = (StackAr) o;
		return s.isEmptyEnabled() && s.isPushEnabled() &&
				s.isTopAndPopEnabled() && s.isFullEnabled() &&
				s.isMakeEmptyEnabled() && s.isTopEnabled();
	}

	@Property
	public void test1(@ForAll
					  @AssumeMethod(className = StackArTest.class, methodName = "EPAPrecondition")
						  StackAr s){
		Gson gson = new Gson();
		StackAr obj2 = gson.fromJson(gson.toJson(s), StackAr.class);

		s.topAndPop();
		s.top();
		s.makeEmpty();

		obj2.top();
		obj2.isFull();
		obj2.topAndPop();
		obj2.isFull();
		obj2.makeEmpty();

		Assertions.assertThat(obj2).isEqualTo(s);

	}
}
