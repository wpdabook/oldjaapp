package com.a21zhewang.constructionapp.bean;

import java.util.List;

import cn.hzw.graffiti.GraffitiColor;

/**
 * Created by WP-PC on 2019/10/26.
 */

public class ProceduralTitle {

    private List<TypeList> typeList;
    private List<StatusList> statusList;

    public List<TypeList> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeList> typeList) {
        this.typeList = typeList;
    }

    public List<StatusList> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusList> statusList) {
        this.statusList = statusList;
    }
}
