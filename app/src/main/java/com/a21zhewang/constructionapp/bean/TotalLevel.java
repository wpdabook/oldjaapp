package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TotalLevel implements Parcelable {

    private int level;
    private String levelName;

    protected TotalLevel(Parcel in) {
        level = in.readInt();
        levelName = in.readString();
    }

    public static final Creator<TotalLevel> CREATOR = new Creator<TotalLevel>() {
        @Override
        public TotalLevel createFromParcel(Parcel in) {
            return new TotalLevel(in);
        }

        @Override
        public TotalLevel[] newArray(int size) {
            return new TotalLevel[size];
        }
    };

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.level);
        dest.writeString(this.levelName);
    }
}
