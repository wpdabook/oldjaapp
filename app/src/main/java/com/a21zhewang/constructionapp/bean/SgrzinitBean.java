package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/27.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class SgrzinitBean implements Parcelable {

    /**
     * projectList : [{"projectID":"GC10001","projectShortName":"绿地636项目","projectAreaList":[{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10023","subProjectName":"内筒楼板砼浇筑"}]},{"projectAreaName":"顶模","subProjectList":[{"subProjectID":"SubPj10002","subProjectName":"核心筒钢骨筋安装"},{"subProjectID":"SubPj10003","subProjectName":"剪力墙铝膜合模"},{"subProjectID":"SubPj10004","subProjectName":"剪力墙砼浇筑"},{"subProjectID":"SubPj10006","subProjectName":"顶模顶升"}]},{"projectAreaName":"外框","subProjectList":[{"subProjectID":"SubPj10012","subProjectName":"外框楼板钢筋绑扎"},{"subProjectID":"SubPj10013","subProjectName":"外框楼板砼浇筑"},{"subProjectID":"SubPj10014","subProjectName":"外框巨柱钢筋绑扎"},{"subProjectID":"SubPj10015","subProjectName":"外框巨柱模板支架工程"},{"subProjectID":"SubPj10016","subProjectName":"外框巨柱砼浇筑"},{"subProjectID":"SubPj10017","subProjectName":"爬模爬升"}]},{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10022","subProjectName":"内筒楼板钢筋绑扎"}]}]}]
     * weather : 当前温度：19 风向：东北风 风速：1级 湿度：39%
     */

    private String weather;
    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * projectAreaList : [{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10023","subProjectName":"内筒楼板砼浇筑"}]},{"projectAreaName":"顶模","subProjectList":[{"subProjectID":"SubPj10002","subProjectName":"核心筒钢骨筋安装"},{"subProjectID":"SubPj10003","subProjectName":"剪力墙铝膜合模"},{"subProjectID":"SubPj10004","subProjectName":"剪力墙砼浇筑"},{"subProjectID":"SubPj10006","subProjectName":"顶模顶升"}]},{"projectAreaName":"外框","subProjectList":[{"subProjectID":"SubPj10012","subProjectName":"外框楼板钢筋绑扎"},{"subProjectID":"SubPj10013","subProjectName":"外框楼板砼浇筑"},{"subProjectID":"SubPj10014","subProjectName":"外框巨柱钢筋绑扎"},{"subProjectID":"SubPj10015","subProjectName":"外框巨柱模板支架工程"},{"subProjectID":"SubPj10016","subProjectName":"外框巨柱砼浇筑"},{"subProjectID":"SubPj10017","subProjectName":"爬模爬升"}]},{"projectAreaName":"核心筒内筒","subProjectList":[{"subProjectID":"SubPj10022","subProjectName":"内筒楼板钢筋绑扎"}]}]
     */

    private List<Project> projectList;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.weather);
        dest.writeTypedList(this.projectList);
    }

    public SgrzinitBean() {
    }

    protected SgrzinitBean(Parcel in) {
        this.weather = in.readString();
        this.projectList = in.createTypedArrayList(Project.CREATOR);
    }

    public static final Parcelable.Creator<SgrzinitBean> CREATOR = new Parcelable.Creator<SgrzinitBean>() {
        @Override
        public SgrzinitBean createFromParcel(Parcel source) {
            return new SgrzinitBean(source);
        }

        @Override
        public SgrzinitBean[] newArray(int size) {
            return new SgrzinitBean[size];
        }
    };
}
