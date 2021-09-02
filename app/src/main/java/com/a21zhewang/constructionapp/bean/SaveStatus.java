package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WP-PC on 2019/11/13.
 */

public class SaveStatus implements Parcelable {

    private int level;
    private String levelName;

    protected SaveStatus(Parcel in) {
        level = in.readInt();
        levelName = in.readString();
    }

    public static final Creator<SaveStatus> CREATOR = new Creator<SaveStatus>() {
        @Override
        public SaveStatus createFromParcel(Parcel in) {
            return new SaveStatus(in);
        }

        @Override
        public SaveStatus[] newArray(int size) {
            return new SaveStatus[size];
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
        dest.writeInt(level);
        dest.writeString(levelName);
    }
}
