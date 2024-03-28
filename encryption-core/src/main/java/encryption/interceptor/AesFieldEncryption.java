package encryption.interceptor;

import encryption.annotation.AesSensitiveField;
import encryption.util.LocalAesUtil;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
/**
 * 普通aes加解密
 *
 * @author chenchicheng
 * @date 2023/8/12
 */
@Component
public class AesFieldEncryption extends SimpleFieldEncryption {

    @Override
    protected boolean match(Field field) {
        AesSensitiveField sensitiveField = field.getAnnotation(AesSensitiveField.class);
        return sensitiveField != null;
    }

    @Override
    String encrypt(String value) {
        return LocalAesUtil.encrypt(value);
    }

    @Override
    String decrypt(String value) {
        return LocalAesUtil.decrypt(value);
    }
}
