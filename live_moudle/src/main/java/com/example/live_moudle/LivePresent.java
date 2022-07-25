package com.example.live_moudle;


import com.example.app_basemodule.base.BaseAppPresent;
import com.example.app_basemodule.bean.LiveDetailBean;
import com.example.app_basemodule.bean.LiveResultBean;
import com.example.app_basemodule.net.AppNetModule;
import com.example.app_basemodule.utils.UserInfoManager;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.bean.shop.ShopCommodityListBean;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.RxScheduler;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class LivePresent extends BaseAppPresent<IModel, IView> {

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManager.getAccount())
                .add("token",UserInfoManager.getUserToken())
                .add("typeEnd", UserInfoManager.getDevType())
                .add("userId", String.valueOf(UserInfoManager.getUserId()));
        if (UserInfoManager.getShopId()>0) {
            builder.add("shopId", String.valueOf(UserInfoManager.getShopId()));
        }
        return builder;
    }
    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilderWithoutToken() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("typeEnd", UserInfoManager.getDevType())
                .add("userId", String.valueOf(UserInfoManager.getUserId()));
        return builder;
    }




    public void startLive(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .startLive(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveResultBean>(getView()) {
                    @Override
                    public void onSuccess(LiveResultBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }
    public void getLiveDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getLiveDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveDetailBean>(getView()) {
                    @Override
                    public void onSuccess(LiveDetailBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    public void getLiveRoomCommodities(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getLiveRoomCommodities(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopCommodityListBean>(getView()) {
                    @Override
                    public void onSuccess(ShopCommodityListBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o);
                        }

                    }

                    @Override
                    public void onError(String msg) {
                        if (getView() != null) {
                            getView().onError(tag, msg);
                        }
                    }
                });
    }


    @Override
    protected IModel createModel() {
        return null;
    }
}
