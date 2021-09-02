package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/26.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class DicBean {

private List<MsgType> msgTypes;
    private List<String> CLQK;

    public List<MsgType> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<MsgType> msgTypes) {
        this.msgTypes = msgTypes;
    }

    /**

     * dicID : C01005001
     * dicName : 机械
     */
//
//    private List<XTLBBean> XTLB;
    /**
     * dicID : C01005001
     * dicName : 机械
     * Child : [{"ChildID":"C01005001001","ChildName":"塔吊"},{"ChildID":"C01005001002","ChildName":"电梯"}]
     */

//    private List<MsgTypeFatherBean> msgTypeFather;
    /**
     * status : 催办
     * color : 0079C9
     */

    private List<MsgStatusBean> msgStatus;

    public List<String> getCLQK() {
        return CLQK;
    }

    public void setCLQK(List<String> CLQK) {
        this.CLQK = CLQK;
    }

//    public List<XTLBBean> getXTLB() {
//        return XTLB;
//    }
//
//    public void setXTLB(List<XTLBBean> XTLB) {
//        this.XTLB = XTLB;
//    }
//
//    public List<MsgTypeFatherBean> getMsgTypeFather() {
//        return msgTypeFather;
//    }
//
//    public void setMsgTypeFather(List<MsgTypeFatherBean> msgTypeFather) {
//        this.msgTypeFather = msgTypeFather;
//    }

    public List<MsgStatusBean> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<MsgStatusBean> msgStatus) {
        this.msgStatus = msgStatus;
    }

//    public static class XTLBBean {
//        private String dicID;
//        private String dicName;
//
//        public String getDicID() {
//            return dicID;
//        }
//
//        public void setDicID(String dicID) {
//            this.dicID = dicID;
//        }
//
//        public String getDicName() {
//            return dicName;
//        }
//
//        public void setDicName(String dicName) {
//            this.dicName = dicName;
//        }
//    }

//    public static class MsgTypeFatherBean {
//        private String dicID;
//        private String dicName;
//        /**
//         * ChildID : C01005001001
//         * ChildName : 塔吊
//         */
//
//        private List<ChildBean> Child;
//
//        public String getDicID() {
//            return dicID;
//        }
//
//        public void setDicID(String dicID) {
//            this.dicID = dicID;
//        }
//
//        public String getDicName() {
//            return dicName;
//        }
//
//        public void setDicName(String dicName) {
//            this.dicName = dicName;
//        }
//
//        public List<ChildBean> getChild() {
//            return Child;
//        }
//
//        public void setChild(List<ChildBean> Child) {
//            this.Child = Child;
//        }
//
//        public static class ChildBean {
//            private String ChildID;
//            private String ChildName;
//
//            public String getChildID() {
//                return ChildID;
//            }
//
//            public void setChildID(String ChildID) {
//                this.ChildID = ChildID;
//            }
//
//            public String getChildName() {
//                return ChildName;
//            }
//
//            public void setChildName(String ChildName) {
//                this.ChildName = ChildName;
//            }
//        }
//    }

    public static class MsgStatusBean {
        private String status;
        private String color;

        public MsgStatusBean(String status, String color) {
            this.status = status;
            this.color = color;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
