package com.data.encryption.interceptor;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenchicheng
 * @date 2023/8/12
 */
public abstract class BaseFieldEncryption {

    public <T> boolean match(T object) {
        if (object == null) {
            return false;
        }
        List<Field> filteredFields = fieldMatch(object);
        return filteredFields != null && !filteredFields.isEmpty();
    }

    public <T> void encrypt(T object) throws IllegalAccessException {
        List<Field> fields = fieldMatch(object);
        fields.parallelStream().forEach(s -> {
            try {
                encryptField(object, s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    public <T> void decrypt(T object) throws IllegalAccessException {
        List<Field> fields = fieldMatch(object);
        fields.parallelStream().forEach(s -> {
            try {
                decryptField(object, s);
            } catch (IllegalAccessException exception) {
                exception.printStackTrace();
            }
        });
    }

    public abstract <T> void encryptList(List<T> objects);

    public abstract <T> void decryptList(List<T> objects);

    abstract boolean match(Field field);

    abstract <T> List<Field> fieldMatch(T object);

    abstract <T> void encryptField(T param, Field field) throws IllegalAccessException;

    abstract <T> void decryptField(T param, Field field) throws IllegalAccessException;

    <T> List<Field> getFields(T object) {
        Class<?> resultClass = object.getClass();
        Field[] fields = resultClass.getDeclaredFields();
        return Arrays.stream(fields).collect(Collectors.toList());
    }

}
