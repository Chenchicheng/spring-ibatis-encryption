package encryption.interceptor;

import encryption.annotation.Base64SensitiveField;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 普通Base64加解密
 *
 * @author chenchicheng
 * @date 2024/3/27
 */
@Component
public class Base64FieldEncryption extends SimpleFieldEncryption {

    @Override
    protected boolean match(Field field) {
        Base64SensitiveField sensitiveField = field.getAnnotation(Base64SensitiveField.class);
        return sensitiveField != null;
    }

    @Override
    String encrypt(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    String decrypt(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
