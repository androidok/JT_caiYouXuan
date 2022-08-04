package com.example.module_nongfa_manager.home.orderManager;


import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.module_nongfa_manager.R;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;


/**
 * @aouther tobato
 * @description 描述  我的信息
 * @date 2021/6/1 16:48
 */
public class NfOrderTextValueAdapter extends BaseQuickAdapter<TextKeyValueBean, BaseViewHolder> {

    public NfOrderTextValueAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TextKeyValueBean item) {
        helper.setText(R.id.item_myinfo_name, item.getKey());
        TextView  valueTv = helper.getView(R.id.item_myinfo_value);
        valueTv.setText(item.getValue());

    }


}