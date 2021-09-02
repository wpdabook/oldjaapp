package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 10430 on 2017/11/8.
 */

public class IndexItem implements Parcelable{

    /**
     * mdID : cm
     * mdName : 生产协调
     */

    private String mdID;
    private String mdName;
    private String mdProjectId;
    private int noRead;
    private int tag;

    public IndexItem(Parcel in) {
        mdID = in.readString();
        mdName = in.readString();
        mdProjectId = in.readString();
        noRead = in.readInt();
        tag = in.readInt();
    }

    public IndexItem() {

    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public static Creator<IndexItem> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<IndexItem> CREATOR = new Creator<IndexItem>() {
        @Override
        public IndexItem createFromParcel(Parcel in) {
            return new IndexItem(in);
        }

        @Override
        public IndexItem[] newArray(int size) {
            return new IndexItem[size];
        }
    };

    public String getMdProjectId() {
        return mdProjectId;
    }

    public void setMdProjectId(String mdProjectId) {
        this.mdProjectId = mdProjectId;
    }


    public int getNoRead() {
        return noRead;
    }

    public void setNoRead(int noRead) {
        this.noRead = noRead;
    }

    public String getMdID() {
        return mdID;
    }

    public void setMdID(String mdID) {
        this.mdID = mdID;
    }

    public String getMdName() {
        return mdName;
    }

    public void setMdName(String mdName) {
        this.mdName = mdName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mdID);
        dest.writeString(mdName);
        dest.writeString(mdProjectId);
        dest.writeInt(noRead);
        dest.writeInt(tag);
    }
}
