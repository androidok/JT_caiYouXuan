package com.example.appbase.base.customview.selectPics;


import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * Author:wang_sir
 * Time:2018/7/19 10:52
 * Description:This is ShowSelectedPicsAdapter
 */
public class SelectedPicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private boolean isDetail;


    public void setDetail(boolean detail) {
        this.isDetail = detail;
    }

    public SelectedPicsAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if ("-1".equals(item)) {
            ImageLoadUtil.loadImage(mContext.getApplicationContext(), R.mipmap.add_icons, (ImageView) helper.getView(R.id.select_pic_icon_iv));
            helper.setGone(R.id.delete_pushed_news_iv, false);
        } else {
            ImageLoadUtil.loadImageNoCache(mContext, item, (ImageView) helper.getView(R.id.select_pic_icon_iv));
            if (isDetail) {
                helper.setGone(R.id.delete_pushed_news_iv, true);
            } else {
                helper.setGone(R.id.delete_pushed_news_iv, false);
            }

            if (item.contains(".mp4")) {
                helper.setGone(R.id.item_video_tag, true);
            } else {
                helper.setGone(R.id.item_video_tag, false);
            }
        }
        helper.addOnClickListener(R.id.select_pic_icon_iv);
        helper.addOnClickListener(R.id.delete_pushed_news_iv);
        ImageView imageView = helper.getView(R.id.select_pic_icon_iv);
//        ConstraintLayout.LayoutParams linearParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
//        linearParams.height = linearParams.width;
//        imageView.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }
}