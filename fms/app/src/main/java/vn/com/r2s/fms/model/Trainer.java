package vn.com.r2s.fms.model;

public class Trainer {
    String userName;
    String activationCode;
    String address;
    String email;
    String idSkill;
    String isActive;
    String isReceiveNotification;
    String name;
    String password;
    String phone;
    String resetPasswordCode;

    public Trainer(String userName, String activationCode, String address, String email, String idSkill, String isActive, String isReceiveNotification, String name, String password, String phone, String resetPasswordCode) {
        this.userName = userName;
        this.activationCode = activationCode;
        this.address = address;
        this.email = email;
        this.idSkill = idSkill;
        this.isActive = isActive;
        this.isReceiveNotification = isReceiveNotification;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.resetPasswordCode = resetPasswordCode;
    }

    public Trainer() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(String idSkill) {
        this.idSkill = idSkill;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsReceiveNotification() {
        return isReceiveNotification;
    }

    public void setIsReceiveNotification(String isReceiveNotification) {
        this.isReceiveNotification = isReceiveNotification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }
}
