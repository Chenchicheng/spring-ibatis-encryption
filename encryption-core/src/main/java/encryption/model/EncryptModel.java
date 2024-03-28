package encryption.model;

import encryption.interceptor.BaseFieldEncryption;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * @author chenchicheng
 * @date 2024/3/27
 */
public class EncryptModel<T>  {

    private T obj;
    private List<Field> matchedFields;
    private BaseFieldEncryption baseFieldEncryption;

    public EncryptModel(T obj, BaseFieldEncryption baseFieldEncryption, List<Field> matchedFields) {
        this.baseFieldEncryption = baseFieldEncryption;
        this.matchedFields = matchedFields;
        this.obj = obj;
    }

    public List<Field> getMatchedFields() {
        return matchedFields;
    }

    public void setMatchedFields(List<Field> matchedFields) {
        this.matchedFields = matchedFields;
    }

    public BaseFieldEncryption getBaseFieldEncryption() {
        return baseFieldEncryption;
    }

    public void setBaseFieldEncryption(BaseFieldEncryption baseFieldEncryption) {
        this.baseFieldEncryption = baseFieldEncryption;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public void encrypt() {
        if (matchedFields == null || matchedFields.isEmpty() || obj == null || baseFieldEncryption == null) {
            return;
        }
        matchedFields.parallelStream().forEach(s -> baseFieldEncryption.encryptField(obj, s));
    }

    public void decrypt() {
        if (matchedFields == null || matchedFields.isEmpty() || obj == null || baseFieldEncryption == null) {
            return;
        }
        matchedFields.parallelStream().forEach(s -> baseFieldEncryption.decryptField(obj, s));
    }

    public void encryptList() {
        if (matchedFields == null || matchedFields.isEmpty() || obj == null || baseFieldEncryption == null) {
            return;
        }
        baseFieldEncryption.encryptList(Collections.singletonList(this));
    }

    public void decryptList() {
        if (matchedFields == null || matchedFields.isEmpty() || obj == null || baseFieldEncryption == null) {
            return;
        }
        baseFieldEncryption.decryptList(Collections.singletonList(this));
    }
}
