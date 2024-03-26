package com.data.encryption.interceptor;

import com.data.encryption.annotation.SensitiveData;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author chenchicheng
 * @date 2022/1/14
 */
@Component
public class MybatisEncryption {


    @Resource
    protected FieldEncryptContext fieldEncryptContext;

    /**
     * 是否为敏感类
     *
     * @param result result
     * @return boolean
     */
    protected <T> boolean sensitive(T result) {
        if (result == null) {
            return false;
        }
        Class<?> resultClass = result.getClass();
        SensitiveData sensitiveData = resultClass.getAnnotation(SensitiveData.class);
        return Objects.nonNull(sensitiveData);
    }

    /**
     * 加密敏感字段
     *
     * @param paramsObject paramsObject
     * @param <T>          <T>
     */
    protected <T> void encrypt(T paramsObject) throws IllegalAccessException {
        if (paramsObject == null) {
            return;
        }
        fieldEncryptContext.encrypt(paramsObject);
    }

    /**
     * 解密敏感字段
     *
     * @param result result
     * @param <T>    <T>
     */
    protected <T> void decrypt(T result) throws IllegalAccessException {
        if (result == null) {
            return;
        }
        fieldEncryptContext.decrypt(result);
    }

    /**
     * 加密敏感字段
     *
     * @param paramsObject paramsObject
     * @param <T>          <T>
     */
    protected <T> void encryptList(List<T> paramsObject) throws IllegalAccessException {
        if (paramsObject == null || paramsObject.isEmpty()) {
            return;
        }
        fieldEncryptContext.encryptList(paramsObject);
    }

    /**
     * 解密敏感字段
     *
     * @param result result
     * @param <T>    <T>
     */
    protected <T> void decryptList(List<T> result) throws IllegalAccessException {
        if (result == null || result.isEmpty()) {
            return;
        }
        fieldEncryptContext.decryptList(result);
    }

}
