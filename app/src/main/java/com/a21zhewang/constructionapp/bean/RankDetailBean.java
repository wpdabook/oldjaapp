package com.a21zhewang.constructionapp.bean;

public class RankDetailBean {
    /*项目ID*/
    private String projectId;
    /*项目名称*/
    private String projectName;
    /*项目类型*/
    private String projectTypeName;
    /*项目经理*/
    private String manager;
    /*建设单位*/
    private String buildEtpName;
    /*施工单位*/
    private String consEtpName;
    /*监理单位*/
    private String supeEtpName;
    /*总监理*/
    private String supeContacts;
    /*排名*/
    private int rank;
    /*建设单位完成率*/
    private double buildRate;
    /*监理单位完成率*/
    private double superRate;
    /*施工单位完成率*/
    private double constrRate;
    /*重点检查完成率*/
    private double keyExamItemRate;
    /*安全隐患整改项*/
    private int safetyTotal;
    /*质量问题整改项*/
    private int qualityTotal;
    /*文明施工整改项*/
    private int civilizedTotal;
    /*今日隐患排查项*/
    private int riskQueryTotal;
    /*累计安全隐患整改项*/
    private int safetyCumTotal;
    /*累计质量隐患整改项*/
    private int qualityCumTotal;
    /*累计文明施工隐患整改项*/
    private int civilizedCumTotal;
    /*累计隐患排查项*/
    private int riskQueryCumTotal;
    /*累计整改率*/
    private double riskQueryRate;
    /*累计安全隐患整改率*/
    private double safetyQueryRate;
    /*累计质量问题整改率*/
    private double qualityQueryRate;
    /*累计文明施工整改率*/
    private double civilizedQueryRate;
    /*重点检查-检查完成率*/
    private double keyCheckRate;
    /*重点检查-排查项*/
    private int hiddenTroub;
    /*重点检查-风险隐患*/
    private int riskDanger;
    /*重点检查-已整改*/
    private int rect;

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

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getBuildEtpName() {
        return buildEtpName;
    }

    public void setBuildEtpName(String buildEtpName) {
        this.buildEtpName = buildEtpName;
    }

    public String getConsEtpName() {
        return consEtpName;
    }

    public void setConsEtpName(String consEtpName) {
        this.consEtpName = consEtpName;
    }

    public String getSupeEtpName() {
        return supeEtpName;
    }

    public void setSupeEtpName(String supeEtpName) {
        this.supeEtpName = supeEtpName;
    }

    public String getSupeContacts() {
        return supeContacts;
    }

    public void setSupeContacts(String supeContacts) {
        this.supeContacts = supeContacts;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public double getBuildRate() {
        return buildRate;
    }

    public void setBuildRate(double buildRate) {
        this.buildRate = buildRate;
    }

    public double getSuperRate() {
        return superRate;
    }

    public void setSuperRate(double superRate) {
        this.superRate = superRate;
    }

    public double getConstrRate() {
        return constrRate;
    }

    public void setConstrRate(double constrRate) {
        this.constrRate = constrRate;
    }

    public double getKeyExamItemRate() {
        return keyExamItemRate;
    }

    public void setKeyExamItemRate(double keyExamItemRate) {
        this.keyExamItemRate = keyExamItemRate;
    }

    public int getSafetyTotal() {
        return safetyTotal;
    }

    public void setSafetyTotal(int safetyTotal) {
        this.safetyTotal = safetyTotal;
    }

    public int getQualityTotal() {
        return qualityTotal;
    }

    public void setQualityTotal(int qualityTotal) {
        this.qualityTotal = qualityTotal;
    }

    public int getCivilizedTotal() {
        return civilizedTotal;
    }

    public void setCivilizedTotal(int civilizedTotal) {
        this.civilizedTotal = civilizedTotal;
    }

    public int getRiskQueryTotal() {
        return riskQueryTotal;
    }

    public void setRiskQueryTotal(int riskQueryTotal) {
        this.riskQueryTotal = riskQueryTotal;
    }

    public int getSafetyCumTotal() {
        return safetyCumTotal;
    }

    public void setSafetyCumTotal(int safetyCumTotal) {
        this.safetyCumTotal = safetyCumTotal;
    }

    public int getQualityCumTotal() {
        return qualityCumTotal;
    }

    public void setQualityCumTotal(int qualityCumTotal) {
        this.qualityCumTotal = qualityCumTotal;
    }

    public int getCivilizedCumTotal() {
        return civilizedCumTotal;
    }

    public void setCivilizedCumTotal(int civilizedCumTotal) {
        this.civilizedCumTotal = civilizedCumTotal;
    }

    public int getRiskQueryCumTotal() {
        return riskQueryCumTotal;
    }

    public void setRiskQueryCumTotal(int riskQueryCumTotal) {
        this.riskQueryCumTotal = riskQueryCumTotal;
    }

    public double getRiskQueryRate() {
        return riskQueryRate;
    }

    public void setRiskQueryRate(double riskQueryRate) {
        this.riskQueryRate = riskQueryRate;
    }

    public double getSafetyQueryRate() {
        return safetyQueryRate;
    }

    public void setSafetyQueryRate(double safetyQueryRate) {
        this.safetyQueryRate = safetyQueryRate;
    }

    public double getQualityQueryRate() {
        return qualityQueryRate;
    }

    public void setQualityQueryRate(double qualityQueryRate) {
        this.qualityQueryRate = qualityQueryRate;
    }

    public double getCivilizedQueryRate() {
        return civilizedQueryRate;
    }

    public void setCivilizedQueryRate(double civilizedQueryRate) {
        this.civilizedQueryRate = civilizedQueryRate;
    }

    public double getKeyCheckRate() {
        return keyCheckRate;
    }

    public void setKeyCheckRate(double keyCheckRate) {
        this.keyCheckRate = keyCheckRate;
    }

    public int getHiddenTroub() {
        return hiddenTroub;
    }

    public void setHiddenTroub(int hiddenTroub) {
        this.hiddenTroub = hiddenTroub;
    }

    public int getRiskDanger() {
        return riskDanger;
    }

    public void setRiskDanger(int riskDanger) {
        this.riskDanger = riskDanger;
    }

    public int getRect() {
        return rect;
    }

    public void setRect(int rect) {
        this.rect = rect;
    }
}
