package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/4/25.
 */

public class RiskSubdivisionBean implements Parcelable{

    private boolean isChecked;
    private String dicId;
    private String dicName;

    protected RiskSubdivisionBean(Parcel in) {
        isChecked = in.readByte() != 0;
        dicId = in.readString();
        dicName = in.readString();
    }

    public static final Creator<RiskSubdivisionBean> CREATOR = new Creator<RiskSubdivisionBean>() {
        @Override
        public RiskSubdivisionBean createFromParcel(Parcel in) {
            return new RiskSubdivisionBean(in);
        }

        @Override
        public RiskSubdivisionBean[] newArray(int size) {
            return new RiskSubdivisionBean[size];
        }
    };

    public RiskSubdivisionBean() {

    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeString(dicId);
        dest.writeString(dicName);

    }
    
}
