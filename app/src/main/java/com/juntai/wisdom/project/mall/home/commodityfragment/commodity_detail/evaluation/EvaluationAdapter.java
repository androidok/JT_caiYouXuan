package com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.evaluation;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.video.img.DisPlayPicsActivity;
import com.juntai.disabled.video.player.DisplayVideoActivity;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.beans.CommodityEvaluationBean;

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

    public EvaluationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityEvaluationBean.DataBean item) {
        ImageLoadUtil.loadHeadPic(mContext, item.getHeadPortrait(), helper.getView(R.id.evaluator_iv), true);
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

        RecyclerView picVideoRv = helper.getView(R.id.evaluate_pic_video_rv);
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        EvaluatePicVideoAdapter picVideoAdapter = new EvaluatePicVideoAdapter(R.layout.show_selected_pic_video_item);
        picVideoRv.setAdapter(picVideoAdapter);
        picVideoRv.setLayoutManager(manager);
        picVideoAdapter.setNewData(picVideos);

        picVideoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<String> arrays = adapter.getData();
                ArrayList<String> pics = new ArrayList<>();
                for (String array : arrays) {
                    if (!array.endsWith(".mp4")) {
                        pics.add(array);
                    }
                }
                String str = (String) adapter.getItem(position);
                if (str.endsWith(".mp4")) {
                    DisplayVideoActivity.startDisplayVideo(mContext,str);
                }else {
                    DisPlayPicsActivity.startDisplayPics(mContext,pics,0);
                }


            }
        });

    }

}
