package com.juntai.wisdom.project.mall.home;


import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.bean.AroundShopBean;
import com.example.appbase.bean.PicTextBean;
import com.example.net.AppNetModule;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.bean.weather.CityBean;
import com.juntai.disabled.basecomponent.bean.weather.ResponseForcastWeather;
import com.juntai.disabled.basecomponent.bean.weather.ResponseRealTimeWeather;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.wisdom.project.mall.R;

import java.util.ArrayList;
import java.util.List;

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


    /**
     * 获取菜单
     *
     * @return
     */
    public List<PicTextBean> getMenus() {

        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.drawable.home_menu_map, HomePageContract.MENUE_MAP_TYPE));
        arrays.add(new PicTextBean(R.drawable.home_menu_weather, HomePageContract.MENUE_WEATHER));
        arrays.add(new PicTextBean(R.drawable.home_menu_shop, HomePageContract.SHOP, true));
        return arrays;
    }


    public void getWeatherRealTime(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit()
                .getWeatherRealtime(lng, lat)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ResponseRealTimeWeather>(getView()) {
                    @Override
                    public void onSuccess(ResponseRealTimeWeather o) {
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

    public void getForcastWeather(String tag, String lng, String lat) {
        AppNetModule.createrRetrofit().getForcast(lng, lat).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<ResponseForcastWeather>(getView()) {
            @Override
            public void onSuccess(ResponseForcastWeather o) {
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


    public void getPrivince(String tag) {
        AppNetModule.createrRetrofit().getProvince().compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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

    public void getCitys(String tag, int privinceNum) {
        AppNetModule.createrRetrofit().getCity(privinceNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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

    public void getTowns(String tag, int cityNum) {
        AppNetModule.createrRetrofit().getArea(cityNum).compose(RxScheduler.ObsIoMain(getView())).subscribe(new BaseObserver<CityBean>(getView()) {
            @Override
            public void onSuccess(CityBean o) {
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

    public void getStreets(String tag, int townNum) {
        AppNetModule.createrRetrofit()
                .getStreet(townNum)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CityBean>(getView()) {
                    @Override
                    public void onSuccess(CityBean o) {
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

    public void getAroundShopes(RequestBody requestBody, String tag) {
        AppNetModule.createrRetrofit()
                .getAroundShopes(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<AroundShopBean>(getView()) {
                    @Override
                    public void onSuccess(AroundShopBean o) {
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
