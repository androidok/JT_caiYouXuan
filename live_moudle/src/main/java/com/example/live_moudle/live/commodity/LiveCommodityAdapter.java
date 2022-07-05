package com.example.live_moudle.live.commodity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.live_moudle.R;
import com.juntai.disabled.basecomponent.bean.CommodityBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class LiveCommodityAdapter extends BaseQuickAdapter<CommodityBean, BaseViewHolder> {
    public LiveCommodityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityBean item) {
        ImageLoadUtil.loadSquareImage(mContext,item.getCoverImg(),helper.getView(R.id.linearlayout_commodity_cover_iv));
        helper.setText(R.id.linearlayout_commodity_des_tv, item.getName());
        helper.setText(R.id.linearlayout_commodity_price_tv,String.format("￥%s",item.getPrice()));
    }
}
