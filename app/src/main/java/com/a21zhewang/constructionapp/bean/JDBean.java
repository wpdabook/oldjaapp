package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 10430 on 2018/5/29.
 */

public class JDBean implements Parcelable {
    /**
     * recordID : 65edee0d-bb73-4bc4-b24a-55e7207d9274
     * projectID : GC10001
     * title : 测试内容标题211:37
     * etpShortName : 总承包
     * etpID : QY201704191139
     * monthPercentage : 80
     * recordDate : 2017-07-23 00:00:00
     * proShortName : 绿地636项目
     * totalPercentage : 70
     * createUserID : rzx
     * createUserName : rzx
     * createTime : 2018-05-29 11:37:57
     * projectPhase : [{"recordRLID":"52ea5861-d719-4369-af8a-386213791274","dicID":"PP01001","dicName":"桩基工程","selfIndex":1,"planStartDate":"2018-05-09","planEndDate":"2018-05-19","actuallyStartDate":"2018-05-09","actuallyEndDate":"2018-05-24","completionRate":"80","recordDesc":"无描述1"},{"recordRLID":"f08e363e-59cc-4cd1-8f74-187bb89782c3","dicID":"PP01002","dicName":"基坑工程","selfIndex":2,"planStartDate":"2018-05-19","planEndDate":"2018-06-08","actuallyStartDate":"2018-05-19","actuallyEndDate":"2018-06-13","completionRate":"80","recordDesc":"无描述2"}]
     */

    private String recordID;
    private String projectID;
    private String title;
    private String etpShortName;
    private String etpID;
    private String monthPercentage;
    private String recordDate;
    private String proShortName;
    private String totalPercentage;
    private String createUserID;
    private String createUserName;
    private String createTime;
    private List<ProjectPhase> projectPhase;

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getMonthPercentage() {
        return monthPercentage;
    }

    public void setMonthPercentage(String monthPercentage) {
        this.monthPercentage = monthPercentage;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getProShortName() {
        return proShortName;
    }

    public void setProShortName(String proShortName) {
        this.proShortName = proShortName;
    }

    public String getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(String totalPercentage) {
        this.totalPercentage = totalPercentage;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ProjectPhase> getProjectPhase() {
        return projectPhase;
    }

    public void setProjectPhase(List<ProjectPhase> projectPhase) {
        this.projectPhase = projectPhase;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recordID);
        dest.writeString(this.projectID);
        dest.writeString(this.title);
        dest.writeString(this.etpShortName);
        dest.writeString(this.etpID);
        dest.writeString(this.monthPercentage);
        dest.writeString(this.recordDate);
        dest.writeString(this.proShortName);
        dest.writeString(this.totalPercentage);
        dest.writeString(this.createUserID);
        dest.writeString(this.createUserName);
        dest.writeString(this.createTime);
        dest.writeTypedList(this.projectPhase);
    }

    public JDBean() {
    }

    protected JDBean(Parcel in) {
        this.recordID = in.readString();
        this.projectID = in.readString();
        this.title = in.readString();
        this.etpShortName = in.readString();
        this.etpID = in.readString();
        this.monthPercentage = in.readString();
        this.recordDate = in.readString();
        this.proShortName = in.readString();
        this.totalPercentage = in.readString();
        this.createUserID = in.readString();
        this.createUserName = in.readString();
        this.createTime = in.readString();
        this.projectPhase = in.createTypedArrayList(ProjectPhase.CREATOR);
    }

    public static final Parcelable.Creator<JDBean> CREATOR = new Parcelable.Creator<JDBean>() {
        @Override
        public JDBean createFromParcel(Parcel source) {
            return new JDBean(source);
        }

        @Override
        public JDBean[] newArray(int size) {
            return new JDBean[size];
        }
    };
}
