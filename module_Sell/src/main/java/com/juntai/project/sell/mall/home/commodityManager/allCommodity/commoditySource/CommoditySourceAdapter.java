package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.project.sell.mall.R;
import com.example.appbase.bean.CommoditySourceDetailBean;

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
        if (helper.getAdapterPosition() == getData().size() - 1 && helper.getAdapterPosition() < 2) {
            helper.setGone(R.id.add_item_tv, true);
        } else {
            helper.setGone(R.id.add_item_tv, false);
        }
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoOne(), helper.getView(R.id.commodity_bill_iv1), R.mipmap.add_icons);
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoTwo(), helper.getView(R.id.commodity_bill_iv2), R.mipmap.add_icons);
        ImageLoadUtil.loadImageNoCache(mContext, item.getPhotoThree(), helper.getView(R.id.commodity_bill_iv3), R.mipmap.add_icons);

        if (TextUtils.isEmpty(item.getPhotoOne())) {
            helper.setGone(R.id.delete_commodity_bill_iv1, false);
        } else {
            helper.setGone(R.id.delete_commodity_bill_iv1, true);
        }
        if (TextUtils.isEmpty(item.getPhotoTwo())) {
            helper.setGone(R.id.delete_commodity_bill_iv2, false);
        } else {
            helper.setGone(R.id.delete_commodity_bill_iv2, true);
        }
        if (TextUtils.isEmpty(item.getPhotoThree())) {
            helper.setGone(R.id.delete_commodity_bill_iv3, false);
        } else {
            helper.setGone(R.id.delete_commodity_bill_iv3, true);
        }


        TextView billDesTv = helper.getView(R.id.bill_des_et);
        billDesTv.setTag(item);
        CommoditySourceDetailBean.DataBean.PhotoListBean bean = (CommoditySourceDetailBean.DataBean.PhotoListBean) billDesTv.getTag();
        billDesTv.setText(bean.getRemarks());
        billDesTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CommoditySourceDetailBean.DataBean.PhotoListBean bean = (CommoditySourceDetailBean.DataBean.PhotoListBean) billDesTv.getTag();
                bean.setRemarks(s.toString().trim());
            }
        });


    }
}
