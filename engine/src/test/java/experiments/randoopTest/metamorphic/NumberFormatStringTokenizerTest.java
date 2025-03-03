package experiments.randoopTest.metamorphic;

import examples.epa.*;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.randoop.AssumeMethod;

import org.assertj.core.api.Assertions;
import randoop.com.google.gson.Gson;


public class NumberFormatStringTokenizerTest {

	public static boolean EPAPrecondition(Object o){
		if(o == null){
			return false;
		}
		NumberFormatStringTokenizer itr = (NumberFormatStringTokenizer) o;
		return itr.isNextTokenEnabled() && itr.isLetterOrDigitAheadEnabled()
			&& itr.isHasMoreTokensEnabled() && itr.isNextIsSepEnabled();
	}

	@Property(tries = 100)
	public void test1(@ForAll
					  @AssumeMethod(className = NumberFormatStringTokenizerTest.class, methodName = "EPAPrecondition")
						  NumberFormatStringTokenizer tok){
		Gson gson = new Gson();
		NumberFormatStringTokenizer obj2 = gson.fromJson(gson.toJson(tok), NumberFormatStringTokenizer.class);

		tok.hasMoreTokens();
		tok.nextIsSep();
		tok.isLetterOrDigitAhead();
		tok.hasMoreTokens();
		tok.nextToken();

		obj2.hasMoreTokens();
		obj2.isLetterOrDigitAhead();
		obj2.hasMoreTokens();
		obj2.hasMoreTokens();
		obj2.nextToken();

		Assertions.assertThat(obj2).isEqualTo(tok);
	}

	@Property(tries = 100)
	public void test2(@ForAll
					  @AssumeMethod(className = NumberFormatStringTokenizerTest.class, methodName = "EPAPrecondition")
					  NumberFormatStringTokenizer tok){
		Gson gson = new Gson();
		NumberFormatStringTokenizer obj2 = gson.fromJson(gson.toJson(tok), NumberFormatStringTokenizer.class);

		tok.isLetterOrDigitAhead();
		tok.nextToken();

		obj2.isLetterOrDigitAhead();
		obj2.hasMoreTokens();
		obj2.nextToken();
		obj2.isLetterOrDigitAhead();


		Assertions.assertThat(obj2).isEqualTo(tok);
	}
}
