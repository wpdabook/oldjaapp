package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/5.
 *
 * @auto  项目实体类
 */

public class ProjectBean implements Parcelable {

    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     */

    private String projectID;
    private String projectShortName;
    private List<ProjectBean> projectList;

    public ProjectBean() {
    }

    public ProjectBean(String projectID, String projectShortName) {
        this.projectID = projectID;
        this.projectShortName = projectShortName;
    }

    public ProjectBean(String projectID, String projectShortName, List<ProjectBean> projectList) {
        this.projectID = projectID;
        this.projectShortName = projectShortName;
        this.projectList = projectList;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectShortName() {
        return projectShortName;
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName;
    }

    public List<ProjectBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectBean> projectList) {
        this.projectList = projectList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectID);
        dest.writeString(this.projectShortName);
        dest.writeList(this.projectList);
    }

    protected ProjectBean(Parcel in) {
        this.projectID = in.readString();
        this.projectShortName = in.readString();
        this.projectList = new ArrayList<ProjectBean>();
        in.readList(this.projectList, ProjectBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProjectBean> CREATOR = new Parcelable.Creator<ProjectBean>() {
        @Override
        public ProjectBean createFromParcel(Parcel source) {
            return new ProjectBean(source);
        }

        @Override
        public ProjectBean[] newArray(int size) {
            return new ProjectBean[size];
        }
    };
}
