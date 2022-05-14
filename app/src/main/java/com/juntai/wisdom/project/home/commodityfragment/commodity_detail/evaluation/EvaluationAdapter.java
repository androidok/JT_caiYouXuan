package com.juntai.wisdom.project.home.commodityfragment.commodity_detail.evaluation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.base.BaseFragment;
import com.juntai.disabled.basecomponent.utils.UrlFormatUtil;
import com.juntai.wisdom.project.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.project.beans.CommodityEvaluationBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品评价的适配器
 * @CreateDate: 2022/5/3 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 16:30
 */
public class EvaluationAdapter extends BaseQuickAdapter<CommodityEvaluationBean.DataBean, BaseViewHolder> {
    private FragmentManager fragmentManager;

    public EvaluationAdapter(int layoutResId, FragmentManager fragmentManager) {
        super(layoutResId);
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityEvaluationBean.DataBean item) {
        ImageLoadUtil.loadHeadPic(mContext, UrlFormatUtil.getImageThumUrl(item.getHeadPortrait()), helper.getView(R.id.evaluator_iv), true);
        helper.setText(R.id.evaluator_name_tv, item.getNickname());
        helper.setText(R.id.evaluate_time_tv, item.getCreateTime());
        helper.setText(R.id.evaluate_content_tv, item.getEvaluate());
        List<String> picVideos = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getVideoUrl())) {
            picVideos.add(item.getVideoUrl());
        }
        String imageUrls = item.getImgUrl();
        if (!TextUtils.isEmpty(imageUrls)) {
            if (imageUrls.contains(",")) {
                String[] urls = imageUrls.split(",");
                for (String url : urls) {
                    picVideos.add(url);
                }
            } else {
                picVideos.add(imageUrls);
            }
        }
        SelectPhotosFragment selectPhotosFragment = SelectPhotosFragment.newInstance()
                .setSpanCount(3)
                .setMaxCount(picVideos.size()).setPhotoDelateable(false);
        selectPhotosFragment.setAdapterData(picVideos);
        replaceFragment(selectPhotosFragment);
    }

    public void replaceFragment(BaseFragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.evaluate_pic_video_fl, fragment);
        transaction.commit();
    }
}
