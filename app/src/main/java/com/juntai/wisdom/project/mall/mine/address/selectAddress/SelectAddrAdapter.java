package com.juntai.wisdom.project.mall.mine.address.selectAddress;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.beans.CitysBean;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2022/5/9 16:53
 */
public class SelectAddrAdapter extends BaseQuickAdapter<CitysBean.DistrictsBean, BaseViewHolder> {
    int selector = 0;
    String pinyin = "0";
    int color = -1;
    //MallHomeB
    public SelectAddrAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CitysBean.DistrictsBean item) {
        if (color == -1) {
            color = mContext.getResources().getColor(R.color.colorTheme);
        }
        if (selector == helper.getAdapterPosition()){
            helper.setBackgroundRes(R.id.item_address_layout, R.color.white);
            helper.setTextColor(R.id.item_address_selector, color);
            helper.setTextColor(R.id.item_address_pinyin, color);
            helper.getView(R.id.item_address_image).setVisibility(View.VISIBLE);
        }else {
            helper.setBackgroundRes(R.id.item_address_layout, R.color.gray);
            helper.setTextColor(R.id.item_address_selector, Color.BLACK);
            helper.setTextColor(R.id.item_address_pinyin, Color.BLACK);
            helper.getView(R.id.item_address_image).setVisibility(View.GONE);
        }

        helper.setText(R.id.item_address_pinyin, item.getPinYin());
        if (pinyin.equals(item.getPinYin())){
            helper.setText(R.id.item_address_pinyin,"");
        }
        helper.setText(R.id.item_address_selector, item.getName());
        pinyin = item.getPinYin();
    }

    /**
     *
     * @param position
     */
    public void selectorPosition(int position){
        selector = position;
    }
}