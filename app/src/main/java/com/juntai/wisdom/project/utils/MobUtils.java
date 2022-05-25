package com.juntai.wisdom.project.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/25 10:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/25 10:27
 */
public class MobUtils {
    
    
    public final static  int  SHARE_WECHAT = 1000;
    public final static  int  SHARE_WECHAT_CIRCLE = 1001;
    public final static  int  SHARE_QQ = 1002;

    private static final String TAG = "ShareUtil";


    /**
     * @param shareType     朋友圈/微信/QQ
     * @param shareTitle
     * @param shareText
     * @param shareImageUrl
     * @param shareUrl
     */
    public static void share(Context mContext, int shareType, String shareTitle, String shareText, String shareImageUrl, String shareUrl) {
        Log.e(TAG, "share: " + shareTitle + "  shareDes  " + shareText + " shareImg  " + shareImageUrl + "   shareUrl " + shareUrl);
        MyShareListener listener = new MyShareListener();
        Platform.ShareParams sharePlatform = new Platform.ShareParams();
        Bitmap logo = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.app_icon);
        Platform platform;
        if (shareType == SHARE_WECHAT) {
            sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
            setSharePlatform(sharePlatform, shareTitle, shareText, shareImageUrl, logo, shareUrl);
            if (shareUrl != null && !shareUrl.equalsIgnoreCase("")) {
                sharePlatform.setTitleUrl(shareUrl);
            }
            platform = ShareSDK.getPlatform(Wechat.NAME);
            if (!platform.isClientValid()) {
                        ToastUtils.toast(mContext,"未安装微信");
                return;
            }
            platform.setPlatformActionListener(listener);
            platform.share(sharePlatform);
        } else if (shareType == SHARE_WECHAT_CIRCLE) {
            platform = ShareSDK.getPlatform(WechatMoments.NAME);
            if (!platform.isClientValid()) {
                ToastUtils.toast(mContext,"未安装微信");

                return;
            }
            sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
            setSharePlatform(sharePlatform, shareTitle, null, shareImageUrl, logo,shareUrl);
            if (shareUrl != null && !shareUrl.equalsIgnoreCase("")) {
                sharePlatform.setTitleUrl(shareUrl);
            }
            platform.setPlatformActionListener(listener);
            platform.share(sharePlatform);
        }
//        /**
//         * 如果是QQ分享
//         */
//        else if (shareType == SHARE_QQ) {
//            setSharePlatform(sharePlatform, shareTitle, shareText, shareImageUrl, logo,shareUrl);
//            if (shareUrl != null && !shareUrl.equalsIgnoreCase("")) {
//                sharePlatform.setTitleUrl(shareUrl);
//            }
//            sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
//            platform = ShareSDK.getPlatform(QQ.NAME);
//            if (!platform.isClientValid()) {
//                ToastUtils.toast(mContext,"未安装QQ");
//                return;
//            }
//            platform.setPlatformActionListener(listener);
//            platform.share(sharePlatform);
//        }

//        else if (shareType == SHARE_QQ_ZONE) {
//
//            setSharePlatform(sharePlatform, shareTitle, shareText, shareImageUrl, logo,shareUrl);
//            if (shareUrl != null && !shareUrl.equalsIgnoreCase("")) {
//                sharePlatform.setTitleUrl(shareUrl);
//            }
//            sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
//            platform = ShareSDK.getPlatform(QZone.NAME);
//            if (!platform.isClientValid()) {
//                GDApplication.getInstance().showToast("未安装QQ");
//                return;
//            }
//            platform.setPlatformActionListener(listener);
//            platform.share(sharePlatform);
//        } else if (shareType == SHARE_SINA_WEIBO) {
//
//            setSharePlatform(sharePlatform, shareTitle, shareText, shareImageUrl, logo,shareUrl );
//            if (shareUrl != null && !shareUrl.equalsIgnoreCase("")) {
//                sharePlatform.setTitleUrl(shareUrl);
//            }
//            sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
//            platform = ShareSDK.getPlatform(SinaWeibo.NAME);
//            if (!platform.isClientValid()) {
//                GDApplication.getInstance().showToast("未安装微博");
//                return;
//            }
//            platform.setPlatformActionListener(listener);
//            platform.share(sharePlatform);
//        }
    }

    private static void setSharePlatform(Platform.ShareParams sharePlatform, String shareTitle, String shareText, String shareImageUrl, Bitmap logo, String url) {
        sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
        sharePlatform.setTitle(shareTitle);

        if (shareText != null && !shareText.equalsIgnoreCase("")) {
            sharePlatform.setText(shareText);
        }
        if (shareImageUrl != null && !shareImageUrl.equalsIgnoreCase("")) {
            sharePlatform.setImageUrl(shareImageUrl);
        } else {
            sharePlatform.setImageData(logo);
        }
        sharePlatform.setUrl(url);
    }


    static class MyShareListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Log.e(TAG, "onComplete: ");
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Log.e(TAG, "onError: " + i + "   throwable   " + throwable.getMessage());
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Log.e(TAG, "onCancel: ");
        }
    }
}
