package com.a21zhewang.constructionapp.bean;

/**
 * 本月或本周 使用人数
 */
public class RiskQueryTimeUserList {

    private int createTime;
    /*使用人数*/
    private int  userTotal;

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    public int getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(int userTotal) {
        this.userTotal = userTotal;
    }
}
