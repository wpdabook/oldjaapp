package com.a21zhewang.constructionapp.bean;

/**
 * 本周或本月的隐患数列表
 */
public class RiskQueryLaunTimeList {

    private int beginTime;
    /*隐患数*/
    private int riskQueryTotal;

    public int getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(int beginTime) {
        this.beginTime = beginTime;
    }

    public int getRiskQueryTotal() {
        return riskQueryTotal;
    }

    public void setRiskQueryTotal(int riskQueryTotal) {
        this.riskQueryTotal = riskQueryTotal;
    }
}
