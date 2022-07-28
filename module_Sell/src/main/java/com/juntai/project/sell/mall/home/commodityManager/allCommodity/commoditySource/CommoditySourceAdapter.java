package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.project.sell.mall.R;
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

        helper.addOnClickListener(R.id.commodity_bill_iv1);
        helper.addOnClickListener(R.id.commodity_bill_iv2);
        helper.addOnClickListener(R.id.commodity_bill_iv3);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv1);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv2);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv3);
        helper.addOnClickListener(R.id.add_item_tv);
        if (helper.getAdapterPosition()==getData().size()-1) {
            helper.setGone(R.id.add_item_tv,true);
        }else {
            helper.setGone(R.id.add_item_tv,false);
        }
    }
}
