package com.juntai.project.sell.mall.home;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.project.sell.mall.AppNetModuleMall;
import com.juntai.project.sell.mall.base.BaseAppPresent;
import com.juntai.project.sell.mall.beans.BillBaseInfoBean;
import com.juntai.project.sell.mall.beans.BillListBean;
import com.juntai.project.sell.mall.beans.MonthStatisticsBean;
import com.juntai.project.sell.mall.beans.WithDrawListBean;
import com.juntai.project.sell.mall.beans.sell.ShopHomeInfoBean;
import com.juntai.project.sell.mall.beans.sell.SystemNoticeBean;
import com.juntai.project.sell.mall.beans.sell.SystemNoticeListBean;

import okhttp3.RequestBody;

/**
 * @aouther Ma
 * @date 2019/3/14
 */
public class HomePagePresent extends BaseAppPresent<IModel, HomePageContract.IHomePageView> implements HomePageContract.IHomePagePresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    public void getShopHomeInfo(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getShopHomeInfo(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopHomeInfoBean>(null) {
                    @Override
                    public void onSuccess(ShopHomeInfoBean o) {
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
    public void getSystemNotice(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getSystemNotice(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SystemNoticeListBean>(getView()) {
                    @Override
                    public void onSuccess(SystemNoticeListBean o) {
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
    public void getSystemNoticeDetail(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getSystemNoticeDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<SystemNoticeBean>(getView()) {
                    @Override
                    public void onSuccess(SystemNoticeBean o) {
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
    public void getBillList(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getBillList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BillListBean>(getView()) {
                    @Override
                    public void onSuccess(BillListBean o) {
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
    public void getBillBaseInfo(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getBillBaseInfo(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BillBaseInfoBean>(getView()) {
                    @Override
                    public void onSuccess(BillBaseInfoBean o) {
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

    public void getBillWithDrawList(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getBillWithDrawList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<WithDrawListBean>(getView()) {
                    @Override
                    public void onSuccess(WithDrawListBean o) {
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
    public void getMonthStatistics(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getMonthStatistics(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<MonthStatisticsBean>(getView()) {
                    @Override
                    public void onSuccess(MonthStatisticsBean o) {
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
