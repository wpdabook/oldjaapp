package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2020/4/14.
 */

public class ScaffoldData implements Parcelable{
    private boolean isChecked;
    private String CID;
    private String ConfigName;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static final Creator<ScaffoldData> CREATOR = new Creator<ScaffoldData>() {
        @Override
        public ScaffoldData createFromParcel(Parcel in) {
            ScaffoldData scaffoldData = new ScaffoldData();
            scaffoldData.isChecked = in.readByte() != 0;
            scaffoldData.CID = in.readString();
            scaffoldData.ConfigName = in.readString();
            return scaffoldData;
        }

        @Override
        public ScaffoldData[] newArray(int size) {
            return new ScaffoldData[size];
        }
    };

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getConfigName() {
        return ConfigName;
    }

    public void setConfigName(String configName) {
        ConfigName = configName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(CID);
        dest.writeString(ConfigName);
    }
}
