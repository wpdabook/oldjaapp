package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/4/26.
 */

public class SubDivisionBean {

    private String Id;
    private String riskReportId;
    private String divisionId;
    private String divisionName;
    private String createUserId;
    private String createUserName;
    private String createDate;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRiskReportId() {
        return riskReportId;
    }

    public void setRiskReportId(String riskReportId) {
        this.riskReportId = riskReportId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
