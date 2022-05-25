package com.juntai.wisdom.project.mall.mine.address.selectAddress;


import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.mall.beans.CitysBean;

/**
 * @aouther tobato 被选中的地址
 * @date 2019/3/10
 */
public class SelectedAddrAdapter extends BaseQuickAdapter<CitysBean.DistrictsBean, BaseViewHolder> {

    public SelectedAddrAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CitysBean.DistrictsBean item) {
        helper.setText(R.id.selected_addr_tv, item.getName());
        if (item.isSelected()) {
            helper.setTextColor(R.id.selected_addr_tv, ContextCompat.getColor(mContext,R.color.red));
        }else {
            helper.setTextColor(R.id.selected_addr_tv, ContextCompat.getColor(mContext,R.color.black));

        }
    }
}