package cn.pxkeji.cms.core;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyTrace {
	String name();
}
