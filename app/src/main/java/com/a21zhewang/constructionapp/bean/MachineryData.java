package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2020/4/14.
 */

public class MachineryData implements Parcelable{
    private boolean isChecked;
    private String CID;
    private String ConfigName;

    public MachineryData(Parcel in) {
        isChecked = in.readByte() != 0;
        CID = in.readString();
        ConfigName = in.readString();
    }

    public MachineryData() {

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static final Creator<MachineryData> CREATOR = new Creator<MachineryData>() {
        @Override
        public MachineryData createFromParcel(Parcel in) {
            MachineryData machineryData = new MachineryData();
            machineryData.isChecked = in.readByte() != 0;
            machineryData.CID = in.readString();
            machineryData.ConfigName = in.readString();
            return machineryData;
        }

        @Override
        public MachineryData[] newArray(int size) {
            return new MachineryData[size];
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
