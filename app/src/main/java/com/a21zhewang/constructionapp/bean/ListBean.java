package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/10/12.
 *
 * @auto
 */

public class ListBean{

    /**
     * recordList : []
     * total : 0
     */

    private int total;
    private List<ProjectSynopsis> recordList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ProjectSynopsis> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ProjectSynopsis> recordList) {
        this.recordList = recordList;
    }
}
