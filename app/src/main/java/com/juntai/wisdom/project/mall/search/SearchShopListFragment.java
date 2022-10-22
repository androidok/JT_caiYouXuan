package com.juntai.wisdom.project.mall.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.ShopListDataBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.HomePagePresent;
import com.juntai.wisdom.project.mall.home.shop.ShopActivity;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class SearchShopListFragment extends BaseRecyclerviewFragment<HomePagePresent> implements HomePageContract.IHomePageView {


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


    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new SearchShopAdapter(R.layout.mall_collect_shop_item);
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
        baseQuickAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView("一个店铺也没有-_-",-1));

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopListDataBean.DataBean.ListBean bean = (ShopListDataBean.DataBean.ListBean) adapter.getItem(position);
                startActivityForResult(new Intent(mContext, ShopActivity.class).putExtra(BaseActivity.BASE_ID, bean.getId()), BaseActivity.BASE_REQUEST_RESULT);

            }
        });
    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }


    @Override
    protected void getRvAdapterData() {
        startSearch(null);

    }

    public void startSearch(String key) {
        if (TextUtils.isEmpty(key)) {
            mSmartrefreshlayout.finishRefresh();
            return;
        }
        if (mPresenter != null) {
            mPresenter.startSearchShop(getBaseBuilderWithoutParama()
                    .add("key", key)
                    .add("type", "2").build(), AppHttpPath.SEARCH_SHOP
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
            case AppHttpPath.SEARCH_SHOP:
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
