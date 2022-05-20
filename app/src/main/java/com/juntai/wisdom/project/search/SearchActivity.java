package com.juntai.wisdom.project.search;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.juntai.wisdom.project.base.BaseTabViewPageActivity;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.home.HomePagePresent;
/**
 * @aouther tobato
 * @description 描述  首页搜索
 * @date 2022/5/20 14:27
 */
public class SearchActivity extends BaseTabViewPageActivity<HomePagePresent> implements HomePageContract.IHomePageView  {

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }


    @Override
    protected int getTabMode() {
        return 0;
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

    }

    @Override
    protected String getTitleName() {
        return null;
    }

    @Override
    protected SparseArray<Fragment> getFragments() {
        return null;
    }

    @Override
    protected String[] getTabTitles() {
        return new String[0];
    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
