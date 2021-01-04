_jqwik_ tries to generate values for those property method parameters that are
annotated with [`@ForAll`](/docs/${docsVersion}/javadoc/net/jqwik/api/ForAll.html). If the annotation does not have a `value` parameter,
jqwik will use default generation for the following types:

- `Object`
- `String`
- Integral types `Byte`, `byte`, `Short`, `short` `Integer`, `int`, `Long`, `long` and `BigInteger`
- Floating types  `Float`, `float`, `Double`, `double` and `BigDecimal`
- `Boolean` and `boolean`
- `Character` and `char`
- All `enum` types
- Collection types `List<T>`, `Set<T>` and `Stream<T>`
  as long as `T` can also be provided by default generation.
- `Iterable<T>` and `Iterator<T>` of types that are provided by default.
- `Optional<T>` of types that are provided by default.
- Array `T[]` of types that are provided by default.
- `Map<K, V>` as long as `K` and `V` can also be provided by default generation.
- `HashMap<K, V>` as long as `K` and `V` can also be provided by default generation.
- `Map.Entry<K, V>` as long as `K` and `V` can also be provided by default generation.
- `java.util.Random`
- [Functional Types](#functional-types)
- Most types of package `java.time` are handled in the [Time Module](#time-module)

If you use [`@ForAll`](/docs/${docsVersion}/javadoc/net/jqwik/api/ForAll.html)
with a value, e.g. `@ForAll("aMethodName")`, the method
referenced by `"aMethodName"` will be called to provide an Arbitrary of the
required type (see [Parameter Provider Methods](#parameter-provider-methods)).

### Constraining Default Generation

Default parameter generation can be influenced and constrained by additional annotations,
depending on the requested parameter type.

#### Allow Null Values

- [`@WithNull(double value = 0.1)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/WithNull.html):
  Inject `null` into generated values with a probability of `value`.

  Works for all generated types.

#### Unique Values

- [`@Unique`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Unique.html):
  Prevent duplicate values to be generated _per try_. That means that
  there can still be duplicate values across several tries. That also means
  that `@Unique` only makes sense as annotation for a parameter type, e.g.:

  ```java
    @Property
    void uniqueInList(@ForAll @Size(5) List<@IntRange(min = 0, max = 10) @Unique Integer> aList) {
        Assertions.assertThat(aList).doesNotHaveDuplicates();
        Assertions.assertThat(aList).allMatch(anInt -> anInt >= 0 && anInt <= 10);
    }
  ```

  Trying to generate a list with more than 11 elements would not work here.

  Works for all generated types.

#### String Length

- [`@StringLength(int value = 0, int min = 0, int max = 0)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/StringLength.html):
  Set either fixed length through `value` or configure the length range between `min` and `max`.

- [`@NotEmpty`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/NotEmpty.html):
  Set minimum length to `1`.

#### Character Sets

When generating chars any unicode character might be generated.

When generating Strings, however,
Unicode "noncharacters" and "private use characters"
will not be generated unless you explicitly include them using
`@Chars` or `@CharRange` (see below).

You can use the following annotations to restrict the set of allowed characters and even
combine several of them:

- [`@Chars(chars[] value = {})`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Chars.html):
  Specify a set of characters.
  This annotation can be repeated which will add up all allowed chars.
- [`@CharRange(char from = 0, char to = 0)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/CharRange.html):
  Specify a start and end character.
  This annotation can be repeated which will add up all allowed chars.
- [`@NumericChars`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/NumericChars.html):
  Use digits `0` through `9`
- [`@LowerChars`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/LowerChars.html):
  Use lower case chars `a` through `z`
- [`@UpperChars`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/UpperChars.html):
  Use upper case chars `A` through `Z`
- [`@AlphaChars`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/AlphaChars.html):
  Lower and upper case chars are allowed.
- [`@Whitespace`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Whitespace.html):
  All whitespace characters are allowed.

They work for generated `String`s and `Character`s.

#### List, Set, Stream, Map and Array Size

- [`@Size(int value = 0, int min = 0, int max = 0)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Size.html):
  Set either fixed size through `value` or configure the size range between `min` and `max`.

- [`@NotEmpty`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/NotEmpty.html):
  Set minimum size to `1`.


#### Integer Constraints

- [`@ByteRange(byte min = 0, byte max = Byte.MAX_VALUE)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/ByteRange.html):
  For `Byte` and `byte` only.
- [`@ShortRange(short min = 0, short max = Short.MAX_VALUE)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/ShortRange.html):
  For `Short` and `short` only.
- [`@IntRange(int min = 0, int max = Integer.MAX_VALUE)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/IntRange.html):
  For `Integer` and `int` only.
- [`@LongRange(long min = 0L, long max = Long.MAX_VALUE)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/LongRange.html):
  For `Long` and `long` only.
- [`@BigRange(String min = "", String max = "")`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/BigRange.html):
  For `BigInteger` generation.
- [`@Positive`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Positive.html):
  Numbers larger than `0`. For all integral types.
- [`@Negative`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Negative.html):
  Numbers lower than `0`. For all integral types.


#### Decimal Constraints

- [`@FloatRange(float min = 0.0f, minIncluded = true, float max = Float.MAX_VALUE, maxIncluded = true)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/FloatRange.html):
  For `Float` and `float` only.
- [`@DoubleRange(double min = 0.0, minIncluded = true, double max = Double.MAX_VALUE, boolean maxIncluded = true)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/DoubleRange.html):
  For `Double` and `double` only.
- [`@BigRange(String min = "", minIncluded = true, String max = "", maxIncluded = true)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/BigRange.html):
  For `BigDecimal` generation.
- [`@Scale(int value)`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Scale.html):
  Specify the maximum number of decimal places. For all decimal types.
- [`@Positive`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Positive.html):
  Numbers larger than `0.0`. For all decimal types.
- [`@Negative`](/docs/${docsVersion}/javadoc/net/jqwik/api/constraints/Negative.html):
  Numbers lower than `0.0`. For all decimal types.

### Constraining parameterized types

When you want to constrain the generation of contained parameter types you can annotate
the parameter type directly, e.g.:

```java
@Property
void aProperty(@ForAll @Size(min= 1) List<@StringLength(max=10) String> listOfStrings) {
}
```
will generate lists with a minimum size of 1 filled with Strings that have 10 characters max.

### Providing variable types

While checking properties of generically typed classes or functions, you often don't care
about the exact type of variables and therefore want to express them with type variables.
_jqwik_ can also handle type variables and wildcard types. The handling of upper and lower
bounds works mostly as you would expect it.

Consider
[the following examples](https://github.com/jlink/jqwik/blob/${gitVersion}/documentation/src/test/java/net/jqwik/docs/VariableTypedPropertyExamples.java):

```java
class VariableTypedPropertyExamples {

	@Property
	<T> boolean unboundedGenericTypesAreResolved(@ForAll List<T> items, @ForAll T newItem) {
		items.add(newItem);
		return items.contains(newItem);
	}

	@Property
	<T extends Serializable & Comparable> void someBoundedGenericTypesCanBeResolved(@ForAll List<T> items, @ForAll T newItem) {
	}

	@Property
	void someWildcardTypesWithUpperBoundsCanBeResolved(@ForAll List<? extends Serializable> items) {
	}

}
```

In the case of bounded type variables and bounded wildcard types, _jqwik_
will check if any [registered arbitrary provider](#providing-default-arbitraries)
can provide suitable arbitraries and choose randomly between those.

There is, however, a potentially unexpected behaviour,
when the same type variable is used in more than one place and can be
resolved by more than one arbitrary. In this case it can happen that the variable
does not represent the same type in all places. You can see this above
in property method `someBoundedGenericTypesCanBeResolved()` where `items`
might be a list of Strings but `newItem` of some number type - and all that
_in the same call to the method_!

### Self-Made Annotations

You can [make your own annotations](http://junit.org/junit5/docs/5.0.0/user-guide/#writing-tests-meta-annotations)
instead of using _jqwik_'s built-in ones. BTW, '@Example' is nothing but a plain annotation using [`@Property`](/docs/${docsVersion}/javadoc/net/jqwik/api/Property.html)
as "meta"-annotation.

The following example provides an annotation to constrain String or Character generation to German letters only:

```java
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@NumericChars
@AlphaChars
@Chars({'ä', 'ö', 'ü', 'Ä', 'Ö', 'Ü', 'ß'})
@Chars({' ', '.', ',', ';', '?', '!'})
@StringLength(min = 10, max = 100)
public @interface GermanText { }

@Property(tries = 10) @Reporting(Reporting.GENERATED)
void aGermanText(@ForAll @GermanText String aText) {}
```

The drawback of self-made annotations is that they do not forward their parameters to meta-annotations,
which constrains their applicability to simple cases.

