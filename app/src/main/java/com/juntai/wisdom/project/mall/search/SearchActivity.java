package com.juntai.wisdom.project.mall.search;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;

import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseTabViewPageActivity;
import com.juntai.wisdom.project.mall.base.search.BaseSearchHeadFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.HomePagePresent;
import com.juntai.wisdom.project.mall.live.LiveCommodityListFragment;

/**
 * @aouther tobato
 * @description 描述  首页搜索
 * @date 2022/5/20 14:27
 */
public class SearchActivity extends BaseTabViewPageActivity<HomePagePresent> implements HomePageContract.IHomePageView, BaseSearchHeadFragment.OnSearchCallBack {

    private SearchCommodityFragment searchCommodityFragment;
    private HomeSearchHeadFragment searchHeadFragment;
    private SearchShopListFragment searchShopListFragment;
    private LiveCommodityListFragment searchLiveListFragment;
    private int type;

    /**
     *
     * @param mContext
     * @param type  0是首页  1是直播
     */
    public static void  startSearchActivity(Context mContext ,int type){
        Intent intent = new Intent(mContext,SearchActivity.class);
        intent.putExtra(BASE_ID,type);
        mContext.startActivity(intent);
    }



    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(BASE_ID,0);
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        mTabTb.setVisibility(View.GONE);
        mViewpageVp.setVisibility(View.GONE);
        mSearchLl.setVisibility(View.GONE);
        initToolbarAndStatusBar(false);
        setMargin(mTabHeadFl,0,20,0,0);
        searchHeadFragment = (HomeSearchHeadFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_serach_top);

    }

    @Override
    protected void onTabSelected(int i) {
        if (type==0) {
            switch (i) {
                case 0:
                    //刷新商品列表
                    searchCommodityFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;
                case 1:
                    //刷新店铺列表
                    searchShopListFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;
                case 2:
                    //刷新直播列表
                    searchLiveListFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;
                default:
                    break;
            }
        }else {
            switch (i) {
                case 0:
                    //刷新直播列表
                    searchLiveListFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;
                case 1:
                    //刷新商品列表
                    searchCommodityFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;
                case 2:
                    //刷新店铺列表
                    searchShopListFragment.startSearch(searchHeadFragment.getSearchContent());
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    protected int getTabMode() {
        return TabLayout.MODE_FIXED;
    }

    @Override
    protected int getTabHeadLayout() {
        return R.layout.fragment_search_head;
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
        SparseArray<Fragment> fragments = new SparseArray<>();
        searchCommodityFragment = SearchCommodityFragment.newInstance(0);
        searchShopListFragment = SearchShopListFragment.newInstance(1);
        searchLiveListFragment = LiveCommodityListFragment.newInstance(2);
        if (0==type) {
            fragments.append(0, searchCommodityFragment);
            fragments.append(1, searchShopListFragment);
            fragments.append(2, searchLiveListFragment);
        }else {
            fragments.append(0, searchLiveListFragment);
            fragments.append(1, searchCommodityFragment);
            fragments.append(2, searchShopListFragment);
        }

        return fragments;
    }

    @Override
    protected String[] getTabTitles() {
        if (0==type) {
            return new String[]{COMMODITY, SHOP,LIVE};
        }
        return new String[]{LIVE, COMMODITY,SHOP};

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onSearch(String s) {
        if (!TextUtils.isEmpty(s)) {
            mTabTb.setVisibility(View.VISIBLE);
            mViewpageVp.setVisibility(View.VISIBLE);
            if (0==type) {
                searchCommodityFragment.startSearch(s);
            }else {
                searchLiveListFragment.startSearch(s);
            }
        } else {
            mTabTb.setVisibility(View.GONE);
            mViewpageVp.setVisibility(View.GONE);

        }

    }
}
