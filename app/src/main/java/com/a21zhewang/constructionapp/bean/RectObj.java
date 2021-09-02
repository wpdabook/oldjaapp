package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by Administrator on 2021/3/11.
 */

public class RectObj {

    private String rectId;
    private String remark;
    private String createDate;
    private List<FileBean> fileInfo1;

    public String getRectId() {
        return rectId;
    }

    public void setRectId(String rectId) {
        this.rectId = rectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List<FileBean> getFileInfo1() {
        return fileInfo1;
    }

    public void setFileInfo1(List<FileBean> fileInfo1) {
        this.fileInfo1 = fileInfo1;
    }
}
