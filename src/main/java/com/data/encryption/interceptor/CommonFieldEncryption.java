package com.data.encryption.interceptor;

import com.data.encryption.annotation.SensitiveField;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 普通aes加解密
 *
 * @author chenchicheng
 * @date 2023/8/12
 */
@Component
public class CommonFieldEncryption extends BaseFieldEncryption {

    @Override
    public <T> void encryptList(List<T> objects) {
        objects.parallelStream().forEach(s -> {
            try {
                super.encrypt(s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public <T> void decryptList(List<T> objects) {
        objects.parallelStream().forEach(s -> {
            try {
                super.decrypt(s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    boolean match(Field field) {
        SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
        return sensitiveField != null;
    }

    @Override
    public <T> void encryptField(T param, Field field) {
        field.setAccessible(true);
        Object object = null;
        try {
            object = field.get(param);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        if (object == null) {
            return;
        }
        String value = object.toString();
        if (value == null || value.length() == 0) {
            return;
        }
        String encrypt = LocalAesUtil.encrypt(value);
        try {
            field.set(param, encrypt);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public <T> void decryptField(T param, Field field) {
        field.setAccessible(true);
        Object object = null;
        try {
            object = field.get(param);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        if (object == null) {
            return;
        }
        String value = object.toString();
        if (value == null || value.length() == 0) {
            return;
        }
        String decry = LocalAesUtil.decrypt(value);
        try {
            field.set(param, decry);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    <T> List<Field> fieldMatch(T object) {
        return super.getFields(object).parallelStream().filter(this::match).collect(Collectors.toList());
    }
}
