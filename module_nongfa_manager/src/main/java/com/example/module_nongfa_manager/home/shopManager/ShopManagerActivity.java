package com.example.module_nongfa_manager.home.shopManager;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.example.module_nongfa_manager.home.HomePresent;
import com.juntai.disabled.basecomponent.mvp.IView;
/**
 * @aouther tobato
 * @description 描述 店铺管理
 * @date 2022/7/29 9:45
 */
public class ShopManagerActivity extends BaseTabViewPageActivity<HomePresent> implements IView {

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
    protected String gettitleName() {
        return "店铺管理";
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
        return new String[]{"待审核", "已审核", "未通过"};
    }


    @Override
    protected HomePresent createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
