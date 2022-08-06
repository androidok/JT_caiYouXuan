package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.base.customview.selectPics.SelectPicVideoBean;
import com.example.appbase.base.customview.selectPics.SelectPicVideoRv;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.util.bannerImageLoader.BannerObject;
import com.juntai.project.sell.mall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class CommoditySourceAdapter extends BaseQuickAdapter<CommoditySourceDetailBean.DataBean.PhotoListBean, BaseViewHolder> {
    public CommoditySourceAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommoditySourceDetailBean.DataBean.PhotoListBean item) {

        SelectPicVideoRv selectPicVideoRv = helper.getView(R.id.select_pics_spr);
        selectPicVideoRv.setDetail(false)
                .setmMaxCount(3);
        selectPicVideoRv.setTag(item);
        selectPicVideoRv.setOnPhotoItemClick(new SelectPicVideoRv.OnPhotoItemClick() {
            @Override
            public void onVedioPicClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onPicClick(BaseQuickAdapter adapter, View view, int position) {
                List<BannerObject> bannerObjects = new ArrayList<>();
                List<SelectPicVideoBean> arrays = adapter.getData();
                for (SelectPicVideoBean array : arrays) {
                    if (SelectPicVideoBean.TYPE_NULL!=array.getType()) {
                        bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE,array.getPath()));
                    }
                }
                DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, position);

            }

            @Override
            public void onSelectPic(BaseQuickAdapter adapter, View view, int position) {
                getOnItemChildClickListener().onItemChildClick(CommoditySourceAdapter.this, selectPicVideoRv, position);
            }
        });
        List<SelectPicVideoBean> pics = new ArrayList<>();
        if (!TextUtils.isEmpty(item.getPhotoOne())) {
            pics.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_IMAGE,item.getPhotoOne()));
        }
        if (!TextUtils.isEmpty(item.getPhotoTwo())) {
            pics.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_IMAGE,item.getPhotoTwo()));

        }
        if (!TextUtils.isEmpty(item.getPhotoThree())) {
            pics.add(new SelectPicVideoBean(SelectPicVideoBean.TYPE_IMAGE,item.getPhotoThree()));

        }
        selectPicVideoRv.setData(pics);

        helper.addOnClickListener(R.id.add_item_tv);
        if (helper.getAdapterPosition() == getData().size() - 1 && helper.getAdapterPosition() < 2) {
            helper.setGone(R.id.add_item_tv, true);
        } else {
            helper.setGone(R.id.add_item_tv, false);
        }


        TextView billDesTv = helper.getView(R.id.bill_des_et);
        billDesTv.setTag(item);
        CommoditySourceDetailBean.DataBean.PhotoListBean bean = (CommoditySourceDetailBean.DataBean.PhotoListBean) billDesTv.getTag();
        billDesTv.setText(bean.getRemarks());
        billDesTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CommoditySourceDetailBean.DataBean.PhotoListBean bean = (CommoditySourceDetailBean.DataBean.PhotoListBean) billDesTv.getTag();
                bean.setRemarks(s.toString().trim());
            }
        });


    }
}
