package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2021/1/3.
 */

public class InspectionGroup implements Parcelable {

    private String dicId;
    private String dicName;
    private ArrayList<InspectionChild> child;
    private boolean isChecked;

    public InspectionGroup(String dicId, String dicName) {
        this.dicId = dicId;
        this.dicName = dicName;
        child = new ArrayList<InspectionChild>();
        this.isChecked = false;
    }
    protected InspectionGroup(Parcel in) {
        this.dicId = in.readString();
        this.dicName = in.readString();
        this.child = in.createTypedArrayList(InspectionChild.CREATOR);
        isChecked = in.readByte() != 0;
    }
    public static final Creator<InspectionGroup> CREATOR = new Creator<InspectionGroup>() {
        @Override
        public InspectionGroup createFromParcel(Parcel source) {
            return new InspectionGroup(source);
        }

        @Override
        public InspectionGroup[] newArray(int size) {
            return new InspectionGroup[size];
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

    public ArrayList<InspectionChild> getChild() {
        return child;
    }

    public void setChild(ArrayList<InspectionChild> child) {
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
    public void addChildItem(InspectionChild inspectionChild) {
        child.add(inspectionChild);
    }

    public int getChildrenCount() {
        return child.size();
    }

    public InspectionChild getChildItem(int index) {
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
        dest.writeTypedList(this.child);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }
}
