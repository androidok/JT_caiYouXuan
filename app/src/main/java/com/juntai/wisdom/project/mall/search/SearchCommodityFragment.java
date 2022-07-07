package com.juntai.wisdom.project.mall.search;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.app_basemodule.net.AppHttpPath;
import com.juntai.wisdom.project.mall.home.commodityfragment.BaseCommodityListFragment;

/**
 * @Author: tobato
 * @Description: 作用描述  首页的商品列表
 * @CreateDate: 2022/5/20 15:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/20 15:59
 */
public class SearchCommodityFragment extends BaseCommodityListFragment {
    private String key = "";


    public static SearchCommodityFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("label", type);
        SearchCommodityFragment fragment = new SearchCommodityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void getRvAdapterData() {
        startSearch(key);
    }



    public void startSearch(String key) {
        this.key = key;
        if (TextUtils.isEmpty(key)) {
            mSmartrefreshlayout.finishRefresh();
            return;
        }
        if (mPresenter != null) {
            mPresenter.startSearchCommodity(getBaseAppActivity().getBaseBuilderWithoutParama()
                    .add("key", key)
                    .add("type", "1").build(), AppHttpPath.SEARCH_COMMODITY
            );
        }


    }
}
