package com.juntai.wisdom.project.mall.home.shop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.appbase.bean.CommodityDesListBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.search.BaseSearchAndListActivity;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.HomePagePresent;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  店铺列表
 * @date 2022/9/8 14:13
 */
public class ShopListActivity extends BaseSearchAndListActivity<HomePagePresent> implements HomePageContract.IHomePageView {

    public static void startShopListActivity(Context mContext, String title, int categoryId) {
        Intent intent = new Intent(mContext, ShopListActivity.class);
        intent.putExtra(BASE_STRING, title);
        intent.putExtra(BASE_ID, categoryId);
        mContext.startActivity(intent);
    }

    @Override
    protected RecyclerView.LayoutManager getBaseAdapterManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected void startSearch(String s) {

    }

    @Override
    protected void getRvAdapterData() {
        mSearchContentSv.setVisibility(View.GONE);

        int labelId = getIntent().getIntExtra(BASE_ID, 0);
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("page", String.valueOf(page))
                .add("limit", String.valueOf(limit));
        mPresenter.getCommodityRecommendList(0 == labelId ? builder.build() : builder
                .add("categoryId", String.valueOf(labelId)).build(), AppHttpPath.COMMODIFY_RECOMMEND);

    }

    @Override
    public void initData() {
        super.initData();
        mSmartrefreshlayout.setBackgroundResource(R.color.gray_lighter);
        mRecyclerview.setBackgroundResource(0);
        mRecyclerview.setPadding(20,20,20,0);
        baseQuickAdapter.setEmptyView(getAdapterEmptyView("没有找到相关的店铺~_~",-1));
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityBean bean = (CommodityBean) adapter.getItem(position);
                startActivityForResult(new Intent(mContext, ShopActivity.class).putExtra(BaseActivity.BASE_ID, bean.getId()), BaseActivity.BASE_REQUEST_RESULT);

            }
        });
    }

    @Override
    protected String getTitleName() {

        String title = getIntent().getStringExtra(BASE_STRING);
        return TextUtils.isEmpty(title) ? "" : title;
    }

    @Override
    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new ShopListAdapter(R.layout.home_shop_item);
    }

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.COMMODIFY_RECOMMEND:
                CommodityDesListBean desListBean = (CommodityDesListBean) o;
                if (desListBean != null) {
                    CommodityDesListBean.DataBean dataBean = desListBean.getData();
                    if (dataBean != null) {
                        List<CommodityBean> data = dataBean.getList();
                        setData(data, dataBean.getTotalCount());
                    }
                }
                break;
            default:
                break;
        }
    }
}
