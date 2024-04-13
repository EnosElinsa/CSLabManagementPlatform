package top.galaxyrockets.cslabmanagementplatform.common;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Enos
 * @since 2024/04/13
 * @description The annotation to mark the method that can pass the authentication of the interceptors
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
    
}