package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/5/29.
 */

public class JdzfCompanys implements Parcelable{

    private String etpID;
    private String etpShortName;
    private boolean isChecked;


    protected JdzfCompanys(Parcel in) {
        etpID = in.readString();
        etpShortName = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<JdzfCompanys> CREATOR = new Creator<JdzfCompanys>() {
        @Override
        public JdzfCompanys createFromParcel(Parcel in) {
            return new JdzfCompanys(in);
        }

        @Override
        public JdzfCompanys[] newArray(int size) {
            return new JdzfCompanys[size];
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.etpID);
        dest.writeString(this.etpShortName);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }
}
