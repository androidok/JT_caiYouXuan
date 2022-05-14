package com.juntai.wisdom.project.beans.order;


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
public class OrderListBean extends BaseResult implements Parcelable {

    private double totalPrice;
    private int totalCommodityNum;
    private List<OrderDetailBean> data;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCommodityNum() {
        return totalCommodityNum;
    }

    public void setTotalCommodityNum(int totalCommodityNum) {
        this.totalCommodityNum = totalCommodityNum;
    }

    public List<OrderDetailBean> getData() {
        return data;
    }

    public void setData(List<OrderDetailBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.totalPrice);
        dest.writeInt(this.totalCommodityNum);
        dest.writeTypedList(this.data);
    }

    public OrderListBean() {
    }

    protected OrderListBean(Parcel in) {
        this.totalPrice = in.readDouble();
        this.totalCommodityNum = in.readInt();
        this.data = in.createTypedArrayList(OrderDetailBean.CREATOR);
    }

    public static final Parcelable.Creator<OrderListBean> CREATOR = new Parcelable.Creator<OrderListBean>() {
        @Override
        public OrderListBean createFromParcel(Parcel source) {
            return new OrderListBean(source);
        }

        @Override
        public OrderListBean[] newArray(int size) {
            return new OrderListBean[size];
        }
    };
}
