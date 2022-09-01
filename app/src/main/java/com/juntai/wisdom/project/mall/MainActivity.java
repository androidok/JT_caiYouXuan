package com.juntai.wisdom.project.mall;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import com.baidu.location.BDLocation;
import com.example.appbase.base.customview.CustomViewPager;
import com.example.appbase.base.customview.MainPagerAdapter;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.base.BaseAppActivity;
import com.juntai.wisdom.project.mall.home.HomeFragment;
import com.juntai.wisdom.project.mall.mine.MyCenterFragment;
import com.juntai.wisdom.project.mall.shoppingCart.ShoppingCartFragment;

public class MainActivity extends BaseAppActivity<MainPagePresent> implements
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;

    private TabLayout mainTablayout;
    private String[] title = new String[]{"首页", "购物车", "个人中心"};
    private int[] tabDrawables = new int[]{R.drawable.home_index, R.drawable.cart_index, R.drawable.mine_index};
    private SparseArray<Fragment> mFragments = new SparseArray<>();
    //

    private int clickTimes = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mainViewpager != null) {
            mainViewpager.setCurrentItem(0);
            if (mainTablayout != null) {
                mainTablayout.getTabAt(0).select();
            }
        }
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        // : 2022/7/13 mob的隐私授权 不能删
        HawkProperty.clearRedPoint(mContext.getApplicationContext());
        initToolbarAndStatusBar(false);
        mainViewpager = findViewById(R.id.main_viewpager);
        mainTablayout = findViewById(R.id.main_tablayout);
        mainLayout = findViewById(R.id.main_layout);
        mainViewpager.setScanScroll(false);
        mFragments.append(0, new HomeFragment());//
//        mFragments.append(1, new LiveFragment());//
        mFragments.append(1, new ShoppingCartFragment());//
        mFragments.append(2, new MyCenterFragment());//
        mainViewpager.setOffscreenPageLimit(3);
        initTab();
    }


    @Override
    public void initData() {
    }


    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, tabDrawables,
                mFragments);
        mainViewpager.setAdapter(adapter);
        /*viewpager切换监听，包含滑动点击两种*/
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = mainTablayout.newTab();
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i, false));
                mainTablayout.addTab(tab);
            }
        }

        mainTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mainViewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (0 == tab.getPosition()) {
                    // : 2022/5/23 刷新首页列表
                    EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_HOMEPAGE_COMMODITY_LIST, ""));

                }

            }
        });

        /*viewpager切换默认第一个*/
        mainViewpager.setCurrentItem(0);
    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {

    }


    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.RE_LOAD:
                reLogin(UserInfoManager.getPhoneNumber());
                break;
            case EventBusObject.UNREAD_MSG_AMOUNT:
                int amount = (int) eventBusObject.getEventObj();
                adapter.setUnReadMsg(amount);
                break;
            default:
                break;
        }
    }

    @Override
    protected MainPagePresent createPresenter() {
        return new MainPagePresent();
    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

        EventManager.getEventBus().post(new EventBusObject(EventBusObject.ON_LOCATION_RECEIVED, bdLocation));
    }

    @Override
    public boolean requestLocation() {
        return true;
    }


    @Override
    public void onBackPressed() {
        clickTimes++;
        if (2 == clickTimes) {
            ActivityManagerTool.getInstance().finishApp();
        } else {
            ToastUtils.toast(mContext, "再点一次退出菜优选");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);//休眠2秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clickTimes = 0;
                }
            }.start();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
