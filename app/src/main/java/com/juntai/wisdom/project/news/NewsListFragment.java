package com.juntai.wisdom.project.news;

import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.project.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.home.HomePageContract;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/19 10:43
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/19 10:43
 */
public class NewsListFragment extends BaseRecyclerviewFragment<NewsPresent> implements HomePageContract.IHomePageView {
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
        return new NewsListAdapter(null);
    }

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }
}
