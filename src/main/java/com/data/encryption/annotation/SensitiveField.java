package com.data.encryption.annotation;

import java.lang.annotation.*;

/**
 * 敏感字段注解
 * @author chenchicheng
 * @date 2022/1/14
 */
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {
}
