package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/8/7.
 */

public class RiskDetailInfo {

    private String checkRegionId;
    private String checkRegionName;
    private String createDate;
    private String createId;
    private String createName;
    private String currFloor;
    private String dicID;
    private String dicName;
    private String id;
    private String projectId;
    private String projectName;
    private String projectType;
    private int status;
    private String title;
    private String totalFloor;
    private String riskReviewReson;
    private List<ItemRiskList> itemRiskList;
    private List<ItemReadList> itemReadList;
    private List<SubDivisionBean> divisionList;

    public String getRiskReviewReson() {
        return riskReviewReson;
    }

    public void setRiskReviewReson(String riskReviewReson) {
        this.riskReviewReson = riskReviewReson;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCheckRegionId() {
        return checkRegionId;
    }

    public void setCheckRegionId(String checkRegionId) {
        this.checkRegionId = checkRegionId;
    }

    public String getCheckRegionName() {
        return checkRegionName;
    }

    public void setCheckRegionName(String checkRegionName) {
        this.checkRegionName = checkRegionName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCurrFloor() {
        return currFloor;
    }

    public void setCurrFloor(String currFloor) {
        this.currFloor = currFloor;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(String totalFloor) {
        this.totalFloor = totalFloor;
    }

    public List<ItemRiskList> getItemRiskList() {
        return itemRiskList;
    }

    public void setItemRiskList(List<ItemRiskList> itemRiskList) {
        this.itemRiskList = itemRiskList;
    }

    public List<ItemReadList> getItemReadList() {
        return itemReadList;
    }

    public void setItemReadList(List<ItemReadList> itemReadList) {
        this.itemReadList = itemReadList;
    }

    public List<SubDivisionBean> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<SubDivisionBean> divisionList) {
        this.divisionList = divisionList;
    }
}
