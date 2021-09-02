package com.a21zhewang.constructionapp.bean;

/**
 * Created by 10430 on 2018/5/29.
 */

public  class ProjectPhaseBean {
    /**
     * recordRLID : 52ea5861-d719-4369-af8a-386213791274
     * dicID : PP01001
     * dicName : 桩基工程
     * selfIndex : 1
     * planStartDate : 2018-05-09
     * planEndDate : 2018-05-19
     * actuallyStartDate : 2018-05-09
     * actuallyEndDate : 2018-05-24
     * completionRate : 80
     * recordDesc : 无描述1
     */

    private String recordRLID;
    private String dicID;
    private String dicName;
    private int selfIndex;
    private String planStartDate;
    private String planEndDate;
    private String actuallyStartDate;
    private String actuallyEndDate;
    private String completionRate;
    private String recordDesc;

    public String getRecordRLID() {
        return recordRLID;
    }

    public void setRecordRLID(String recordRLID) {
        this.recordRLID = recordRLID;
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

    public int getSelfIndex() {
        return selfIndex;
    }

    public void setSelfIndex(int selfIndex) {
        this.selfIndex = selfIndex;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getActuallyStartDate() {
        return actuallyStartDate;
    }

    public void setActuallyStartDate(String actuallyStartDate) {
        this.actuallyStartDate = actuallyStartDate;
    }

    public String getActuallyEndDate() {
        return actuallyEndDate;
    }

    public void setActuallyEndDate(String actuallyEndDate) {
        this.actuallyEndDate = actuallyEndDate;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }
}