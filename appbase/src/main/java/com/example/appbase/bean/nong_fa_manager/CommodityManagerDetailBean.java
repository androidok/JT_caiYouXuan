package com.example.appbase.bean.nong_fa_manager;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommodityManagerDetailBean extends BaseResult {


    /**
     * data : {"id":224,"shopName":"杂货店","classifyName":"日常","categoryName":"水果类","name":"西瓜","coverImg":"https://www.juntaikeji.com:21900/2022-07-27/1658913914550.png","videoUrl":"","description":"<p><img src=https://www.juntaikeji.com:21900/2022-07-27/u=388367301,179605807&fm=30&app=106&size=f242,162&n=0&g=0n&f=JPEG.jpg /><\/p>\n<p><img src=\"https://www.juntaikeji.com:21900/2022-07-27/u=1445362820,2874277112&fm=199&app=68&size=w256&n=0&f=JPEG&fmt=auto.webp\" /><\/p>\n<p><img src=\"https://www.juntaikeji.com:21900/2022-07-27/u=975916566,732522029&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto.webp\" /><\/p>","price":0.01,"state":1,"stateContent":null,"createTime":"2022-07-27 17:25:19","traceability":{"id":8,"commodityId":224,"supplier":21,"purchaseTime":"2022-07-28 00:00:00","purchaseName":"张三","createTime":"2022-07-28 14:54:13","traceabilityFile":[{"id":29,"traceabilityId":8,"photoOne":"https://www.juntaikeji.com:21900/2022-07-28/1658991254122.png","photoTwo":null,"photoThree":null,"remarks":"张三"}]}}
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
         * id : 224
         * shopName : 杂货店
         * classifyName : 日常
         * categoryName : 水果类
         * name : 西瓜
         * coverImg : https://www.juntaikeji.com:21900/2022-07-27/1658913914550.png
         * videoUrl :
         * description : <p><img src=https://www.juntaikeji.com:21900/2022-07-27/u=388367301,179605807&fm=30&app=106&size=f242,162&n=0&g=0n&f=JPEG.jpg /></p>
         <p><img src="https://www.juntaikeji.com:21900/2022-07-27/u=1445362820,2874277112&fm=199&app=68&size=w256&n=0&f=JPEG&fmt=auto.webp" /></p>
         <p><img src="https://www.juntaikeji.com:21900/2022-07-27/u=975916566,732522029&fm=253&app=120&size=w931&n=0&f=JPEG&fmt=auto.webp" /></p>
         * price : 0.01
         * state : 1
         * stateContent : null
         * createTime : 2022-07-27 17:25:19
         * traceability : {"id":8,"commodityId":224,"supplier":21,"purchaseTime":"2022-07-28 00:00:00","purchaseName":"张三","createTime":"2022-07-28 14:54:13","traceabilityFile":[{"id":29,"traceabilityId":8,"photoOne":"https://www.juntaikeji.com:21900/2022-07-28/1658991254122.png","photoTwo":null,"photoThree":null,"remarks":"张三"}]}
         */

        private int id;
        private String shopName;
        private String classifyName;
        private String categoryName;
        private String name;
        private String coverImg;
        private String videoUrl;
        private String description;
        private double price;
        private int state;
        private Object stateContent;
        private String createTime;
        private TraceabilityBean traceability;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getClassifyName() {
            return classifyName;
        }

        public void setClassifyName(String classifyName) {
            this.classifyName = classifyName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public Object getStateContent() {
            return stateContent;
        }

        public void setStateContent(Object stateContent) {
            this.stateContent = stateContent;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public TraceabilityBean getTraceability() {
            return traceability;
        }

        public void setTraceability(TraceabilityBean traceability) {
            this.traceability = traceability;
        }

        public static class TraceabilityBean {
            /**
             * id : 8
             * commodityId : 224
             * supplier : 21
             * purchaseTime : 2022-07-28 00:00:00
             * purchaseName : 张三
             * createTime : 2022-07-28 14:54:13
             * traceabilityFile : [{"id":29,"traceabilityId":8,"photoOne":"https://www.juntaikeji.com:21900/2022-07-28/1658991254122.png","photoTwo":null,"photoThree":null,"remarks":"张三"}]
             */

            private int id;
            private int commodityId;
            private int supplier;
            private String purchaseTime;
            private String purchaseName;
            private String createTime;
            private List<TraceabilityFileBean> traceabilityFile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public List<TraceabilityFileBean> getTraceabilityFile() {
                return traceabilityFile;
            }

            public void setTraceabilityFile(List<TraceabilityFileBean> traceabilityFile) {
                this.traceabilityFile = traceabilityFile;
            }

            public static class TraceabilityFileBean {
                /**
                 * id : 29
                 * traceabilityId : 8
                 * photoOne : https://www.juntaikeji.com:21900/2022-07-28/1658991254122.png
                 * photoTwo : null
                 * photoThree : null
                 * remarks : 张三
                 */

                private int id;
                private int traceabilityId;
                private String photoOne;
                private String photoTwo;
                private String photoThree;
                private String remarks;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getTraceabilityId() {
                    return traceabilityId;
                }

                public void setTraceabilityId(int traceabilityId) {
                    this.traceabilityId = traceabilityId;
                }

                public String getPhotoOne() {
                    return photoOne;
                }

                public void setPhotoOne(String photoOne) {
                    this.photoOne = photoOne;
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
                    return remarks;
                }

                public void setRemarks(String remarks) {
                    this.remarks = remarks;
                }
            }
        }
    }
}
