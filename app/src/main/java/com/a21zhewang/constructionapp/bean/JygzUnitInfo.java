package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2021/8/17.
 */

public class JygzUnitInfo {

    private String etpID;
    private String etpShortName;
    private List<EptBean> userList;

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public List<EptBean> getUserList() {
        return userList;
    }

    public void setUserList(List<EptBean> userList) {
        this.userList = userList;
    }
}
