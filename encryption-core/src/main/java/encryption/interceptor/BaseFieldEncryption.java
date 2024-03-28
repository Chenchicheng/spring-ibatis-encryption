package encryption.interceptor;

import encryption.model.EncryptModel;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenchicheng
 * @date 2023/8/12
 */
public abstract class BaseFieldEncryption {

    protected <T> void encrypt(EncryptModel<T> encryptModel) throws IllegalAccessException {
        encryptModel.encrypt();
    }

    protected <T> void decrypt(EncryptModel<T> encryptModel) throws IllegalAccessException {
        encryptModel.decrypt();
    }

    protected <T> List<Field> getFields(T object) {
        Class<?> resultClass = object.getClass();
        Field[] fields = resultClass.getDeclaredFields();
        return Arrays.stream(fields).collect(Collectors.toList());
    }

    protected abstract boolean match(Field field);

    protected abstract <T> EncryptModel<T> fieldMatch(T object);

    public <T> EncryptModel<T> getFieldEncryptInfo(T object) {
        if (object == null) {
            return null;
        }
        return fieldMatch(object);
    }

    /**
     * 批量加密方法，可自主实现，如使用多线程批量加密
     * @param objects objects
     * @param <T> T
     */
    public abstract <T> void encryptList(List<EncryptModel<T>> objects);

    /**
     * 批量解密方法，可自主实现，如使用多线程批量解密
     * @param objects objects
     * @param <T> T
     */
    public abstract <T> void decryptList(List<EncryptModel<T>> objects);

    public abstract <T> void encryptField(T param, Field field);

    public abstract <T> void decryptField(T param, Field field);

}
