package com.example.chatroom;

public class Users {
    String profpic, username, mail, pass, uid, lstmsg, status, phone;

    public Users() {}

    public Users(String profpic, String username, String mail, String pass, String uid, String lstmsg, String status, String phone) {
        this.profpic = profpic;
        this.username = username;
        this.mail = mail;
        this.pass = pass;
        this.uid = uid;
        this.lstmsg = lstmsg;
        this.status = status;
        this.phone = phone;
    }

    public Users(String username, String mail, String pass, String phone) {
        this.username = username;
        this.mail = mail;
        this.pass = pass;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfpic() {
        return profpic;
    }

    public void setProfpic(String profpic) {
        this.profpic = profpic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public String getLstmsg() {
        return lstmsg;
    }

    public void setLstmsg(String lstmsg) {
        this.lstmsg = lstmsg;
    }
}
