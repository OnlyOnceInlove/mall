package cms.utils;

import java.lang.annotation.*;

/**
 * @ClassName Authorize
 * @Author MaZhuli
 * @Date 2018/8/25 15:55
 * @Description 权限注解
 * @Version 1.0
 **/
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {
    String setting();

    public enum AuthorizeType {ALL, ONE}

    AuthorizeType type() default AuthorizeType.ALL;
}
