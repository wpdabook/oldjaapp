package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2020/8/21.
 */

public class ProjectStatus {

    private String areaStatus;
    private String noConstrType;
    private String noConstrReason;
    private List<FilesBean> files;

    public String getAreaStatus() {
        return areaStatus;
    }

    public void setAreaStatus(String areaStatus) {
        this.areaStatus = areaStatus;
    }

    public String getNoConstrType() {
        return noConstrType;
    }

    public void setNoConstrType(String noConstrType) {
        this.noConstrType = noConstrType;
    }

    public String getNoConstrReason() {
        return noConstrReason;
    }

    public void setNoConstrReason(String noConstrReason) {
        this.noConstrReason = noConstrReason;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }
}
