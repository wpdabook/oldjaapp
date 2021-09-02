package com.a21zhewang.constructionapp.ui.tzgg;


public  class TzggActivity extends TzggAndSgtxActivity{


    /**
     * @return 设置请求的的页面
     */
    @Override
    public String setmethod() {
        return "notice2";
    }

    /**
     * @return 设置请求数据的方法
     */
    @Override
    public String setgetdatamethod() {
        return "GetListNoticeMsg";
    }

    @Override
    public String settitle() {
        return "通知公告";
    }

    @Override
    public String setaddimgisvisiable() {
        return "Notice2AddBtn";
    }
}
