package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WP-PC on 2020/4/14.
 */

public class ReportDate  implements Parcelable {

    private List<ScaffoldData> ScaffoldData;

    private List<MachineryData> MachineryData;

    protected ReportDate(Parcel in) {
        this.ScaffoldData = new ArrayList<ScaffoldData>();
        this.MachineryData = new ArrayList<MachineryData>();
    }

    public static final Creator<ReportDate> CREATOR = new Creator<ReportDate>() {
        @Override
        public ReportDate createFromParcel(Parcel in) {
            return new ReportDate(in);
        }

        @Override
        public ReportDate[] newArray(int size) {
            return new ReportDate[size];
        }
    };

    public List<com.a21zhewang.constructionapp.bean.ScaffoldData> getScaffoldData() {
        return ScaffoldData;
    }

    public void setScaffoldData(List<com.a21zhewang.constructionapp.bean.ScaffoldData> scaffoldData) {
        ScaffoldData = scaffoldData;
    }

    public List<com.a21zhewang.constructionapp.bean.MachineryData> getMachineryData() {
        return MachineryData;
    }

    public void setMachineryData(List<com.a21zhewang.constructionapp.bean.MachineryData> machineryData) {
        MachineryData = machineryData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ScaffoldData);
        dest.writeTypedList(this.MachineryData);
    }
}
