package com.a21zhewang.constructionapp.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zhewang_ljw on 2017/2/8.
 * 控件命名规范：当前界面名+控件名+名字
 * 此文件作用：
 */
@Table(name="UPJsonBean")
public class UPJsonBean {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "datatype")//存储数据类型
    private String datatype;

    @Column(name = "datatypename")//存储数据类型
    private String datatypename;

    @Column(name = "addtime")//存储时间
    private String addtime;


    @Column(name = "datatext")//存储数据
    private String datatext;

    @Column(name = "filepaths")//文件
    private String filepaths;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getDatatypename() {
        return datatypename;
    }

    public void setDatatypename(String datatypename) {
        this.datatypename = datatypename;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getDatatext() {
        return datatext;
    }

    public void setDatatext(String datatext) {
        this.datatext = datatext;
    }

    public String getFilepaths() {
        return filepaths;
    }

    public void setFilepaths(String filepaths) {
        this.filepaths = filepaths;
    }

    @Override
    public String toString() {
        return "UPJsonBean{" +
                "id=" + id +
                ", datatype='" + datatype + '\'' +
                ", datatypename='" + datatypename + '\'' +
                ", addtime='" + addtime + '\'' +
                ", datatext='" + datatext + '\'' +
                ", filepaths=" + filepaths +
                '}';
    }
}
