package com.example.module_nongfa_manager.home.shopManager;

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
    public void initView() {
        super.initView();
        mSearchContentSv.setQueryHint("请输入店铺名称");
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
    protected List<BaseTabBean> getTabTitles() {
        List<BaseTabBean> arrays = new ArrayList<>();
        arrays.add(new BaseTabBean("待审核"));
        arrays.add(new BaseTabBean("已通过"));
        arrays.add(new BaseTabBean("未通过"));
        return arrays;
    }

    @Override
    protected HomePresent createPresenter() {
        return null;
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
