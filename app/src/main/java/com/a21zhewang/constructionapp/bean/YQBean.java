package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by wp-pc on 2020/4/5.
 */

public class YQBean {
    private List<ProjectUser> ProjectUserList;
    private List<Equipment> EquipmentList;
    private List<Dange> DangerList;
    private int ProjectUserTotal;
    private int EquipmentTotal;
    private int DangerTotal;
    private int ProjectUserPageCount;
    private int EquipmentPageCount;
    private int DangerPageCount;

    public List<ProjectUser> getProjectUserList() {
        return ProjectUserList;
    }

    public void setProjectUserList(List<ProjectUser> projectUserList) {
        ProjectUserList = projectUserList;
    }

    public List<Equipment> getEquipmentList() {
        return EquipmentList;
    }

    public void setEquipmentList(List<Equipment> equipmentList) {
        EquipmentList = equipmentList;
    }

    public List<Dange> getDangerList() {
        return DangerList;
    }

    public void setDangerList(List<Dange> dangerList) {
        DangerList = dangerList;
    }

    public int getProjectUserTotal() {
        return ProjectUserTotal;
    }

    public void setProjectUserTotal(int projectUserTotal) {
        ProjectUserTotal = projectUserTotal;
    }

    public int getEquipmentTotal() {
        return EquipmentTotal;
    }

    public void setEquipmentTotal(int equipmentTotal) {
        EquipmentTotal = equipmentTotal;
    }

    public int getDangerTotal() {
        return DangerTotal;
    }

    public void setDangerTotal(int dangerTotal) {
        DangerTotal = dangerTotal;
    }

    public int getProjectUserPageCount() {
        return ProjectUserPageCount;
    }

    public void setProjectUserPageCount(int projectUserPageCount) {
        ProjectUserPageCount = projectUserPageCount;
    }

    public int getEquipmentPageCount() {
        return EquipmentPageCount;
    }

    public void setEquipmentPageCount(int equipmentPageCount) {
        EquipmentPageCount = equipmentPageCount;
    }

    public int getDangerPageCount() {
        return DangerPageCount;
    }

    public void setDangerPageCount(int dangerPageCount) {
        DangerPageCount = dangerPageCount;
    }
}
