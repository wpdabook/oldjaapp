package com.a21zhewang.constructionapp.bean;

import com.nostra13.universalimageloader.utils.L;

import java.util.List;

public class RiskHistory {
    /*文明施工整改 已整改*/
    private int civilizedPass;
    /*文明施工整改率*/
    private double civilizedRate;
    /*文明施工隐患*/
    private int civilizedTotal;
    /*重点检查-日 已排查项*/
    private int keyExamDayItemPass;
    /*日检查率*/
    private double keyExamDayItemRate;
    /*重点检查-日 总排查项*/
    private int keyExamDayItemTotal;
    /*重点检查-月 隐患数*/
    private int keyExamMonthItemPass;
    /*月检查率*/
    private double keyExamMonthItemRate;
    /*重点检查-月 总排查项*/
    private int keyExamMonthItemTotal;
    /*重点检查-周 隐患数*/
    private int keyExamWeekItemPass;
    /*周检查率*/
    private double keyExamWeekItemRate;
    /*重点检查-周 总排查项*/
    private int keyExamWeekItemTotal;
    /*质量整改 已整改*/
    private int qualityPass;
    /*质量整改率*/
    private double qualityRate;
    /*质量整改总数*/
    private int qualityTotal;
    /*整改率 （总）*/
    private double riskQueryRate;
    /*风险排查 （总）*/
    private int riskQueryTotal;
    /*安全生产已整改*/
    private int safetyPass;
    /*安全生产整改率*/
    private double safetyRate;
    /*安全生产总数*/
    private int safetyTotal;
    /*本（周、月）隐患总数*/
    private int riskQueryLaunTimeTotal;
    /*本（周、月）使用人总数*/
    private int riskQueryTimeUserTotal;
    /*本周或本月隐患数列表*/
    private List<RiskQueryLaunProjectList> riskQueryLaunProjectList;
    /*本周或本月隐患数*/
    private List<RiskQueryLaunTimeList> riskQueryLaunTimeList;
    /*本周或本月使用人数*/
    private List<RiskQueryTimeUserList> riskQueryTimeUserList;

    public void setCivilizedRate(double civilizedRate) {
        this.civilizedRate = civilizedRate;
    }

    public void setRiskQueryRate(double riskQueryRate) {
        this.riskQueryRate = riskQueryRate;
    }

    public void setSafetyRate(double safetyRate) {
        this.safetyRate = safetyRate;
    }

    public int getCivilizedPass() {
        return civilizedPass;
    }

    public void setCivilizedPass(int civilizedPass) {
        this.civilizedPass = civilizedPass;
    }

    public double getCivilizedRate() {
        return civilizedRate;
    }

    public void setCivilizedRate(int civilizedRate) {
        this.civilizedRate = civilizedRate;
    }

    public int getCivilizedTotal() {
        return civilizedTotal;
    }

    public void setCivilizedTotal(int civilizedTotal) {
        this.civilizedTotal = civilizedTotal;
    }

    public int getKeyExamDayItemPass() {
        return keyExamDayItemPass;
    }

    public void setKeyExamDayItemPass(int keyExamDayItemPass) {
        this.keyExamDayItemPass = keyExamDayItemPass;
    }

    public int getKeyExamDayItemTotal() {
        return keyExamDayItemTotal;
    }

    public void setKeyExamDayItemTotal(int keyExamDayItemTotal) {
        this.keyExamDayItemTotal = keyExamDayItemTotal;
    }

    public int getKeyExamMonthItemPass() {
        return keyExamMonthItemPass;
    }

    public void setKeyExamMonthItemPass(int keyExamMonthItemPass) {
        this.keyExamMonthItemPass = keyExamMonthItemPass;
    }

    public int getKeyExamMonthItemTotal() {
        return keyExamMonthItemTotal;
    }

    public void setKeyExamMonthItemTotal(int keyExamMonthItemTotal) {
        this.keyExamMonthItemTotal = keyExamMonthItemTotal;
    }

    public int getKeyExamWeekItemPass() {
        return keyExamWeekItemPass;
    }

    public void setKeyExamWeekItemPass(int keyExamWeekItemPass) {
        this.keyExamWeekItemPass = keyExamWeekItemPass;
    }

