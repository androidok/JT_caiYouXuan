package com.juntai.wisdom.project.home.commodityfragment.commodity_detail.evaluation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.UrlFormatUtil;
import com.juntai.wisdom.project.R;

/**
 * @Author: tobato
 * @Description: 作用描述  评价的视频和图片
 * @CreateDate: 2022/5/17 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/17 14:22
 */
public class EvaluatePicVideoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public EvaluatePicVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item.contains(".mp4")) {
            ImageLoadUtil.loadRoundCornerImg(mContext,item, helper.getView(R.id.select_pic_icon_iv), R.mipmap.empty_pic,1);
            helper.setGone(R.id.item_video_tag, true);
        } else {
            ImageLoadUtil.loadSquareImage(mContext, UrlFormatUtil.getImageThumUrl(item), helper.getView(R.id.select_pic_icon_iv), R.mipmap.empty_pic);
            helper.setGone(R.id.item_video_tag, false);
        }
    }
}
