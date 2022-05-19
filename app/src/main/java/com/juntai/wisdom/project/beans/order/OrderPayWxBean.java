package com.juntai.wisdom.project.beans.order;

import com.juntai.disabled.basecomponent.base.BaseResult;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/19 9:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/19 9:19
 */
public class OrderPayWxBean extends BaseResult {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nonce_str : b0b183c207f46f0cca7dc63b2604f5cc
         * packageVal : Sign=WXPay
         * appid : wx55706643d79cd22a
         * sign : 1357FE3B2572B305FBD1A843A59F4BEE
         * prepayId : wx07145136021297e82b6e51961982447200
         * mch_id : 1573354791
         * timestamp : 1578379888
         */

        private String nonce_str;
        private String packageVal;
        private String appid;
        private String sign;
        private String prepayId;
        private String mch_id;
        private String timestamp;

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getPackageVal() {
            return packageVal;
        }

        public void setPackageVal(String packageVal) {
            this.packageVal = packageVal;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
