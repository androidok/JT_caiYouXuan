package com.example.module_nongfa_manager.home;

import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerListBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
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


    public void getManagerCommodityList(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getManagerCommodityList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityManagerListBean>(getView()) {
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
    public void updateCommodityStatus(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .updateCommodityStatus(requestBody)
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
