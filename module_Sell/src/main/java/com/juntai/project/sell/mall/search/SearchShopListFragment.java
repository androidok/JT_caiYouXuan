package com.juntai.project.sell.mall.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.ShopListDataBean;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.base.BaseRecyclerviewFragment;
import com.juntai.project.sell.mall.home.HomePageContract;
import com.juntai.project.sell.mall.home.HomePagePresent;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class SearchShopListFragment extends BaseRecyclerviewFragment<HomePagePresent> implements HomePageContract.IHomePageView {

    private int labelId;

    public static SearchShopListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("label", type);
        SearchShopListFragment fragment = new SearchShopListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        labelId = getArguments().getInt("label");


    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new SearchShopAdapter(R.layout.sell_mall_collect_shop_item);
    }

    @Override

    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter.setEmptyView(getBaseAppActivity().getAdapterEmptyView("一个店铺也没有-_-",-1));

    }

    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);
        if (EventBusObject.REFRESH_SEARCH_SHOP_LIST.equals(eventBusObject.getEventKey())) {// : 2022/5/20 搜索中的店铺列表
            String key = (String) eventBusObject.getEventObj();
            startSearch(key);
        }
    }

    @Override
    protected void getRvAdapterData() {
        startSearch(null);

    }

    private void startSearch(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (mPresenter != null) {
            mPresenter.startSearchShop(getBaseAppActivity().getBaseBuilderWithoutParama()
                    .add("key", key)
                    .add("type", "2").build(), AppHttpPathMall.SEARCH_COMMODITY
            );
        }


    }


    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPathMall.SEARCH_SHOP:
                ShopListDataBean shopListDataBean = (ShopListDataBean) o;
                if (shopListDataBean != null) {
                    ShopListDataBean.DataBean dataBean = shopListDataBean.getData();
                    if (dataBean != null) {
                        List<ShopListDataBean.DataBean.ListBean> data = dataBean.getList();
                        setData(data, dataBean.getTotalCount());
                    }
                }
                break;
            default:
                break;
        }
    }
}
