package com.example.module_nongfa_manager.home.orderManager;

import android.content.DialogInterface;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.search.BaseSearchAndListActivity;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.HomePresent;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.beans.order.OrderDetailBean;
import com.juntai.project.sell.mall.beans.order.OrderListBean;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述 农发订单管理
 * @date 2022/8/1 15:57
 */
public class NFOrderManagerActivity extends BaseSearchAndListActivity<HomePresent> implements IView {

    private OrderDetailBean orderDetailBean;
    private int currentPosition;

    @Override
    protected HomePresent createPresenter() {
        return new HomePresent();
    }


    @Override
    protected String getTitleName() {
        return "订单管理";
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
    protected void getRvAdapterData() {

        getData();


    }

    @Override
    public void initData() {
        super.initData();

        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                orderDetailBean = (OrderDetailBean) adapter.getItem(position);
                currentPosition = position;
                int id = view.getId();
                if (id == R.id.order_left_tv) {// : 2022/8/2 分拣
                    showAlertDialog("是否确认已分拣完成？?", "是", "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.updateSortStatus(getBaseBuilder().add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.UPDATE_SORT_STATUS
                            );
                        }
                    });
                } else if (id == R.id.order_right_tv) {// : 2022/8/2 配送
                    showAlertDialog("是否确认已配送完成？?", "是", "否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.updateDeliveryStatus(getBaseBuilder().add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.UPDATE_DELIVERY_STATUS
                            );
                        }
                    });
                }
            }
        });
    }

    private void getData() {
        FormBody.Builder builder = getBaseBuilder()
                .add("page", String.valueOf(page))
                .add("keyword", mSearchContentSv.getQuery().toString().trim())
                .add("payType", "0")
                .add("limit", String.valueOf(limit));
        mPresenter.getOrderList(builder
                .build(), AppHttpPathMall.ORDER_LIST
        );
    }

    @Override
    protected void startSearch(String s) {
        getData();
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new NFOrderListShopAdapter(R.layout.nf_manager_order_shop_item);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPathMall.ORDER_LIST:
                OrderListBean orderListBean = (OrderListBean) o;
                if (orderListBean != null) {
                    OrderListBean.DataBean dataBean = orderListBean.getData();
                    if (dataBean != null) {
                        List<OrderDetailBean> arrays = dataBean.getList();
                        setData(arrays, dataBean.getTotalCount());
                    }
                }
                break;

            case AppHttpPath.UPDATE_SORT_STATUS:
                orderDetailBean.setSorting(2);
                baseQuickAdapter.notifyItemChanged(currentPosition,orderDetailBean);
                break;
            case AppHttpPath.UPDATE_DELIVERY_STATUS:
                orderDetailBean.setDelivery(2);
                baseQuickAdapter.notifyItemChanged(currentPosition,orderDetailBean);
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }

    @Override
    protected boolean enableLoadMore() {
        return true;
    }
}
