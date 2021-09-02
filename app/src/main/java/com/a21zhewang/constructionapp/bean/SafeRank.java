package com.a21zhewang.constructionapp.bean;

import java.util.List;

public class SafeRank {

    private String statusName;
    private List<TotalLevel> totalLevel;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<TotalLevel> getTotalLevel() {
        return totalLevel;
    }

    public void setTotalLevel(List<TotalLevel> totalLevel) {
        this.totalLevel = totalLevel;
    }
}
