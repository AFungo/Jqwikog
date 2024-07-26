package net.jqwik.api.randoop;

import java.lang.annotation.*;
import java.util.*;

import org.apiguardian.api.*;

import net.jqwik.api.constraints.*;

import static org.apiguardian.api.API.Status.*;

/**
 * Take all necessary classes for object generation
 *
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = MAINTAINED, since = "1.0")
public @interface Classes {
	Class<?>[] classes();
}
