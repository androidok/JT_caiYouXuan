package com.juntai.wisdom.project.mall.home;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.HomeMenuBean;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class HomePageMenuAdapter extends BaseQuickAdapter<HomeMenuBean, BaseViewHolder> {
    public HomePageMenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeMenuBean item) {
        helper.setText(R.id.home_menu_title_tv,item.getTitle());
        helper.setText(R.id.home_menu_des_tv,item.getDes());
        helper.setTextColor(R.id.home_menu_title_tv, ContextCompat.getColor(mContext,item.getColor()));
        helper.setTextColor(R.id.home_menu_des_tv,ContextCompat.getColor(mContext,item.getColor()));
        helper.setImageResource(R.id.home_menu_iv,item.getRes());
    }
}
