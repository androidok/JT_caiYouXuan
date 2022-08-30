package com.juntai.wisdom.project.mall.home.shop;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.appbase.bean.ShopCommodityListBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.wisdom.project.mall.base.search.BaseSearchActivity;
import com.juntai.wisdom.project.mall.home.HomePageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  搜索店铺内商品
 * @date 2022/5/31 16:59
 */
public class SearchShopCommodityActivity extends BaseSearchActivity<ShopPresent> implements HomePageContract.IHomePageView {


    private int shopId;

    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        shopId = getIntent().getIntExtra(BASE_ID,0);
        baseQuickAdapter.setEmptyView(getAdapterEmptyView("没有搜索到相关商品",-1));
    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected boolean commitSearch(String s) {
        mPresenter.getShopCommodityList(getBaseBuilderWithoutParama()
                .add("shopId", String.valueOf(shopId))
                .add("page",String.valueOf(page))
                .add("limit",String.valueOf(limit))
                .add("key", s).build(), AppHttpPath.SHOP_COMMODITY_LIST);
        return true;
    }

    @Override
    protected int getSearchType() {
        return 0;
    }

    @Override
    protected void onAdapterItemClick(BaseQuickAdapter adapter, int position) {
        MultipleItem multipleItem = (MultipleItem) adapter.getItem(position);
        CommodityBean commodityBean = (CommodityBean) multipleItem.getObject();
        // : 2022/5/10 跳转到商品详情
        startToCommodityDetail(commodityBean.getId());
    }

    @Override
    protected void getRvAdapterData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPath.SHOP_COMMODITY_LIST:

                ShopCommodityListBean shopCommodityListBean = (ShopCommodityListBean) o;
                if (shopCommodityListBean != null) {
                    ShopCommodityListBean.DataBean dataBean = shopCommodityListBean.getData();
                    if (dataBean != null) {
                        List<CommodityBean> arrays = dataBean.getList();
                        if (arrays != null) {
                            List<MultipleItem> multipleItems = new ArrayList<>();
                            for (CommodityBean array : arrays) {
                                multipleItems.add(new MultipleItem(MultipleItem.ITEM_COMMODITY,array));
                            }
                           setData(multipleItems,dataBean.getTotalCount());
                        }
                    }

                }

                break;
            default:
                break;
        }
    }
}
