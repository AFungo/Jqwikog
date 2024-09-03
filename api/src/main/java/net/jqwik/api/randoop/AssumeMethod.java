package net.jqwik.api.randoop;

import net.jqwik.api.*;

import org.apiguardian.api.API;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.MAINTAINED;

/**
 * Take all necessary classes for object generation
 *
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = MAINTAINED, since = "1.0")
public @interface AssumeMethod {
	String methodName() default "";
	Class<?> className();
}
