package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/18.
 *
 * @auto
 */

    public  class SubProjectBean implements Parcelable {
        private String subProjectID;
        private String subProjectName;
       private List<SubProjectBean> subProjectList;

    public List<SubProjectBean> getSubProjectList() {
        return subProjectList;
    }

    public void setSubProjectList(List<SubProjectBean> subProjectList) {
        this.subProjectList = subProjectList;
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

    public SubProjectBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subProjectID);
        dest.writeString(this.subProjectName);
        dest.writeTypedList(this.subProjectList);
    }

    protected SubProjectBean(Parcel in) {
        this.subProjectID = in.readString();
        this.subProjectName = in.readString();
        this.subProjectList = in.createTypedArrayList(SubProjectBean.CREATOR);
    }

    public static final Creator<SubProjectBean> CREATOR = new Creator<SubProjectBean>() {
        @Override
        public SubProjectBean createFromParcel(Parcel source) {
            return new SubProjectBean(source);
        }

        @Override
        public SubProjectBean[] newArray(int size) {
            return new SubProjectBean[size];
        }
    };
}
