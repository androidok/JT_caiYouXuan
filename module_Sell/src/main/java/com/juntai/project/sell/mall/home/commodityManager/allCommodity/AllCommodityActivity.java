package com.juntai.project.sell.mall.home.commodityManager.allCommodity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.project.sell.mall.beans.sell.ShopCommodityManagerListBean;
import com.juntai.project.sell.mall.home.HomePageContract;
import com.juntai.project.sell.mall.home.commodityManager.allCommodity.editCommodity.AddCommodityActivity;
import com.juntai.project.sell.mall.home.shop.ShopPresent;

/**
 * @aouther tobato
 * @description 描述 店铺内所有商品 管理
 * @date 2022/6/12 14:44
 */
public class AllCommodityActivity extends BaseTabViewPageActivity<ShopPresent> implements HomePageContract.IHomePageView {
    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    protected int getTabMode() {
        return TabLayout.MODE_FIXED;
    }

    @Override
    protected int getTabHeadLayout() {
        return 0;
    }

    @Override
    protected int getTabFootLayout() {
        return 0;
    }

    @Override
    protected void commitSearch(String s) {
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_COMMODITY_LIST, s));

    }

    @Override
    protected String getTitleName() {
        return "商品管理";
    }

    @Override
    public void initData() {
        super.initData();
        getTitleRightTv().setText("添加商品");
        mSearchContentSv.setQueryHint("请输入商品名称");
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // : 2022/6/12 添加商品
                startActivity(new Intent(mContext, AddCommodityActivity.class));
            }
        });
    }

    @Override
    protected void onTabSelected(int i) {

    }

    @Override
    public void onEvent(EventBusObject eventBusObject) {
       switch (eventBusObject.getEventKey()) {
           case EventBusObject.SET_RED_POINT:
               ShopCommodityManagerListBean.DataBean dataBean = (ShopCommodityManagerListBean.DataBean) eventBusObject.getEventObj();
               setRedPoint(0,dataBean.getOnSale());


               break;
           default:
               break;
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideKeyboard(mBaseRootCol);
    }

    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> arrays = new SparseArray<>();
        arrays.append(0, ShopManagerCommodityFragment.getInstance(0));
        arrays.append(1, ShopManagerCommodityFragment.getInstance(1));
        return arrays;
    }

    @Override
    protected String[] getTabTitles() {
        return new String[]{"已上架商品", "待上架商品"};
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }


}
