package cn.pxkeji.cms.core;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface ModelTrace {
	Class<?> serviceType();
	String key() default "id";
	String name();
}
