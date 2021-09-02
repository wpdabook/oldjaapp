package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HiddenBean implements Parcelable {

    private String level;
    private int num;

    protected HiddenBean(Parcel in) {
        level = in.readString();
        num = in.readInt();
    }

    public static final Creator<HiddenBean> CREATOR = new Creator<HiddenBean>() {
        @Override
        public HiddenBean createFromParcel(Parcel in) {
            return new HiddenBean(in);
        }

        @Override
        public HiddenBean[] newArray(int size) {
            return new HiddenBean[size];
        }
    };

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(level);
        dest.writeInt(num);
    }
}
