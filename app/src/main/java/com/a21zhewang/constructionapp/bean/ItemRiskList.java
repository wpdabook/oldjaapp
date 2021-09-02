package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/8/7.
 */

public class ItemRiskList implements Parcelable{

    private String id;
    private String riskReportId;
    private String dicId;
    private String dicName;
    private String fatherId;
    private String level;
    private String selfIndex;


    public static final Creator<ItemRiskList> CREATOR = new Creator<ItemRiskList>() {
        @Override
        public ItemRiskList createFromParcel(Parcel in) {
            ItemRiskList msg = new ItemRiskList();
            msg.id = in.readString();
            msg.riskReportId = in.readString();
            msg.dicId = in.readString();
            msg.dicName = in.readString();
            msg.fatherId = in.readString();
            msg.level = in.readString();
            msg.selfIndex = in.readString();
            return msg;
        }

        @Override
        public ItemRiskList[] newArray(int size) {
            return new ItemRiskList[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiskReportId() {
        return riskReportId;
    }

    public void setRiskReportId(String riskReportId) {
        this.riskReportId = riskReportId;
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

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSelfIndex() {
        return selfIndex;
    }

    public void setSelfIndex(String selfIndex) {
        this.selfIndex = selfIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(id);
         dest.writeString(riskReportId);
         dest.writeString(dicId);
         dest.writeString(dicName);
         dest.writeString(fatherId);
         dest.writeString(level);
         dest.writeString(selfIndex);
    }
}
