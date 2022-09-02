package com.juntai.disabled.basecomponent.bean.shop;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/8 14:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/8 14:03
 */
public class ShopDetailBuyBean extends BaseResult {


    /**
     * data : {"id":1,"userId":101,"name":"测试小店","headPortrait":"https://www.juntaikeji.com","introduction":"店铺简介","shopFraction":5,"createTime":"2022-04-24 12:03:01","backImg":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","cameraUrl":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","shopImg":"https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png,https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png,https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png","classifyList":[{"id":52,"shopId":1,"shopClassifyName":"上衣"},{"id":53,"shopId":1,"shopClassifyName":"鞋子"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * userId : 101
         * name : 测试小店
         * headPortrait : https://www.juntaikeji.com
         * introduction : 店铺简介
         * shopFraction : 5.0
         * createTime : 2022-04-24 12:03:01
         * backImg : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * cameraUrl : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * shopImg : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png,https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png,https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
         * classifyList : [{"id":52,"shopId":1,"shopClassifyName":"上衣"},{"id":53,"shopId":1,"shopClassifyName":"鞋子"}]
         */

        private int id;
        private int userId;
        private int isCollect;
        private String shareUrl;//分享地址
        private String shareLiveUrl;//分享直播地址
        private String liveNumber;//房间号
        private String rtmpUrl;//直播地址
        private String coverImg;//直播封面
        private String name;
        private String headPortrait;
        private String phoneNumber;
        /**
         * 直播标题
         */
        private String title;
        private String introduction;
        private String startTime;
        private String endTime;
        private double shopFraction;
        private String createTime;
        private String backImg;
        private String cameraUrl;
        private String cameraNumber;
        private String cameraCover;
        private int commodityCount;//在售商品个数
        private String shopImg;
        private List<ClassifyListBean> classifyList;

        public int getCommodityCount() {
            return commodityCount;
        }

        public void setCommodityCount(int commodityCount) {
            this.commodityCount = commodityCount;
        }

        public String getCoverImg() {
            return coverImg == null ? "" : coverImg;
        }

        public String getPhoneNumber() {
            return phoneNumber == null ? "" : phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber == null ? "" : phoneNumber;
        }

        public String getStartTime() {
            return startTime == null ? "" : startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime == null ? "" : startTime;
        }

        public String getEndTime() {
            return endTime == null ? "" : endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime == null ? "" : endTime;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg == null ? "" : coverImg;
        }

        public String getLiveNumber() {
            return liveNumber == null ? "" : liveNumber;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public String getRtmpUrl() {
            return rtmpUrl == null ? "" : rtmpUrl;
        }

        public void setRtmpUrl(String rtmpUrl) {
            this.rtmpUrl = rtmpUrl == null ? "" : rtmpUrl;
        }

        public void setTitle(String title) {
            this.title = title == null ? "" : title;
        }

        public void setLiveNumber(String liveNumber) {
            this.liveNumber = liveNumber == null ? "" : liveNumber;
        }

        public int getIsCollect() {
            return isCollect;
        }

        public String getCameraNumber() {
            return cameraNumber == null ? "" : cameraNumber;
        }

        public void setCameraNumber(String cameraNumber) {
            this.cameraNumber = cameraNumber == null ? "" : cameraNumber;
        }

        public String getCameraCover() {
            return cameraCover == null ? "" : cameraCover;
        }

        public void setCameraCover(String cameraCover) {
            this.cameraCover = cameraCover == null ? "" : cameraCover;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getId() {
            return id;
        }

        public String getShareUrl() {
            return shareUrl == null ? "" : shareUrl;
        }

        public void setShareUrl(String shareUrl) {
            this.shareUrl = shareUrl == null ? "" : shareUrl;
        }

        public String getShareLiveUrl() {
            return shareLiveUrl == null ? "" : shareLiveUrl;
        }

        public void setShareLiveUrl(String shareLiveUrl) {
            this.shareLiveUrl = shareLiveUrl == null ? "" : shareLiveUrl;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public double getShopFraction() {
            return shopFraction;
        }

        public void setShopFraction(double shopFraction) {
            this.shopFraction = shopFraction;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getBackImg() {
            return backImg;
        }

        public void setBackImg(String backImg) {
            this.backImg = backImg;
        }

        public String getCameraUrl() {
            return cameraUrl;
        }

        public void setCameraUrl(String cameraUrl) {
            this.cameraUrl = cameraUrl;
        }

        public String getShopImg() {
            return shopImg;
        }

        public void setShopImg(String shopImg) {
            this.shopImg = shopImg;
        }

        public List<ClassifyListBean> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<ClassifyListBean> classifyList) {
            this.classifyList = classifyList;
        }

        public static class ClassifyListBean {
            /**
             * id : 52
             * shopId : 1
             * shopClassifyName : 上衣
             */

            private int id;
            private int shopId;
            private String shopClassifyName;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getShopId() {
                return shopId;
            }

            public void setShopId(int shopId) {
                this.shopId = shopId;
            }

            public String getShopClassifyName() {
                return shopClassifyName;
            }

            public void setShopClassifyName(String shopClassifyName) {
                this.shopClassifyName = shopClassifyName;
            }
        }
    }
}
