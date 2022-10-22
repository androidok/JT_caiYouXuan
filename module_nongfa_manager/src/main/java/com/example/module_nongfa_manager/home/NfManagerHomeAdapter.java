package com.example.module_nongfa_manager.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.PicTextBean;
import com.example.module_nongfa_manager.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class NfManagerHomeAdapter extends BaseQuickAdapter<PicTextBean, BaseViewHolder> {
    public NfManagerHomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicTextBean item) {
        helper.setBackgroundRes(R.id.menu_bg_cl, item.getPicbg());
        helper.setImageResource(R.id.menu_icon_iv,item.getPicRes());
        helper.setText(R.id.menu_title_tv,item.getTextName());
        helper.setText(R.id.menu_title_en_tv,item.getTextNameEn());
    }
}
