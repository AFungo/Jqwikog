package net.jqwik.api.randoop;

import java.lang.annotation.*;
import java.util.*;

import org.apiguardian.api.*;

import net.jqwik.api.constraints.*;

import static org.apiguardian.api.API.Status.*;

/**
 * Constrain the range of a generated int or Integer parameters.
 *
 * Applies to int or Integer parameters which are also annotated with {@code @ForAll}.
 *
 * @see net.jqwik.api.ForAll
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = MAINTAINED, since = "1.0")
public @interface Classes {
	Class<?>[] classes();
}
