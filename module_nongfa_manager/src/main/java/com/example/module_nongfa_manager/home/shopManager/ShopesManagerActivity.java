package com.example.module_nongfa_manager.home.shopManager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.example.module_nongfa_manager.home.HomePresent;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;

/**
 * @aouther tobato
 * @description 描述 店铺管理
 * @date 2022/7/29 9:45
 */
public class ShopesManagerActivity extends BaseTabViewPageActivity<HomePresent> implements IView {

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
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_SHOP_MANAGER_LIST, mSearchContentSv.getQuery().toString().trim()));

    }

    @Override
    protected String getTitleName() {
            return "店铺管理";
        }
    @Override
    protected void onTabSelected(int i) {

    }


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        fragments.append(0, ShopesFragment.newInstance(1));
        fragments.append(1, ShopesFragment.newInstance(2));
        fragments.append(2, ShopesFragment.newInstance(3));

        return fragments;
    }

    @Override
    protected String[] getTabTitles() {
        return new String[]{"待审核", "已通过", "未通过"};
    }


    @Override
    protected HomePresent createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
