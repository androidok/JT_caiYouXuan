package com.example.appbase.base.multi;

import com.example.appbase.base.BaseAppPresent;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.bean.multiBean.ImportantTagBean;
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

        initTextSelectType(arrays, MultiContact.COMMODITY_RESTOC_TIME, "0", bean == null ? "" : bean.getPurchaseTime(), true);
        initTextType(arrays, MultipleItem.ITEM_EDIT, MultiContact.COMMODITY_RESTOC_PERSON, ""
                , true, 0, isDetail);

        arrays.add(new MultipleItem(MultipleItem.ITEM_TITILE_SMALL, new ImportantTagBean
                (MultiContact.COMMODITY_BILL, false)));
        initRadioType(arrays, MultiContact.IS_AGREE, new String[]{"是", "否"}, 0, true);
        return arrays;
    }


}
