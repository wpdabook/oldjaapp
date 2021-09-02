package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/3/19.
 */

public class ZddbTypesStatus implements Parcelable {

    private String name;
    private String value;

    public ZddbTypesStatus(Parcel in) {
        name = in.readString();
        value = in.readString();
    }

    public static final Creator<ZddbTypesStatus> CREATOR = new Creator<ZddbTypesStatus>() {
        @Override
        public ZddbTypesStatus createFromParcel(Parcel in) {
            return new ZddbTypesStatus(in);
        }

        @Override
        public ZddbTypesStatus[] newArray(int size) {
            return new ZddbTypesStatus[size];
        }
    };

    public ZddbTypesStatus() {

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
