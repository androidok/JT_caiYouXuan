package com.juntai.project.sell.mall.home.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.example.appbase.base.selectPics.SelectPhotosFragment;
import com.example.appbase.base.sendcode.SendCodeModel;
import com.example.appbase.bean.BasePicVideoBean;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.bean.SellCommodityDetailBean;
import com.example.appbase.bean.multiBean.BaseAdapterDataBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.LocationBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.example.appbase.bean.multiBean.MultiRadioBean;
import com.example.appbase.util.UserInfoManager;
import com.example.appbase.util.bannerImageLoader.BannerObject;
import com.juntai.disabled.basecomponent.base.EditDialog;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.bdmap.act.LocateSelectionActivity;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.base.BaseRecyclerviewActivity;
import com.juntai.project.sell.mall.beans.CommodityUnitBean;
import com.juntai.project.sell.mall.beans.sell.ShopCommodityCategoryListBean;
import com.juntai.project.sell.mall.home.HomePageContract;
import com.juntai.project.sell.mall.home.commodityManager.commodityCategory.CommodityCategoryActivity;
import com.juntai.project.sell.mall.home.shop.shopCategory.ChoseCategoryActivity;
import com.juntai.project.sell.mall.mine.myinfo.HeadCropActivity;
import com.juntai.project.sell.mall.utils.CalendarUtil;
import com.juntai.project.sell.mall.utils.StringTools;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/6/9 11:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/6/9 11:08
 */
public abstract class BaseShopActivity extends BaseRecyclerviewActivity<ShopPresent> implements HomePageContract.IHomePageView, SelectPhotosFragment.OnPhotoItemClick, View.OnClickListener, BaseShopAdapter.OnPicVideoLoadSuccessCallBack {

    private int currentPosition;
    private MultiPicBean picBean;
    private String address;
    private TextView mSelectTv;
    private TextKeyValueBean selectBean;
    private EditDialog editDialog;

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {

    }

    @Override
    public boolean requestLocation() {
        return false;
    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        if (isDetail()) {
            mSmartrefreshlayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        } else {
            mSmartrefreshlayout.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        }

        return new BaseShopAdapter(null, isDetail(), getSupportFragmentManager(), this);
    }


