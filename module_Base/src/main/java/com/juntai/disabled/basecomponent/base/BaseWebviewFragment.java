package com.juntai.disabled.basecomponent.base;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.web.HtmlFormat;
import com.juntai.disabled.basecomponent.web.ImageJavascriptInterface;

/**
 * @Author: tobato
 * @Description: 作用描述  有webview的fragment
 * @CreateDate: 2022/5/22 16:28
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/22 16:28
 */
public class BaseWebviewFragment extends BaseMvpFragment {


    private WebView mAgreementWeb;
    private LinearLayout mAgreementLayout;
    private ImageJavascriptInterface imageJavascriptInterface;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_agreement;
    }

    @Override
    protected void initView() {

        mAgreementWeb = (WebView) getView(R.id.agreement_web);
        mAgreementLayout = (LinearLayout) getView(R.id.agreement_layout);
    }

    @Override
    protected void initData() {
    }

    public void setWebData(String urlString) {
        if (!TextUtils.isEmpty(urlString)) {
            urlString = HtmlFormat.getNewContent(urlString);
            mAgreementWeb = new WebView(mContext.getApplicationContext());
            //        ScrollView 内置 Webview导致底部页面下方空白区域无限下滑,设置height=LayoutParams.WRAP_CONTENT解决
            mAgreementWeb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            mAgreementWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            mAgreementWeb.setScrollBarSize(0);

            mAgreementWeb.getSettings().setJavaScriptEnabled(true);
            // 解决对某些标签的不支持出现白屏
            mAgreementWeb.getSettings().setDomStorageEnabled(true);
            //设置字符编码，避免乱码
            mAgreementWeb.getSettings().setDefaultTextEncodingName("utf-8");
            imageJavascriptInterface = new ImageJavascriptInterface(mContext);
            mAgreementWeb.addJavascriptInterface(imageJavascriptInterface, "newsInfoActivity");

//            mAgreementWeb.setWebViewClient(new WebViewClientDemo());
            mAgreementWeb.loadDataWithBaseURL("file:///android_asset/", urlString, "text/html",
                    "utf-8", null);
            mAgreementWeb.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView webView, String s) {
                    //加载个人中心数据
                    //加载个人中心数据
                    s = HtmlFormat.getNewContent(s);
                    imageJavascriptInterface.setImageUrls(HtmlFormat.getImageUrlsFromHtml(s));
                    setWebImageClick(webView, "newsInfoActivity");
                }
            });
        }



    }

    @Override
    public void onSuccess(String tag, Object o) {

    }


    private class WebViewClientDemo extends WebViewClient {
//        @Override
        // 在WebView中不在默认浏览器下显示页面
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
        @Override
        public void onPageFinished(WebView webView, String s) {
            //加载个人中心数据
            s = HtmlFormat.getNewContent(s);
            imageJavascriptInterface.setImageUrls(HtmlFormat.getImageUrlsFromHtml(s));
            setWebImageClick(webView, "newsInfoActivity");
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
    @Override
    public void onDestroy() {
        //但是注意：webview调用destory时,webview仍绑定在Activity上
        //这是由于自定义webview构建时传入了该Activity的context对象
        //因此需要先从父容器中移除webview,然后再销毁webview
        mAgreementLayout.removeView(mAgreementWeb);
        mAgreementWeb.destroy();
        super.onDestroy();
    }

}
