package com.juntai.wisdom.project.home.commodityfragment.commodity_detail;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juntai.wisdom.project.beans.CommodityDetailBean;
import com.juntai.wisdom.project.beans.CommodityEvaluationBean;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.home.commodityfragment.commodity_detail.evaluation.EvaluationAdapter;
import com.juntai.wisdom.project.utils.GlideImageLoader;
import com.juntai.wisdom.project.utils.MultipleItem;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品的适配器  包含基本信息  评价  详情
 * @CreateDate: 2022/5/3 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 16:30
 */
public class CommodityAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    private FragmentManager fragmentManager;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CommodityAdapter(List<MultipleItem> data, FragmentManager fragmentManager) {
        super(data);
        this.fragmentManager = fragmentManager;
        addItemType(MultipleItem.ITEM_COMMODITY_BASE_INFO, R.layout.shop_commodity_detail);
        addItemType(MultipleItem.ITEM_COMMODITY_EVALUTA, R.layout.shop_commodity_evaluta_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.ITEM_COMMODITY_BASE_INFO:
                CommodityDetailBean.DataBean dataBean = (CommodityDetailBean.DataBean) item.getObject();
                List<String> picVideos = new ArrayList<>();
                Banner banner = helper.getView(R.id.commodity_banner);
                banner.isAutoPlay(false);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        // TODO: 2022/5/4 查看图片大图 

                    }
                });
                GlideImageLoader imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
                    @Override
                    public void onFullScreen() {

                    }
                });
                if (!TextUtils.isEmpty(dataBean.getCoverImg())) {
                    picVideos.add(dataBean.getCoverImg());
                }
                if (!TextUtils.isEmpty(dataBean.getVideoUrl())) {
                    picVideos.add(dataBean.getVideoUrl());
                }
                List<CommodityDetailBean.DataBean.ImagesBean>  imagesBeans = dataBean.getImages();
                if (imagesBeans != null&&imagesBeans.size()>0) {
                    for (CommodityDetailBean.DataBean.ImagesBean imagesBean : imagesBeans) {
                        picVideos.add(imagesBean.getImgUrl());
                    }
                }
                banner.setImages(picVideos).setImageLoader(imageLoader).start();
                helper.setText(R.id.commodity_des_tv, dataBean.getName());
                helper.setText(R.id.commodity_price_tv, String.format("￥%s", dataBean.getPrice()));
                helper.setText(R.id.commodity_sales_tv, String.format("销量:%s", dataBean.getSales()));


                break;
            case MultipleItem.ITEM_COMMODITY_EVALUTA:
                helper.addOnClickListener(R.id.all_evaluation_tv);
                List<CommodityEvaluationBean.DataBean> arrays = (List<CommodityEvaluationBean.DataBean>) item.getObject();
                helper.setText(R.id.evaluation_title_tv, String.format("宝贝评价(%s)", arrays.size()));
                RecyclerView evaluationRv = helper.getView(R.id.evaluation_rv);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                evaluationRv.setLayoutManager(manager);
                EvaluationAdapter evaluationAdapter = new EvaluationAdapter(R.layout.shop_commodity_evaluta_child_item, fragmentManager);
                evaluationRv.setAdapter(evaluationAdapter);
                if (arrays.size() > 3) {
                    // TODO: 2022/5/4 暂定只显示2行评价
                    arrays = arrays.subList(0, 2);
                }
                evaluationAdapter.setNewData(arrays);
                break;
            default:
                break;
        }
    }
}
