package com.juntai.project.sell.mall.beans;

import com.juntai.project.sell.mall.beans.sell.CommodityDetailBean;
import com.juntai.project.sell.mall.beans.sell.CommoditySourceDetailBean;

import okhttp3.FormBody;

/**
 * @Author: tobato
 * @Description: 作用描述  适配器中的数据
 * @CreateDate: 2021/5/11 11:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/5/11 11:12
 */
public class BaseAdapterDataBean {

    private FormBody.Builder builder;
    private CommodityDetailBean commodityDetailBean;
    private CommoditySourceDetailBean.DataBean sourceBean;

    public CommoditySourceDetailBean.DataBean getSourceBean() {
        return sourceBean;
    }

    public void setSourceBean(CommoditySourceDetailBean.DataBean sourceBean) {
        this.sourceBean = sourceBean;
    }

    public FormBody.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(FormBody.Builder builder) {
        this.builder = builder;
    }

    public CommodityDetailBean getCommodityDetailBean() {
        return commodityDetailBean;
    }

    public void setCommodityDetailBean(CommodityDetailBean commodityDetailBean) {
        this.commodityDetailBean = commodityDetailBean;
    }
}
