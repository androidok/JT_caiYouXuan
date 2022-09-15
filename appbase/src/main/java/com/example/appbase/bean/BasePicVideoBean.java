package com.example.appbase.bean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class BasePicVideoBean {

    public static int TYPE_NUM = 0;
    public static int TYPE_IMAGE = 1;
    public static int TYPE_VIDEO = 2;
    private String type;
    private String url;
    private String cover;

    public BasePicVideoBean(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public BasePicVideoBean(String type, String url, String cover) {
        this.type = type;
        this.url = url;
        this.cover = cover;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type == null ? "" : type;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url == null ? "" : url;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? "" : cover;
    }
}
