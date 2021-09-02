package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/8/15.
 */

public class TotalRankInfo {

    private String projectID;
    private String projectName;
    private String dicID;
    private String dicName;
    private double keyCompleteRate;
    private double sore;

    /*整改率*/
    private String  etpID;
    private String  etpShortName;


    /*危大工程*/
    private String proShortName;
    private int dangerNum;
    private int checkNum;
    private int checkAllNum;
    private double rate;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public String getProShortName() {
        return proShortName;
    }

    public void setProShortName(String proShortName) {
        this.proShortName = proShortName;
    }

    public int getDangerNum() {
        return dangerNum;
    }

    public void setDangerNum(int dangerNum) {
        this.dangerNum = dangerNum;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public int getCheckAllNum() {
        return checkAllNum;
    }

    public void setCheckAllNum(int checkAllNum) {
        this.checkAllNum = checkAllNum;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public double getKeyCompleteRate() {
        return keyCompleteRate;
    }

    public void setKeyCompleteRate(double keyCompleteRate) {
        this.keyCompleteRate = keyCompleteRate;
    }

    public double getSore() {
        return sore;
    }

    public void setSore(double sore) {
        this.sore = sore;
    }
}
