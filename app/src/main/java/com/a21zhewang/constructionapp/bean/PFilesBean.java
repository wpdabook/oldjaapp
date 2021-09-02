package com.a21zhewang.constructionapp.bean;

import java.io.Serializable;

/**
 * Created by zhewang_ljw on 2017/6/13.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class PFilesBean implements Serializable{
    private String fileBrief;
    private String url;
    private String viewUrl;

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getFileBrief() {
        return fileBrief;
    }

    public void setFileBrief(String fileBrief) {
        this.fileBrief = fileBrief;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
