package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/14.
 *
 * @auto
 */

public class ErrorMsgBean implements Serializable {


    private String relationID;
    private String relationTable;
    private String projectAreaName;
    private String projectAreaID;
    /**从图库还是相册选择标识*/
    private int selectTag;
    private String checkContent;
    private List<FilesBean>  filesBeans;

    public List<FilesBean> getFilesBeans() {
        return filesBeans;
    }

    public void setFilesBeans(List<FilesBean> filesBeans) {
        this.filesBeans = filesBeans;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }
    public String getProjectAreaID() {
        return projectAreaID;
    }

    public void setProjectAreaID(String projectAreaID) {
        this.projectAreaID = projectAreaID;
    }

    private String floor;
    private String elevation;
    private String recEtpID;
    private String recEtpShortName;

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getRelationTable() {
        return relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getProjectAreaName() {
        return projectAreaName;
    }

    public void setProjectAreaName(String projectAreaName) {
        this.projectAreaName = projectAreaName;
    }

    public String getRecEtpID() {
        return recEtpID;
    }

    public void setRecEtpID(String recEtpID) {
        this.recEtpID = recEtpID;
    }

    public String getRecEtpShortName() {
        return recEtpShortName;
    }

    public void setRecEtpShortName(String recEtpShortName) {
        this.recEtpShortName = recEtpShortName;
    }

    public int getSelectTag() {
        return selectTag;
    }

    public void setSelectTag(int selectTag) {
        this.selectTag = selectTag;
    }
}
