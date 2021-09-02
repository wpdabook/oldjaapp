package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/5/15.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class AqscSpinnerBean {



    private List<MsgType> msgTypes;
    /**
     * status : 未读
     * color : FF0001
     */

    private List<MsgStatusBean> msgStatus;

    public List<MsgType> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<MsgType> msgTypes) {
        this.msgTypes = msgTypes;
    }

    public List<MsgStatusBean> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<MsgStatusBean> msgStatus) {
        this.msgStatus = msgStatus;
    }
}
