package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/3/19.
 */

public class JygzProjectType implements Parcelable {

    private String key;
    private String value;

    public JygzProjectType(Parcel in) {
        key = in.readString();
        value = in.readString();
    }

    public static final Creator<JygzProjectType> CREATOR = new Creator<JygzProjectType>() {
        @Override
        public JygzProjectType createFromParcel(Parcel in) {
            return new JygzProjectType(in);
        }

        @Override
        public JygzProjectType[] newArray(int size) {
            return new JygzProjectType[size];
        }
    };

    public JygzProjectType() {

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
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
        dest.writeString(key);
        dest.writeString(value);
    }
}
