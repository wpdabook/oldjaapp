package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by WP-PC on 2019/8/6.
 */

public class RiskProject implements Parcelable {
    /**
     * 风险上报
     */
    private List<RiskAreaAndStage> backdata;

    protected RiskProject(Parcel in) {
        backdata = in.createTypedArrayList(RiskAreaAndStage.CREATOR);
    }

    public static final Creator<RiskProject> CREATOR = new Creator<RiskProject>() {
        @Override
        public RiskProject createFromParcel(Parcel in) {
            return new RiskProject(in);
        }

        @Override
        public RiskProject[] newArray(int size) {
            return new RiskProject[size];
        }
    };

    public List<RiskAreaAndStage> getBackdata() {
        return backdata;
    }

    public void setBackdata(List<RiskAreaAndStage> backdata) {
        this.backdata = backdata;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(backdata);
    }
}
