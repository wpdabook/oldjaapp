package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险上报-选择区域
 * Created by WP-PC on 2019/8/6.
 */

public class RiskAreaAndStage implements Parcelable {

    /*施工区域 ID*/
    private String id;
    /*施工区域名称*/
    private String regionName;
    /*施工阶段ID*/
    private String dicID;
    /*施工阶段名称*/
    private String dicName;

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

    public List<RiskAreaAndStage> getRiskAreaAndStageList() {
        return riskAreaAndStageList;
    }

    public void setRiskAreaAndStageList(List<RiskAreaAndStage> riskAreaAndStageList) {
        this.riskAreaAndStageList = riskAreaAndStageList;
    }

    private List<RiskAreaAndStage> riskAreaAndStageList;

    public List<RiskAreaAndStage> getRiskAreaList() {
        return riskAreaAndStageList;
    }

    public void setRiskAreaList(List<RiskAreaAndStage> riskAreaList) {
        this.riskAreaAndStageList = riskAreaList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public static Creator<RiskAreaAndStage> getCREATOR() {
        return CREATOR;
    }

    protected RiskAreaAndStage(Parcel in) {
        this.id = in.readString();
        this.regionName = in.readString();
        this.dicID = in.readString();
        this.dicName = in.readString();
        this.riskAreaAndStageList = new ArrayList<RiskAreaAndStage>();
        in.readList(this.riskAreaAndStageList, RiskAreaAndStage.class.getClassLoader());
    }

    public static final Creator<RiskAreaAndStage> CREATOR = new Creator<RiskAreaAndStage>() {
        @Override
        public RiskAreaAndStage createFromParcel(Parcel in) {
            return new RiskAreaAndStage(in);
        }

        @Override
        public RiskAreaAndStage[] newArray(int size) {
            return new RiskAreaAndStage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.regionName);
        dest.writeString(this.dicID);
        dest.writeString(this.dicName);
        dest.writeTypedList(this.riskAreaAndStageList);
    }
}
