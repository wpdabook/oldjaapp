package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhewang_ljw on 2017/9/13.
 *
 * @auto
 */

public class MsgType implements Parcelable {

    private String typeID;
    private String typeName;
    private String remark;
    private List<MsgType> msgTypes;

    public MsgType() {
    }

    public MsgType(String typeID, String typeName, String remark,List<MsgType> msgTypes) {
        this.typeID = typeID;
        this.typeName = typeName;
        this.msgTypes = msgTypes;
        this.remark = remark;
    }
    public MsgType(CheckType checkType) {
        this.typeID = checkType.getDicID();
        this.typeName = checkType.getDicName();
        this.remark = checkType.getRemark();
        this.msgTypes = checkTypes2msgTypes(checkType.getCheckItemList());
    }

    public static List<MsgType>  checkTypes2msgTypes(List<? extends CheckType> checkTypes){
        if (checkTypes==null&&checkTypes.size()==0)return null;
        List<MsgType> msgTypes=new ArrayList<>();
        for (int i = 0; i < checkTypes.size(); i++) {
            msgTypes.add(new MsgType(checkTypes.get(i)))  ;
        }
            return msgTypes;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<MsgType> getMsgTypes() {
        return msgTypes;
    }

    public void setMsgTypes(List<MsgType> msgTypes) {
        this.msgTypes = msgTypes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.typeID);
        dest.writeString(this.typeName);
        dest.writeString(this.remark);
        dest.writeList(this.msgTypes);
    }

    protected MsgType(Parcel in) {
        this.typeID = in.readString();
        this.typeName = in.readString();
        this.remark = in.readString();
        this.msgTypes = new ArrayList<MsgType>();
        in.readList(this.msgTypes, MsgType.class.getClassLoader());
    }

    public static final Parcelable.Creator<MsgType> CREATOR = new Parcelable.Creator<MsgType>() {
        @Override
        public MsgType createFromParcel(Parcel source) {
            return new MsgType(source);
        }

        @Override
        public MsgType[] newArray(int size) {
            return new MsgType[size];
        }
    };

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static Creator<MsgType> getCREATOR() {
        return CREATOR;
    }
}
