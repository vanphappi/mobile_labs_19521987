package com.example.moblie_labs_19521987;

public class users {
    private String fullname;
    private String password;
    private String phone;
    private String username;

    public users(String fullname, String password, String phone, String username) {
        this.fullname = fullname;
        this.password = password;
        this.phone = phone;
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
