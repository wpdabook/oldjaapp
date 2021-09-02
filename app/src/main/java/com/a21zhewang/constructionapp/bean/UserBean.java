package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.a21zhewang.constructionapp.publicContent.PublicUtils;

import java.util.List;

/**
 * Created by zhewang_ljw on 2017/4/20.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */

public class UserBean implements Parcelable{


    /**
     * userType : 分包
     * etpShortName : 土建一部
     * userName : 李四
     * userMail :
     * userPhone : 12345678911
     * sign : lxRp2FXGygHIR6OlOtn6Mg==
     * deptName : 管理员
     * etpID : QY201704191154
     * login_id : lisi
     */

    private String userType;
    private String etpShortName;
    private String userName;
    private String userMail;
    private String userPhone;
    private String sign;
    private String deptName;
    private String etpID;
    private String userID;
    private String weather;
    private int GraphStart;
    private String className;
    private List<IndexItem> modules;

    protected UserBean(Parcel in) {
        userType = in.readString();
        etpShortName = in.readString();
        userName = in.readString();
        userMail = in.readString();
        userPhone = in.readString();
        sign = in.readString();
        deptName = in.readString();
        etpID = in.readString();
        userID = in.readString();
        weather = in.readString();
        GraphStart = in.readInt();
        userTypeID = in.readString();
        className = in.readString();
        modules = in.createTypedArrayList(IndexItem.CREATOR);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getGraphStart() {
        return GraphStart;
    }

    public void setGraphStart(int graphStart) {
        GraphStart = graphStart;
    }


    public List<IndexItem> getModules() {
        for(int i=0;i<modules.size();i++){
            if("SupervisionEnforcement".equals(modules.get(i).getMdID())){
                modules.remove(i);
                i--;
            }else if("DataManagement".equals(modules.get(i).getMdID())){
                modules.remove(i);
                i--;
            }else if ("Statistics_Basics".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_one","1");
                modules.remove(i);
                i--;
            }
            else  if("Statistics_KeyFollow".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_two","2");
                modules.remove(i);
                i--;
            }else if("Statistics_ProjectInfo".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_third","3");
                PublicUtils.putspstring("tab_two_projectId",modules.get(i).getMdProjectId());
                modules.remove(i);
                i--;
            }else if("Statistics_CheckInfo".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_four","4");
                modules.remove(i);
                i--;
            }else if ("Statistics_Rank".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_five","5");
                modules.remove(i);
                i--;
            }else if ("Statistics_ResumWork".equals(modules.get(i).getMdID())){
                PublicUtils.putspstring("tab_tag_six","6");
                modules.remove(i);
                i--;
            }else if ("Epidemic".equals(modules.get(i).getMdID())){
                modules.remove(i);
                i--;
            }
        }
        return modules;
    }

    public void setModules(List<IndexItem> modules) {
        this.modules = modules;

    }

    public String getUserTypeID() {
        return userTypeID;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    private String userTypeID;
private List<ButtonBean> buttons;

    public List<ButtonBean> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonBean> buttons) {
        this.buttons = buttons;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEtpShortName() {
        return etpShortName;
    }

    public void setEtpShortName(String etpShortName) {
        this.etpShortName = etpShortName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getEtpID() {
        return etpID;
    }

    public void setEtpID(String etpID) {
        this.etpID = etpID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userType);
        dest.writeString(etpShortName);
        dest.writeString(userName);
        dest.writeString(userMail);
        dest.writeString(userPhone);
        dest.writeString(sign);
        dest.writeString(deptName);
        dest.writeString(etpID);
        dest.writeString(userID);
        dest.writeString(weather);
        dest.writeString(className);
        dest.writeList(modules);
    }
}
