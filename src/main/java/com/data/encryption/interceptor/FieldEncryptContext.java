package com.data.encryption.interceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author chenchicheng
 * @date 2023/8/12
 */
@Component
public class FieldEncryptContext {

    @Resource
    private ApplicationContext applicationContext;

    private <T> BaseFieldEncryption createFieldEncryption(T object) {
        Map<String, BaseFieldEncryption> beansOfType = applicationContext.getBeansOfType(BaseFieldEncryption.class);
        Collection<BaseFieldEncryption> handleChains = beansOfType.values();
        return handleChains.parallelStream().filter(s -> s.match(object)).findFirst().orElse(null);
    }

    public <T> void encrypt(T object) throws IllegalAccessException {
        if (object == null) {
            return;
        }
        BaseFieldEncryption fieldEncryption = createFieldEncryption(object);
        if (fieldEncryption == null) {
            return;
        }
        fieldEncryption.encrypt(object);
    }

    public <T> void decrypt(T object) throws IllegalAccessException {
        if (object == null) {
            return;
        }
        BaseFieldEncryption fieldEncryption = createFieldEncryption(object);
        if (fieldEncryption == null) {
            return;
        }
        fieldEncryption.decrypt(object);
    }

    public <T> void encryptList(List<T> object) throws IllegalAccessException {
        T first = object.stream().findFirst().orElse(null);
        if (first == null) {
            return;
        }
        BaseFieldEncryption fieldEncryption = createFieldEncryption(first);
        if (fieldEncryption == null) {
            return;
        }
        fieldEncryption.encryptList(object);
    }

    public <T> void decryptList(List<T> object) throws IllegalAccessException {
        T first = object.stream().findFirst().orElse(null);
        if (first == null) {
            return;
        }
        BaseFieldEncryption fieldEncryption = createFieldEncryption(first);
        if (fieldEncryption == null) {
            return;
        }
        fieldEncryption.decryptList(object);
    }
}
