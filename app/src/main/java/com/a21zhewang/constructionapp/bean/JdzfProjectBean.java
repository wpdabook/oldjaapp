package com.a21zhewang.constructionapp.bean;

import java.util.List;

/**
 * 控件命名规范：当前界面名+控件名+名字
 */

public class JdzfProjectBean {

    /**
     * projectID : GC10001
     * projectShortName : 绿地636项目
     * etpInfoList : [{"etpID":"QY201704191154","etpShortName":"土建一部","userInfoList":[{"userID":"13554277692","userName":"李少强"},{"userID":"13588703356","userName":"阳杰"},{"userID":"18071476156","userName":"陈成"},{"userID":"liubingyang","userName":"刘冰洋"},{"userID":"longjinwei","userName":"隆谨蔚"},{"userID":"wuyuhuai","userName":"吴玉怀"}]},{"etpID":"QY201704191156","etpShortName":"中建众鑫","userInfoList":[{"userID":"13517248172","userName":"黄  斌"}]}]
     */

    private List<Project> projectList;

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }


}
