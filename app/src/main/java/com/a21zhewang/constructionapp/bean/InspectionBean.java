package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/10/29.
 */

public class InspectionBean {
    private String recordRLID;
    private String itemName;
    private String itemID;
    private int troubleCount;
    private int checkStatus;

    /*程序检查历史-数据*/
    private int type;
    private String checkRecordID;
    private String responsibleUnit;
    private String responsible;
    private boolean isBack;
    private String url;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
    }

    public String getRecordRLID() {
        return recordRLID;
    }

    public void setRecordRLID(String recordRLID) {
        this.recordRLID = recordRLID;
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public int getTroubleCount() {
        return troubleCount;
    }

    public void setTroubleCount(int troubleCount) {
        this.troubleCount = troubleCount;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCheckRecordID() {
        return checkRecordID;
    }

    public void setCheckRecordID(String checkRecordID) {
        this.checkRecordID = checkRecordID;
    }

    public String getResponsibleUnit() {
        return responsibleUnit;
    }

    public void setResponsibleUnit(String responsibleUnit) {
        this.responsibleUnit = responsibleUnit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
