package com.example.appbase.base.multi;

import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
import com.example.appbase.bean.multiBean.ItemFragmentBean;
import com.example.appbase.bean.multiBean.MultiPicBean;
import com.example.appbase.bean.nong_fa_manager.ShopManagerDetailBean;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.MultipleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class MultiPresent extends BaseAppPresent<IModel, IView> {
    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 商品审核
     *
     * @return
     */
    public List<MultipleItem> checkCommodity(CommoditySourceDetailBean.DataBean bean, boolean isDetail) {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_NAME, UserInfoManager.getShopName()
                , true, 0, isDetail);

        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CLASSIFY_NAME, ""
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CATEGORY_NAME, ""
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_NAME, ""
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_PRICE, ""
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.CREAT_TIME, ""
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_DES, ""
                , true, 1, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.COMMODITY_PIC_VIDEO, false)));
        // TODO: 2022/7/29 fragment
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_PROVIDER, UserInfoManager.getShopName()
                , true, 0, isDetail);

        initTextSelectType(arrays, MultiContact.COMMODITY_RESTOC_TIME, "0", bean == null ? "" : bean.getPurchaseTime(), true,true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_RESTOC_PERSON, ""
                , true, 0, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.COMMODITY_BILL, false)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.IS_AGREE, false)));
        return arrays;
    }
    /**
     * 审核店铺
     *
     * @return
     */
    public List<MultipleItem> checkShop(ShopManagerDetailBean.DataBean bean, boolean isDetail) {
        List<MultipleItem> arrays = new ArrayList<>();
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.REAL_NAME, bean == null ? "" :
                        bean.getRealName()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.IDCARD, bean == null ? "" :
                        bean.getIdCode()
                , true, 0, isDetail);
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_BIG, "店家资料"));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_FRONT, 1, bean == null ? "" :
                        bean.getBusinessLicense())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_BACK, 2, bean == null ? "" :
                        bean.getIdPositive())));
        arrays.add(new MultipleItem(MultipleItem.ITEM_PIC,
                new MultiPicBean(MultiContact.ID_CARD_HAND, 3, bean == null ? "" :
                        bean.getIdSide())));



        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.USER_ACCOUNT, bean == null ? "" :
                        bean.getUserAccount()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_NAME, bean == null ? "" :
                        bean.getName()
                , true, 0, isDetail);

        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_TEL, bean == null ? "" :
                        bean.getPhoneNumber()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_CATEGORY, bean == null ? "" :
                        bean.getCategory()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_ADDR, bean == null ? "" :
                        bean.getGpsAddress()
                , true, 0, isDetail);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.SHOP_CREAT_TIME, bean == null ? "" :
                        bean.getCreateTime()
                , true, 0, isDetail);


        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_BIG, "店铺资料"));

        List<String> fragmentPics = new ArrayList<>();
        if (bean != null) {
            addFragmentPics(bean.getHeadPortrait(), fragmentPics);
            addFragmentPics(bean.getBusinessLicense(), fragmentPics);
            addFragmentPics(bean.getShopRealScene(), fragmentPics);
        }
        arrays.add(new MultipleItem(MultipleItem.ITEM_FRAGMENT, new ItemFragmentBean(3, isDetail ?
                fragmentPics.size() : 3,
                3, 1, true,
                fragmentPics)));
        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.IS_AGREE, false)));
        return arrays;
    }
    private void addFragmentPics(String picPath, List<String> fragmentPics) {
        fragmentPics.add(picPath);
//        if (!TextUtils.isEmpty(picPath)) {
//            if (picPath.contains(BaseInspectionActivity.SDCARD_TAG)) {
//                fragmentPics.add(picPath);
//            } else {
//                if (picPath.contains(AppHttpPath.BASE_IMAGE_THUM)) {
//                    fragmentPics.add(picPath);
//                } else {
//                    fragmentPics.add(UrlFormatUtil.getImageThumUrl(picPath));
//                }
//
//            }
//
//        }
    }

}
