package com.juntai.project.sell.mall.order.allOrder;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.example.appbase.bean.BaseTabBean;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.project.sell.mall.SellMainActivity;
import com.juntai.project.sell.mall.home.HomePageContract;
import com.juntai.project.sell.mall.order.OrderPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 所有订单
 * @date 2022/5/12 9:31
 */
public class OrderManagerActivity extends BaseTabViewPageActivity<OrderPresent> implements HomePageContract.IHomePageView {
    private int enterType;
    private int tabPosition;
    protected TextView orderTypeTv;
    private PopupWindow popupWindow;


    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        enterType = intent.getIntExtra(BASE_ID, 0);
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, mSearchContentSv.getQuery().toString().trim()));

    }

    @Override
    public void initView() {
        enterType = getIntent().getIntExtra(BASE_ID, 0);
        tabPosition = getIntent().getIntExtra(BASE_ID2, 0);
        super.initView();
        mTabTb.setVisibility(View.VISIBLE);
        mSearchContentSv.setQueryHint("请输入订单编号、商品名称或店铺名称");
    }

    @Override
    public void initData() {
        super.initData();
        mViewpageVp.setCurrentItem(tabPosition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow=null;
        }
    }

    @Override
    protected void onTabSelected(int i) {

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
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, mSearchContentSv.getQuery().toString().trim()));

    }

    @Override
    protected String getTitleName() {
        return "订单管理";
    }


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        fragments.append(0, OrderListFragment.newInstance(-1));
        fragments.append(1, OrderListFragment.newInstance(1));
        fragments.append(2, OrderListFragment.newInstance(5));

        return fragments;
    }

    @Override
    protected List<BaseTabBean> getTabTitles() {
        List<BaseTabBean> arrays = new ArrayList<>();
        arrays.add(new BaseTabBean(ORDER_ALL));
        arrays.add(new BaseTabBean(ORDER_SEND));
        arrays.add(new BaseTabBean(ORDER_FINISHED));
        return arrays;
    }
    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onBackPressed() {
        if (0 == enterType) {
            // : 2022/5/12   跳到首页
            startActivity(new Intent(mContext, SellMainActivity.class));
        } else {
            super.onBackPressed();
        }
    }
}
