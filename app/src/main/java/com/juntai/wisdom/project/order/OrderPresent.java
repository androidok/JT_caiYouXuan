package com.juntai.wisdom.project.order;

import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.wisdom.project.AppNetModuleMall;
import com.juntai.wisdom.project.base.BaseAppMallPresent;
import com.juntai.wisdom.project.beans.UserInfoManagerMall;
import com.juntai.wisdom.project.beans.order.OrderDetailDataBean;
import com.juntai.wisdom.project.beans.order.OrderListBean;
import com.juntai.wisdom.project.utils.HawkProperty;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/10 17:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/10 17:31
 */
public class OrderPresent extends BaseAppMallPresent {


    public void commitOrder(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .commitOrder(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OrderListBean>(getView()) {
                    @Override
                    public void onSuccess(OrderListBean o) {
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

    public void cancelOrder(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .cancelOrder(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
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

    public void getOrderStatus(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getOrderStatus(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
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

    public void getOrderList(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getOrderList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OrderListBean>(getView()) {
                    @Override
                    public void onSuccess(OrderListBean o) {
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

    public void getOrderDetail(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getOrderDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OrderDetailDataBean>(getView()) {
                    @Override
                    public void onSuccess(OrderDetailDataBean o) {
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


    public void payByWeixin(List<Integer> ids, String tag) {
        AppNetModuleMall.createrRetrofit()
                .payByWeixin(UserInfoManagerMall.getAccount(), Hawk.get(HawkProperty.SP_KEY_TOKEN), UserInfoManagerMall.DEVICE_TYPE,ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
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

    public void payByZhifubao(List<Integer> ids, String tag) {
        AppNetModuleMall.createrRetrofit()
                .payByZhifubao(UserInfoManagerMall.getAccount(), Hawk.get(HawkProperty.SP_KEY_TOKEN), UserInfoManagerMall.DEVICE_TYPE,ids)

                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
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

    public void payByPubAccount(List<Integer> ids, String tag) {
        AppNetModuleMall.createrRetrofit()
                .payByPubAccount(UserInfoManagerMall.getAccount(), Hawk.get(HawkProperty.SP_KEY_TOKEN), UserInfoManagerMall.DEVICE_TYPE,ids)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<BaseResult>(getView()) {
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
