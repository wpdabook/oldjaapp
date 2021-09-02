package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/5/29.
 */

public class JdzfCompanysBean {

    /**
     * 监督执法--单位
     */
    private List<JdzfCompanys> backdata;
    /**
     * 通知公告--单位
     */
    private List<JdzfCompanys> companys;

    public List<JdzfCompanys> getBackdata() {
        return backdata;
    }

    public List<JdzfCompanys> getCompanys() {
        return companys;
    }

    public void setCompanys(List<JdzfCompanys> companys) {
        this.companys = companys;
    }

    public void setBackdata(List<JdzfCompanys> backdata) {
        this.backdata = backdata;
    }
}
