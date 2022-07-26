package com.example.appbase.base;

import com.example.appbase.bean.CommodityDesListBean;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.LiveListBean;
import com.example.appbase.bean.LiveTypeListBean;
import com.example.appbase.bean.ShopListDataBean;
import com.example.appbase.bean.UserBeanMall;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.bean.shop.ShopDetailBean;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.RxScheduler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/6/3 8:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/6/3 8:38
 */
public abstract class BaseAppPresent<M extends IModel, V extends IView> extends BasePresenter<M, V> {


        /*====================================================    商城   ==============================================================*/

    public FormBody.Builder getBaseBuilderWithoutParama() {
        FormBody.Builder builder = new FormBody.Builder();
        return builder;
    }
    public void editCart(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .editCart(requestBody)
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

    public void getCommodityDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getCommodityDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDetailBean>(getView()) {
                    @Override
                    public void onSuccess(CommodityDetailBean o) {
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


        public void creatOrderCart(RequestBody requestBody, String tag) {
            AppNetModule.createrRetrofit()
                    .creatOrderCart(requestBody)
                    .compose(RxScheduler.ObsIoMain(getView()))
                    .subscribe(new BaseObserver<CreatOrderBean>(getView()) {
                        @Override
                        public void onSuccess(CreatOrderBean o) {
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


        public void creatOrderBuy(RequestBody requestBody, String tag) {
            AppNetModule.createrRetrofit()
                    .creatOrderBuy(requestBody)
                    .compose(RxScheduler.ObsIoMain(getView()))
                    .subscribe(new BaseObserver<CreatOrderBean>(getView()) {
                        @Override
                        public void onSuccess(CreatOrderBean o) {
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

    public void login(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .login(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBeanMall>(getView()) {
                    @Override
                    public void onSuccess(UserBeanMall o) {
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
    public void regist(String tag, RequestBody body) {
        AppNetModule.createrRetrofit()
                .regist(body)
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
    public void modifyPwd( RequestBody body,String tag) {
        AppNetModule.createrRetrofit()
                .modifyPwd(body)
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

    /**
     * 上传文件
     *
     * @param
     * @param body
     */
    private void uploadFiles(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .uploadFiles(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UploadFileBean>(getView()) {
                    @Override
                    public void onSuccess(UploadFileBean o) {
                        if (getView() != null) {
                            getView().onSuccess(tag, o.getFilePaths());
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


    /**
     * 上传文件
     *
     * @param filePaths
     */
    public void uploadFile(String tag, String... filePaths) {
        if (filePaths.length > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String filePath : filePaths) {
                try {
                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            uploadFiles(builder.build(), tag);
        }

    }

    /**
     * 上传文件
     *
     * @param filePaths
     */
    public void uploadFile(List<String> filePaths, String tag) {
        if (filePaths.size() > 0) {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            for (String filePath : filePaths) {
                try {
                    builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            uploadFiles(builder.build(), tag);
        }

    }

    /**
     * 获取用户信息
     *
     * @param tag
     * @param body
     */
    public void getUserInfo(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .getUserInfo(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<UserBeanMall>(null) {
                    @Override
                    public void onSuccess(UserBeanMall o) {
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


    public void getShopDetail(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getShopDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopDetailBean>(getView()) {
                    @Override
                    public void onSuccess(ShopDetailBean o) {
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

    public void collectShop(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .collectShop(requestBody)
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

    public void collectCommodity(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .collectCommodity(requestBody)
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
    public void startSearchCommodity(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .startSearchCommodity(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDesListBean>(null) {
                    @Override
                    public void onSuccess(CommodityDesListBean o) {
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
    public void startSearchShop(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .startSearchShop(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopListDataBean>(null) {
                    @Override
                    public void onSuccess(ShopListDataBean o) {
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
    public void getLiveList(RequestBody body, String tag) {
        AppNetModule.createrRetrofit()
                .getLiveList(body)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveListBean>(null) {
                    @Override
                    public void onSuccess(LiveListBean o) {
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
    public void getLiveType(String tag) {
        AppNetModule.createrRetrofit()
                .getLiveType()
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<LiveTypeListBean>(null) {
                    @Override
                    public void onSuccess(LiveTypeListBean o) {
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
