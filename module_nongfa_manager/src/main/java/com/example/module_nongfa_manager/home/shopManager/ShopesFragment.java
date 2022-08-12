package com.example.module_nongfa_manager.home.shopManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.BaseAppModuleActivity;
import com.example.appbase.base.OnBaseInterface;
import com.example.appbase.base.BaseRecyclerviewFragment;
import com.example.appbase.bean.nong_fa_manager.ShopManagerListBean;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.HomePresent;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  订单列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class ShopesFragment extends BaseRecyclerviewFragment<HomePresent> implements IView {

    /**
     * 1 待审核 2 已审核 3 未通过
     */
    private int labelId;
    private int currentPosition;

    public static ShopesFragment newInstance(int labelId) {
        Bundle args = new Bundle();
        args.putInt("label", labelId);
        ShopesFragment fragment = new ShopesFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        labelId = getArguments().getInt("label");
        super.lazyLoad();
        if (baseQuickAdapter != null) {
            ((ShopesAdapter) baseQuickAdapter).setStatus(labelId);
        }

    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new ShopesAdapter(R.layout.nf_manager_shop_item);
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
        baseQuickAdapter.setEmptyView(getBaseAppActivity().getAdapterEmptyView("一个店铺也没有-_-", -1));
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                ShopManagerListBean.DataBean.ListBean listBean = (ShopManagerListBean.DataBean.ListBean) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.left_tv) {// : 2022/8/11 详情
                    startActivity(new Intent(mContext, NfShopDetailActivity.class)
                            .putExtra(BASE_ID, labelId)
                            .putExtra(BASE_ID2, listBean.getId()));
                } else if (id == R.id.right_tv) {
                    switch (labelId) {
                        case 1:
                            //审核
                            startActivityForResult(new Intent(mContext, CheckShopActivity.class)
                                    .putExtra(BASE_ID2, listBean.getId()), BaseActivity.BASE_RSULT);
                            break;
                        case 2:
                            // 关店
                            ((BaseAppModuleActivity) getActivity()).showAlertDialogWithEditText("关店原因", new OnBaseInterface.OnCommitInterface() {
                                @Override
                                public void commit(String content) {
                                    mPresenter.commitShopCheck(getBaseAppActivity().getBaseBuilder()
                                            .add("state", "3")
                                            .add("content", content)
                                            .add("shopId", String.valueOf(listBean.getId())).build(), AppHttpPath.COMMIT_SHOP_CHECK);
                                }
                            });
                            break;
                        default:
                            break;
                    }

                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BaseActivity.BASE_RSULT) {
            baseQuickAdapter.remove(currentPosition);
        }

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
            case EventBusObject.REFRESH_SHOP_MANAGER_LIST:
                String keyword = (String) eventBusObject.getEventObj();
                getList(keyword);
                break;
            default:
                break;
        }
    }

    private void getList(String key) {
        mPresenter.getManagerShopList(getBaseAppActivity().getBaseBuilder()
                .add("page", String.valueOf(page))
                .add("keyword", key)
                .add("limit", String.valueOf(limit))
                .add("state", String.valueOf(labelId)).build(), AppHttpPath.MANAGER_SHOP_LIST
        );
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        getList(((ShopesManagerActivity) getActivity()).mSearchContentSv.getQuery().toString().trim());

    }


    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.MANAGER_SHOP_LIST:
                ShopManagerListBean commodityManagerListBean = (ShopManagerListBean) o;
                if (commodityManagerListBean != null) {
                    ShopManagerListBean.DataBean dataBean = commodityManagerListBean.getData();
                    if (dataBean != null) {
                        List<ShopManagerListBean.DataBean.ListBean> arrays = dataBean.getList();
                        setData(arrays, dataBean.getTotalCount());
                    }
                }
                break;

            case AppHttpPath.COMMIT_SHOP_CHECK:
                baseQuickAdapter.remove(currentPosition);
                break;
            default:
                break;
        }
    }
}
