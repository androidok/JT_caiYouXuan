package com.example.module_nongfa_manager.home.orderManager.orderDetail;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.base.customview.selectPics.SelectPicRv;
import com.example.appbase.bean.nong_fa_manager.SortDetailBean;
import com.example.module_nongfa_manager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class OrderCommoditySourceAdapter extends BaseQuickAdapter<SortDetailBean.DataBean.CommodityListBean.TraceabilityListBean, BaseViewHolder> {
    public OrderCommoditySourceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortDetailBean.DataBean.CommodityListBean.TraceabilityListBean item) {
        SelectPicRv selectPicRv = helper.getView(R.id.source_spr);
        List<String> pics = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getPhotoOne())) {
            pics.add(item.getPhotoOne());
        }
        if (!TextUtils.isEmpty(item.getPhotoTwo())) {
            pics.add(item.getPhotoTwo());
        }
        if (!TextUtils.isEmpty(item.getPhotoThree())) {
            pics.add(item.getPhotoThree());
        }
        SelectPicRv.Builder builder = new SelectPicRv.Builder()
                .setDetail(true)
                .setmMaxCount(pics.size());
        selectPicRv.setBuilder(builder);
        selectPicRv.setData(pics);
        helper.setGone(R.id.source_spr, !pics.isEmpty());
        helper.setGone(R.id.source_des_tv, !TextUtils.isEmpty(item.getRemarks()));
        helper.setText(R.id.source_des_tv, item.getRemarks());
    }
}
