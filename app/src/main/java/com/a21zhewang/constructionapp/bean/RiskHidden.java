package com.a21zhewang.constructionapp.bean;

import java.util.List;

public class RiskHidden {
    /*风险隐患-总*/
    private List<RiskHiddenBean> statusList;
    /*风险隐患-安全隐患详情*/
    private List<RiskHiddenBean> data;

    public List<RiskHiddenBean> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<RiskHiddenBean> statusList) {
        this.statusList = statusList;
    }

    public List<RiskHiddenBean> getData() {
        return data;
    }

    public void setData(List<RiskHiddenBean> data) {
        this.data = data;
    }
}
