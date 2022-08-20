package com.juntai.project.sell.mall.home.shop;

import android.text.TextUtils;

import com.example.appbase.bean.ShopCommodityListBean;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.base.BaseObserver;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.disabled.basecomponent.utils.RxScheduler;
import com.juntai.project.sell.mall.AppNetModuleMall;
import com.juntai.project.sell.mall.base.BaseAppMallPresent;
import com.juntai.project.sell.mall.beans.CommodityFormatDataBean;
import com.juntai.project.sell.mall.beans.CommodityFormatListBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.MultiRadioBean;
import com.example.appbase.bean.SellCommodityDetailBean;
import com.juntai.project.sell.mall.beans.sell.CommodityDetailDataBean;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.juntai.project.sell.mall.beans.sell.ShopCommodityCategoryListBean;
import com.juntai.project.sell.mall.beans.sell.ShopCommodityManagerListBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
import com.example.appbase.bean.multiBean.LocationBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.juntai.project.sell.mall.home.HomePageContract;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/8 8:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/8 8:50
 */
public class ShopPresent extends BaseAppMallPresent {
    public void getShopCommodityList(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getShopCommodityList(requestBody)
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

    /**
     * 商品溯源
     *
     * @return
     */
    public List<MultipleItem> getCommoditySourceData(CommoditySourceDetailBean.DataBean bean, boolean isDetail) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_NOTICE, "        根据《中华人民共和国食品安全法》及《食用农产品市场销售质量安全监督管理办法》(原国家食品药品监督管理总局令第20号)等法律法规的相关规定，“源本溯源检测系统”要求供应商在使用平台进行交易时，须对其生产、加工销售的食用农产品的来源等质量安全信息进行公开并在系统上填报。部分具备检测条件的供应商，还应对食用农产品的质量安全进行检测。具体注意事项告如下：\n" +
                "  1.为保障食用农产品溯源信息的及时性，系统仅提供今日明日的来源填报功能，请供应商及时进行填报。\n" +
                "  2.同一来源的食用农产品，若已经检测合格的，无需再次检测。同一来源的产品指产品名称、进货日期、供货商一致的"));

        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.COMMODITY_PROVIDER, UserInfoManager.getShopName()
                , true, 0, isDetail);

        initTextSelectType(arrays, HomePageContract.COMMODITY_RESTOC_TIME, "", "", true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.COMMODITY_RESTOC_PERSON, bean==null?"":bean.getPurchaseName()
                , true, 0, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (HomePageContract.SHOP_CARD, true)));


        List<String> fragmentPics = new ArrayList<>();
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getPhotoOne())) {
                fragmentPics.add(bean.getPhotoOne());
            }
            if (!TextUtils.isEmpty(bean.getPhotoTwo())) {
                fragmentPics.add(bean.getPhotoTwo());
            }
            if (!TextUtils.isEmpty(bean.getPhotoThree())) {
                fragmentPics.add(bean.getPhotoThree());
            }
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(HomePageContract.SHOP_CARD, 3, 3,
                3,  false,
                fragmentPics)));


        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (HomePageContract.Q_CARD, true)));
        List<String> photoList = new ArrayList<>();
        if (bean != null) {
            List<CommoditySourceDetailBean.DataBean.PhotoListBean> listBeans = bean.getPhotoList();
            if (listBeans != null && !listBeans.isEmpty()) {
                for (CommoditySourceDetailBean.DataBean.PhotoListBean listBean : listBeans) {
                    if (!TextUtils.isEmpty(listBean.getFileUrl())) {
                        photoList.add(listBean.getFileUrl());
                    }
                }

            }
        }


        arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT2, new ItemFragmentBean(HomePageContract.Q_CARD,3, 9,
                1,  false,
                photoList)));


        return arrays;
    }

    /**
     * 店铺商品信息
     *
     * @return
     */
    public List<MultipleItem> getCommodityBaseInfoData(SellCommodityDetailBean bean, boolean isDetail, boolean isDraft) {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextSelectType(arrays, HomePageContract.COMMODITY_CATEGORY_NAME, bean == null ? "" : String.valueOf(bean.getCategoryId()), bean == null ? "" : bean.getCategoryName(), true);
        initTextSelectType(arrays, HomePageContract.COMMODITY_SORT, bean == null ? "" : String.valueOf(bean.getShopClassifyId()), bean == null ? "" : bean.getShopClassifyName(), true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.COMMODITY_NAME, bean == null ? "" :
                        bean.getName()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.COMMODITY_PRICE, bean == null ? "" :
                        String.valueOf(bean.getPrice())
                , true, 0, isDetail);
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (HomePageContract.COMMODITY_PRIMARY_PIC, true)));
        if (bean != null) {
            String coverPic = bean.getCoverImg();
            List<String> pics = new ArrayList<>();
            pics.add(coverPic);
            arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(HomePageContract.COMMODITY_PRIMARY_PIC, 4, 1,
                    1, false,
                    pics)));
        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(HomePageContract.COMMODITY_PRIMARY_PIC, 4, 1,
                    1, false,
                    new ArrayList<>())));
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (HomePageContract.COMMODITY_BANNER_PICS, true)));
        if (bean != null) {
            List<SellCommodityDetailBean.ImagesBean> imagesBeans = null;
            if (isDraft) {
                imagesBeans = bean.getCommodityImg();
            } else {
                imagesBeans = bean.getImages();
            }

            List<String> pics = new ArrayList<>();
            for (SellCommodityDetailBean.ImagesBean imagesBean : imagesBeans) {
                pics.add(imagesBean.getImgUrl());
            }
            arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT2, new ItemFragmentBean(HomePageContract.COMMODITY_BANNER_PICS, 4, isDetail ? pics.size() : 4,
                    pics.size(), false,
                    pics)));
        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT2, new ItemFragmentBean(HomePageContract.COMMODITY_BANNER_PICS, 4, 4,
                    1, false,
                    new ArrayList<>())));
        }
        if (bean != null) {
            List<String> videoPaht = new ArrayList<>();
            if (!TextUtils.isEmpty(bean.getVideoUrl())) {
                arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                        (HomePageContract.COMMODITY_VIDEO, false)));
                videoPaht.add(bean.getVideoUrl());
                arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT_VIDEO, new ItemFragmentBean(HomePageContract.COMMODITY_VIDEO, 4, 1,
                        1, false,
                        videoPaht)));
            } else {
                if (!isDetail) {
                    arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                            (HomePageContract.COMMODITY_VIDEO, false)));
                    arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT_VIDEO, new ItemFragmentBean(HomePageContract.COMMODITY_VIDEO, 4, 1,
                            1, false,
                            videoPaht)));
                }
            }

        } else {
            arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                    (HomePageContract.COMMODITY_VIDEO, false)));
            arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT_VIDEO, new ItemFragmentBean(HomePageContract.COMMODITY_VIDEO, 4, 1,
                    1, false,
                    new ArrayList<>())));
        }
        if (isDetail) {
            arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                    (HomePageContract.COMMODITY_DETAIL_INFO, true)));
            arrays.add(new MultipleItem(MultipleItem.ITEM_RICH_TEXT, bean.getDescription()));
        }


        return arrays;
    }

    /**
     * @param arrays
     * @param typeName
     */
    private void initRadioType(List<MultipleItem> arrays, String typeName, int defaultIndex, String[] values,
                               boolean isImportant) {
        String titleName = null;
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean(typeName,
                isImportant)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_RADIO, new MultiRadioBean(typeName, values, defaultIndex)));
    }

    /**
     * 发送货物
     *
     * @return
     */
    public List<MultipleItem> sendGoods() {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SEND_COMPANY, ""
                , true, 0, false);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SEND_NO, "", true, 0, false);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SEND_LINK, "", true, 0, false);

        return arrays;
    }

    /**
     * 店铺管理
     *
     * @return
     */
    public List<MultipleItem> getShopManagerData(ShopDetailSellBean.DataBean bean, boolean isDetail) {
        List<MultipleItem> arrays = new ArrayList<>();
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (HomePageContract.SHOP_PIC, true)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_HEAD_PIC,
                new MultiPicBean(HomePageContract.SHOP_PIC, -1,
                        bean == null ? "" : bean.getHeadPortrait())));
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SHOP_NAME, bean == null ? "" :
                        bean.getName()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SHOP_INTRODUCTION, bean == null ? "" :
                bean.getIntroduction(), true, 1, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_LOCATION, new LocationBean(bean == null ? null :
                bean.getGpsAddress()
                , bean == null ? null : bean.getLatitude(), bean == null ? null : bean.getLongitude())));
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.SHOP_TEL, bean == null ? "" :
                        bean.getPhoneNumber()
                , true, 0, isDetail);
        initTextSelectType(arrays, HomePageContract.SHOP_CATEGORY, bean == null ? "" : TextUtils.join(",", bean.getCategoryList()), bean == null ? "" : bean.getCategory(), true);

        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.ASSETS_WITHDRAW_REAL_NAME, bean == null ? "" :
                        bean.getRealName()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, HomePageContract.ASSETS_WITHDRAW_IDCARD, bean == null ? "" :
                        bean.getIdCode()
                , true, 0, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_BIG, "店铺证件"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(HomePageContract.SHOP_LICENSE, 1, bean == null ? "" :
                        bean.getBusinessLicense())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(HomePageContract.ID_CARD_FRONT, 2, bean == null ? "" :
                        bean.getIdPositive())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(HomePageContract.ID_CARD_BACK, 3, bean == null ? "" :
                        bean.getIdSide())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(HomePageContract.ID_CARD_HAND, 4, bean == null ? "" :
                        bean.getHandPicture())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(HomePageContract.SHOP_INNER_PICS, 5, bean == null ? "" :
                        bean.getShopRealScene())));
        return arrays;
    }

    /**
     * initTextType
     *
     * @param arrays
     * @param key
     */
    private void initTextSelectType(List<MultipleItem> arrays, String key, String id, String value,
                                    boolean isImportant) {
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (key, isImportant)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_SELECT,
                new TextKeyValueBean(key, value, id, String.format("%s%s", "请选择",
                        key), 0, isImportant)));
    }


    public void addCommodityCategorys(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .addCommodityCategorys(requestBody)
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

    public void getCommodityCategorys(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getCommodityCategorys(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopCommodityCategoryListBean>(null) {
                    @Override
                    public void onSuccess(ShopCommodityCategoryListBean o) {
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

    public void modifyCommodityCategorys(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .modifyCommodityCategorys(requestBody)
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

    public void deleteCommodityCategorys(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .deleteCommodityCategorys(requestBody)
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

    public void getAllCommodity(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getAllCommodity(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<ShopCommodityManagerListBean>(null) {
                    @Override
                    public void onSuccess(ShopCommodityManagerListBean o) {
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
        AppNetModuleMall.createrRetrofit()
                .getCommodityDetail(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityDetailDataBean>(null) {
                    @Override
                    public void onSuccess(CommodityDetailDataBean o) {
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

    public void addCommodityBaseInfo(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .addCommodityBaseInfo(requestBody)
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

    public void updateCommodityBaseInfo(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .updateCommodityBaseInfo(requestBody)
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

    public void deleteCommodity(RequestBody requestBody, List<Integer> ids,String tag) {
        AppNetModuleMall.createrRetrofit()
                .deleteCommodity(requestBody,ids)
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

    public void editCommodityProperty(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .editCommodityProperty(requestBody)
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
    public void updateCommodityPrice(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .updateCommodityPrice(requestBody)
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

    public void onSaleCommodity(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .onSaleCommodity(requestBody)
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


    public void createCommodityFormatList(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .createCommodityFormatList(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityFormatListBean>(null) {
                    @Override
                    public void onSuccess(CommodityFormatListBean o) {
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

    public void getCommodityFormat(RequestBody requestBody, String tag) {
        AppNetModuleMall.createrRetrofit()
                .getCommodityFormat(requestBody)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityFormatDataBean>(null) {
                    @Override
                    public void onSuccess(CommodityFormatDataBean o) {
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

    public void addShopBannerPics(RequestBody requestBody, List<String> pics, String tag) {
        AppNetModuleMall.createrRetrofit()
                .addShopBannerPics(requestBody, pics)
                .compose(RxScheduler.ObsIoMain(getView()))
                .subscribe(new BaseObserver<CommodityFormatDataBean>(null) {
                    @Override
                    public void onSuccess(CommodityFormatDataBean o) {
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