    @Override
    public void deletePicVideo(BasePicVideoBean basePicVideoBean) {
        // : 2022/9/15 调用删除图片的接口
        if (basePicVideoBean.getId() > 0) {
            mPresenter.deleteImage(getBaseBuilder().add("id",String.valueOf(basePicVideoBean.getId())).build(), AppHttpPathMall.DELETE_IMAGE);
        }


//        if (icons.size() > 0) {
//            List<String> localPics = new ArrayList<>();
//            List<String> olderPics = new ArrayList<>();
//            for (String icon : icons) {
//                if (!icon.contains("www.juntaikeji")) {
//                    localPics.add(icon);
//                } else {
//                    olderPics.add(icon);
//                }
//            }
//            if (localPics.size() > 0) {
//
//                MultipartBody.Builder builder = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM);
//
//                for (String filePath : localPics) {
//
//                    try {
//                        builder.addFormDataPart("file", URLEncoder.encode(filePath, "utf-8"), RequestBody.create(MediaType.parse("file"), new File(filePath)));
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//                AppNetModuleMall.createrRetrofit()
//                        .uploadFiles(builder.build())
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new BaseObserver<UploadFileBean>(this) {
//                            @Override
//                            public void onSuccess(UploadFileBean o) {
//                                olderPics.addAll(o.getFilePaths());
//                                itemFragmentBean.setFragmentPics(olderPics);
//                            }
//
//                            @Override
//                            public void onError(String msg) {
//                                ToastUtils.toast(mContext, msg);
//                            }
//                        });
//            } else {
//                itemFragmentBean.setFragmentPics(olderPics);
//            }
//        } else {
//            // : 2022/6/10 删没了
//            itemFragmentBean.setFragmentPics(null);
//        }
    }

    @Override
    public void initData() {
        super.initData();
        setTitleName(getTitleName());
        initAdapterClick();

    }

    private void initAdapterClick() {

        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                MultipleItem multipleItem = (MultipleItem) adapter.getData().get(position);

                int id = view.getId();
                if (id == R.id.edit_iv) {
                    //编辑文本信息
                    TextKeyValueBean textBean = (TextKeyValueBean) multipleItem.getObject();
                    if (editDialog == null) {
                        editDialog = new EditDialog(mContext).builder();
                    }
                    editDialog.setCanceledOnTouchOutside(false)
                            .setTitle(textBean.getKey())
                            .setContent(textBean.getValue())
                            .setOnConfirmListener("", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EditText editText = (EditText) v;
                                    String content = editText.getText().toString().trim();
                                    switch (textBean.getKey()) {
                                        case HomePageContract.SHOP_INTRODUCTION:
                                            // : 2022/8/30 设置店铺简介
                                            textBean.setValue(content);
                                            adapter.notifyItemChanged(position);
                                            mPresenter.updateShopInfo(getBaseBuilder()
                                                    .add("userAccount", UserInfoManager.getAccount())
                                                    .add("id", String.valueOf(UserInfoManagerMall.getShopId()))

                                                    .add("introduction", content).build(), AppHttpPathMall.UPDATE_SHOP_INFO

                                            );
                                            break;
                                        case HomePageContract.SHOP_TEL:
                                            // : 2022/8/30 设置店铺联系方式
                                            if (!SendCodeModel.isMobileNO(content)) {
                                                ToastUtils.toast(mContext, "手机号格式错误");
                                                return;
                                            }
                                            textBean.setValue(content);
                                            adapter.notifyItemChanged(position);
                                            mPresenter.updateShopInfo(getBaseBuilder()
                                                    .add("userAccount", UserInfoManager.getAccount())
                                                    .add("id", String.valueOf(UserInfoManagerMall.getShopId()))

                                                    .add("phoneNumber", content).build(), AppHttpPathMall.UPDATE_SHOP_INFO

                                            );
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }).show();

                } else if (id == R.id.form_pic_src_iv || id == R.id.form_head_pic_iv) {
                    choseImage(0, BaseShopActivity.this, 1);
                } else if (id == R.id.action_img) {
                    choseImage(0, BaseShopActivity.this, 9);
                } else if (id == R.id.location_ll) {//跳转到选择位置类
                    startActivityForResult(new Intent(mContext, LocateSelectionActivity.class),
                            LocateSelectionActivity.SELECT_ADDR);
                } else if (id == R.id.select_value_tv) {
                    mSelectTv = (TextView) adapter.getViewByPosition(mRecyclerview, position,
                            R.id.select_value_tv);
                    selectBean = (TextKeyValueBean) multipleItem.getObject();
                    switch (selectBean.getKey()) {
                        case HomePageContract.SHOP_CATEGORY:
                            startActivityForResult(new Intent(mContext, ChoseCategoryActivity.class)
                                    .putExtra(BASE_ID, 1), ChoseCategoryActivity.ACTIVITY_RESULT);
                            break;
                        case HomePageContract.COMMODITY_RESTOC_TIME:
                            PickerManager.getInstance().showTimePickerView(mContext, new boolean[]{true, true, true, false, false, false}, "选择年月日", new PickerManager.OnTimePickerTimeSelectedListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    String time = CalendarUtil.getCurrentTime("yyyy-MM-dd", date);
                                    mSelectTv.setText(time);
                                    selectBean.setValue(time);
                                }
                            });
                            break;
                        case HomePageContract.SHOP_ORDER_START_TIME:
                            PickerManager.getInstance().showTimePickerView(mContext, new boolean[]{true, true, true, true, true, true}, "截单开始时间", new PickerManager.OnTimePickerTimeSelectedListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    String time = CalendarUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss", date);
                                    mSelectTv.setText(time);
                                    selectBean.setValue(time);
                                    String endTime = getStartEndTime(1);
                                    if (!TextUtils.isEmpty(endTime) && UserInfoManagerMall.getShopId() > 0) {
                                        mPresenter.updateShopInfo(getBaseBuilder()
                                                .add("id", String.valueOf(UserInfoManagerMall.getShopId()))
                                                .add("userAccount", UserInfoManager.getAccount())
                                                .add("endTime", endTime)
                                                .add("startTime", time).build(), AppHttpPathMall.UPDATE_SHOP_INFO

                                        );
                                    }

                                }
                            });
                            break;
                        case HomePageContract.SHOP_ORDER_END_TIME:
                            PickerManager.getInstance().showTimePickerView(mContext, new boolean[]{true, true, true, true, true, true}, "截单结束时间", new PickerManager.OnTimePickerTimeSelectedListener() {
                                @Override
                                public void onTimeSelect(Date date, View v) {
                                    String time = CalendarUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss", date);
                                    mSelectTv.setText(time);
                                    selectBean.setValue(time);
                                    String startTime = getStartEndTime(0);
                                    if (!TextUtils.isEmpty(startTime) && UserInfoManagerMall.getShopId() > 0) {
                                        mPresenter.updateShopInfo(getBaseBuilder()
                                                .add("id", String.valueOf(UserInfoManagerMall.getShopId()))
                                                .add("userAccount", UserInfoManager.getAccount())
                                                .add("startTime", startTime)
                                                .add("endTime", time).build(), AppHttpPathMall.UPDATE_SHOP_INFO

                                        );
                                    }

                                }
                            });
                            break;
                        case HomePageContract.COMMODITY_CATEGORY_NAME:
                            // : 2022/6/10 选择店铺主营类目
                            startActivityForResult(new Intent(mContext, ChoseCategoryActivity.class)
                                    .putExtra(BASE_ID, 0), ChoseCategoryActivity.ACTIVITY_RESULT);
                            break;
                        case HomePageContract.COMMODITY_SORT:
                            // : 2022/6/10 选择商品类目
                            mPresenter.getCommodityCategorys(getBaseBuilder().build(), AppHttpPathMall.ALL_COMMODITY_CATEGORY);
                            break;
                        case HomePageContract.COMMODITY_UNIT:
                            // : 2022/9/12   选择商品单位
                            mPresenter.getAllCommodityUnit(getBaseBuilder().build(), AppHttpPathMall.GET_ALL_COMMODITY_UNIT);
                            break;
                    }
                }
            }
        });


    }

    @Override
    protected void onPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            MultipleItem multipleItem = (MultipleItem) baseQuickAdapter.getItem(currentPosition);
            switch (multipleItem.getItemType()) {
                default:
                    picBean = (MultiPicBean) multipleItem.getObject();
                    if (HomePageContract.SHOP_PIC.equals(picBean.getPicName())) {
                        //跳转到裁剪头像的界面
                        startActivityForResult(new Intent(this, HeadCropActivity.class).putExtra(HeadCropActivity.HEAD_PIC,
                                path), BASE_REQUEST_RESULT);
                    } else {
                        // : 2022/6/10 上传图片
                        mPresenter.uploadFile(AppHttpPathMall.UPLOAD_ONE_PIC, path);
                    }
                    break;
            }


        }
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {
        super.onLocationReceived(bdLocation);
        if (bdLocation != null) {
            lat = bdLocation.getLatitude();
            lng = bdLocation.getLongitude();
            address = bdLocation.getAddrStr();
            notifyLocationItem();
        }

    }

    private void notifyLocationItem() {
        List<MultipleItem> arrays = baseQuickAdapter.getData();
        for (int i = 0; i < arrays.size(); i++) {
            MultipleItem array = arrays.get(i);
            if (MultipleItem.ITEM_LOCATION == array.getItemType()) {
                //定位
                LocationBean locationBean = (LocationBean) array.getObject();
                if (!TextUtils.isEmpty(locationBean.getAddress()) && UserInfoManagerMall.getShopId() > 0) {
                    // : 2022/8/30  调用更改地址的接口
                    mPresenter.updateShopInfo(getBaseBuilder()
                            .add("id", String.valueOf(UserInfoManagerMall.getShopId()))
                            .add("userAccount", UserInfoManager.getAccount())
                            .add("longitude", String.valueOf(lng))
                            .add("latitude", String.valueOf(lat))
                            .add("gpsAddress", address).build(), AppHttpPathMall.UPDATE_SHOP_INFO

                    );
                }
                locationBean.setAddress(address);
                locationBean.setLatitude(String.valueOf(lat));
                locationBean.setLongitude(String.valueOf(lng));
                baseQuickAdapter.notifyItemChanged(i);


                break;
            }
        }
    }

    /**
     * 获取截单开始结束时间
     * <p>
     * 0 开始
     *
     * @return
     */
    private String getStartEndTime(int type) {
        String time = null;
        List<MultipleItem> arrays = baseQuickAdapter.getData();
        for (int i = 0; i < arrays.size(); i++) {
            MultipleItem array = arrays.get(i);
            if (MultipleItem.ITEM_SELECT == array.getItemType()) {
                TextKeyValueBean textKeyValueBean = (TextKeyValueBean) array.getObject();
                String key = textKeyValueBean.getKey();
                String value = textKeyValueBean.getValue();
                if (0 == type) {
                    if (HomePageContract.SHOP_ORDER_START_TIME.equals(key)) {
                        time = value;
                        break;
                    }
                } else {
                    if (HomePageContract.SHOP_ORDER_END_TIME.equals(key)) {
                        time = value;
                        break;
                    }
                }
            }
        }
        return time;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BASE_REQUEST_RESULT && resultCode == BASE_REQUEST_RESULT) {
            if (data != null) {
                String path = data.getStringExtra(HeadCropActivity.CROPED_HEAD_PIC);
                // : 2022/6/10 上传头像图片到后台
                mPresenter.uploadFile(AppHttpPathMall.UPLOAD_ONE_PIC, path);
            }

        }
        if (requestCode == LocateSelectionActivity.SELECT_ADDR && resultCode == BASE_REQUEST_RESULT) {
            //地址选择
            lat = data.getDoubleExtra(LocateSelectionActivity.LAT, 0.0);
            lng = data.getDoubleExtra(LocateSelectionActivity.LNG, 0.0);
            address = data.getStringExtra(LocateSelectionActivity.ADDRNAME);
            notifyLocationItem();

        }

        if (resultCode == ChoseCategoryActivity.ACTIVITY_RESULT) {
            if (data == null) {
                return;
            }
            //返回选择的经营类目
            String categoryids = data.getStringExtra(BASE_STRING);
            String categoryNames = data.getStringExtra(BASE_STRING2);
            if (selectBean != null) {
                mSelectTv.setText(categoryNames);
                selectBean.setValue(categoryNames);
                selectBean.setIds(categoryids);
            }

        }


    }


    protected abstract String getTitleName();

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取adapter中的数据
     *
     * @return
     */
    protected BaseAdapterDataBean getBaseOfAdapterData() {
        BaseAdapterDataBean baseAdapterDataBean = new BaseAdapterDataBean();
        SellCommodityDetailBean commodityDetailBean = new SellCommodityDetailBean();
        CommoditySourceDetailBean.DataBean sourceBean = new CommoditySourceDetailBean.DataBean();
        FormBody.Builder builder = getBaseBuilder();
        builder.add("userAccount", UserInfoManagerMall.getAccount());
        List<MultipleItem> arrays = baseQuickAdapter.getData();
        for (MultipleItem array : arrays) {
            switch (array.getItemType()) {
                case MultipleItem.ITEM_RADIO:
                    //户口类别 家庭经济状况 项目级别 这几个上传的字段对应的是从1开始的 所以需要从默认的索引+1  相反的 获取到详情展示的时候 就需要-1了
                    MultiRadioBean radioBean = (MultiRadioBean) array.getObject();
//                    switch (radioBean.getKey()) {
//                        case HomePageContract.COMMODITY_POST_FREE:
//                            commodityDetailBean.setIsPostage(radioBean.getDefaultSelectedIndex());
//                            break;
//                        default:
//                            break;
//                    }
                    break;
                case MultipleItem.ITEM_HEAD_PIC:
                    MultiPicBean headPicBean = (MultiPicBean) array.getObject();
                    if (TextUtils.isEmpty(headPicBean.getPicPath())) {
                        ToastUtils.toast(mContext, "请选择店铺照片");
                        return null;
                    }
                    builder.add("headPortrait", headPicBean.getPicPath());
                    break;

                case MultipleItem.ITEM_LOCATION:
                    LocationBean locationBean = (LocationBean) array.getObject();
                    builder.add("gpsAddress", locationBean.getAddress());
                    builder.add("longitude", locationBean.getLongitude());
                    builder.add("latitude", locationBean.getLatitude());
                    break;

                case MultipleItem.ITEM_FRAGMENT:
                case MultipleItem.ITEM_FRAGMENT2:
                case MultipleItem.ITEM_FRAGMENT_VIDEO:
                    //多选图片
                    ItemFragmentBean fragmentPicBean = (ItemFragmentBean) array.getObject();
                    List<BasePicVideoBean> photos = fragmentPicBean.getFragmentPics();
                    String name = fragmentPicBean.getKey();
                    String msg = String.format("请选择%s", name);
                    if (!HomePageContract.COMMODITY_VIDEO.equals(name)) {
                        if (photos == null || photos.isEmpty()) {
                            ToastUtils.toast(mContext, msg);
                            return null;
                        }
                    }
                    if (photos != null && photos.size() > 0) {
                        BasePicVideoBean bean = photos.get(0);
                        switch (name) {
                            case HomePageContract.COMMODITY_PRIMARY_PIC:
                                commodityDetailBean.setCoverImg(bean.getUrl());

                                break;
                            case HomePageContract.COMMODITY_VIDEO:
                                commodityDetailBean.setVideoUrl(bean.getUrl());
                                break;
                            case HomePageContract.COMMODITY_BANNER_PICS:
                                List<SellCommodityDetailBean.ImagesBean> imagesBeans = new ArrayList<>();

                                for (BasePicVideoBean photo : photos) {
                                    imagesBeans.add(new SellCommodityDetailBean.ImagesBean(photo.getUrl()));
                                }
                                commodityDetailBean.setImages(imagesBeans);
                                commodityDetailBean.setCommodityImg(imagesBeans);

                                break;
                            case HomePageContract.SHOP_CARD:
                                if (photos.size() != 3) {
                                    ToastUtils.toast(mContext, "商家证件一共需要上传3张图片");

                                    return null;
                                }
                                for (int i = 0; i < photos.size(); i++) {
                                    BasePicVideoBean pic = photos.get(i);
                                    switch (i) {
                                        case 0:
                                            sourceBean.setPhotoOne(pic.getUrl());
                                            break;
                                        case 1:
                                            sourceBean.setPhotoTwo(pic.getUrl());
                                            break;
                                        case 2:
                                            sourceBean.setPhotoThree(pic.getUrl());
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case HomePageContract.Q_CARD:
                                if (photos.size() < 1) {
                                    ToastUtils.toast(mContext, "请上传产品检测检疫等合格证件");
                                    return null;
                                }
                                List<CommoditySourceDetailBean.DataBean.PhotoListBean> photoListBeans = new ArrayList<>();
                                for (BasePicVideoBean photo : photos) {
                                    photoListBeans.add(new CommoditySourceDetailBean.DataBean.PhotoListBean(photo.getUrl()));
                                }
                                sourceBean.setPhotoList(photoListBeans);
                                break;
                            default:
                                break;
                        }
                    }


                    break;
                case MultipleItem.ITEM_PIC:
                    MultiPicBean picBean = (MultiPicBean) array.getObject();
                    String picKey = picBean.getPicName();
                    String picPath = picBean.getPicPath();
                    if (picKey != null) {
                        switch (picKey) {
                            case HomePageContract.SHOP_LICENSE:
                                if (!StringTools.isStringValueOk(picPath)) {
                                    ToastUtils.toast(mContext, "请选择店铺营业执照");
                                    return null;
                                }
                                builder.add("businessLicense", picPath);
                                break;
                            case HomePageContract.ID_CARD_FRONT:
                                if (!StringTools.isStringValueOk(picPath)) {
                                    ToastUtils.toast(mContext, "法人身份证正面照");
                                    return null;
                                }
                                builder.add("idPositive", picPath);
                                break;
                            case HomePageContract.ID_CARD_BACK:
                                if (!StringTools.isStringValueOk(picPath)) {
                                    ToastUtils.toast(mContext, "请选择法人身份证反面照");
                                    return null;
                                }
                                builder.add("idSide", picPath);
                                break;
                            case HomePageContract.ID_CARD_HAND:
                                if (!StringTools.isStringValueOk(picPath)) {
                                    ToastUtils.toast(mContext, "请选择法人身份证手持照");
                                    return null;
                                }
                                builder.add("handPicture", picPath);
                                break;
                            case HomePageContract.SHOP_INNER_PICS:
                                if (!StringTools.isStringValueOk(picPath)) {
                                    ToastUtils.toast(mContext, "请选择店铺实景相片");
                                    return null;
                                }
                                builder.add("shopRealScene", picPath);
                                break;
                        }
                    }
                    break;
                case MultipleItem.ITEM_EDIT:
                    TextKeyValueBean textValueEditBean = (TextKeyValueBean) array
                            .getObject();
                    String textValue = textValueEditBean.getValue();
                    if (textValueEditBean.isImportant() && TextUtils.isEmpty(textValue)) {
                        String key = textValueEditBean.getKey();
                        ToastUtils.toast(mContext, "请输入" + key);
                        return null;
                    }
                    switch (textValueEditBean.getKey()) {
                        case HomePageContract.SHOP_NAME:
                            builder.add("name", textValue);
                            break;
                        case HomePageContract.COMMODITY_SEND_LEVEL:
                            builder.add("delivery", textValue);
                            commodityDetailBean.setDelivery(Double.valueOf(textValue).intValue());
                            break;
                        case HomePageContract.SEND_COMPANY:
                            builder.add("logisticsName", textValue);
                            break;
                        case HomePageContract.SEND_NO:
                            builder.add("logisticsNumber", textValue);
                            break;
                        case HomePageContract.COMMODITY_PROVIDER:
                            sourceBean.setSupplier(UserInfoManager.getShopId());
                            break;
                        case HomePageContract.COMMODITY_RESTOC_PERSON:
                            sourceBean.setPurchaseName(textValue);
                            break;
                        case HomePageContract.SEND_LINK:
                            builder.add("logisticsLink", textValue);
                            break;
                        case HomePageContract.SHOP_INTRODUCTION:
                            builder.add("introduction", textValue);
                            break;
                        case HomePageContract.ASSETS_WITHDRAW_PHONE:
                        case HomePageContract.SHOP_TEL:
                            builder.add("phoneNumber", textValue);
                            break;
                        case HomePageContract.ASSETS_WITHDRAW_BANK_CARD:
                            builder.add("bankCode", textValue);
                            break;
                        case HomePageContract.ASSETS_WITHDRAW_BANK:
                            builder.add("bankAddress", textValue);
                            break;
                        case HomePageContract.ASSETS_WITHDRAW_REAL_NAME:
                            builder.add("realName", textValue);
                            break;
                        case HomePageContract.ASSETS_WITHDRAW_IDCARD:
                            builder.add("idCode", textValue);
                            break;
                        case HomePageContract.COMMODITY_NAME:
                            commodityDetailBean.setName(textValue);
                            break;
                        case HomePageContract.COMMODITY_PRICE:
                            commodityDetailBean.setPrice("暂无".equals(textValue) ? Double.parseDouble("0.0") : Double.parseDouble(textValue));
                            break;
                    }
                    break;
                case MultipleItem.ITEM_TEXT:
                    TextKeyValueBean textBean = (TextKeyValueBean) array
                            .getObject();
                    String value = textBean.getValue();
                    if (textBean.isImportant() && TextUtils.isEmpty(value)) {
                        String key = textBean.getKey();
                        ToastUtils.toast(mContext, "请输入" + key);
                        return null;
                    }
                    switch (textBean.getKey()) {
                        case HomePageContract.SHOP_INTRODUCTION:
                            builder.add("introduction", value);
                            break;
                        case HomePageContract.SHOP_TEL:
                            builder.add("phoneNumber", value);
                            break;
                    }
                    break;

                case MultipleItem.ITEM_SELECT:
                    TextKeyValueBean textValueSelectBean = (TextKeyValueBean) array.getObject();
                    String textSelectIds = textValueSelectBean.getIds();
                    String textSelectValue = textValueSelectBean.getValue();

                    if (textValueSelectBean.isImportant() && !StringTools.isStringValueOk(textSelectValue)) {
                        ToastUtils.toast(mContext, "请选择" + textValueSelectBean.getKey());
                        return null;
                    }
                    switch (textValueSelectBean.getKey()) {
                        case HomePageContract.COMMODITY_UNIT:
                            builder.add("unit", textSelectValue);
                            commodityDetailBean.setUnit(textSelectValue);
                            break;
                        case HomePageContract.SHOP_CATEGORY:
                            builder.add("category", textSelectIds);
                            break;
                        case HomePageContract.SHOP_ORDER_START_TIME:
                            builder.add("startTime", textSelectValue);
                            break;
                        case HomePageContract.SHOP_ORDER_END_TIME:
                            builder.add("endTime", textSelectValue);
                            break;
                        case HomePageContract.COMMODITY_RESTOC_TIME:
                            sourceBean.setPurchaseTime(textSelectValue);
                            break;
                        case HomePageContract.COMMODITY_CATEGORY_NAME:
                            commodityDetailBean.setCategoryId(Integer.parseInt(textValueSelectBean.getIds()));
                            commodityDetailBean.setCategoryName(textSelectIds);
                            break;
                        case HomePageContract.COMMODITY_SORT:
                            commodityDetailBean.setShopClassifyId(Integer.parseInt(textValueSelectBean.getIds()));
                            commodityDetailBean.setShopClassifyName(textSelectIds);
                            break;
                    }
                    break;

            }
        }
        baseAdapterDataBean.setBuilder(builder);
        baseAdapterDataBean.setSourceBean(sourceBean);
        baseAdapterDataBean.setSellCommodityDetailBean(commodityDetailBean);
        return baseAdapterDataBean;
    }

    /**
     * 是否是详情
     *
     * @return
     */
    protected abstract boolean isDetail();

    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {
        showPicVideo(adapter, position);
    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {
        showPicVideo(adapter, position);
    }

    private void showPicVideo(BaseQuickAdapter adapter, int position) {
        List<BannerObject> bannerObjects = new ArrayList<>();
        List<BasePicVideoBean> arrays = adapter.getData();
        for (BasePicVideoBean pic : arrays) {
            if (pic.getType() == BasePicVideoBean.TYPE_VIDEO) {
                bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_VIDEO, new BannerObject.VideoBean(pic.getUrl(), "")));
            } else {
                bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, pic.getUrl()));
            }
        }
        DisplayPicAndVideosActivity.startPicVideoPlayActivity(mContext, bannerObjects, position);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPathMall.UPDATE_SHOP_INFO:
                ToastUtils.toast(mContext, "更改成功");
                if (editDialog != null) {
                    editDialog.releaseDialog();
                    editDialog = null;
                }
                break;
            case AppHttpPathMall.UPLOAD_ONE_PIC:
                List<String> paths = (List<String>) o;
                if (paths != null && !paths.isEmpty()) {
                    String firstPic = paths.get(0);
                    if (picBean != null && baseQuickAdapter != null) {
                        picBean.setPicPath(firstPic);
                        baseQuickAdapter.notifyItemChanged(currentPosition);
                    }
                }
                break;
            case AppHttpPathMall.ALL_COMMODITY_CATEGORY:
                ShopCommodityCategoryListBean categoryListBean = (ShopCommodityCategoryListBean) o;
                if (categoryListBean != null) {
                    List<ShopCommodityCategoryListBean.DataBean> arrays = categoryListBean.getData();
                    if (arrays != null && !arrays.isEmpty()) {
                        PickerManager.getInstance().showOptionPicker(mContext, arrays, new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                String name = arrays.get(options1).getShopClassifyName();
                                selectBean.setValue(name);
                                selectBean.setIds(String.valueOf(arrays.get(options1).getId()));
                                mSelectTv.setText(name);
                            }
                        });
                    } else {
                        showAlertDialogOfKnown("您还没有创建商品分类,请先添加商品分类", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(mContext, CommodityCategoryActivity.class));
                            }
                        });
                    }


                }

                break;
            case AppHttpPathMall.GET_ALL_COMMODITY_UNIT:
                CommodityUnitBean unitBean = (CommodityUnitBean) o;
                if (unitBean != null) {
                    List<CommodityUnitBean.DataBean> arrays = unitBean.getData();
                    if (arrays != null && !arrays.isEmpty()) {
                        PickerManager.getInstance().showOptionPicker(mContext, arrays, new PickerManager.OnOptionPickerSelectedListener() {
                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                                String name = arrays.get(options1).getName();
                                selectBean.setValue(name);
                                mSelectTv.setText(name);
                            }
                        });
                    } else {
                        showAlertDialogOfKnown("您还没有创建商品分类,请先添加商品分类", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(mContext, CommodityCategoryActivity.class));
                            }
                        });
                    }


                }

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (editDialog != null) {
            editDialog.releaseDialog();
        }
    }


}
