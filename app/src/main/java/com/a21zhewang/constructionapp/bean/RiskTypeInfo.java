package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/8/8.
 */

public class RiskTypeInfo implements Parcelable{

    private boolean isChecked;
    private String dicID;
    private String fatherID;
    private String dicName;

    protected RiskTypeInfo(Parcel in) {
        isChecked = in.readByte() != 0;
        dicID = in.readString();
        fatherID = in.readString();
        dicName = in.readString();
    }

    public static final Creator<RiskTypeInfo> CREATOR = new Creator<RiskTypeInfo>() {
        @Override
        public RiskTypeInfo createFromParcel(Parcel in) {
            return new RiskTypeInfo(in);
        }

        @Override
        public RiskTypeInfo[] newArray(int size) {
            return new RiskTypeInfo[size];
        }
    };

    public RiskTypeInfo() {

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(dicID);
        dest.writeString(fatherID);
        dest.writeString(dicName);

    }
}
