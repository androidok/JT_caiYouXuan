package com.juntai.wisdom.project.mall.live;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.LiveListBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/3 14:07
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 14:07
 */
public class LiveListAdapter extends BaseQuickAdapter<LiveListBean.DataBean.ListBean, BaseViewHolder> {



    public LiveListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveListBean.DataBean.ListBean item) {
        ImageLoadUtil.loadSquareImage(mContext,item.getCoverImg(),helper.getView(R.id.live_cover_iv));
        ImageLoadUtil.loadHeadCirclePic(mContext,item.getHeadPortrait(),helper.getView(R.id.shop_pic_iv));
        helper.setText(R.id.shop_name_tv, item.getShopName());
        helper.setText(R.id.live_title_tv, item.getTitle());
        helper.setText(R.id.item_top_tag, String.format("%s观看",item.getViewNumber()));
        Glide.with(mContext).load(R.mipmap.live_going).into((ImageView) helper.getView(R.id.item_top_iv));


    }

}
