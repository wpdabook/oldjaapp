package com.a21zhewang.constructionapp.bean;

/**
 * Created by Administrator on 2021/1/13.
 */

public class NoiceFile {
    private String AreaTaskID;
    private int CanOperate;
    private String CreateTime;
    private String DocSource;
    private String Nid;
    private String Title;
    private String TaskStatus;
    private String HandleUnitName;

    public String getHandleUnitName() {
        return HandleUnitName;
    }

    public void setHandleUnitName(String handleUnitName) {
        HandleUnitName = handleUnitName;
    }

    public String getAreaTaskID() {
        return AreaTaskID;
    }

    public void setAreaTaskID(String areaTaskID) {
        AreaTaskID = areaTaskID;
    }

    public int getCanOperate() {
        return CanOperate;
    }

    public void setCanOperate(int canOperate) {
        CanOperate = canOperate;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getDocSource() {
        return DocSource;
    }

    public void setDocSource(String docSource) {
        DocSource = docSource;
    }

    public String getNid() {
        return Nid;
    }

    public void setNid(String nid) {
        Nid = nid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
