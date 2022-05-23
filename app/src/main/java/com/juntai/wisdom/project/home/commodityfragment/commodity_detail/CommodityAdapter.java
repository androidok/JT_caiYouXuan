package com.juntai.wisdom.project.home.commodityfragment.commodity_detail;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chat.util.MultipleItem;
import com.juntai.disabled.basecomponent.web.HtmlFormat;
import com.juntai.disabled.basecomponent.web.ImageJavascriptInterface;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.beans.CommodityDetailBean;
import com.juntai.wisdom.project.beans.CommodityEvaluationBean;
import com.juntai.wisdom.project.home.commodityfragment.commodity_detail.evaluation.EvaluationAdapter;
import com.juntai.wisdom.project.utils.bannerImageLoader.BannerObject;
import com.juntai.wisdom.project.utils.bannerImageLoader.GlideImageLoader;
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
    private GlideImageLoader imageLoader;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CommodityAdapter(FragmentManager fragmentManager,List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_COMMODITY_BASE_INFO, R.layout.shop_commodity_base_info);
        addItemType(MultipleItem.ITEM_COMMODITY_EVALUTA, R.layout.shop_commodity_evaluta_item);
        addItemType(MultipleItem.ITEM_COMMODITY_DETAIL, R.layout.shop_commodity_detail_item);
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (item.getItemType()) {
            case MultipleItem.ITEM_COMMODITY_BASE_INFO:
                CommodityDetailBean.DataBean dataBean = (CommodityDetailBean.DataBean) item.getObject();
                List<BannerObject> bannerObjects = new ArrayList<>();
                Banner banner = helper.getView(R.id.commodity_banner);
                banner.isAutoPlay(false);
                banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        // TODO: 2022/5/4 查看图片大图 

                    }
                });
                banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        if (0 != i) {
                            // : 2022/5/22 如果视频在播放 释放资源
                            imageLoader.pause();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                imageLoader = new GlideImageLoader().setOnFullScreenCallBack(new GlideImageLoader.OnFullScreenListener() {
                    @Override
                    public void onFullScreen() {

                    }
                });
                if (!TextUtils.isEmpty(dataBean.getVideoUrl())) {
                    bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_VIDEO, dataBean.getVideoUrl()));
                }
                if (!TextUtils.isEmpty(dataBean.getCoverImg())) {
                    bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, dataBean.getCoverImg()));
                }

                List<CommodityDetailBean.DataBean.ImagesBean> imagesBeans = dataBean.getImages();
                if (imagesBeans != null && imagesBeans.size() > 0) {
                    for (CommodityDetailBean.DataBean.ImagesBean imagesBean : imagesBeans) {
                        bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, imagesBean.getImgUrl()));
                    }
                }
                banner.setImages(bannerObjects).setImageLoader(imageLoader).start();
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
                EvaluationAdapter evaluationAdapter = new EvaluationAdapter(R.layout.shop_commodity_evaluta_child_item);
                evaluationRv.setAdapter(evaluationAdapter);
                if (arrays.size() > 2) {
                    // TODO: 2022/5/4 暂定只显示2行评价
                    arrays = arrays.subList(0, 2);
                }
                evaluationAdapter.setNewData(arrays);
                break;

            case MultipleItem.ITEM_COMMODITY_DETAIL:
                // : 2022/5/22 商品详情
                String content = (String) item.getObject();
                LinearLayout webviewContainner = helper.getView(R.id.webview_container_ll);
                WebView   mNewsContentWeb = new WebView(mContext.getApplicationContext());
//                //        ScrollView 内置 Webview导致底部页面下方空白区域无限下滑,设置height=LayoutParams.WRAP_CONTENT解决
                mNewsContentWeb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                mNewsContentWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                mNewsContentWeb.setScrollBarSize(0);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    mNewsContentWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                }
                mNewsContentWeb.getSettings().setBlockNetworkImage(false);
                mNewsContentWeb.getSettings().setJavaScriptEnabled(true);
                // 解决对某些标签的不支持出现白屏
                mNewsContentWeb.getSettings().setDomStorageEnabled(true);
                //设置字符编码，避免乱码
                mNewsContentWeb.getSettings().setDefaultTextEncodingName("utf-8");
                ImageJavascriptInterface imageJavascriptInterface = new ImageJavascriptInterface(mContext);
                mNewsContentWeb.addJavascriptInterface(imageJavascriptInterface, "newsInfoActivity");
                webviewContainner.addView(mNewsContentWeb);
                // TODO: 2022/5/23 详情有问题 
                content = "<p><img src=https://image.juntaikeji.com/look/2022-05-21/7e8d6f417ba64d2da1a735725bdf89d6.jpg width=\"175\" height=\"175\" /></p>\n" +
                        "2022-05-23 08:34:03.478 17995-18293/com.juntai.wisdom.project.mall E/菜优选: │ <p>产品介绍</p>";
                content = HtmlFormat.getNewContent(content);
                mNewsContentWeb.loadDataWithBaseURL("file:///android_asset/", content, "text/html",
                        "utf-8", null);
                String finalContent = content;
                mNewsContentWeb.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageFinished(WebView webView, String s) {
                        //加载个人中心数据
                        imageJavascriptInterface.setImageUrls(HtmlFormat.getImageUrlsFromHtml(finalContent));
                        setWebImageClick(webView, "newsInfoActivity");
                    }
                });
                break;

            default:
                break;
        }
    }
    /**
     * 设置网页中图片的点击事件   当页面加载完成后 注入JavaScript
     *
     * @param view
     */
    public void setWebImageClick(WebView view, String method) {
        String jsCode = "javascript:(function(){" +
                "var imgs=document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<imgs.length;i++){" +
                "imgs[i].pos = i;" +
                "imgs[i].onclick=function(){" +
                "window." + method + ".openImage(this.src,this.pos);" +
                "}}})()";
        view.loadUrl(jsCode);
    }
    public void releaseVideo() {
        if (imageLoader != null) {
            imageLoader.release();
        }
    }
}
