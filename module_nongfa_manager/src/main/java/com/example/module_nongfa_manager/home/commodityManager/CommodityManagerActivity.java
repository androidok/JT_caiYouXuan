package com.example.module_nongfa_manager.home.commodityManager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.example.appbase.bean.BaseTabBean;
import com.example.module_nongfa_manager.home.HomePresent;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  商品管理
 * @date 2022/7/29 9:23
 */
public class CommodityManagerActivity extends BaseTabViewPageActivity<HomePresent> implements IView {

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
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_COMMODITY_MANAGER_LIST, mSearchContentSv.getQuery().toString().trim()));

    }

    @Override
    public void initData() {
        super.initData();
        mSearchContentSv.setQueryHint("请输入商品名称");

    }

    @Override
    protected String getTitleName() {
        return "商品管理";
    }

    @Override
    protected void onTabSelected(int i) {

    }


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        fragments.append(0, CommoditiesFragment.newInstance(1));
        fragments.append(1, CommoditiesFragment.newInstance(2));
        fragments.append(2, CommoditiesFragment.newInstance(3));

        return fragments;
    }

    @Override
    protected List<BaseTabBean> getTabTitles() {
        List<BaseTabBean> arrays = new ArrayList<>();
        arrays.add(new BaseTabBean("待审核"));
        arrays.add(new BaseTabBean("已审核"));
        arrays.add(new BaseTabBean("未通过"));
        return arrays;
    }
    @Override
    protected HomePresent createPresenter() {
        return new HomePresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }


}
