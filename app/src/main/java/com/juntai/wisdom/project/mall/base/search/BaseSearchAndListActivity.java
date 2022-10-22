package com.juntai.wisdom.project.mall.base.search;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewActivity;
import com.juntai.wisdom.project.mall.utils.StringTools;

/**
 * @aouther tobato
 * @description 描述   这是在当前页面搜索
 * @date 2021-10-09 10:21
 */
public abstract class BaseSearchAndListActivity<P extends BasePresenter> extends BaseRecyclerviewActivity<P> {

    public SearchView mSearchContentSv;

    @Override
    protected void getRvAdapterData() {
        if (mSearchContentSv != null) {
            startSearch(mSearchContentSv.getQuery().toString().trim());
        }
    }
    @Override
    protected RecyclerView.LayoutManager getBaseAdapterManager() {
        return null;
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
    public int getLayoutView() {
        return R.layout.activity_base_search_and_list;
    }

    @Override
    public void initView() {
        super.initView();
        setTitleName(getTitleName());
        mSearchContentSv = (SearchView) findViewById(R.id.search_content_sv);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) mSearchContentSv.findViewById(R.id.search_src_text);
        textView.setTextSize(12);
        mSearchContentSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!StringTools.isStringValueOk(mSearchContentSv.getQuery().toString().trim())) {
//                    ToastUtils.warning(mContext, "请输入要搜索的内容");
//                    return;
//                }
//                startSearch(mSearchContentSv.getQuery().toString().trim());
            }
        });
        mSearchContentSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if (!StringTools.isStringValueOk(s)) {
//                    ToastUtils.warning(mContext, "请输入要搜索的内容");
//                    return false;
//                }
                // 调用搜索接口
                startSearch(mSearchContentSv.getQuery().toString().trim());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!StringTools.isStringValueOk(s)) {
                    startSearch(mSearchContentSv.getQuery().toString().trim());
                }
                return false;
            }
        });
    }

    protected abstract void startSearch(String s);

    protected abstract String getTitleName();



}
