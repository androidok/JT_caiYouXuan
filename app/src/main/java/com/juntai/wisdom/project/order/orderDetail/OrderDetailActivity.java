package com.juntai.wisdom.project.order.orderDetail;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseAppActivity;
import com.juntai.wisdom.project.beans.order.OrderDetailBean;
import com.juntai.wisdom.project.beans.order.OrderDetailDataBean;
import com.juntai.wisdom.project.beans.order.OrderListBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.order.OrderPresent;
import com.juntai.wisdom.project.order.allOrder.OrderCommodityAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 订单详情
 * @date 2022/5/13 14:06
 */
public class OrderDetailActivity extends BaseAppActivity<OrderPresent> implements HomePageContract.IHomePageView, View.OnClickListener {

    private int orderId;
    private int orderStatus;
    private FrameLayout mOrderDetailTopFl;
    /**
     * 商铺名称
     */
    private TextView mOrderShopNameTv;
    private RecyclerView mOrderDetailCommodityRv;
    /**
     * 实付款:
     */
    private TextView mFinalPaymentTv;
    private RecyclerView mOrderDetailInfoRv;
    /**
     * 配送服务
     */
    private TextView mOrderLeftTv;
    /**
     * 快递包邮
     */
    private TextView mOrderRightTv;
    private OrderDetailBean orderDetailBean;
    private OrderCommodityAdapter mOrderCommodityAdapter;
    private OrderBaseInfoAdapter mOrderBaseInfoAdapter;
    private TextView mReceiverNameTv;
    private TextView mReceiverAddrTv;
    private TextView mReceiverPhoneTv;

    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }


    @Override
    public int getLayoutView() {
        return R.layout.order_detail_activity;
    }

    @Override
    public void initView() {
        setTitleName("订单详情");
        orderId = getIntent().getIntExtra(BASE_ID, 0);
        orderStatus = getIntent().getIntExtra(BASE_ID2, 0);
        mOrderDetailTopFl = (FrameLayout) findViewById(R.id.order_detail_top_fl);
        mOrderShopNameTv = (TextView) findViewById(R.id.order_shop_name_tv);
        mOrderShopNameTv.setOnClickListener(this);
        mOrderDetailCommodityRv = (RecyclerView) findViewById(R.id.order_detail_commodity_rv);
        mFinalPaymentTv = (TextView) findViewById(R.id.final_payment_tv);
        mOrderDetailInfoRv = (RecyclerView) findViewById(R.id.order_detail_info_rv);
        mOrderLeftTv = (TextView) findViewById(R.id.order_left_tv);
        mOrderLeftTv.setOnClickListener(this);
        mOrderRightTv = (TextView) findViewById(R.id.order_right_tv);
        mOrderRightTv.setOnClickListener(this);
        mOrderDetailTopFl.setVisibility(View.GONE);
        mOrderCommodityAdapter = new OrderCommodityAdapter(R.layout.comfirm_order_commodity_item);
        initRecyclerview(mOrderDetailCommodityRv, mOrderCommodityAdapter, LinearLayoutManager.VERTICAL);
        mOrderBaseInfoAdapter = new OrderBaseInfoAdapter(R.layout.mall_order_baseinfo_item);
        initRecyclerview(mOrderDetailInfoRv, mOrderBaseInfoAdapter, LinearLayoutManager.VERTICAL);
        if (0 == orderStatus || 1 == orderStatus) {
            mOrderDetailTopFl.setVisibility(View.VISIBLE);
            //显示收货地址
            mOrderDetailTopFl.addView(getAddrView());
        } else if (2 == orderStatus || 3 == orderStatus) {
        } else {
            // TODO: 2022/5/14 退货订单详情 需要展示不同的界面

        }

    }

    /**
     * 获取收货地址
     *
     * @return
     */
    private View getAddrView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.default_addr_layout, null);
        mReceiverNameTv = view.findViewById(R.id.receiver_name_tv);
        mReceiverPhoneTv = view.findViewById(R.id.receiver_phone_tv);
        mReceiverAddrTv = view.findViewById(R.id.addr_des_tv);
        view.findViewById(R.id.arrow_right_iv).setVisibility(View.GONE);
        return view;
    }

    @Override
    public void initData() {
        mPresenter.getOrderDetail(getBaseBuilder().add("orderId", String.valueOf(orderId)).build(), AppHttpPathMall.ORDER_DETAIL);

    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPathMall.ORDER_DETAIL:
                OrderDetailDataBean orderDetailDataBean = (OrderDetailDataBean) o;
                if (orderDetailDataBean != null) {
                    orderDetailBean = orderDetailDataBean.getData();
                    if (orderDetailBean != null) {
                        mOrderCommodityAdapter.setNewData(orderDetailBean.getCommodityList());
                        mOrderShopNameTv.setText(orderDetailBean.getShopName());
                        mFinalPaymentTv.setText(0 == orderStatus ? String.format("需付款:%s", orderDetailBean.getPayPrice()) : String.format("实付款:%s", orderDetailBean.getPayPrice()));
                        List<TextKeyValueBean> arrays = new ArrayList<>();
                        switch (orderStatus) {
                            case 0:
                                initAddrData();
                                mOrderLeftTv.setText(HomePageContract.ORDER_CANCEL);
                                mOrderRightTv.setText(HomePageContract.ORDER_PAY);
                                arrays.add(new TextKeyValueBean("订单编号:", orderDetailBean.getOrderFormNumber()));
                                arrays.add(new TextKeyValueBean("下单时间:", orderDetailBean.getEstablishTime()));
                                break;
                            case 1:
                                initAddrData();
                                mOrderLeftTv.setText(HomePageContract.ORDER_REFUND);
                                mOrderRightTv.setText(HomePageContract.ORDER_SEND);
                                arrays.add(new TextKeyValueBean("订单编号:", orderDetailBean.getOrderFormNumber()));
                                arrays.add(new TextKeyValueBean("下单时间:", orderDetailBean.getEstablishTime()));
                                arrays.add(new TextKeyValueBean("支付方式:", getPayTypeName(orderDetailBean.getPayType())));
                                arrays.add(new TextKeyValueBean("付款时间:", orderDetailBean.getPaymentTime()));
                                break;
                            case 2:
                                mOrderLeftTv.setText(HomePageContract.ORDER_REFUND);
                                mOrderRightTv.setText(HomePageContract.ORDER_RECEIVE);
                                arrays.add(new TextKeyValueBean("订单编号:", orderDetailBean.getOrderFormNumber()));
                                arrays.add(new TextKeyValueBean("下单时间:", orderDetailBean.getEstablishTime()));
                                arrays.add(new TextKeyValueBean("支付方式:", getPayTypeName(orderDetailBean.getPayType())));
                                arrays.add(new TextKeyValueBean("付款时间:", orderDetailBean.getPaymentTime()));
                                arrays.add(new TextKeyValueBean("发货时间:", orderDetailBean.getShipmentsTime()));
                                arrays.add(new TextKeyValueBean("物流公司:", orderDetailBean.getLogisticsName()));
                                arrays.add(new TextKeyValueBean("快递单号:", orderDetailBean.getLogisticsNumber()));
                                break;
                            case 3:
//                                mOrderLeftTv.setText(HomePageContract.ORDER_REBUY);
                                mOrderLeftTv.setVisibility(View.GONE);
                                mOrderRightTv.setText(HomePageContract.ORDER_EVALUATE);
                                arrays.add(new TextKeyValueBean("收货信息:", String.format("%s\u3000%s\u3000%s", orderDetailBean.getName(),
                                        orderDetailBean.getPhone(), orderDetailBean.getAddress())));
                                arrays.add(new TextKeyValueBean("订单编号:", orderDetailBean.getOrderFormNumber()));
                                arrays.add(new TextKeyValueBean("下单时间:", orderDetailBean.getEstablishTime()));
                                arrays.add(new TextKeyValueBean("支付方式:", getPayTypeName(orderDetailBean.getPayType())));
                                arrays.add(new TextKeyValueBean("付款时间:", orderDetailBean.getPaymentTime()));
                                arrays.add(new TextKeyValueBean("发货时间:", orderDetailBean.getShipmentsTime()));
                                arrays.add(new TextKeyValueBean("物流公司:", orderDetailBean.getLogisticsName()));
                                arrays.add(new TextKeyValueBean("快递单号:", orderDetailBean.getLogisticsNumber()));
                                arrays.add(new TextKeyValueBean("成交时间:", orderDetailBean.getConfirmTime()));
                                break;
                            default:
                                break;
                        }
                        mOrderBaseInfoAdapter.setNewData(arrays);

                    }

                }

                break;
            case AppHttpPathMall.CANCEL_ORDER:
                finish();
                // : 2022/5/14 通知订单刷新列表
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_ORDER_LIST, ""));
                break;
            default:
                break;
        }
    }

    private void initAddrData() {

        mReceiverNameTv.setText(orderDetailBean.getName());
        mReceiverPhoneTv.setText(orderDetailBean.getPhone());
        mReceiverAddrTv.setText(orderDetailBean.getAddress());
    }

    /**
     * 获取支付方式
     *
     * @param payType
     * @return /**
     * * 支付方式（0：未支付）（1：支付宝）（2：微信）（3：银行卡）（4：对公账户）
     */
    private String getPayTypeName(int payType) {
        if (payType == 0) {
            return "未支付";
        } else if (payType == 1) {
            return "支付宝";
        } else if (payType == 2) {
            return "微信";
        } else if (payType == 3) {
            return "银行卡";
        } else if (payType == 4) {
            return "对公账户";
        }
        return "";
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.order_shop_name_tv:
                startToShop(orderDetailBean.getShopId());
                break;
            case R.id.order_left_tv:
                switch (getTextViewValue(mOrderLeftTv)) {
                    case HomePageContract.ORDER_CANCEL:
                        // : 2022/5/14 取消订单
                        showAlertDialog("是否取消当前订单?", "确定", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.cancelOrder(getBaseBuilder()
                                        .add("orderId", String.valueOf(orderDetailBean.getId())).build(), AppHttpPathMall.CANCEL_ORDER);
                            }
                        });

                        break;

                    case HomePageContract.ORDER_REFUND:
                        // TODO: 2022/5/14 申请退款
                        ToastUtils.toast(mContext, "申请退款");

                        break;
                    case HomePageContract.ORDER_REBUY:
                        // TODO: 2022/5/14 再来一单
                        ToastUtils.toast(mContext, "再来一单");
                        break;
                    default:
                        break;
                }
                break;
            case R.id.order_right_tv:
                switch (getTextViewValue(mOrderRightTv)) {
                    case HomePageContract.ORDER_PAY:
                        // : 2022/5/14 立即付款
                        OrderListBean orderListBean = new OrderListBean();
                        List<OrderDetailBean> orderDetailBeans = new ArrayList<>();
                        orderDetailBeans.add(orderDetailBean);
                        orderListBean.setData(orderDetailBeans);
                        orderListBean.setTotalPrice(orderDetailBean.getPayPrice());
                        startToOrderPayActivity(orderListBean, 2);
                        break;

                    case HomePageContract.ORDER_SEND:
                        // TODO: 2022/5/14 提箱发货
                        ToastUtils.toast(mContext, "已提醒");
                        break;
                    case HomePageContract.ORDER_RECEIVE:
                        // TODO: 2022/5/14 确认收货
                        ToastUtils.toast(mContext, "确认收货");
                        break;
                    case HomePageContract.ORDER_EVALUATE:
                        // TODO: 2022/5/14 立即评价
                        ToastUtils.toast(mContext, "立即评价");
                        break;
                    default:
                        break;
                }
                break;
        }
    }
}