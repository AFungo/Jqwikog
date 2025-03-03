package experiments.randoopTest.metamorphic;

import examples.epa.*;

import net.jqwik.api.*;

import net.jqwik.api.constraints.*;
import net.jqwik.api.randoop.*;

import org.assertj.core.api.*;
import randoop.com.google.gson.*;

import java.lang.reflect.*;

public class ListIterableTest {

	public static boolean EPAPrecondition(Object o){
		if(o == null){
			return false;
		}
		ListItr itr = (ListItr) o;
		return itr.isRemoveEnabled() &&
				   itr.isHasNextEnabled() &&
				   itr.isNextEnabled() && itr.isPreviousEnabled() &&
				   itr.isSetEnabled() && itr.isNextIndexEnabled() &&
				   itr.isPreviousIndexEnabled() && itr.isModCountEquals();
	}

	@Property(tries = 100)
	public void test1(@ForAll
					  @AssumeMethod(className = ListIterableTest.class, methodName = "EPAPrecondition")
						  ListItr itr){

		// Gson gson = new Gson();
		Gson gson = new GsonBuilder()
						.registerTypeAdapter(Class.class, new JsonSerializer<Class>() {
							@Override
							public JsonElement serialize(Class src, Type typeOfSrc, JsonSerializationContext context) {
								return new JsonPrimitive(src.getName());
							}
						})
						.create();
		ListItr obj2 = gson.fromJson(gson.toJson(itr), ListItr.class);

		obj2.previousIndex();

		itr.hasNext();

		Assertions.assertThat(obj2).isEqualTo(itr);
	}

	@Property(tries=100)
	public void test2(@ForAll
					  @AssumeMethod(className = ListIterableTest.class, methodName = "EPAPrecondition")
					  ListItr itr){
		Assume.that(itr != null);
		Gson gson = new Gson();
		ListItr obj2 = gson.fromJson(gson.toJson(itr), ListItr.class);

		itr.previous();
		itr.remove();
		itr.hasPrevious();
		itr.hasNext();

		obj2.previous();
		obj2.nextIndex();
		obj2.set(new Object());
		obj2.remove();
		obj2.nextIndex();


		Assertions.assertThat(obj2).isEqualTo(itr);
	}
}
