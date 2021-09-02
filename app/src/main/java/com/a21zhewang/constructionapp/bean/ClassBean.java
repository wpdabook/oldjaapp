package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * Created by fan.feng on 2017/9/8.
 */

public class ClassBean {

    public String className;
    public List<String> classStudents;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<String> getClassStudents() {
        return classStudents;
    }

    public void setClassStudents(List<String> classStudents) {
        this.classStudents = classStudents;
    }
}
