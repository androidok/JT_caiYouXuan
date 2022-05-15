package com.juntai.wisdom.project.beans.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/11 15:25
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/11 15:25
 */
public class OrderDetailBean implements Parcelable {
    /**
     * id : 305
     * orderFormNumber : b40170d7c1b840a189883e97e0045a51
     * userId : 101
     * name : 顾启杭
     * phone : 18669505929
     * address : 山东省临沂市河东区九曲街道阳光水岸
     * shopId : 1
     * shopName : 测试小店
     * totalPrices : 30.0
     * transportCharges : 2.0
     * sumPackingCharges : 0.0
     * payPrice : 32.0
     * payPostage : 2.0
     * payPackingCharges : 0.0
     * payType : 1
     * establishTime : 2022-05-04 14:57:34
     * paymentTime : 2022-05-05 15:40:10
     * shipmentsTime : 2022-05-05 15:40:13
     * confirmTime : 2022-05-05 10:51:14
     * refundTime : 2022-05-05 14:29:02
     * cancelTime : 2022-05-05 10:44:59
     * expireTime : null
     * remark : 订单备注
     * state : 7
     * logisticsName : 顺丰
     * logisticsNumber : 123456
     * logisticsLink : http://www.baidu.com
     * commodityList : [{"id":15,"orderFormNumber":"b40170d7c1b840a189883e97e0045a51","commodityId":5,"commodityName":"短裤","prices":20,"commodityNum":2,"cartInfo":"L,红色","unique":"0b03454b9d0246d99e8ffceaa5fcd103"},{"id":16,"orderFormNumber":"b40170d7c1b840a189883e97e0045a51","commodityId":5,"commodityName":"短裤","prices":10,"commodityNum":1,"cartInfo":"M,黑色","unique":"be028e5ef938464bb6a6f2e975f12f83"}]
     */

    private int id;
    private String orderFormNumber;
    private int userId;
    private String name;
    private String phone;
    private String address;
    private int shopId;
    private String shopName;
    private double totalPrices;
    private double transportCharges;
    private double sumPackingCharges;
    private double payPrice;
    private double payPostage;
    private double payPackingCharges;
    private int payType;
    private String establishTime;
    private String paymentTime;
    private String shipmentsTime;
    private String confirmTime;
    private String refundTime;
    private String cancelTime;
    private String expireTime;
    private String remark;
    /**
     * 订单状态(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）
     */
    private int state;
    private String logisticsName;
    private String logisticsNumber;
    private String logisticsLink;
    private List<CommodityListBean> commodityList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderFormNumber() {
        return orderFormNumber;
    }

