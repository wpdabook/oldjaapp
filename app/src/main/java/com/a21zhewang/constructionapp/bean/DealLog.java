package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/10/30.
 *
 * @auto
 */

public class DealLog {
    /**
     * userName : 回复人：吴玉怀
     * etpShortName : 土建一部
     * content : 测试回复
     * time : 10-30 14:43
     */

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
