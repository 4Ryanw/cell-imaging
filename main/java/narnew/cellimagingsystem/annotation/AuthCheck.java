package narnew.cellimagingsystem.annotation;


import java.lang.annotation.*;

/**
 * 权限校验
 *
 * @author WenRan
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthCheck {
    String value() default "";
}
