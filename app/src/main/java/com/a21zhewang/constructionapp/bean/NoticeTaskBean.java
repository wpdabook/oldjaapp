package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2021/8/2.
 */

public class NoticeTaskBean {

    private List<NotifiTypeList> notifiTypeList;
    private List<TaskBean> objList;

    public List<NotifiTypeList> getNotifiTypeList() {
        return notifiTypeList;
    }

    public void setNotifiTypeList(List<NotifiTypeList> notifiTypeList) {
        this.notifiTypeList = notifiTypeList;
    }

    public List<TaskBean> getObjList() {
        return objList;
    }

    public void setObjList(List<TaskBean> objList) {
        this.objList = objList;
    }
}
