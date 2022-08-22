package com.juntai.wisdom.project.mall.order.allOrder;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;

import com.example.appbase.base.BaseTabViewPageActivity;
import com.example.appbase.bean.BaseTabBean;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.order.OrderPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 所有订单
 * @date 2022/5/12 9:31
 */
public class AllOrderActivity extends BaseTabViewPageActivity<OrderPresent> implements HomePageContract.IHomePageView {
    //0代表支付成功之后  1代表个人中心进入
    private int enterType;
    private int tabPosition;



    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        enterType = intent.getIntExtra(BASE_ID, 100);
        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, mSearchContentSv.getQuery().toString().trim()));

    }

    @Override
    public void initView() {
        enterType = getIntent().getIntExtra(BASE_ID, 0);
        tabPosition = getIntent().getIntExtra(BASE_ID2, 0);
        super.initView();
        if (2==tabPosition) {
            mTabTb.setVisibility(View.GONE);
        }else {
            mTabTb.setVisibility(View.VISIBLE);
        }
        mSearchContentSv.setQueryHint("搜索订单编号、商品名称或店铺名称");
    }

    @Override
    public void initData() {
        super.initData();
        mViewpageVp.setCurrentItem(tabPosition);
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
        if (2== tabPosition) {
            return "退款/售后";
        }
        return "全部订单";
    }


    @Override
    protected SparseArray<Fragment> getFragments() {
        SparseArray<Fragment> fragments = new SparseArray<>();
        if (2== tabPosition) {
            fragments.append(0, OrderListFragment.newInstance(4));
        }else {
            fragments.append(0, OrderListFragment.newInstance(-1));
            fragments.append(1, OrderListFragment.newInstance(1));
            fragments.append(2, OrderListFragment.newInstance(4));
        }

        return fragments;
    }

    @Override
    protected List<BaseTabBean> getTabTitles() {
        List<BaseTabBean> arrays = new ArrayList<>();

        if (2== tabPosition) {
            arrays.add(new BaseTabBean(""));
        }else {
            arrays.add(new BaseTabBean(ORDER_TOTAL));
            arrays.add(new BaseTabBean(ORDER_SEND));
            arrays.add(new BaseTabBean(ORDER_BACK));

        }
        return arrays;
    }
    @Override
    public void onSuccess(String tag, Object o) {

    }

}
