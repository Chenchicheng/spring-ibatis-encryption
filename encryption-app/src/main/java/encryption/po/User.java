package encryption.po;

import encryption.annotation.AesSensitiveField;
import encryption.annotation.Base64SensitiveField;
import encryption.annotation.SensitiveData;

@SensitiveData
public class User {

    private Long id;

    @AesSensitiveField
    private String name;

    @Base64SensitiveField
    private String phone;

    @AesSensitiveField
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}