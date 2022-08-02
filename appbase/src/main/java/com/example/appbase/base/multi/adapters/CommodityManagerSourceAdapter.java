package com.example.appbase.base.multi.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommodityManagerSourceAdapter extends BaseQuickAdapter<CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean, BaseViewHolder> {
    public CommodityManagerSourceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean item) {

        helper.addOnClickListener(R.id.commodity_bill_iv1);
        helper.addOnClickListener(R.id.commodity_bill_iv2);
        helper.addOnClickListener(R.id.commodity_bill_iv3);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv1);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv2);
        helper.addOnClickListener(R.id.delete_commodity_bill_iv3);
        helper.addOnClickListener(R.id.add_item_tv);
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoOne(), helper.getView(R.id.commodity_bill_iv1), R.mipmap.add_icons);
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoTwo(), helper.getView(R.id.commodity_bill_iv2), R.mipmap.add_icons);
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoThree(), helper.getView(R.id.commodity_bill_iv3), R.mipmap.add_icons);

//        if (TextUtils.isEmpty(item.getPhotoOne())) {
//            helper.setGone(R.id.delete_commodity_bill_iv1, false);
//        } else {
//            helper.setGone(R.id.delete_commodity_bill_iv1, true);
//        }
//        if (TextUtils.isEmpty(item.getPhotoTwo())) {
//            helper.setGone(R.id.delete_commodity_bill_iv2, false);
//        } else {
//            helper.setGone(R.id.delete_commodity_bill_iv2, true);
//        }
//        if (TextUtils.isEmpty(item.getPhotoThree())) {
//            helper.setGone(R.id.delete_commodity_bill_iv3, false);
//        } else {
//            helper.setGone(R.id.delete_commodity_bill_iv3, true);
//        }


        TextView billDesTv = helper.getView(R.id.bill_des_tv);
        billDesTv.setText(item.getRemarks());


    }
}
