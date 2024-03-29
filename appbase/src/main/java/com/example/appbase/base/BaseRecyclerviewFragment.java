package com.example.appbase.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  只有一个recyclerview的fragment
 * @CreateDate: 2021/4/29 16:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/29 16:16
 */
public abstract class BaseRecyclerviewFragment<P extends IPresenter> extends BaseAppModuleFragment<P> {
    protected RecyclerView mRecyclerview;
    protected SmartRefreshLayout mSmartrefreshlayout;
    protected BaseQuickAdapter baseQuickAdapter;
    protected LinearLayoutManager linearLayoutManager;


    @Override
    protected int getLayoutRes() {
        return R.layout.recycleview_layout;
    }

    @Override
    protected void initView() {
        if (0 == getLayoutRes()) {
            return;
        }
        mRecyclerview = (RecyclerView) getView(R.id.recyclerview);
        mSmartrefreshlayout = (SmartRefreshLayout) getView(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableRefresh(enableRefresh());
        mSmartrefreshlayout.setEnableLoadMore(enableLoadMore());
        baseQuickAdapter = getBaseQuickAdapter();
        linearLayoutManager = getBaseAdapterManager() == null ? new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) : getBaseAdapterManager();
        if (baseQuickAdapter != null) {
            if (getAdapterHeadView() != null) {
                baseQuickAdapter.addHeaderView(getAdapterHeadView());
            }
            if (getAdapterFootView() != null) {
                baseQuickAdapter.addFooterView(getAdapterFootView());
            }
            mRecyclerview.setLayoutManager(linearLayoutManager);
            mRecyclerview.setAdapter(baseQuickAdapter);
            mSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshLayout) {
                    page = 1;
                    getRvAdapterData();
                }
            });
            mSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshLayout) {
                    page++;
                    getRvAdapterData();
                }
            });

        }



    }
    protected abstract View getAdapterHeadView();
    protected abstract View getAdapterFootView();

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        getRvAdapterData();
    }

    protected abstract LinearLayoutManager getBaseAdapterManager();


    protected abstract void getRvAdapterData();


    protected abstract boolean enableRefresh();

    protected abstract boolean enableLoadMore();

    protected abstract BaseQuickAdapter getBaseQuickAdapter();

    @Override
    public void onSuccess(String tag, Object o) {
        if (enableLoadMore()) {
            mSmartrefreshlayout.finishLoadMore();
        }
        if (enableRefresh()) {
            mSmartrefreshlayout.finishRefresh();
        }
//        getBaseActivity().getViewFocus(mRecyclerview);
    }


    @Override
    public void onError(String tag, Object o) {
        super.onError(tag, o);
        if (enableLoadMore()) {
            mSmartrefreshlayout.finishLoadMore();
        }
        if (enableRefresh()) {
            mSmartrefreshlayout.finishRefresh();
        }
    }

    public void setData(List data, int totalAmount) {
        boolean isEnd = false;
        final int size = data == null ? 0 : data.size();
        if (page == 1) {
            baseQuickAdapter.setNewData(data);
            mRecyclerview.scrollToPosition(0);
        } else {
            if (size > 0) {
                baseQuickAdapter.addData(data);
            }
        }
        if (baseQuickAdapter.getData().size()==totalAmount) {
            isEnd = true;
        }
        if (enableLoadMore()) {
            if (isEnd) {
                mSmartrefreshlayout.setNoMoreData(true);
            } else {
                mSmartrefreshlayout.setNoMoreData(false);
            }
        }

    }

    @Override
    protected void lazyloadGone() {

    }
}
