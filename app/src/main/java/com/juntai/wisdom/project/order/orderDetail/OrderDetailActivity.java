package com.juntai.wisdom.project.order.orderDetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseAppActivity;
import com.juntai.wisdom.project.beans.order.OrderDetailBean;
import com.juntai.wisdom.project.beans.order.OrderDetailDataBean;
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
        mOrderBaseInfoAdapter = new OrderBaseInfoAdapter(R.layout.item_key_value);
        initRecyclerview(mOrderDetailInfoRv, mOrderBaseInfoAdapter, LinearLayoutManager.VERTICAL);
        if (0 == orderStatus || 1 == orderStatus) {
            mOrderDetailTopFl.setVisibility(View.VISIBLE);
            //显示收货地址
            mOrderDetailTopFl.addView(getAddrView());
        } else if (2 == orderStatus || 3 == orderStatus) {
        } else {

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
                        mReceiverNameTv.setText(orderDetailBean.getName());
                        mReceiverAddrTv.setText(orderDetailBean.getAddress());
                        mOrderCommodityAdapter.setNewData(orderDetailBean.getCommodityList());
                        mOrderShopNameTv.setText(orderDetailBean.getShopName());
                        mFinalPaymentTv.setText(0 == orderStatus ? String.format("需付款:%s", orderDetailBean.getPayPrice()) : String.format("实付款:%s", orderDetailBean.getPayPrice()));
                        List<TextKeyValueBean> arrays = new ArrayList<>();
                        switch (orderStatus) {
                            case 0:
                                arrays.add(new TextKeyValueBean("订单编号:", orderDetailBean.getOrderFormNumber()));
                                arrays.add(new TextKeyValueBean("下单时间:", orderDetailBean.getEstablishTime()));

                                break;
                            default:
                                break;
                        }
                        mOrderBaseInfoAdapter.setNewData(arrays);

                    }

                }

                break;
            default:
                break;
        }
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
                break;
            case R.id.order_right_tv:
                break;
        }
    }
}