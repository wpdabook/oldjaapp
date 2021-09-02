package com.a21zhewang.constructionapp.bean;

public class RiskQueryLaunProjectList {

    private String projectId;
    private String projectName;
    /*隐患发起数*/
    private int riskQueryLaun;
    /*隐患整改数*/
    private int riskQueryPass;

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

    public int getRiskQueryLaun() {
        return riskQueryLaun;
    }

    public void setRiskQueryLaun(int riskQueryLaun) {
        this.riskQueryLaun = riskQueryLaun;
    }

    public int getRiskQueryPass() {
        return riskQueryPass;
    }

    public void setRiskQueryPass(int riskQueryPass) {
        this.riskQueryPass = riskQueryPass;
    }
}
