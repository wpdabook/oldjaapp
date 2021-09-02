package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/28.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class IndexBean {


    /**
     * MsgXG : 4
     */

    private IndexitemBean GTXT;
    /**
     * MsgXG : 4
     */

    private IndexitemBean ZLJC;
    /**
     * MsgXG : 4
     */

    private IndexitemBean AQSC;
    /**
     * MsgXG : 4
     */

    private IndexitemBean WMSG;
    /**
     * MsgXG : 4
     */

    private IndexitemBean TZGG;
    /**
     * MsgXG : 4
     */

    private IndexitemBean SGTX;
    /**
     * MsgXG : 4
     */

    private IndexitemBean XMXX;
    /**
     * TZGGTop : ["关于顶模的工序生产协调","关于顶模的工序生产协调","关于顶模的工序生产协调","关于顶模的工序生产协调"]
     * GTXT : {"MsgXG":"4"}
     * ZLJC : {"MsgXG":"4"}
     * AQSC : {"MsgXG":"4"}
     * WMSG : {"MsgXG":"4"}
     * TZGG : {"MsgXG":"4"}
     * SGTX : {"MsgXG":"4"}
     * XMXX : {"MsgXG":"4"}
     */

    private List<String> TZGGTop;
    /**
     * topBars : [{"msgTitle":"关于顶模的工序生产协调","msgID":"320e835f-a37a-4e8e-bda5-ac2aacb9653e","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"4b12a977-2531-41a9-b9dc-82c2593bf6e6","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"63033420-5cef-4460-a747-0a44057a5112","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"ee08d70e-7752-4f3e-abea-28f22b4e7b0d","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"fc50095b-5594-4e54-9454-c83f46dca666","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"5566f9e1-d497-4caa-8147-910e05d33ca2","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于顶模的工序生产协调","msgID":"cd8aaea2-c643-42e8-9190-fcc4077ead50","Method":"GetCoordinateMsgDetails"},{"msgTitle":"关于核心筒内筒的工序生产协调","msgID":"cda9681e-ae6b-44d9-bac4-adc5ec3b4de3","Method":"GetCoordinateMsgDetails"}]
     * coordinateNumber : 8
     * qualityNumber : 0
     * safetyNumber : 0
     * civilizedNumber : 0
     * noticeNumber : 0
     * attentionNumber : 0
     * dailyLogNumber : 0
     * projectNumber : 0
     */

    private int coordinateNumber;
    private int qualityNumber;
    private int QualityMsgCount;
    private int safetyNumber;
    private int SafetyMsgCount;
    private int civilizedNumber;
    private int CivilizedMsgCount;
    private int noticeNumber;
    private int attentionNumber;
    private int dailyLogNumber;
    private int projectNumber;
    private int proAcceptanceNmuber;
    private int JGNoticeNumber;
    private int lawSuperviseNumber;
    private int ResumWorkExaminationCount;
    private int KeyExaminationCount;
    private int ProgExaminationCount;
    private int TypeTodoCount;

    public int getTypeTodoCount() {
        return TypeTodoCount;
    }

    public void setTypeTodoCount(int typeTodoCount) {
        TypeTodoCount = typeTodoCount;
    }

    public int getJGNoticeNumber() {
        return JGNoticeNumber;
    }

    public void setJGNoticeNumber(int JGNoticeNumber) {
        this.JGNoticeNumber = JGNoticeNumber;
    }

    public int getProAcceptanceNmuber() {
        return proAcceptanceNmuber;
    }

    public void setProAcceptanceNmuber(int proAcceptanceNmuber) {
        this.proAcceptanceNmuber = proAcceptanceNmuber;
    }

    public int getCivilizedMsgCount() {
        return CivilizedMsgCount;
    }

    public int getResumWorkExaminationCount() {
        return ResumWorkExaminationCount;
    }

    public void setResumWorkExaminationCount(int resumWorkExaminationCount) {
        ResumWorkExaminationCount = resumWorkExaminationCount;
    }

    public int getProgExaminationCount() {
        return ProgExaminationCount;
    }

    public void setProgExaminationCount(int progExaminationCount) {
        ProgExaminationCount = progExaminationCount;
    }

    public int getKeyExaminationCount() {
        return KeyExaminationCount;
    }

    public void setKeyExaminationCount(int keyExaminationCount) {
        KeyExaminationCount = keyExaminationCount;
    }

    public void setCivilizedMsgCount(int civilizedMsgCount) {
        CivilizedMsgCount = civilizedMsgCount;
    }

    public int getLawSuperviseNumber() {
        return lawSuperviseNumber;
    }

    public void setLawSuperviseNumber(int lawSuperviseNumber) {
        this.lawSuperviseNumber = lawSuperviseNumber;
    }

    public int getSafetyMsgCount() {
        return SafetyMsgCount;
    }

    public void setSafetyMsgCount(int safetyMsgCount) {
        SafetyMsgCount = safetyMsgCount;
    }

    public int getQualityMsgCount() {
        return QualityMsgCount;
    }

    public void setQualityMsgCount(int qualityMsgCount) {
        QualityMsgCount = qualityMsgCount;
    }

    /**
     * msgTitle : 关于顶模的工序生产协调
     * msgID : 320e835f-a37a-4e8e-bda5-ac2aacb9653e
     * Method : GetCoordinateMsgDetails
     */

    private List<TopBarsBean> topBars;

    public IndexitemBean getGTXT() {
        return GTXT;
    }

    public void setGTXT(IndexitemBean GTXT) {
        this.GTXT = GTXT;
    }

    public IndexitemBean getZLJC() {
        return ZLJC;
    }

    public void setZLJC(IndexitemBean ZLJC) {
        this.ZLJC = ZLJC;
    }

    public IndexitemBean getAQSC() {
        return AQSC;
    }

    public void setAQSC(IndexitemBean AQSC) {
        this.AQSC = AQSC;
    }

    public IndexitemBean getWMSG() {
        return WMSG;
    }

    public void setWMSG(IndexitemBean WMSG) {
        this.WMSG = WMSG;
    }

    public IndexitemBean getTZGG() {
        return TZGG;
    }

    public void setTZGG(IndexitemBean TZGG) {
        this.TZGG = TZGG;
    }

    public IndexitemBean getSGTX() {
        return SGTX;
    }

    public void setSGTX(IndexitemBean SGTX) {
        this.SGTX = SGTX;
    }

    public IndexitemBean getXMXX() {
        return XMXX;
    }

    public void setXMXX(IndexitemBean XMXX) {
        this.XMXX = XMXX;
    }

    public List<String> getTZGGTop() {
        return TZGGTop;
    }

    public void setTZGGTop(List<String> TZGGTop) {
        this.TZGGTop = TZGGTop;
    }

    public int getCoordinateNumber() {
        return coordinateNumber;
    }

    public void setCoordinateNumber(int coordinateNumber) {
        this.coordinateNumber = coordinateNumber;
    }

    public int getQualityNumber() {
        return qualityNumber;
    }

    public void setQualityNumber(int qualityNumber) {
        this.qualityNumber = qualityNumber;
    }

    public int getSafetyNumber() {
        return safetyNumber;
    }

    public void setSafetyNumber(int safetyNumber) {
        this.safetyNumber = safetyNumber;
    }

    public int getCivilizedNumber() {
        return civilizedNumber;
    }

    public void setCivilizedNumber(int civilizedNumber) {
        this.civilizedNumber = civilizedNumber;
    }

    public int getNoticeNumber() {
        return noticeNumber;
    }

    public void setNoticeNumber(int noticeNumber) {
        this.noticeNumber = noticeNumber;
    }

    public int getAttentionNumber() {
        return attentionNumber;
    }

    public void setAttentionNumber(int attentionNumber) {
        this.attentionNumber = attentionNumber;
    }

    public int getDailyLogNumber() {
        return dailyLogNumber;
    }

    public void setDailyLogNumber(int dailyLogNumber) {
        this.dailyLogNumber = dailyLogNumber;
    }

    public int getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(int projectNumber) {
        this.projectNumber = projectNumber;
    }

    public List<TopBarsBean> getTopBars() {
        return topBars;
    }

    public void setTopBars(List<TopBarsBean> topBars) {
        this.topBars = topBars;
    }

    public static class IndexitemBean {
        private String MsgXG;

        public String getMsgXG() {
            return MsgXG;
        }

        public void setMsgXG(String MsgXG) {
            this.MsgXG = MsgXG;
        }
    }


    public static class TopBarsBean {
        private String msgTitle;
        private String msgID;
        private String Method;

        public String getMsgTitle() {
            return msgTitle;
        }

        public void setMsgTitle(String msgTitle) {
            this.msgTitle = msgTitle;
        }

        public String getMsgID() {
            return msgID;
        }

        public void setMsgID(String msgID) {
            this.msgID = msgID;
        }

        public String getMethod() {
            return Method;
        }

        public void setMethod(String Method) {
            this.Method = Method;
        }
    }
}
