package com.example.appbase.base.multi.adapters;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.R;
import com.example.appbase.base.customview.selectPics.SelectPicRv;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.appbase.util.bannerImageLoader.BannerObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommodityManagerSourceDetailAdapter extends BaseQuickAdapter<CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean, BaseViewHolder> {
    public CommodityManagerSourceDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityManagerDetailBean.DataBean.TraceabilityBean.TraceabilityFileBean item) {

        SelectPicRv selectPicRv = helper.getView(R.id.source_spr);
        List<String> pics = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getPhotoOne())) {
            pics.add(item.getPhotoOne());
        }
        if (!TextUtils.isEmpty(item.getPhotoTwo())) {
            pics.add(item.getPhotoTwo());
        }
        if (!TextUtils.isEmpty(item.getPhotoThree())) {
            pics.add(item.getPhotoThree());
        }
        selectPicRv.setDetail(true)
                .setmMaxCount(pics.size())
        .setOnPhotoItemClick(new SelectPicRv.OnPhotoItemClick() {
            @Override
            public void onVedioPicClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onPicClick(BaseQuickAdapter adapter, View view, int position) {
                List<BannerObject> bannerObjects = new ArrayList<>();
                List<String> arrays = adapter.getData();
                for (String array : arrays) {
                    if (!"-1".equals(array)) {
                        bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE,array));
                    }
                }
                DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, position);
            }

            @Override
            public void onSelectPic(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        selectPicRv.setData(pics);
        helper.setGone(R.id.source_spr, !pics.isEmpty());
        helper.setGone(R.id.source_des_tv, !TextUtils.isEmpty(item.getRemarks()));
        helper.setText(R.id.source_des_tv, item.getRemarks());


    }
}
