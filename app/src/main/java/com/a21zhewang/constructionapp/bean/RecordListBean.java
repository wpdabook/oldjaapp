package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/10/29.
 */

public class RecordListBean {

    /*程序检查类型-界数据*/
    private String sectionName;
    private String sectionID;
    private int uncheckCount;
    private int NotFitCount;

    public int getNotFitCount() {
        return NotFitCount;
    }

    public void setNotFitCount(int notFitCount) {
        NotFitCount = notFitCount;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public int getUncheckCount() {
        return uncheckCount;
    }

    public void setUncheckCount(int uncheckCount) {
        this.uncheckCount = uncheckCount;
    }


}
