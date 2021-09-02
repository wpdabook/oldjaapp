package com.a21zhewang.constructionapp.bean;

/**
 * 整改图片
 * Created by WP-PC on 2019/5/31.
 */

public class FileBean {

        private String fileBrief;
        private String url;
        private String viewUrl;

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    public String getFileBrief() {
            return fileBrief;
        }

        public void setFileBrief(String fileBrief) {
            this.fileBrief = fileBrief;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
