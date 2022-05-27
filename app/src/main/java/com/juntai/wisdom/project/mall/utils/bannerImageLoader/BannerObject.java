package com.juntai.wisdom.project.mall.utils.bannerImageLoader;

import java.io.Serializable;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/21 16:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/21 16:52
 */
public class BannerObject implements Serializable {
    /**
     * 图片
     */
    public final static String BANNER_TYPE_IMAGE = "BANNER_TYPE_IMAGE";

    /**
     * 视频
     */
    public final static String BANNER_TYPE_VIDEO = "BANNER_TYPE_VIDEO";
    /**
     * 流媒体
     */
    public final static String BANNER_TYPE_RTMP = "BANNER_TYPE_RTMP";


    private String eventKey;

    private Object eventObj;

    public BannerObject(String eventKey, Object eventObj) {
        this.eventKey = eventKey;
        this.eventObj = eventObj;
    }

    public String getEventKey() {
        return eventKey == null ? "" : eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? "" : eventKey;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }


    public class VideoBean {

        private String videoPath;
        private String videoCover;

        public VideoBean(String videoPath, String videoCover) {
            this.videoPath = videoPath;
            this.videoCover = videoCover;
        }

        public String getVideoPath() {
            return videoPath == null ? "" : videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath == null ? "" : videoPath;
        }

        public String getVideoCover() {
            return videoCover == null ? "" : videoCover;
        }

        public void setVideoCover(String videoCover) {
            this.videoCover = videoCover == null ? "" : videoCover;
        }
    }
}
