package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2021/8/2.
 */

public class TaskBean implements Parcelable {
    private String receiveTitle;
    private List<TaskCompany> companyList;
    private boolean isChecked;

    protected TaskBean(Parcel in) {
        receiveTitle = in.readString();
        this.companyList = in.createTypedArrayList(TaskCompany.CREATOR);
        isChecked = in.readByte() != 0;
    }

    public static final Creator<TaskBean> CREATOR = new Creator<TaskBean>() {
        @Override
        public TaskBean createFromParcel(Parcel in) {
            return new TaskBean(in);
        }

        @Override
        public TaskBean[] newArray(int size) {
            return new TaskBean[size];
        }
    };


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getReceiveTitle() {
        return receiveTitle;
    }

    public void setReceiveTitle(String receiveTitle) {
        this.receiveTitle = receiveTitle;
    }

    public List<TaskCompany> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<TaskCompany> companyList) {
        this.companyList = companyList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(receiveTitle);
        dest.writeTypedList(this.companyList);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }
    public void addChildItem(TaskCompany TaskCompany) {
        companyList.add(TaskCompany);
    }
    public TaskCompany getChildItem(int index) {
        return companyList.get(index);
    }
    public int getChildrenCount() {
        return companyList.size();
    }
}
