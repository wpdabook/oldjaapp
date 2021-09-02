package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/7/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class TagBean {


    /**
     * tagID : 1234567
     * apiName : AddCivilizedMsg
     * tagData :
     * tagDisc : 这是测试模拟的文明施工的电子标签
     */

    private String tagID;
    private String apiName;
    private String tagData;
    private String tagDisc;
    private  String tagType;

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getTagData() {
        return tagData;
    }

    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    public String getTagDisc() {
        return tagDisc;
    }

    public void setTagDisc(String tagDisc) {
        this.tagDisc = tagDisc;
    }
}
