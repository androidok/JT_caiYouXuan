package com.juntai.project.sell.mall;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baidu.location.BDLocation;
import com.example.appbase.base.customview.CustomViewPager;
import com.example.appbase.base.customview.MainPagerAdapter;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.project.sell.mall.base.BaseAppActivity;
import com.orhanobut.hawk.Hawk;

@Route(path = ARouterPath.sellMain)
public class SellMainActivity extends BaseAppActivity<MainPagePresent> implements
        View.OnClickListener, MainPageContract.IMainPageView {
    private MainPagerAdapter adapter;
    private LinearLayout mainLayout;
    private CustomViewPager mainViewpager;

    private TabLayout mainTablayout;
    private String[] title = new String[]{"首页", "消息", "个人中心"};
    private int[] tabDrawables = new int[]{R.drawable.home_index,  R.drawable.notice_index,  R.drawable.mine_index};
    private SparseArray<Fragment> mFragments = new SparseArray<>();
    //
    private int clickTimes = 0;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mainViewpager != null) {
            mainViewpager.setCurrentItem(0);
        }
    }

    @Override
    public int getLayoutView() {
        return R.layout.sell_activity_main;
    }

    @Override
    public void initView() {
        HawkProperty.clearRedPoint(mContext.getApplicationContext());

        initToolbarAndStatusBar(false);
//        mainViewpager = findViewById(R.id.main_viewpager);
//        mainTablayout = findViewById(R.id.main_tablayout);
//        mainLayout = findViewById(R.id.main_layout);
//        mainViewpager.setScanScroll(false);
//        mFragments.append(0, new HomeShopFragment());//
//        mFragments.append(1, new NewsListFragment());//
//        mFragments.append(2, new MyCenterFragment());//
//        mainViewpager.setOffscreenPageLimit(5);
//        initTab();
        //检测密码是否为弱口令
        if (Hawk.get(HawkProperty.SP_KEY_PWD)!=null&&!PubUtil.checkPwdMark(Hawk.get(HawkProperty.SP_KEY_PWD))) {
            showAlertDialog("检测到当前账号的密码过于简单,需要重新设置复杂密码", "前往更改", "下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityManagerTool.getInstance().startToModifyPwd(UserInfoManager.getPhoneNumber());
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }


    @Override
    public void initData() {
        update(false);
    }


    public void initTab() {
        adapter = new MainPagerAdapter(getSupportFragmentManager(), getApplicationContext(), title, tabDrawables,
                mFragments);
        mainViewpager.setAdapter(adapter);
        mainViewpager.setOffscreenPageLimit(5);
        /*viewpager切换监听，包含滑动点击两种*/
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = mainTablayout.newTab();
            if (tab != null) {
                if (i == 1) {
                    tab.setCustomView(adapter.getTabView(i, true));
                } else {
                    tab.setCustomView(adapter.getTabView(i, false));
                }
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
                reLogin(UserInfoManager.getAccount());
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
