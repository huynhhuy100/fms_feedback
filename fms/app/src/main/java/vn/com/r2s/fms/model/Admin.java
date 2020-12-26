package vn.com.r2s.fms.model;

public class Admin {
    String userName;
    String email;
    String name;
    String password;

    public Admin(String userName, String email, String name, String password) {
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Admin() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
