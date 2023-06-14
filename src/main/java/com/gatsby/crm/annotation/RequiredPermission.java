package com.gatsby.crm.annotation;

import java.lang.annotation.*;

/**
 * @PACKAGE_NAME: com.gatsby.crm.annotation
 * @NAME: RequiredPermission
 * @AUTHOR: Jonah
 * @DATE: 2023/6/10
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
    String code() default "";
}
