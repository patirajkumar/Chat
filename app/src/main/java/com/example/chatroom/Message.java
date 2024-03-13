package com.example.chatroom;

public class Message {

    String uid,msg;
    Long timestamp;

    public Message() {}

    public Message(String uid, String msg, Long timestamp) {
        this.uid = uid;
        this.msg = msg;
        this.timestamp = timestamp;
    }

    public Message(String uid, String msg) {
        this.uid = uid;
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
