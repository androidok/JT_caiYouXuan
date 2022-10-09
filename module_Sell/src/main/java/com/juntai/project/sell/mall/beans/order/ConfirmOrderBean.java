package com.juntai.project.sell.mall.beans.order;

import android.os.Parcel;

import com.juntai.disabled.basecomponent.base.BaseResult;
import com.example.appbase.bean.SellOrderDetailBean;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  确认订单
 * @CreateDate: 2022/5/16 16:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/16 16:32
 */
public class ConfirmOrderBean extends BaseResult {
    private double totalPrice;
    private double totalCommodityNum;
    private List<SellOrderDetailBean> data;

    public List<SellOrderDetailBean> getData() {
        return data;
    }

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


    public void setData(List<SellOrderDetailBean> data) {
        this.data = data;
    }

    public ConfirmOrderBean() {
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
        dest.writeTypedList(this.data);
    }

    protected ConfirmOrderBean(Parcel in) {
        super(in);
        this.totalPrice = in.readDouble();
        this.totalCommodityNum = in.readDouble();
        this.data = in.createTypedArrayList(SellOrderDetailBean.CREATOR);
    }

    public static final Creator<ConfirmOrderBean> CREATOR = new Creator<ConfirmOrderBean>() {
        @Override
        public ConfirmOrderBean createFromParcel(Parcel source) {
            return new ConfirmOrderBean(source);
        }

        @Override
        public ConfirmOrderBean[] newArray(int size) {
            return new ConfirmOrderBean[size];
        }
    };
}
