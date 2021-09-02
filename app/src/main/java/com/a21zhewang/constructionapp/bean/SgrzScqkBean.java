package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhewang_ljw on 2017/4/27.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SgrzScqkBean implements Parcelable {

    /**
     * floor : 3层
     * number : 50
     * projectAreaName : 顶模
     * recordDesc : 按计划执行
     * subProjectID : SubPj10004
     * subProjectName : 剪力墙砼浇筑
     */

    private String floor;
    private String number;
    private String projectAreaName;
    private String projectAreaID;
    private String recordDesc;
    private String subProjectID;
    private String subProjectName;
    private String elevation;

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getProjectAreaID() {
        return projectAreaID;
    }

    public void setProjectAreaID(String projectAreaID) {
        this.projectAreaID = projectAreaID;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getSubProjectID() {
        return subProjectID;
    }

    public void setSubProjectID(String subProjectID) {
        this.subProjectID = subProjectID;
    }

    public String getSubProjectName() {
        return subProjectName;
    }

    public void setSubProjectName(String subProjectName) {
        this.subProjectName = subProjectName;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.floor);
        dest.writeString(this.number);
        dest.writeString(this.projectAreaName);
        dest.writeString(this.projectAreaID);
        dest.writeString(this.recordDesc);
        dest.writeString(this.subProjectID);
        dest.writeString(this.subProjectName);
        dest.writeString(this.elevation);
    }

    public SgrzScqkBean() {
    }

    protected SgrzScqkBean(Parcel in) {
        this.floor = in.readString();
        this.number = in.readString();
        this.projectAreaName = in.readString();
        this.projectAreaID = in.readString();
        this.recordDesc = in.readString();
        this.subProjectID = in.readString();
        this.subProjectName = in.readString();
        this.elevation = in.readString();
    }

    public static final Creator<SgrzScqkBean> CREATOR = new Creator<SgrzScqkBean>() {
        @Override
        public SgrzScqkBean createFromParcel(Parcel source) {
            return new SgrzScqkBean(source);
        }

        @Override
        public SgrzScqkBean[] newArray(int size) {
            return new SgrzScqkBean[size];
        }
    };
}
