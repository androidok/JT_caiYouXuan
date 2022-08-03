package com.example.appbase.base;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.location.BDLocation;
import com.example.appbase.base.selectPics.BaseSelectPicsActivity;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseWebViewActivity;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppModuleActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {


    @Override
    protected String getUpdateHttpUrl() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }
    /**
     * 解析二维码
     *
     * @param result
     */
    public void resolveQrcode(String result) {
        // : 2022/7/15 分享的逻辑需要调整
        /**
         *  21960 菜优选的端口号
         */
        if (result.contains("juntaikeji") && result.contains("juntaitype")) {
            //内部二维码
            String type = result.substring(result.lastIndexOf("=") + 1, result.length());
            String id = result.substring(result.indexOf("=") + 1, result.indexOf("&"));
            /**
             * 1 商品 2 商家 3 直播
             */
            switch (type) {
                case "1":
                    // : 2022/5/31 商品分享
                    ARouter.getInstance().build(ARouterPath.appCommodityDetailActivity)
                            .withInt(BaseActivity.BASE_ID,Integer.parseInt(id))
                            .navigation(BaseAppModuleActivity.this,BaseActivity.BASE_REQUEST_RESULT);
                    break;
                case "2":
                    //店铺分享

                    ARouter.getInstance().build(ARouterPath.appShopActivity)
                            .withInt(BaseActivity.BASE_ID,Integer.parseInt(id))
                            .navigation(BaseAppModuleActivity.this,BaseActivity.BASE_REQUEST_RESULT);
                    break;
                case "3":
                    //直播
                    ARouter.getInstance().build(ARouterPath.live_LiveRoomActivity)
                            .withString(BASE_STRING,id)
                            .navigation();

                    break;
                case "4":
                    //todo 农发 订单详情
                    break;
                default:
                    break;
            }
        } else {
            startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url", result));
        }
        finish();
    }
    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManager.getAccount())
                .add("token", UserInfoManager.getUserToken())
                .add("typeEnd", UserInfoManager.getDevType())
                .add("userId", String.valueOf(UserInfoManager.getUserId()))
                .add("schoolId", String.valueOf(UserInfoManager.getSchoolId()));


        return builder;
    }


    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }



    @Override
    public boolean requestLocation() {
        return false;
    }



    @Override
    protected void onPicsAndEmpressed(List<String> icons) {

    }

    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

}
