package com.a21zhewang.constructionapp.bean;

/**
 * Created by zhewang_ljw on 2017/4/20.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class ObjBean {


    /**
     * Sign : 123456789
     * Ver : 0.2
     * Code : 1
     * Msg : 无此用户
     * Data : null
     */

    private int Code;
    private String Msg;
    private Object Data;


    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }
}
