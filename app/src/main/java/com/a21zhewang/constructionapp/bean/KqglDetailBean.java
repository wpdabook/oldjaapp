package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/4/27.
 */

public class KqglDetailBean {


    private String Name;
    private int AttendanceCount;
    private int UnAttendanceCount;
    private double AttendanceRate;
    private boolean IsAttendance;
    private String WorkTypeName;
    /*最近打卡*/
    private String RecentPhoto;
    private String LastAttendanceTime;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAttendanceCount() {
        return AttendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        AttendanceCount = attendanceCount;
    }

    public int getUnAttendanceCount() {
        return UnAttendanceCount;
    }

    public void setUnAttendanceCount(int unAttendanceCount) {
        UnAttendanceCount = unAttendanceCount;
    }

    public double getAttendanceRate() {
        return AttendanceRate;
    }

    public void setAttendanceRate(double attendanceRate) {
        AttendanceRate = attendanceRate;
    }

    public void setAttendanceRate(int attendanceRate) {
        AttendanceRate = attendanceRate;
    }

    public boolean isAttendance() {
        return IsAttendance;
    }

    public void setAttendance(boolean attendance) {
        IsAttendance = attendance;
    }

    public String getWorkTypeName() {
        return WorkTypeName;
    }

    public void setWorkTypeName(String workTypeName) {
        WorkTypeName = workTypeName;
    }

    public String getRecentPhoto() {
        return RecentPhoto;
    }

    public void setRecentPhoto(String recentPhoto) {
        RecentPhoto = recentPhoto;
    }

    public String getLastAttendanceTime() {
        return LastAttendanceTime;
    }

    public void setLastAttendanceTime(String lastAttendanceTime) {
        LastAttendanceTime = lastAttendanceTime;
    }
}
