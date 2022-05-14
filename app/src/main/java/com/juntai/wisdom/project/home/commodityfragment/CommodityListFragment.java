package com.juntai.wisdom.project.home.commodityfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.beans.CommodityBean;
import com.juntai.wisdom.project.beans.CommodityDesListBean;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.home.HomePageContract;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class CommodityListFragment extends BaseRecyclerviewFragment<CommodityPresent> implements HomePageContract.IHomePageView {

    private int labelId;

    public static CommodityListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("label", type);
        CommodityListFragment fragment = new CommodityListFragment();
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
        return new CommodityListAdapter(R.layout.shop_commodity_list);
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

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CommodityBean bean = (CommodityBean) adapter.getItem(position);
                getBaseAppActivity().startToCommodityDetail(bean.getId());


            }
        });

    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        mPresenter.getCommodityRecommendList(0 == labelId ? getBaseAppActivity().getBaseBuilderWithoutParama().build() : getBaseAppActivity().getBaseBuilderWithoutParama()
                .add("categoryId", String.valueOf(labelId)).build(), AppHttpPathMall.COMMODIFY_RECOMMEND);
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPathMall.COMMODIFY_RECOMMEND:
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
