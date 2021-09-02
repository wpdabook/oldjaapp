package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2021/1/3.
 */

public class InspectionOneLevel implements Parcelable {

    private String dicId;
    private String dicName;
    private String fatherId;
    private int itemCount;
    private ArrayList<InspectionTwoLevel> child;
    private boolean isChecked;

    public InspectionOneLevel(String dicId, String dicName,String fatherId) {
        this.dicId = dicId;
        this.dicName = dicName;
        this.fatherId = fatherId;
        child = new ArrayList<InspectionTwoLevel>();
        this.isChecked = false;
    }
    protected InspectionOneLevel(Parcel in) {
        this.dicId = in.readString();
        this.dicName = in.readString();
        this.fatherId = in.readString();
        this.itemCount = in.readInt();
        this.child = in.createTypedArrayList(InspectionTwoLevel.CREATOR);
        isChecked = in.readByte() != 0;
    }
    public static final Creator<InspectionOneLevel> CREATOR = new Creator<InspectionOneLevel>() {
        @Override
        public InspectionOneLevel createFromParcel(Parcel source) {
            return new InspectionOneLevel(source);
        }

        @Override
        public InspectionOneLevel[] newArray(int size) {
            return new InspectionOneLevel[size];
        }
    };
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

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public ArrayList<InspectionTwoLevel> getChild() {
        return child;
    }

    public void setChild(ArrayList<InspectionTwoLevel> child) {
        this.child = child;
    }
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }
    public void addChildItem(InspectionTwoLevel InspectionTwoLevel) {
        child.add(InspectionTwoLevel);
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public int getChildrenCount() {
        return child.size();
    }

    public InspectionTwoLevel getChildItem(int index) {
        return child.get(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dicId);
        dest.writeString(this.dicName);
        dest.writeString(this.fatherId);
        dest.writeInt(this.itemCount);
        dest.writeTypedList(this.child);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }
}
