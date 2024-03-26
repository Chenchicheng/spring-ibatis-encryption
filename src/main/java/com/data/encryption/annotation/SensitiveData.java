package com.data.encryption.annotation;

import java.lang.annotation.*;

/**
 * 敏感类注解
 *
 * @author chenchicheng
 * @date 2022/1/14
 */
@Inherited
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {
}
