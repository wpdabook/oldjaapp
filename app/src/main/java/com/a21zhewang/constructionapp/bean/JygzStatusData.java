package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/3/8.
 */

public class JygzStatusData implements Parcelable {

    private String statusName;
    private String statusValue;

    public static Creator<JygzStatusData> getCREATOR() {
        return CREATOR;
    }
    public JygzStatusData() {
    }
    public JygzStatusData(Parcel in) {
        statusName = in.readString();
        statusValue = in.readString();
    }

    public static final Creator<JygzStatusData> CREATOR = new Creator<JygzStatusData>() {
        @Override
        public JygzStatusData createFromParcel(Parcel in) {
            return new JygzStatusData(in);
        }

        @Override
        public JygzStatusData[] newArray(int size) {
            return new JygzStatusData[size];
        }
    };

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(statusName);
        dest.writeString(statusValue);
    }
}
