package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/11/21.
 */

public class SupervisorInfo {

    private String userId;
    private String userName;
    private String dicID;
    private String dicName;
    private double SumCheckrate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public double getSumCheckrate() {
        return SumCheckrate;
    }

    public void setSumCheckrate(double sumCheckrate) {
        SumCheckrate = sumCheckrate;
    }
}
