package vn.com.r2s.fms.model;

public class Trainee {
    String userName;
    String activationCode;
    String address;
    String email;
    String isActive;
    String name;
    String password;
    String phone;
    String resetPasswordCode;

    public Trainee(String userName, String activationCode, String address, String email, String isActive, String name, String password, String phone, String resetPasswordCode) {
        this.userName = userName;
        this.activationCode = activationCode;
        this.address = address;
        this.email = email;
        this.isActive = isActive;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.resetPasswordCode = resetPasswordCode;
    }

    public Trainee(){

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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
