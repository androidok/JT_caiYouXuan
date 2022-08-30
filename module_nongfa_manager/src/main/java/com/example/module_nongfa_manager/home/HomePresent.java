package com.example.module_nongfa_manager.home;

import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.bean.SellOrderListBean;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerListBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerListBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.RxScheduler;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class HomePresent extends BaseAppPresent<IModel, IView> {
    @Override
    protected IModel createModel() {
        return null;
    }

    public void getNfOrderList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getNfOrderList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SellOrderListBean>(null) {
                    @Override
                    public void onSuccess(SellOrderListBean o) {
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
    public void getManagerCommodityList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getManagerCommodityList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityManagerListBean>(null) {
                    @Override
                    public void onSuccess(CommodityManagerListBean o) {
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
    public void getManagerShopList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getManagerShopList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopManagerListBean>(null) {
                    @Override
                    public void onSuccess(ShopManagerListBean o) {
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



}
