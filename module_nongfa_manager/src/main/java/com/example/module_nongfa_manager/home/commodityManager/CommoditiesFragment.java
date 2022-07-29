package com.example.module_nongfa_manager.home.commodityManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.BaseRecyclerviewFragment;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerListBean;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.HomePresent;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  订单列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class CommoditiesFragment extends BaseRecyclerviewFragment<HomePresent> implements IView {

    private int labelId;
    private int currentPosition;

    public static CommoditiesFragment newInstance(int labelId) {
        Bundle args = new Bundle();
        args.putInt("label", labelId);
        CommoditiesFragment fragment = new CommoditiesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        labelId = getArguments().getInt("label");
        super.lazyLoad();
        if (baseQuickAdapter != null) {
            ((CommoditiesAdapter) baseQuickAdapter).setStatus(labelId);
        }

    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new CommoditiesAdapter(R.layout.nf_manager_commodity_manager_item);
    }

    @Override

    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected HomePresent createPresenter() {
        return new HomePresent();
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter.setEmptyView(getBaseAppActivity().getAdapterEmptyView("一个商品也没有-_-", -1));
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition =position;
                CommodityManagerListBean.DataBean.ListBean listBean = (CommodityManagerListBean.DataBean.ListBean) adapter.getItem(position);
                switch (labelId) {
                    case 1:
                        //待审核
                       startActivity(new Intent(mContext,CheckCommodityActivity.class));
                        break;
                    case 2:
                        //已审核  下架

                        // : 2022/6/13 删除商品
                        getBaseAppActivity().showAlertDialog("是否删除当前商品?", "确定", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.updateCommodityStatus(getBaseAppActivity().getBaseBuilder()
                                        .add("commodityId",String.valueOf(listBean.getId()))
                                        .add("state","3").build(),AppHttpPath.UPDATE_COMMODITY_STATUS_DOWN
                                );
                            }
                        });


                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }


    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);

        switch (eventBusObject.getEventKey()) {
            case EventBusObject.REFRESH_COMMODITY_MANAGER_LIST:
                String keyword = (String) eventBusObject.getEventObj();
                getList(keyword);
                break;
            default:
                break;
        }
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        getList(((CommodityManagerActivity) getActivity()).mSearchContentSv.getQuery().toString().trim());
    }

    private void getList(String key) {
        mPresenter.getManagerCommodityList(getBaseAppActivity().getBaseBuilder()
                .add("page", String.valueOf(page))
                .add("keyword", key)
                .add("limit", String.valueOf(limit))
                .add("state", String.valueOf(labelId)).build(), AppHttpPath.MANAGER_COMMODITY_LIST
        );
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.MANAGER_COMMODITY_LIST:
                CommodityManagerListBean commodityManagerListBean = (CommodityManagerListBean) o;
                if (commodityManagerListBean != null) {
                    CommodityManagerListBean.DataBean dataBean = commodityManagerListBean.getData();
                    if (dataBean != null) {
                        List<CommodityManagerListBean.DataBean.ListBean> arrays = dataBean.getList();
                        setData(arrays, dataBean.getTotalCount());
                    }
                }
                break;

            case AppHttpPath.UPDATE_COMMODITY_STATUS_DOWN:
                baseQuickAdapter.remove(currentPosition);
                        ToastUtils.toast(mContext,"下架成功");
                break;
            default:
                break;
        }
    }
}
