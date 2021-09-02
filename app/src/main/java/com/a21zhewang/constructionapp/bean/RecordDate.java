package com.a21zhewang.constructionapp.bean;

/**
 * Created by WP-PC on 2019/6/3.
 */

public class RecordDate {

    private String id;
    private String conManager;
    private String title;
    private String createDate;
    private int type; //1：限期整改	2：停工整改	3：简易处罚	4：立案处罚
    private int status;  //1:处理中  2：已闭合
    private String typeName;
    private int readStatus;
    private boolean isRead;
    private int otherStatus;
    public boolean isRead() {
        return isRead;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConManager() {
        return conManager;
    }

    public void setConManager(String conManager) {
        this.conManager = conManager;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getOtherStatus() {
        return otherStatus;
    }

    public void setOtherStatus(int otherStatus) {
        this.otherStatus = otherStatus;
    }
}
