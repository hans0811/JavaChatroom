package com;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Personalinfor {
    private Long Id;
    private String Loginid;
    private String Nickname;
    private String Realname;
    private String Password;
    private Long Gender;
    private String Email;
    private String Phonenum;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getLoginid() {
        return Loginid;
    }

    public void setLoginid(String loginid) {
        Loginid = loginid;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getRealname() {
        return Realname;
    }

    public void setRealname(String realname) {
        Realname = realname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Long getGender() {
        return Gender;
    }

    public void setGender(Long gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhonenum() {
        return Phonenum;
    }

    public void setPhonenum(String phonenum) {
        Phonenum = phonenum;
    }
}
