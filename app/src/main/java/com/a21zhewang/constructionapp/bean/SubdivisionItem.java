package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2021/4/25.
 */

public class SubdivisionItem implements Parcelable {

    private String divisionId;
    private String divisionName;

    public SubdivisionItem(String divisionId, String divisionName) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
    }
    public static final Creator<SubdivisionItem> CREATOR = new Creator<SubdivisionItem>() {
        @Override
        public SubdivisionItem createFromParcel(Parcel in) {
            SubdivisionItem msg = new SubdivisionItem();
            msg.divisionId = in.readString();
            msg.divisionName = in.readString();
            return msg;
        }

        @Override
        public SubdivisionItem[] newArray(int size) {
            return new SubdivisionItem[size];
        }
    };

    public SubdivisionItem() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(divisionId);
        dest.writeString(divisionName);
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
