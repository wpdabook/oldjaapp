package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/3/19.
 */

public class InspectionListBean {
    private String projectID;
    private String proShortName;
    private String dataType;
    private String createTime;
    private String checkTime;
    private int riskCount;
    private String recordID;
    private Double inspectionProgress;
    private String creatorName;
    private String creatorUnit;
    private String etpTypeName;
    private String checkCategory;
    private String lookupTime;
    private String approveType;
    private String area;
    private boolean isAddNotice;
    private boolean canMakeEasyNotice;
    private int depotLevel;

    public int getDepotLevel() {
        return depotLevel;
    }

    public void setDepotLevel(int depotLevel) {
        this.depotLevel = depotLevel;
    }

    public boolean isCanMakeEasyNotice() {
        return canMakeEasyNotice;
    }

    public void setCanMakeEasyNotice(boolean canMakeEasyNotice) {
        this.canMakeEasyNotice = canMakeEasyNotice;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProShortName() {
        return proShortName;
    }

    public void setProShortName(String proShortName) {
        this.proShortName = proShortName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public int getRiskCount() {
        return riskCount;
    }

    public void setRiskCount(int riskCount) {
        this.riskCount = riskCount;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Double getInspectionProgress() {
        return inspectionProgress;
    }

    public void setInspectionProgress(Double inspectionProgress) {
        this.inspectionProgress = inspectionProgress;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorUnit() {
        return creatorUnit;
    }

    public void setCreatorUnit(String creatorUnit) {
        this.creatorUnit = creatorUnit;
    }

    public String getEtpTypeName() {
        return etpTypeName;
    }

    public void setEtpTypeName(String etpTypeName) {
        this.etpTypeName = etpTypeName;
    }

    public String getCheckCategory() {
        return checkCategory;
    }

    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    public String getLookupTime() {
        return lookupTime;
    }

    public void setLookupTime(String lookupTime) {
        this.lookupTime = lookupTime;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public boolean isAddNotice() {
        return isAddNotice;
    }

    public void setAddNotice(boolean addNotice) {
        isAddNotice = addNotice;
    }
}
