package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/10/26.
 */

public class TypeList implements Parcelable{
    private String status;
    private String statusName;

    protected TypeList(Parcel in) {
        status = in.readString();
        statusName = in.readString();
    }

    public static final Creator<TypeList> CREATOR = new Creator<TypeList>() {
        @Override
        public TypeList createFromParcel(Parcel in) {
            return new TypeList(in);
        }

        @Override
        public TypeList[] newArray(int size) {
            return new TypeList[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(statusName);
    }
}
