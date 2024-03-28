package encryption.interceptor;

import cn.hutool.core.collection.CollUtil;
import encryption.model.EncryptModel;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段简易加解密基类
 *
 * @author chenchicheng
 * @date 2024/3/27
 */
public abstract class SimpleFieldEncryption extends BaseFieldEncryption {

    @Override
    public <T> void encryptList(List<EncryptModel<T>> objects) {
        objects.parallelStream().forEach(s -> {
            try {
                super.encrypt(s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    @Override
    public <T> void decryptList(List<EncryptModel<T>> objects) {
        objects.parallelStream().forEach(s -> {
            try {
                super.decrypt(s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
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
        String encrypt = encrypt(value);
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
        String decry = decrypt(value);
        try {
            field.set(param, decry);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected <T> EncryptModel<T> fieldMatch(T object) {
        List<Field> collect = super.getFields(object).parallelStream().filter(this::match).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            return null;
        }
        return new EncryptModel<>(object, this, collect);
    }

    abstract String encrypt(String value);

    abstract String decrypt(String value);
}
