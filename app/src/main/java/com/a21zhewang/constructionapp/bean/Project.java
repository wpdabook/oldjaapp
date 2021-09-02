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

public class Project implements Parcelable {
    private String projectID;
    private String projectShortName;
    private String projectType;
    private List<Project> projectList;
    private List<ProjectArea> projectAreaList;
    private List<Etp> etpInfoList;


    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectID() {
        return this.projectID;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getProjectShortName() {
        return this.projectShortName;
    }

    public void setProjectAreaList(List<ProjectArea> projectAreaList) {
        this.projectAreaList = projectAreaList;
    }

    public List<ProjectArea> getProjectAreaList() {
        return this.projectAreaList;
    }

    public List<Etp> getEtpInfoList() {
        return etpInfoList;
    }

    public void setEtpInfoList(List<Etp> etpInfoList) {
        this.etpInfoList = etpInfoList;
    }

    public Project() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectID);
        dest.writeString(this.projectShortName);
        dest.writeString(this.projectType);
        dest.writeTypedList(this.projectList);
        dest.writeTypedList(this.projectAreaList);
        dest.writeTypedList(this.etpInfoList);
    }

    protected Project(Parcel in) {
        this.projectID = in.readString();
        this.projectShortName = in.readString();
        this.projectType = in.readString();
        this.projectList = in.createTypedArrayList(Project.CREATOR);
        this.projectAreaList = in.createTypedArrayList(ProjectArea.CREATOR);
        this.etpInfoList = in.createTypedArrayList(Etp.CREATOR);
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel source) {
            return new Project(source);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };
}
