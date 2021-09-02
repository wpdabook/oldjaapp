package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by WP-PC on 2019/11/13.
 */

public class SafeLevel {

    private String statusName;
    private List<SaveStatus> totalLevel;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<SaveStatus> getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(List<SaveStatus> totalLevel) {
        this.totalLevel = totalLevel;
    }
}
