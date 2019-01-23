package xyz.tmlh.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    /**
     * 表示参数的别名
     */
    String value() default "";

    /**
     * 请求方式 默认为GET
     */
    RequestMethod method() default RequestMethod.GET;

    public enum RequestMethod {
        GET, POST
    };

}
