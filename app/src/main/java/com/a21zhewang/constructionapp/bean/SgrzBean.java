package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhewang_ljw on 2017/4/27.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SgrzBean implements Parcelable {

    /**
     * content : 安全生产台式可控
     * createUserID : lisi
     * createUserName : 李四
     * etpID : QY201704191154
     * etpShortName : 土建一部
     * projectID : GC10001
     * proShortName : 绿地636项目
     * recordID : 66aa7ffd-9ced-4878-a770-dbfb3e4eb909
     * recordTime : 2017-04-26
     * remarks : 明天计划开始4层施工
     * title : 绿地636项目2017-04-26施工日志
     * weather : 气温：20度，风速：2级
     */

    private String content;
    private String createUserID;
    private String createUserName;
    private String etpID;
    private String etpShortName;
    private String projectID;
    private String proShortName;
    private String recordID;
    private String recordTime;
    private String remarks;
    private String title;
    private String weather;
    private boolean isRead;

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUserID() {
        return createUserID;
    }

    public void setCreateUserID(String createUserID) {
        this.createUserID = createUserID;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
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

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public SgrzBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.createUserID);
        dest.writeString(this.createUserName);
        dest.writeString(this.etpID);
        dest.writeString(this.etpShortName);
        dest.writeString(this.projectID);
        dest.writeString(this.proShortName);
        dest.writeString(this.recordID);
        dest.writeString(this.recordTime);
        dest.writeString(this.remarks);
        dest.writeString(this.title);
        dest.writeString(this.weather);
        dest.writeByte(this.isRead ? (byte) 1 : (byte) 0);
    }

    protected SgrzBean(Parcel in) {
        this.content = in.readString();
        this.createUserID = in.readString();
        this.createUserName = in.readString();
        this.etpID = in.readString();
        this.etpShortName = in.readString();
        this.projectID = in.readString();
        this.proShortName = in.readString();
        this.recordID = in.readString();
        this.recordTime = in.readString();
        this.remarks = in.readString();
        this.title = in.readString();
        this.weather = in.readString();
        this.isRead = in.readByte() != 0;
    }

    public static final Creator<SgrzBean> CREATOR = new Creator<SgrzBean>() {
        @Override
        public SgrzBean createFromParcel(Parcel source) {
            return new SgrzBean(source);
        }

        @Override
        public SgrzBean[] newArray(int size) {
            return new SgrzBean[size];
        }
    };
}
