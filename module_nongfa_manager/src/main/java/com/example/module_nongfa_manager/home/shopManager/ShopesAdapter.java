package com.example.module_nongfa_manager.home.shopManager;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.nong_fa_manager.ShopManagerListBean;
import com.example.module_nongfa_manager.R;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class ShopesAdapter extends BaseQuickAdapter<ShopManagerListBean.DataBean.ListBean, BaseViewHolder> {
    public ShopesAdapter(int layoutResId) {
        super(layoutResId);
    }
    private int status;
    public void setStatus(int status) {
        this.status = status;
    }
    @Override
    protected void convert(BaseViewHolder helper, ShopManagerListBean.DataBean.ListBean item) {
        ImageLoadUtil.loadHeadCirclePic(mContext,item.getHeadPortrait(), helper.getView(R.id.shop_icon_iv));
        helper.setText(R.id.shop_title_tv,item.getName());
        String catagory = item.getCategory();
        if (!TextUtils.isEmpty(catagory)) {
            if (catagory.contains(",")) {
                catagory = catagory.replace(",","/");
            }
            helper.setText(R.id.shop_property_tv,catagory);
        }
        helper.setText(R.id.left_tv,"详情");
        helper.setGone(R.id.right_tv, true);
        switch (status) {
            case 1:
                helper.setText(R.id.right_tv, "审核");
                break;
            case 2:
                helper.setText(R.id.right_tv, "关店");
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