    public int getKeyExamWeekItemTotal() {
        return keyExamWeekItemTotal;
    }

    public void setKeyExamWeekItemTotal(int keyExamWeekItemTotal) {
        this.keyExamWeekItemTotal = keyExamWeekItemTotal;
    }

    public int getQualityPass() {
        return qualityPass;
    }

    public void setQualityPass(int qualityPass) {
        this.qualityPass = qualityPass;
    }

    public double getQualityRate() {
        return qualityRate;
    }

    public void setQualityRate(int qualityRate) {
        this.qualityRate = qualityRate;
    }

    public int getQualityTotal() {
        return qualityTotal;
    }

    public void setQualityTotal(int qualityTotal) {
        this.qualityTotal = qualityTotal;
    }

    public double getRiskQueryRate() {
        return riskQueryRate;
    }

    public void setRiskQueryRate(int riskQueryRate) {
        this.riskQueryRate = riskQueryRate;
    }

    public int getRiskQueryTotal() {
        return riskQueryTotal;
    }

    public void setRiskQueryTotal(int riskQueryTotal) {
        this.riskQueryTotal = riskQueryTotal;
    }

    public int getSafetyPass() {
        return safetyPass;
    }

    public void setSafetyPass(int safetyPass) {
        this.safetyPass = safetyPass;
    }

    public double getSafetyRate() {
        return safetyRate;
    }

    public void setSafetyRate(int safetyRate) {
        this.safetyRate = safetyRate;
    }

    public int getSafetyTotal() {
        return safetyTotal;
    }

    public void setSafetyTotal(int safetyTotal) {
        this.safetyTotal = safetyTotal;
    }

    public List<RiskQueryLaunProjectList> getRiskQueryLaunProjectList() {
        return riskQueryLaunProjectList;
    }

    public void setRiskQueryLaunProjectList(List<RiskQueryLaunProjectList> riskQueryLaunProjectList) {
        this.riskQueryLaunProjectList = riskQueryLaunProjectList;
    }

    public List<RiskQueryLaunTimeList> getRiskQueryLaunTimeList() {
        return riskQueryLaunTimeList;
    }

    public void setRiskQueryLaunTimeList(List<RiskQueryLaunTimeList> riskQueryLaunTimeList) {
        this.riskQueryLaunTimeList = riskQueryLaunTimeList;
    }

    public List<RiskQueryTimeUserList> getRiskQueryTimeUserList() {
        return riskQueryTimeUserList;
    }

    public void setRiskQueryTimeUserList(List<RiskQueryTimeUserList> riskQueryTimeUserList) {
        this.riskQueryTimeUserList = riskQueryTimeUserList;
    }

    public double getKeyExamDayItemRate() {
        return keyExamDayItemRate;
    }

    public void setKeyExamDayItemRate(double keyExamDayItemRate) {
        this.keyExamDayItemRate = keyExamDayItemRate;
    }

    public double getKeyExamMonthItemRate() {
        return keyExamMonthItemRate;
    }

    public void setKeyExamMonthItemRate(double keyExamMonthItemRate) {
        this.keyExamMonthItemRate = keyExamMonthItemRate;
    }

    public double getKeyExamWeekItemRate() {
        return keyExamWeekItemRate;
    }

    public void setKeyExamWeekItemRate(double keyExamWeekItemRate) {
        this.keyExamWeekItemRate = keyExamWeekItemRate;
    }

    public void setQualityRate(double qualityRate) {
        this.qualityRate = qualityRate;
    }

    public int getRiskQueryLaunTimeTotal() {
        return riskQueryLaunTimeTotal;
    }

    public void setRiskQueryLaunTimeTotal(int riskQueryLaunTimeTotal) {
        this.riskQueryLaunTimeTotal = riskQueryLaunTimeTotal;
    }

    public int getRiskQueryTimeUserTotal() {
        return riskQueryTimeUserTotal;
    }

    public void setRiskQueryTimeUserTotal(int riskQueryTimeUserTotal) {
        this.riskQueryTimeUserTotal = riskQueryTimeUserTotal;
    }
}
