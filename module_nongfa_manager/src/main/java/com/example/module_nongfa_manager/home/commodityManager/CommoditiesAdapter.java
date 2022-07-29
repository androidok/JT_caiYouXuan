package com.example.module_nongfa_manager.home.commodityManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerListBean;
import com.example.module_nongfa_manager.R;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditiesAdapter extends BaseQuickAdapter<CommodityManagerListBean.DataBean.ListBean, BaseViewHolder> {
    private int status;

    public CommoditiesAdapter(int layoutResId) {
        super(layoutResId);
    }


    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityManagerListBean.DataBean.ListBean item) {
        ImageLoadUtil.loadSquareImageHasCorner(mContext, item.getCoverImg(), helper.getView(R.id.commodity_pic_iv));
        helper.setText(R.id.commodity_name_tv, item.getName());
        helper.setText(R.id.commodity_prise_tv, String.format("￥:%s", item.getPrice()));
        helper.setGone(R.id.commodity_operate_tv, false);
        switch (status) {
            case 1:
                helper.setGone(R.id.commodity_operate_tv, true);
                helper.setText(R.id.commodity_operate_tv, "审核");
                break;
            case 2:
                helper.setGone(R.id.commodity_operate_tv, true);
                helper.setText(R.id.commodity_operate_tv, "下架");
                break;
            default:
                break;
        }

    }
}
