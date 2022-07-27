package com.juntai.wisdom.project.mall.home.shop;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.CommodityBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/3 14:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 14:07
 */
public class ShopListAdapter extends BaseQuickAdapter<CommodityBean, BaseViewHolder> {

    public ShopListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityBean item) {
        ImageLoadUtil.loadHeadCirclePic(mContext,item.getHeadPortrait(),helper.getView(R.id.shop_logo_iv));
        helper.setText(R.id.shop_name_tv, item.getName());
        helper.setText(R.id.shop_create_time_value_tv,String.valueOf(item.getCreateTime()));
        helper.setText(R.id.shop_sell_commodity_amount_value_tv,String.valueOf(item.getCommodityCount()));
    }

}
