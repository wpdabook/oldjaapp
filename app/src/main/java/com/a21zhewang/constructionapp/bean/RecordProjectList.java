package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordProjectList implements Parcelable {

    private String projectId;
    private String projectName;
    private boolean isChecked;

    protected RecordProjectList(Parcel in) {
        projectId = in.readString();
        projectName = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<RecordProjectList> CREATOR = new Creator<RecordProjectList>() {
        @Override
        public RecordProjectList createFromParcel(Parcel in) {
            return new RecordProjectList(in);
        }

        @Override
        public RecordProjectList[] newArray(int size) {
            return new RecordProjectList[size];
        }
    };

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.projectId);
        dest.writeString(this.projectName);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }
}
