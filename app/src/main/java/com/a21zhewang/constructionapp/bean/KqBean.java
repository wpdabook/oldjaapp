package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/4/27.
 */

public class KqBean {

    private String ProjectName;
    private String GroupId;
    private String GroupName;
    private String CompanyTypeId;
    private String GroupDisplay;
    private int EnterCount;
    private int AttendanceCount;
    private int UnAttendanceCount;
    private Double AttendanceRate;

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getCompanyTypeId() {
        return CompanyTypeId;
    }

    public void setCompanyTypeId(String companyTypeId) {
        CompanyTypeId = companyTypeId;
    }

    public String getGroupDisplay() {
        return GroupDisplay;
    }

    public void setGroupDisplay(String groupDisplay) {
        GroupDisplay = groupDisplay;
    }

    public int getEnterCount() {
        return EnterCount;
    }

    public void setEnterCount(int enterCount) {
        EnterCount = enterCount;
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


    public Double getAttendanceRate() {
        return AttendanceRate;
    }

    public void setAttendanceRate(Double attendanceRate) {
        AttendanceRate = attendanceRate;
    }
}
