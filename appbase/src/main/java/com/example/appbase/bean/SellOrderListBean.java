package com.example.appbase.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/11 15:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/11 15:12
 */
public class SellOrderListBean extends BaseResult implements Parcelable {

    private double totalPrice;
    private double totalCommodityNum;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalCommodityNum() {
        return totalCommodityNum;
    }

    public void setTotalCommodityNum(double totalCommodityNum) {
        this.totalCommodityNum = totalCommodityNum;
    }

    /**
     * data : {"totalCount":1,"pageSize":10,"totalPage":1,"currPage":1,"list":[{"id":368,"shopId":1,"shopName":"测试小店","payType":4,"state":1,"totalPrices":123,"transportCharges":10,"payPrice":133,"payPostage":10,"expireTime":"2022-05-16 14:12:16","commodityList":[{"id":89,"orderFormNumber":"7084104558239564","commodityId":15,"commodityName":"运动鞋","coverImg":"https://www.juntaikeji.com:21900/2022-05-13/1652406679479.png","prices":123,"commodityNum":1,"cartInfo":"红色;m","unique":"184fb43ae77d437f9c63d310b96b1aed"}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * totalCount : 1
         * pageSize : 10
         * totalPage : 1
         * currPage : 1
         * list : [{"id":368,"shopId":1,"shopName":"测试小店","payType":4,"state":1,"totalPrices":123,"transportCharges":10,"payPrice":133,"payPostage":10,"expireTime":"2022-05-16 14:12:16","commodityList":[{"id":89,"orderFormNumber":"7084104558239564","commodityId":15,"commodityName":"运动鞋","coverImg":"https://www.juntaikeji.com:21900/2022-05-13/1652406679479.png","prices":123,"commodityNum":1,"cartInfo":"红色;m","unique":"184fb43ae77d437f9c63d310b96b1aed"}]}]
         */

        private int totalCount;
        private int pageSize;
        private int totalPage;
        private int currPage;
        private List<SellOrderDetailBean> list;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public List<SellOrderDetailBean> getList() {
            return list;
        }

        public void setList(List<SellOrderDetailBean> list) {
            this.list = list;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.totalCount);
            dest.writeInt(this.pageSize);
            dest.writeInt(this.totalPage);
            dest.writeInt(this.currPage);
            dest.writeTypedList(this.list);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.totalCount = in.readInt();
            this.pageSize = in.readInt();
            this.totalPage = in.readInt();
            this.currPage = in.readInt();
            this.list = in.createTypedArrayList(SellOrderDetailBean.CREATOR);
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public SellOrderListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.totalPrice);
        dest.writeDouble(this.totalCommodityNum);
        dest.writeParcelable(this.data, flags);
    }

    protected SellOrderListBean(Parcel in) {
        super(in);
        this.totalPrice = in.readDouble();
        this.totalCommodityNum = in.readDouble();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Creator<SellOrderListBean> CREATOR = new Creator<SellOrderListBean>() {
        @Override
        public SellOrderListBean createFromParcel(Parcel source) {
            return new SellOrderListBean(source);
        }

        @Override
        public SellOrderListBean[] newArray(int size) {
            return new SellOrderListBean[size];
        }
    };
}
