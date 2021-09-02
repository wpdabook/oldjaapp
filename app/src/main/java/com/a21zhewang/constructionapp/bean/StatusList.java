package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/10/26.
 */

public class StatusList implements Parcelable{
    private String type;
    private String typeName;

    protected StatusList(Parcel in) {
        type = in.readString();
        typeName = in.readString();
    }

    public static final Creator<StatusList> CREATOR = new Creator<StatusList>() {
        @Override
        public StatusList createFromParcel(Parcel in) {
            return new StatusList(in);
        }

        @Override
        public StatusList[] newArray(int size) {
            return new StatusList[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(typeName);
    }
}
