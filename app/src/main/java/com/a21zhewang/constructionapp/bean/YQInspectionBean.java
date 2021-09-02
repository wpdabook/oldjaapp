package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2020/3/24.
 */

public class YQInspectionBean {

    private String itemName;
    private String itemID;
    private int troubleCount;
    private int checkStatus;
    private String riskValueType;
    private String checkContent;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemID() {
        return itemID;
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

    public String getRiskValueType() {
        return riskValueType;
    }

    public void setRiskValueType(String riskValueType) {
        this.riskValueType = riskValueType;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }
}
