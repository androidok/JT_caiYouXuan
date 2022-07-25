package com.juntai.wisdom.project.mall.order.orderDetail;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.displayPicVideo.PicVideoDisplayActivity;
import com.juntai.wisdom.project.mall.base.selectPics.ShowSelectedPicsAdapter;
import com.juntai.wisdom.project.mall.utils.bannerImageLoader.BannerObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @aouther tobato
 * @description 描述  我的信息
 * @date 2021/6/1 16:48
 */
public class OrderBaseInfoAdapter extends BaseQuickAdapter<OrderDetailItemBean, BaseViewHolder> {

    public OrderBaseInfoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailItemBean orderDetailBean) {
        helper.setText(R.id.order_info_title_tv, orderDetailBean.getTitle());

        RecyclerView  recyclerView = helper.getView(R.id.order_detail_info_rv);
        OrderBaseInfoChildAdapter childAdapter = new OrderBaseInfoChildAdapter(R.layout.sell_mall_order_baseinfo_child_item);
        recyclerView.setAdapter(childAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        childAdapter.setNewData(orderDetailBean.getArrays());

        List<String> pics = orderDetailBean.getImages();
        if (pics != null&&pics.size()>0) {
            List<BannerObject> bannerObjects = new ArrayList<>();
            for (String pic : pics) {
                if (pic.endsWith(".mp4")) {
                    bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_VIDEO, new BannerObject.VideoBean(pic, orderDetailBean.getVideoCover())));
                }else {
                    bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, pic));
                }
            }
            helper.setGone(R.id.order_pics_rv,true);
            RecyclerView refundPicRv = helper.getView(R.id.order_pics_rv);
            ShowSelectedPicsAdapter refundPicAdapter = new ShowSelectedPicsAdapter(R.layout.sell_show_selected_pic_item);
            refundPicAdapter.setDelateable(false);
            refundPicRv.setAdapter(refundPicAdapter);
            GridLayoutManager reFundLm = new GridLayoutManager(mContext,4);
            refundPicRv.setLayoutManager(reFundLm);
            refundPicAdapter.setNewData(pics);
            refundPicAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    PicVideoDisplayActivity.startPicVideoPlayActivity(mContext,bannerObjects,position);

                }
            });
        }else {
            helper.setGone(R.id.order_pics_rv,false);
        }

    }

}