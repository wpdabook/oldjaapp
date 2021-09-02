package com.a21zhewang.constructionapp.bean;

import android.text.TextUtils;

import com.a21zhewang.constructionapp.ui.zxjc.AddzxjcActivity;
import com.a21zhewang.constructionapp.ui.zxjc.CheckListAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhewang_ljw on 2017/9/5.
 *
 * @auto
 */

public abstract class CheckType<T extends CheckType> extends AbstractExpandableItem<T> implements MultiItemEntity, Serializable {

    public static class Status{
        public  final  static int NOSELECT=0;
        public  final  static int ALLSELECT=1;
        public  final  static int SOMESELECT=2;
    }

    /**
     * dicID : P01003001
     * dicName : 地下结构施工
     */
    public String remark;
    public String dicID;
    public String dicName;
  //  public Boolean isSelector = false;
    public Boolean mustSelect;
    public String level;
    private List<T> checkItemList;
    private String recordRLID;
    private String checkContent;
    private List<FilesBean> files;
  // private String startTime;
   // private String endTime;
    private String planStartDate;
    private String planEndDate;

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getStartTime() {
        return planStartDate;
    }

    public void setStartTime(String startTime) {
        this.planStartDate = startTime;
    }

    public String getEndTime() {
        return planEndDate;
    }

    public void setEndTime(String endTime) {
        this.planEndDate = endTime;
    }

    public List<FilesBean> getFiles() {
        return files;
    }

    public void setFiles(List<FilesBean> files) {
        this.files = files;
    }

    private String checkResult = "未检查";
    private List<ErrorMsgBean> errorMsgList;
    private String errorFinishPercent;
    private String dicFatherID;
    private String dicFatherName;
    private int status=0;
    private int errorNumber = 0;


    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getDicFatherID() {
        return dicFatherID;
    }

    public void setDicFatherID(String dicFatherID) {
        this.dicFatherID = dicFatherID;
    }

    public int getStatus() {
        return Integer.valueOf(status);
    }

    public void setStatus(int status) {
        this.status = Integer.valueOf(status);
    }

    public String getDicFatherName() {
        return dicFatherName;
    }

    public void setDicFatherName(String dicFatherName) {
        this.dicFatherName = dicFatherName;
    }

    public String getErrorFinishPercent() {
        return errorFinishPercent;
    }

    public void setErrorFinishPercent(String errorFinishPercent) {
        this.errorFinishPercent = errorFinishPercent;
    }


    public String getRecordRLID() {
        return recordRLID;
    }

    public CheckType() {
    }
    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }
    public void setRecordRLID(String recordRLID) {
        this.recordRLID = recordRLID;
    }

    public void setRecordRLID() {
        this.recordRLID = UUID.randomUUID().toString();
    }


    public List<ErrorMsgBean> getErrorMsgList() {
        if (errorMsgList == null) this.errorMsgList = new ArrayList<>();
        return errorMsgList;
    }

    public void setErrorMsgList(List<ErrorMsgBean> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }


    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }


    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getlevel() {
        return level;
    }

    public void setlevel(String level) {
        this.level = level;
    }

    public List<T> getCheckItemList() {
        return checkItemList;
    }

    public void setCheckItemList(List<T> checkItemList) {
        if (checkItemList == null) {
            checkItemList = new ArrayList<>();
        }
        this.checkItemList = checkItemList;
    }

    @Override
    public List<T> getSubItems() {
        return checkItemList;
    }

    public Boolean getMustSelect() {
        return mustSelect;
    }

    public void setMustSelect(Boolean mustSelect) {
        this.mustSelect = mustSelect;
    }
}
