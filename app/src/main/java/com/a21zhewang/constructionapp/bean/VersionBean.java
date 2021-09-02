package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/4/28.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class VersionBean {

    /**
     * CreateTime : 2017-04-23 08:01:59
     * Version : 0.01
     * VerDisc : 测试
     */

    private String CreateTime;
    private double Version;
    private String VerDisc;

    private int version_code;
    private String version_name;
    private String version_desc;
    private String version_path;

    /*业主版（市安全站）*/
    private String VerPath;

    public String getVerPath() {
        return VerPath;
    }

    public void setVerPath(String verPath) {
        VerPath = verPath;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion_desc() {
        return version_desc;
    }

    public void setVersion_desc(String version_desc) {
        this.version_desc = version_desc;
    }

    public String getVersion_path() {
        return version_path;
    }

    public void setVersion_path(String version_path) {
        this.version_path = version_path;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public double getVersion() {
        return Version;
    }

    public void setVersion(double Version) {
        this.Version = Version;
    }

    public String getVerDisc() {
        return VerDisc;
    }

    public void setVerDisc(String VerDisc) {
        this.VerDisc = VerDisc;
    }
}
