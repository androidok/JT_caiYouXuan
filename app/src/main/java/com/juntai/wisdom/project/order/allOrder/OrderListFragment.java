package com.juntai.wisdom.project.order.allOrder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.beans.order.OrderDetailBean;
import com.juntai.wisdom.project.beans.order.OrderListBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.order.OrderPresent;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  订单列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class OrderListFragment extends BaseRecyclerviewFragment<OrderPresent> implements HomePageContract.IHomePageView {

    private int labelId;

    public static OrderListFragment newInstance(int labelId) {
        Bundle args = new Bundle();
        args.putInt("label", labelId);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        labelId = getArguments().getInt("label");
        super.lazyLoad();
    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new OrderListShopAdapter(R.layout.mall_order_list_item);
    }

    @Override

    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }

    @Override
    protected void initView() {
        super.initView();

        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDetailBean orderDetailBean = (OrderDetailBean) adapter.getItem(position);
                TextView orderLeftTv = (TextView) adapter.getViewByPosition(mRecyclerview, position, R.id.order_left_tv);
                TextView orderRightTv = (TextView) adapter.getViewByPosition(mRecyclerview, position, R.id.order_right_tv);

                switch (view.getId()) {

                    case R.id.shop_bottom_cl:
                        // : 2022/5/12 跳转到订单详情
                        getBaseAppActivity().startToOrderDetailActivity(orderDetailBean.getId(), orderDetailBean.getState());
                        break;
                    case R.id.order_shop_name_tv:
                        getBaseAppActivity().startToShop(orderDetailBean.getShopId());
                        break;

                    case R.id.order_left_tv:
                        switch (orderLeftTv.getText().toString().trim()) {
                            case HomePageContract.ORDER_CANCEL:
                                // : 2022/5/12 取消订单
                                getBaseAppActivity().showAlertDialog("是否取消当前订单?", "确定", "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.cancelOrder(getBaseAppActivity().getBaseBuilder()
                                                .add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPathMall.CANCEL_ORDER);
                                    }
                                });

                                break;
                            case HomePageContract.ORDER_REFUND:
                                // : 2022/5/12 申请退款
                                getBaseAppActivity().startToOrderRefundRequestActivity(orderDetailBean);
                                break;
                            default:
                                break;
                        }

                        break;
                    case R.id.order_right_tv:
                        switch (orderRightTv.getText().toString().trim()) {
                            case HomePageContract.ORDER_PAY:
                                // : 2022/5/12 立即付款
                                OrderListBean orderListBean = new OrderListBean();
                                List<OrderDetailBean> orderDetailBeans = new ArrayList<>();
                                orderDetailBeans.add(orderDetailBean);
                                orderListBean.setData(orderDetailBeans);
                                orderListBean.setTotalPrice(orderDetailBean.getPayPrice());
                                getBaseAppActivity().startToOrderPayActivity(orderListBean, 2);

                                break;
                            case HomePageContract.ORDER_SEND:
                                //TODO : 2022/5/12 提醒发货
                                ToastUtils.toast(mContext, "已提醒");
                                break;
                            case HomePageContract.ORDER_RECEIVE:
                                // TODO: 2022/5/12 确认收货
                                break;
                            case HomePageContract.ORDER_EVALUATE:
                                // TODO: 2022/5/12 立即评价
                                break;
                            case HomePageContract.ORDER_PROGRESS:
                                // TODO: 2022/5/12 查看进度
                                break;
                            case HomePageContract.ORDER_DELETE:
                                // TODO: 2022/5/12 删除订单
                                break;
                            default:
                                break;
                        }

                        break;
                    default:
                        break;
                }

            }
        });
    }


    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);

        switch (eventBusObject.getEventKey()) {
            case EventBusObject.REFRESH_ORDER_LIST:
                getRvAdapterData();
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
        // : 2022/5/12 获取订单列表
        mPresenter.getOrderList(getBaseAppActivity().getBaseBuilder()
                .add("type", String.valueOf(labelId)).build(), AppHttpPathMall.ORDER_LIST
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
            case AppHttpPathMall.ORDER_LIST:
                OrderListBean orderListBean = (OrderListBean) o;
                if (orderListBean != null) {
                    List<OrderDetailBean> orderDetailBeans = orderListBean.getData();
                    baseQuickAdapter.setNewData(orderDetailBeans);
                }


                break;
            case AppHttpPathMall.CANCEL_ORDER:
                getRvAdapterData();
                break;
            default:
                break;
        }
    }
}
