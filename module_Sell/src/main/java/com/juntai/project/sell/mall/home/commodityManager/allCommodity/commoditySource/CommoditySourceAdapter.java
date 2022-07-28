package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.project.sell.mall.beans.sell.CommoditySourceDetailBean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditySourceAdapter extends BaseQuickAdapter<CommoditySourceDetailBean.DataBean.PhotoListBean, BaseViewHolder> {
    public CommoditySourceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommoditySourceDetailBean.DataBean.PhotoListBean item) {

    }
}
