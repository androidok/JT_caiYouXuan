package com.juntai.wisdom.project.mall.order.allOrder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.app_basemodule.net.AppHttpPath;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewFragment;
import com.example.app_basemodule.bean.order.OrderDetailBean;
import com.example.app_basemodule.bean.order.OrderListBean;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.order.OrderPresent;

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
        return new OrderListShopAdapter(R.layout.sell_mall_order_list_item);
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
        baseQuickAdapter.setEmptyView(getBaseAppActivity().getAdapterEmptyView("一个订单也没有-_-",-1));
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
                                                .add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.CANCEL_ORDER);
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
                                OrderListBean.DataBean dataBean = new OrderListBean.DataBean();
                                List<OrderDetailBean> orderDetailBeans = new ArrayList<>();
                                orderDetailBeans.add(orderDetailBean);
                                dataBean.setList(orderDetailBeans);
                                orderListBean.setTotalPrice(orderDetailBean.getPayPrice());
                                orderListBean.setData(dataBean);
                                getBaseAppActivity().startToOrderPayActivity(orderListBean, 2);

                                break;
                            case HomePageContract.ORDER_SEND:
                                // : 2022/5/12 提醒发货
                                mPresenter.noticeSend(getBaseAppActivity().getBaseBuilder().add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.NOTICE_SEND);

                                break;
                            case HomePageContract.ORDER_RECEIVE:
                                // : 2022/5/12 确认收货
                                getBaseActivity().showAlertDialog("确定收到货物了吗?", "确定", "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.confirmReceived(getBaseAppActivity().getBaseBuilder()
                                                .add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.CONFIRM_RECEIVED);
                                    }
                                });
                                break;
                            case HomePageContract.ORDER_REFUND_AGREE:
                                // : 2022/5/12 商家已同意
                            case HomePageContract.ORDER_PROGRESS:
                                // : 2022/5/12 查看进度
                            case HomePageContract.ORDER_REFUND_UNAGREE:
                                // : 2022/5/12 商家不同意
                                getBaseAppActivity().startToOrderDetailActivity(orderDetailBean.getId(), orderDetailBean.getState());

                                break;
                            case HomePageContract.ORDER_DELETE:
                                // : 2022/5/12 删除订单
                                getBaseActivity().showAlertDialog("确定删除该订单?", "确定", "取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mPresenter.deleteCancelOrder(getBaseAppActivity().getBaseBuilder()
                                                .add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPath.DELETE_CANCEL_ORDER);
                                    }
                                });

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
                String key = (String) eventBusObject.getEventObj();
                page = 1;
               getList(key);
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
        getList(((AllOrderActivity)getActivity()).mSearchContentSv.getQuery().toString().trim());

    }

    private void getList(String key) {
        mPresenter.getOrderList(getBaseAppActivity().getBaseBuilder()
                .add("page", String.valueOf(page))
                .add("key",key)
                .add("limit", String.valueOf(limit))
                .add("type", String.valueOf(labelId)).build(), AppHttpPath.ORDER_LIST
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
            case AppHttpPath.ORDER_LIST:
                OrderListBean orderListBean = (OrderListBean) o;
                if (orderListBean != null) {
                    OrderListBean.DataBean dataBean = orderListBean.getData();
                    if (dataBean != null) {
                        List<OrderDetailBean> arrays = dataBean.getList();
                        setData(arrays,dataBean.getTotalCount());
                    }
                }


                break;
            case AppHttpPath.CANCEL_ORDER:
                ToastUtils.toast(mContext, "已取消订单");
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, ""));
                break;
            case AppHttpPath.NOTICE_SEND:
                ToastUtils.toast(mContext, "已通知卖家尽快发货");
                break;
            case AppHttpPath.CONFIRM_RECEIVED:
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, ""));

                break;
            case AppHttpPath.DELETE_CANCEL_ORDER:
                ToastUtils.toast(mContext, "已删除订单");
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, ""));

                break;
            default:
                break;
        }
    }
}
