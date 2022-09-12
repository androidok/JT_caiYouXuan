package com.juntai.project.sell.mall.base;

import com.example.appbase.bean.CommoditySourceDetailBean;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.project.sell.mall.AppNetModuleMall;
import com.juntai.project.sell.mall.beans.CommodityUnitBean;
import com.juntai.project.sell.mall.home.HomePageContract;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/8 8:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/8 8:52
 */
public abstract class BaseAppMallPresent extends BaseAppPresent<IModel, HomePageContract.IHomePageView> {

    @Override
    protected IModel createModel() {
        return null;
    }

    public void sendGoods(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .sendGoods(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
    public void getAllCommodityUnit(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getAllCommodityUnit(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityUnitBean>(null) {
                    @Override
                    public void onSuccess(CommodityUnitBean o) {
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
    public void getCommoditySource(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getCommoditySource(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommoditySourceDetailBean>(null) {
                    @Override
                    public void onSuccess(CommoditySourceDetailBean o) {
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

    public void handlerRefundRequest(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .handlerRefundRequest(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(null) {
                    @Override
                    public void onSuccess(BaseResult o) {
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
