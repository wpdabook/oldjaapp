package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class DealLogsBean{
    private String userName;
    private String etpShortName;
    private String content;
    private String time;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

