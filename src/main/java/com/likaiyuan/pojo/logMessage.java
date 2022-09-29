package com.likaiyuan.pojo;

public class logMessage {
    private String username;
    private  String date;
    private  String log;
    private  String myID;
    private  String otherID;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setMyID(String myID) {
        this.myID = myID;
    }

    public void setOtherID(String otherID) {
        this.otherID = otherID;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getLog() {
        return log;
    }

    public String getMyID() {
        return myID;
    }

    public String getOtherID() {
        return otherID;
    }
}
