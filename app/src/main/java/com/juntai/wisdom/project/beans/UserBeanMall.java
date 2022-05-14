package com.juntai.wisdom.project.beans;


import com.juntai.disabled.basecomponent.base.BaseResult;

/**
 * 个人信息
 * Created by Ma
 * on 2019/4/27
 */
public class UserBeanMall extends BaseResult {

    /**
     * data : {"userId":97,"account":"15735046365","phoneNumber":"15735046365","nickname":"铁人王进喜","headPortrait":"https://www.juntaikeji.com:17002/head_img/b66f7c92700d41fc83b23a07d8b37537.jpeg","schoolName":"临沂一中","paymentType":0,"token":"4J5WRF5T2-K7A0PY9UEY82297LS9ZX2-03SB0X2L-0"}
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
         * userId : 97
         * account : 15735046365
         * phoneNumber : 15735046365
         * nickname : 铁人王进喜
         * headPortrait : https://www.juntaikeji.com:17002/head_img/b66f7c92700d41fc83b23a07d8b37537.jpeg
         * schoolName : 临沂一中
         * paymentType : 0
         * token : 4J5WRF5T2-K7A0PY9UEY82297LS9ZX2-03SB0X2L-0
         */

        private int userId;
        private String account;
        private String phoneNumber;
        private String nickname;
        private String headPortrait;
        private String schoolName;
        private int paymentType;
        private String token;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHeadPortrait() {
            return headPortrait;
        }

        public void setHeadPortrait(String headPortrait) {
            this.headPortrait = headPortrait;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public int getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(int paymentType) {
            this.paymentType = paymentType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
