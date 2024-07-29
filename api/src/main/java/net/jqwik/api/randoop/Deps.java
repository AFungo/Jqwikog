package net.jqwik.api.randoop;

import java.lang.annotation.*;
import org.apiguardian.api.*;
import static org.apiguardian.api.API.Status.*;

/**
 * Take all necessary classes for object generation
 *
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = MAINTAINED, since = "1.0")
public @interface Deps {
	Class<?>[] classes();
}
