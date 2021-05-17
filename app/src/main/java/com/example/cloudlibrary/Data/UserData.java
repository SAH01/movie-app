package com.example.cloudlibrary.Data;

public class UserData {
    private String user_name;
    private String user_password;
    private String user_phone;
    private String user_email;
    private String user_date;
    private long user_mesc;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public long getUser_mesc() {
        return user_mesc;
    }

    public void setUser_mesc(long user_mesc) {
        this.user_mesc = user_mesc;
    }
    public UserData(String user_name, String user_password, String user_phone, String user_date, long user_mesc) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_date = user_date;
        this.user_mesc = user_mesc;
    }
}
