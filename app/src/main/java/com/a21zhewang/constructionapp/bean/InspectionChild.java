package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/1/3.
 */

public class InspectionChild implements Parcelable {
    private String dicId;
    private String dicName;
    private String child;
    private boolean isChecked;

    public InspectionChild(String dicId, String dicName) {
        this.dicId = dicId;
        this.dicName = dicName;
    }

    protected InspectionChild(Parcel in) {
        dicId = in.readString();
        dicName = in.readString();
        child = in.readString();
        isChecked = in.readByte() != 0;
    }

    public static final Creator<InspectionChild> CREATOR = new Creator<InspectionChild>() {
        @Override
        public InspectionChild createFromParcel(Parcel in) {
            return new InspectionChild(in);
        }

        @Override
        public InspectionChild[] newArray(int size) {
            return new InspectionChild[size];
        }
    };

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }
    public String getDicId() {
        return dicId;
    }

    public void setDicId(String dicId) {
        this.dicId = dicId;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dicId);
        dest.writeString(this.dicName);
        dest.writeString(this.child);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }
}