    public void setOrderFormNumber(String orderFormNumber) {
        this.orderFormNumber = orderFormNumber;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(double totalPrices) {
        this.totalPrices = totalPrices;
    }

    public double getTransportCharges() {
        return transportCharges;
    }

    public void setTransportCharges(double transportCharges) {
        this.transportCharges = transportCharges;
    }

    public double getSumPackingCharges() {
        return sumPackingCharges;
    }

    public void setSumPackingCharges(double sumPackingCharges) {
        this.sumPackingCharges = sumPackingCharges;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public double getPayPostage() {
        return payPostage;
    }

    public void setPayPostage(double payPostage) {
        this.payPostage = payPostage;
    }

    public double getPayPackingCharges() {
        return payPackingCharges;
    }

    public void setPayPackingCharges(double payPackingCharges) {
        this.payPackingCharges = payPackingCharges;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getShipmentsTime() {
        return shipmentsTime;
    }

    public void setShipmentsTime(String shipmentsTime) {
        this.shipmentsTime = shipmentsTime;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getExpireTime() {
        return expireTime == null ? "" : expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime == null ? "" : expireTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsNumber() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber = logisticsNumber;
    }

    public String getLogisticsLink() {
        return logisticsLink;
    }

    public void setLogisticsLink(String logisticsLink) {
        this.logisticsLink = logisticsLink;
    }

    public List<CommodityListBean> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<CommodityListBean> commodityList) {
        this.commodityList = commodityList;
    }

    public static class CommodityListBean implements Parcelable {
        /**
         * id : 15
         * orderFormNumber : b40170d7c1b840a189883e97e0045a51
         * commodityId : 5
         * commodityName : 短裤
         * prices : 20.0
         * commodityNum : 2
         * cartInfo : L,红色
         * unique : 0b03454b9d0246d99e8ffceaa5fcd103
         */

        private int id;
        private String orderFormNumber;
        private int commodityId;
        private String commodityName;
        private double prices;
        private String coverImg;//商品图片
        private int commodityNum;
        private String cartInfo;
        private String unique;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoverImg() {
            return coverImg == null ? "" : coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg == null ? "" : coverImg;
        }

        public String getOrderFormNumber() {
            return orderFormNumber;
        }

        public void setOrderFormNumber(String orderFormNumber) {
            this.orderFormNumber = orderFormNumber;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public double getPrices() {
            return prices;
        }

        public void setPrices(double prices) {
            this.prices = prices;
        }

        public int getCommodityNum() {
            return commodityNum;
        }

        public void setCommodityNum(int commodityNum) {
            this.commodityNum = commodityNum;
        }

        public String getCartInfo() {
            return cartInfo;
        }

        public void setCartInfo(String cartInfo) {
            this.cartInfo = cartInfo;
        }

        public String getUnique() {
            return unique;
        }

        public void setUnique(String unique) {
            this.unique = unique;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.orderFormNumber);
            dest.writeInt(this.commodityId);
            dest.writeString(this.commodityName);
            dest.writeDouble(this.prices);
            dest.writeString(this.coverImg);
            dest.writeInt(this.commodityNum);
            dest.writeString(this.cartInfo);
            dest.writeString(this.unique);
        }

        public CommodityListBean() {
        }

        protected CommodityListBean(Parcel in) {
            this.id = in.readInt();
            this.orderFormNumber = in.readString();
            this.commodityId = in.readInt();
            this.commodityName = in.readString();
            this.prices = in.readDouble();
            this.coverImg = in.readString();
            this.commodityNum = in.readInt();
            this.cartInfo = in.readString();
            this.unique = in.readString();
        }

        public static final Creator<CommodityListBean> CREATOR = new Creator<CommodityListBean>() {
            @Override
            public CommodityListBean createFromParcel(Parcel source) {
                return new CommodityListBean(source);
            }

            @Override
            public CommodityListBean[] newArray(int size) {
                return new CommodityListBean[size];
            }
        };
    }

    public OrderDetailBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.orderFormNumber);
        dest.writeInt(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.address);
        dest.writeInt(this.shopId);
        dest.writeString(this.shopName);
        dest.writeDouble(this.totalPrices);
        dest.writeDouble(this.transportCharges);
        dest.writeDouble(this.sumPackingCharges);
        dest.writeDouble(this.payPrice);
        dest.writeDouble(this.payPostage);
        dest.writeDouble(this.payPackingCharges);
        dest.writeInt(this.payType);
        dest.writeString(this.establishTime);
        dest.writeString(this.paymentTime);
        dest.writeString(this.shipmentsTime);
        dest.writeString(this.confirmTime);
        dest.writeString(this.refundTime);
        dest.writeString(this.cancelTime);
        dest.writeString(this.expireTime);
        dest.writeString(this.remark);
        dest.writeInt(this.state);
        dest.writeString(this.logisticsName);
        dest.writeString(this.logisticsNumber);
        dest.writeString(this.logisticsLink);
        dest.writeList(this.commodityList);
    }

    protected OrderDetailBean(Parcel in) {
        this.id = in.readInt();
        this.orderFormNumber = in.readString();
        this.userId = in.readInt();
        this.name = in.readString();
        this.phone = in.readString();
        this.address = in.readString();
        this.shopId = in.readInt();
        this.shopName = in.readString();
        this.totalPrices = in.readDouble();
        this.transportCharges = in.readDouble();
        this.sumPackingCharges = in.readDouble();
        this.payPrice = in.readDouble();
        this.payPostage = in.readDouble();
        this.payPackingCharges = in.readDouble();
        this.payType = in.readInt();
        this.establishTime = in.readString();
        this.paymentTime = in.readString();
        this.shipmentsTime = in.readString();
        this.confirmTime = in.readString();
        this.refundTime = in.readString();
        this.cancelTime = in.readString();
        this.expireTime = in.readString();
        this.remark = in.readString();
        this.state = in.readInt();
        this.logisticsName = in.readString();
        this.logisticsNumber = in.readString();
        this.logisticsLink = in.readString();
        this.commodityList = new ArrayList<CommodityListBean>();
        in.readList(this.commodityList, CommodityListBean.class.getClassLoader());
    }

    public static final Creator<OrderDetailBean> CREATOR = new Creator<OrderDetailBean>() {
        @Override
        public OrderDetailBean createFromParcel(Parcel source) {
            return new OrderDetailBean(source);
        }

        @Override
        public OrderDetailBean[] newArray(int size) {
            return new OrderDetailBean[size];
        }
    };
}
