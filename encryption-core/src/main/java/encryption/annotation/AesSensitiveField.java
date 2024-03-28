package encryption.annotation;

import java.lang.annotation.*;

/**
 * 敏感字段注解, 使用普通aes加密
 * @see encryption.interceptor.AesFieldEncryption
 *
 * @author chenchicheng
 * @date 2022/1/14
 */
@Inherited
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AesSensitiveField {
}
