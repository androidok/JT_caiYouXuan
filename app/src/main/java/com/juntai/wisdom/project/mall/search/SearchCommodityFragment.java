package com.juntai.wisdom.project.mall.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.home.commodityfragment.BaseCommodityListFragment;
import com.juntai.wisdom.project.mall.home.commodityfragment.CommodityListAdapter;
import com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.CommodityDetailActivity;

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
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new CommodityListAdapter(R.layout.shop_commodity_list);
    }

    @Override
    protected void itemClick(BaseQuickAdapter adapter, int position) {
        CommodityBean bean = (CommodityBean) adapter.getItem(position);
        startActivityForResult(new Intent(mContext, CommodityDetailActivity.class).putExtra(BaseActivity.BASE_ID, bean.getId()), BaseActivity.BASE_REQUEST_RESULT);

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
            mPresenter.startSearchCommodity(getBaseBuilderWithoutParama()
                    .add("key", key)
                    .add("type", "1").build(), AppHttpPath.SEARCH_COMMODITY
            );
        }


    }
}
