package com.juntai.wisdom.project.home.commodityfragment.commodity_detail;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.project.beans.PicTextBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.R;

/**
 * @Author: tobato
 * @Description: 作用描述  图片视频上下样式的适配器
 * @CreateDate: 2022/5/3 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 16:30
 */
public class PicTextAdapter extends BaseQuickAdapter<PicTextBean, BaseViewHolder> {
    public PicTextAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicTextBean item) {
        ImageLoadUtil.loadImage(mContext,item.getPicRes(),helper.getView(R.id.tabitem_image));
        helper.setText(R.id.tabitem_text,item.getTextName());
        helper.setGone(R.id.tabitem_count,item.getUnReadAmount()>0);
        helper.setText(R.id.tabitem_count,String.valueOf(item.getUnReadAmount()));

    }
}
