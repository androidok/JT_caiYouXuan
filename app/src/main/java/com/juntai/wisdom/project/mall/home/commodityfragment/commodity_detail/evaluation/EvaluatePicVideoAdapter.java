package com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.evaluation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.utils.bannerImageLoader.BannerObject;

/**
 * @Author: tobato
 * @Description: 作用描述  评价的视频和图片
 * @CreateDate: 2022/5/17 14:22
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/17 14:22
 */
public class EvaluatePicVideoAdapter extends BaseQuickAdapter<BannerObject, BaseViewHolder> {
    public EvaluatePicVideoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, BannerObject item) {
        ImageLoadUtil.loadSquareImageHasCorner(mContext, (String) item.getEventObj(), helper.getView(R.id.select_pic_icon_iv));
        switch (item.getEventKey()) {
            case BannerObject.BANNER_TYPE_VIDEO:
                helper.setGone(R.id.item_video_tag, true);
                break;
            case BannerObject.BANNER_TYPE_IMAGE:
                helper.setGone(R.id.item_video_tag, false);
                break;
            default:
                break;
        }

    }
}
