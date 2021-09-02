package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskTypesStatus implements Parcelable {

    private String name;
    private String value;

    public TaskTypesStatus(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<TaskTypesStatus> CREATOR = new Creator<TaskTypesStatus>() {
        @Override
        public TaskTypesStatus createFromParcel(Parcel in) {
            return new TaskTypesStatus(in);
        }

        @Override
        public TaskTypesStatus[] newArray(int size) {
            return new TaskTypesStatus[size];
        }
    };

    public TaskTypesStatus() {

    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(value);
    }
}
