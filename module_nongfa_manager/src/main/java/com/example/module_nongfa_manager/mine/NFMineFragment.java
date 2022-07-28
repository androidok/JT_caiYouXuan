package com.example.module_nongfa_manager.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.BaseRecyclerviewFragment;
import com.example.module_nongfa_manager.R;
import com.juntai.disabled.basecomponent.mvp.IPresenter;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class NFMineFragment extends BaseRecyclerviewFragment {
    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {

    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
    }
    @Override
    protected View getAdapterHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nf_manager_home_head,null);
        return view;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }
}
