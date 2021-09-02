package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/18.
 *
 * @auto
 */

public class ProjectArea implements Parcelable {
    private String projectAreaID;
    private String projectAreaName;
    private List<Etp> etpInfoList;
    private List<ProjectArea> projectAreaList;
    private List<SubProjectBean> subProjectList;
    private List<User> userInfoList;

    public List<User> getUserInfoList() {
        return userInfoList;
    }

    public void setUserInfoList(List<User> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public String getProjectAreaID() {
        return projectAreaID;
    }

    public void setProjectAreaID(String projectAreaID) {
        this.projectAreaID = projectAreaID;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public List<Etp> getEtpInfoList() {
        return etpInfoList;
    }

    public void setEtpInfoList(List<Etp> etpInfoList) {
        this.etpInfoList = etpInfoList;
    }

    public List<ProjectArea> getProjectAreaList() {
        return projectAreaList;
    }

    public void setProjectAreaList(List<ProjectArea> projectAreaList) {
        this.projectAreaList = projectAreaList;
    }

    public List<SubProjectBean> getSubProjectList() {
        return subProjectList;
    }

    public void setSubProjectList(List<SubProjectBean> subProjectList) {
        this.subProjectList = subProjectList;
    }




    public ProjectArea() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectAreaID);
        dest.writeString(this.projectAreaName);
        dest.writeTypedList(this.etpInfoList);
        dest.writeList(this.projectAreaList);
        dest.writeTypedList(this.subProjectList);
        dest.writeTypedList(this.userInfoList);
    }

    protected ProjectArea(Parcel in) {
        this.projectAreaID = in.readString();
        this.projectAreaName = in.readString();
        this.etpInfoList = in.createTypedArrayList(Etp.CREATOR);
        this.projectAreaList = new ArrayList<ProjectArea>();
        in.readList(this.projectAreaList, ProjectArea.class.getClassLoader());
        this.subProjectList = in.createTypedArrayList(SubProjectBean.CREATOR);
        this.userInfoList = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<ProjectArea> CREATOR = new Parcelable.Creator<ProjectArea>() {
        @Override
        public ProjectArea createFromParcel(Parcel source) {
            return new ProjectArea(source);
        }

        @Override
        public ProjectArea[] newArray(int size) {
            return new ProjectArea[size];
        }
    };
}
