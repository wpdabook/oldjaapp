package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 10430 on 2018/6/8.
 */

public class Dic implements Parcelable {
    private String dicID ;
    private String dicName ;
    private int selfIndex ;

    public int getSelfIndex() {
        return selfIndex;
    }

    public void setSelfIndex(int selfIndex) {
        this.selfIndex = selfIndex;
    }

    public Dic() {
    }

    public Dic(String dicID, String dicName) {
        this.dicID = dicID;
        this.dicName = dicName;
    }

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
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
        dest.writeString(this.dicID);
        dest.writeString(this.dicName);
        dest.writeInt(this.selfIndex);
    }

    protected Dic(Parcel in) {
        this.dicID = in.readString();
        this.dicName = in.readString();
        this.selfIndex = in.readInt();
    }

    public static final Creator<Dic> CREATOR = new Creator<Dic>() {
        @Override
        public Dic createFromParcel(Parcel source) {
            return new Dic(source);
        }

        @Override
        public Dic[] newArray(int size) {
            return new Dic[size];
        }
    };
}
