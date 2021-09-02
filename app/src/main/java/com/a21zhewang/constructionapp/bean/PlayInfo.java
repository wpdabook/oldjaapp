package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/11/23.
 */

public class PlayInfo {

    private String etpId;
    private String etpFullName;
    private List<PlayUserList> userList;

    public String getEtpId() {
        return etpId;
    }

    public void setEtpId(String etpId) {
        this.etpId = etpId;
    }

    public String getEtpFullName() {
        return etpFullName;
    }

    public void setEtpFullName(String etpFullName) {
        this.etpFullName = etpFullName;
    }

    public List<PlayUserList> getUserList() {
        return userList;
    }

    public void setUserList(List<PlayUserList> userList) {
        this.userList = userList;
    }
}
