package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/18.
 *
 * @auto
 */

public class Etp implements Parcelable {
    private String etpID;

    private String etpShortName;

    private String companyId;

    private String companyName;

    private List<User> userInfoList;

    public void setEtpID(String etpID){
        this.etpID = etpID;
    }
    public String getEtpID(){
        return this.etpID;
    }
    public void setEtpShortName(String etpShortName){
        this.etpShortName = etpShortName;
    }
    public String getEtpShortName(){
        return this.etpShortName;
    }
    public void setUserInfoList(List<User> userInfoList){
        this.userInfoList = userInfoList;
    }
    public List<User> getUserInfoList(){
        return this.userInfoList;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public static Creator<Etp> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.etpID);
        dest.writeString(this.etpShortName);
        dest.writeString(this.companyId);
        dest.writeString(this.companyName);
        dest.writeList(this.userInfoList);
    }

    public Etp() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Etp etp = (Etp) o;

        return etpID.equals(etp.etpID);
    }

    @Override
    public int hashCode() {
        return etpID.hashCode();
    }

    protected Etp(Parcel in) {
        this.etpID = in.readString();
        this.etpShortName = in.readString();
        this.companyId = in.readString();
        this.companyName = in.readString();
        this.userInfoList = new ArrayList<User>();
        in.readList(this.userInfoList, User.class.getClassLoader());
    }

    public static final Parcelable.Creator<Etp> CREATOR = new Parcelable.Creator<Etp>() {
        @Override
        public Etp createFromParcel(Parcel source) {
            return new Etp(source);
        }

        @Override
        public Etp[] newArray(int size) {
            return new Etp[size];
        }
    };
}
