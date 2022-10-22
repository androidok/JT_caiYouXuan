package com.juntai.wisdom.project.mall.home.commodityfragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.appbase.bean.CommodityDesListBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.shop.ShopListAdapter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.List;

import okhttp3.FormBody;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public abstract class BaseCommodityListFragment extends BaseRecyclerviewFragment<CommodityPresent> implements HomePageContract.IHomePageView {

    private int labelId;

    @Override
    protected void lazyLoad() {
        labelId = getArguments().getInt("label");
        super.lazyLoad();


    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new ShopListAdapter(R.layout.home_shop_item);
    }

    @Override

    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    protected void initView() {
        super.initView();
        // : 2022/9/1 这个地方涉及到主页的颜色配置
//        mRecyclerview.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_lighter));
        mRecyclerview.setBackgroundResource(0);
        mRecyclerview.setPadding(0,0,0,0);
        mSmartrefreshlayout.setPrimaryColors(ContextCompat.getColor(mContext, R.color.gray_lighter));
        ClassicsHeader classicsHeader = (ClassicsHeader) mSmartrefreshlayout.getRefreshHeader();
        if (classicsHeader != null) {
            classicsHeader.setAccentColor(ContextCompat.getColor(mContext, R.color.black));

        }
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        baseQuickAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView("一个商品也没有-_-", -1));
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                itemClick(adapter,position);

            }
        });

    }

    protected abstract void itemClick(BaseQuickAdapter adapter, int position);


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.REFRESH_HOMEPAGE_COMMODITY_LIST:
                page = 1;
                getRvAdapterData();
                break;
            default:
                break;
        }
    }

    @Override
    protected void getRvAdapterData() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("page", String.valueOf(page))
                .add("limit", String.valueOf(limit));
        mPresenter.getCommodityRecommendList(0 == labelId ? builder.build() : builder
                .add("categoryId", String.valueOf(labelId)).build(), AppHttpPath.COMMODIFY_RECOMMEND);
    }



    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPath.COMMODIFY_RECOMMEND:
            case AppHttpPath.SEARCH_COMMODITY:
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
