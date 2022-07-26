package com.juntai.wisdom.project.mall.mine;


import com.example.app_basemodule.bean.CollectDataBean;
import com.example.app_basemodule.bean.PicTextBean;
import com.example.appbase.bean.order.OrderStatusAmountBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.wisdom.project.mall.R;
import com.example.app_basemodule.base.BaseAppPresent;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * Describe:
 * Create by zhangzhenlong
 * 2020/3/7
 * email:954101549@qq.com
 */
public class MyCenterPresent extends BaseAppPresent<IModel, MyCenterContract.ICenterView> implements MyCenterContract.ICenterPresent {
    @Override
    protected IModel createModel() {
        return null;
    }


    public List<PicTextBean> getMyCenterTopMenus() {
// : 2022/5/3 这个地方需要更换图片资源
        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.mipmap.collect_commodity_icon, MyCenterContract.TOP_MENU_COLLECT_COMMODITY));
        arrays.add(new PicTextBean(R.mipmap.collect_shop_icon, MyCenterContract.TOP_MENU_COLLECT_SHOP));
        arrays.add(new PicTextBean(R.mipmap.addr_manage_icon, MyCenterContract.TOP_MENU_ADDR_MANAGER));
        return arrays;
    }

    public List<PicTextBean> getMyCenterOrderMenus() {
// : 2022/5/3 这个地方需要更换图片资源
        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.mipmap.to_pay_icon, MyCenterContract.ORDER_TO_PAY));
        arrays.add(new PicTextBean(R.mipmap.to_send_icon, MyCenterContract.ORDER_TO_SEND));
        arrays.add(new PicTextBean(R.mipmap.to_receive_icon, MyCenterContract.ORDER_TO_RECEIVE));
        arrays.add(new PicTextBean(R.mipmap.to_evaluate_icon, MyCenterContract.ORDER_TO_EVALUATE));
        arrays.add(new PicTextBean(R.mipmap.to_refund_icon, MyCenterContract.ORDER_TO_BACK));
        return arrays;
    }


    public void getOrderStatusAmount(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getOrderStatusAmount(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<OrderStatusAmountBean>(null) {
                    @Override
                    public void onSuccess(OrderStatusAmountBean o) {
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

    public void getShopCollectList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getShopCollectList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectDataBean>(getView()) {
                    @Override
                    public void onSuccess(CollectDataBean o) {
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

    public void logout(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .logout(requestBody)
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
    public void modifyUserInfo(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .modifyUserInfo(requestBody)
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

    public void getCommodityCollectList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getCommodityCollectList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CollectDataBean>(getView()) {
                    @Override
                    public void onSuccess(CollectDataBean o) {
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
