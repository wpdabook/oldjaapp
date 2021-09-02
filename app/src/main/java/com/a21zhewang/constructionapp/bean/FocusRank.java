package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/8/14.
 */

public class FocusRank {

    /*重点检查排名*/
    private List<FocusRankInfo> Data;

    private int Code;

    private String Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public List<FocusRankInfo> getData() {
        return Data;
    }

    public void setData(List<FocusRankInfo> data) {
        Data = data;
    }
}
