package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/3/8.
 */

public class JygzAreaData implements Parcelable {

    private String regionName;
    private String regionValue;
    public JygzAreaData() {
    }
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionValue() {
        return regionValue;
    }

    public void setRegionValue(String regionValue) {
        this.regionValue = regionValue;
    }

    public static Creator<JygzAreaData> getCREATOR() {
        return CREATOR;
    }

    public JygzAreaData(Parcel in) {
        regionName = in.readString();
        regionValue = in.readString();
    }

    public static final Creator<JygzAreaData> CREATOR = new Creator<JygzAreaData>() {
        @Override
        public JygzAreaData createFromParcel(Parcel in) {
            return new JygzAreaData(in);
        }

        @Override
        public JygzAreaData[] newArray(int size) {
            return new JygzAreaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.regionName);
        dest.writeString(this.regionValue);
    }



}
