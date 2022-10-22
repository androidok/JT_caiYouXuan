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
    public void setStatus(int status) {
        this.status = status;
    }
    public CommoditiesAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityManagerListBean.DataBean.ListBean item) {
        ImageLoadUtil.loadSquareImageHasCorner(mContext, item.getCoverImg(), helper.getView(R.id.commodity_pic_iv));
        helper.setText(R.id.commodity_name_tv, item.getName());
        helper.setText(R.id.commodity_prise_tv, String.format("￥%s", item.getPrice()));
        helper.setText(R.id.left_tv,"详情");
        helper.addOnClickListener(R.id.left_tv);
        helper.addOnClickListener(R.id.right_tv);
        helper.setGone(R.id.right_tv, true);
        switch (status) {
            case 1:
                helper.setText(R.id.right_tv, "审核");
                break;
            case 2:
                helper.setText(R.id.right_tv, "下架");
                break;
            case 3:
                helper.setGone(R.id.right_tv, false);

                break;
            default:
                helper.setGone(R.id.right_tv, false);
                break;
        }

    }
}
