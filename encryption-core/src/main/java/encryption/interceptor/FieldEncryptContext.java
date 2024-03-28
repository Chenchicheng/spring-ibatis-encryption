package encryption.interceptor;

import encryption.model.EncryptModel;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenchicheng
 * @date 2023/8/12
 */
@Component
public class FieldEncryptContext {

    @Resource
    private ApplicationContext applicationContext;

    private <T> List<EncryptModel<T>> createFieldEncryption(T object) {
        if (object == null) {
            return Collections.emptyList();
        }
        Map<String, BaseFieldEncryption> beansOfType = applicationContext.getBeansOfType(BaseFieldEncryption.class);
        Collection<BaseFieldEncryption> handleChains = beansOfType.values();
        return handleChains.parallelStream().map(s -> s.getFieldEncryptInfo(object)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public <T> void encrypt(T object) {
        List<EncryptModel<T>> encryptModel = createFieldEncryption(object);
        if (encryptModel == null || encryptModel.isEmpty()) {
            return;
        }
        encryptModel.parallelStream().forEach(EncryptModel::encrypt);
    }

    public <T> void decrypt(T object) {
        List<EncryptModel<T>> encryptModel = createFieldEncryption(object);
        if (encryptModel == null || encryptModel.isEmpty()) {
            return;
        }
        encryptModel.parallelStream().forEach(EncryptModel::decrypt);
    }

    public <T> void encryptList(List<T> object) {
        if (object == null || object.isEmpty()) {
            return;
        }
        object.parallelStream().map(this::createFieldEncryption).flatMap(List::parallelStream).forEach(EncryptModel::encryptList);
    }

    public <T> void decryptList(List<T> object) {
        if (object == null || object.isEmpty()) {
            return;
        }
        object.parallelStream().map(this::createFieldEncryption).flatMap(List::parallelStream).forEach(EncryptModel::decryptList);
    }
}
