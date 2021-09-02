package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.List;

public class SafetyListBean implements Parcelable{

    private String typeName;

    private List<RiskHiddenBean> data;


    protected SafetyListBean(Parcel in) {
        this.typeName = in.readString();
        this.data = new ArrayList<RiskHiddenBean>();
        in.readList(this.data, RiskHiddenBean.class.getClassLoader());
    }

    public static final Creator<SafetyListBean> CREATOR = new Creator<SafetyListBean>() {
        @Override
        public SafetyListBean createFromParcel(Parcel in) {
            return new SafetyListBean(in);
        }

        @Override
        public SafetyListBean[] newArray(int size) {
            return new SafetyListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(typeName);
        dest.writeList(this.data);
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<RiskHiddenBean> getData() {
        return data;
    }

    public void setData(List<RiskHiddenBean> data) {
        this.data = data;
    }
}
