package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskCompany implements Parcelable {
    private String etpId;
    private String etpName;
    private boolean isChecked;

    protected TaskCompany(Parcel in) {
        etpId = in.readString();
        etpName = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<TaskCompany> CREATOR = new Creator<TaskCompany>() {
        @Override
        public TaskCompany createFromParcel(Parcel in) {
            return new TaskCompany(in);
        }

        @Override
        public TaskCompany[] newArray(int size) {
            return new TaskCompany[size];
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getEtpId() {
        return etpId;
    }

    public void setEtpId(String etpId) {
        this.etpId = etpId;
    }

    public String getEtpName() {
        return etpName;
    }

    public void setEtpName(String etpName) {
        this.etpName = etpName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(etpId);
        dest.writeString(etpName);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }
    public void toggle() {
        this.isChecked = !this.isChecked;
    }
}
