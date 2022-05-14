package com.juntai.disabled.basecomponent.bean.address;


import android.os.Parcel;
import android.os.Parcelable;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2022/5/9 15:56
 */
public class AddressListBean extends BaseResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : 1
         * userId : 101
         * name : 顾启杭
         * phone : 18669505929
         * provinceName : 山东省
         * cityName : 临沂市
         * areaName : 河东区
         * streetName : 九曲街道
         * detailedAddress : 阳光水岸
         * defaultAddress : 1
         * status : 0
         * createTime : 2022-05-03 10:59:48
         */

        private int id;
        private int userId;
        private String name;
        private String phone;
        private String cityName;
        private String detailedAddress;
        private int defaultAddress;
        private int status;
        private String createTime;

        public int getId() {
            return id;
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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }


        public String getDetailedAddress() {
            return detailedAddress;
        }

        public void setDetailedAddress(String detailedAddress) {
            this.detailedAddress = detailedAddress;
        }

        public int getDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(int defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.userId);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.cityName);
            dest.writeString(this.detailedAddress);
            dest.writeInt(this.defaultAddress);
            dest.writeInt(this.status);
            dest.writeString(this.createTime);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.userId = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.cityName = in.readString();
            this.detailedAddress = in.readString();
            this.defaultAddress = in.readInt();
            this.status = in.readInt();
            this.createTime = in.readString();
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
}
