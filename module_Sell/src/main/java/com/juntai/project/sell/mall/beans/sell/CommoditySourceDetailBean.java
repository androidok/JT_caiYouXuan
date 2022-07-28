package com.juntai.project.sell.mall.beans.sell;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditySourceDetailBean extends BaseResult {


    /**
     * data : {"commodityId":11,"supplier":2,"purchaseTime":"2022-07-24","purchaseName":"张三","photoList":[{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]}
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
         * commodityId : 11
         * supplier : 2
         * purchaseTime : 2022-07-24
         * purchaseName : 张三
         * photoList : [{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试111"},{"commodityId":11,"photoOne":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoTwo":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","photoThree":"https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg","remarks":"测试222"}]
         */

        private int commodityId;
        private int supplier;
        private String purchaseTime;
        private String purchaseName;
        private String account;
        private String token;
        private List<PhotoListBean> photoList;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getAccount() {
            return account == null ? "" : account;
        }

        public void setAccount(String account) {
            this.account = account == null ? "" : account;
        }

        public String getToken() {
            return token == null ? "" : token;
        }

        public void setToken(String token) {
            this.token = token == null ? "" : token;
        }

        public int getSupplier() {
            return supplier;
        }

        public void setSupplier(int supplier) {
            this.supplier = supplier;
        }

        public String getPurchaseTime() {
            return purchaseTime;
        }

        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }

        public String getPurchaseName() {
            return purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }

        public List<PhotoListBean> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<PhotoListBean> photoList) {
            this.photoList = photoList;
        }

        public static class PhotoListBean {
            /**
             * commodityId : 11
             * photoOne : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
             * photoTwo : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
             * photoThree : https://www.juntaikeji.com:21900/2022-05-08/1651970794230.jpg
             * remarks : 测试111
             */

            private String photoOne;
            private String photoTwo;
            private String photoThree;
            private String remarks;


            public String getPhotoOne() {
                return photoOne == null ? "" : photoOne;
            }

            public void setPhotoOne(String photoOne) {
                this.photoOne = photoOne == null ? "" : photoOne;
            }

            public String getPhotoTwo() {
                return photoTwo == null ? "" : photoTwo;
            }

            public void setPhotoTwo(String photoTwo) {
                this.photoTwo = photoTwo == null ? "" : photoTwo;
            }

            public String getPhotoThree() {
                return photoThree == null ? "" : photoThree;
            }

            public void setPhotoThree(String photoThree) {
                this.photoThree = photoThree == null ? "" : photoThree;
            }

            public String getRemarks() {
                return remarks == null ? "" : remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks == null ? "" : remarks;
            }
        }
    }
}
