package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class RiskHiddenBean implements Parcelable{
    private String statusName;
    private List<HiddenBean> data;

    protected RiskHiddenBean(Parcel in) {
        statusName = in.readString();
        data = in.createTypedArrayList(HiddenBean.CREATOR);
    }

    public static final Creator<RiskHiddenBean> CREATOR = new Creator<RiskHiddenBean>() {
        @Override
        public RiskHiddenBean createFromParcel(Parcel in) {
            return new RiskHiddenBean(in);
        }

        @Override
        public RiskHiddenBean[] newArray(int size) {
            return new RiskHiddenBean[size];
        }
    };

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<HiddenBean> getData() {
        return data;
    }

    public void setData(List<HiddenBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(statusName);
        dest.writeTypedList(data);
    }
}
