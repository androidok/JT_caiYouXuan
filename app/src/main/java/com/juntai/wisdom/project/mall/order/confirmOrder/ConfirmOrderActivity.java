package com.juntai.wisdom.project.mall.order.confirmOrder;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appbase.bean.order.CreatOrderBean;
import com.example.appbase.bean.order.OrderDetailBean;
import com.example.appbase.util.CalendarUtil;
import com.example.appbase.util.UserInfoManager;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.address.AddressListBean;
import com.juntai.disabled.basecomponent.utils.GsonTools;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseAppActivity;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.order.OrderPresent;
import com.juntai.wisdom.project.mall.order.orderPay.PaySuccessActivity;
import com.orhanobut.hawk.Hawk;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 确认订单
 * @date 2022/5/10 17:30
 */
public class ConfirmOrderActivity extends BaseAppActivity<OrderPresent> implements HomePageContract.IHomePageView, View.OnClickListener {

    /**
     * 描述
     */
    private TextView mReceiverNameTv;
    /**
     * 价格
     */
    private TextView mAddrDesTv, mSendTimeTv;
    /**
     * 销量
     */
    private TextView mReceiverPhoneTv;
    private ConstraintLayout mConfirmOrderAddrCl, mSendTimeCl;
    private RecyclerView mOrderShopCommodityRv;
    /**
     * 共4件,小计:
     */
    private TextView mCommodityAmountTv;
    /**
     * Y666
     */
    private TextView mTotalPriceTv;
    /**
     * 提交订单
     */
    private TextView mCommitOrderTv;
    /**
     * 选择收货地址
     */
    private TextView mSelectAddrTv;
    private LinearLayout mDefaultAddrLl;
    private CreatOrderBean.DataBean dataBean;
    private ConfirmOrderShopAdapter orderShopAdapter;
    private OrderDetailBean orderDetailBe;

    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {
        setTitleName("确认订单");
        mReceiverNameTv = (TextView) findViewById(R.id.receiver_name_tv);
        mAddrDesTv = (TextView) findViewById(R.id.addr_des_tv);
        mSendTimeTv = (TextView) findViewById(R.id.send_time_value_tv);
        mReceiverPhoneTv = (TextView) findViewById(R.id.receiver_phone_tv);
        mConfirmOrderAddrCl = (ConstraintLayout) findViewById(R.id.confirm_order_addr_cl);
        mSendTimeCl = (ConstraintLayout) findViewById(R.id.send_time_cl);
        mSendTimeCl.setOnClickListener(this);
        mConfirmOrderAddrCl.setOnClickListener(this);
        mOrderShopCommodityRv = (RecyclerView) findViewById(R.id.order_shop_commodity_rv);
        mCommodityAmountTv = (TextView) findViewById(R.id.commodity_amount_tv);
        mTotalPriceTv = (TextView) findViewById(R.id.total_price_tv);
        mCommitOrderTv = (TextView) findViewById(R.id.commit_order_tv);
        mCommitOrderTv.setOnClickListener(this);
        mSelectAddrTv = (TextView) findViewById(R.id.select_addr_tv);
        mDefaultAddrLl = (LinearLayout) findViewById(R.id.default_addr_ll);
        if (Hawk.contains(HawkProperty.getDefaultAddrKey(UserInfoManager.getUserId()))) {
            mDefaultAddrLl.setVisibility(View.VISIBLE);
            mSelectAddrTv.setVisibility(View.GONE);
            AddressListBean.DataBean dataBean = Hawk.get(HawkProperty.getDefaultAddrKey(UserInfoManager.getUserId()));
            if (dataBean != null) {
                initDefaultAddr(dataBean);
            }
        } else {
            mDefaultAddrLl.setVisibility(View.GONE);
            mSelectAddrTv.setVisibility(View.VISIBLE);
        }
        orderShopAdapter = new ConfirmOrderShopAdapter(R.layout.mall_comfirm_order_shop_item);
        initRecyclerview(mOrderShopCommodityRv, orderShopAdapter, LinearLayoutManager.VERTICAL);

    }

    /**
     * 默认地址
     *
     * @param dataBean
     */
    private void initDefaultAddr(AddressListBean.DataBean dataBean) {
        mReceiverNameTv.setText(dataBean.getName());
        mReceiverPhoneTv.setText(dataBean.getPhone());
        mAddrDesTv.setText(dataBean.getDetailedAddress());

    }

    @Override
    public void initData() {
        dataBean = getIntent().getParcelableExtra(BASE_PARCELABLE);
        if (dataBean == null) {
            ToastUtils.toast(mContext, "订单生成异常");
            finish();
        }
        mCommodityAmountTv.setText(String.format("共%s件,小计:", dataBean.getTotalCommodityNum()));
        mTotalPriceTv.setText(String.valueOf(dataBean.getTotalPrice()));
        orderShopAdapter.setNewData(dataBean.getShopList());

    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.COMMIT_ORDER:
                // : 2022/5/11 跳转到选择支付方式的界面
                startActivity(new Intent(mContext, PaySuccessActivity.class).putExtra(BASE_PARCELABLE, orderDetailBe));
                finish();
//                ConfirmOrderBean orderListBean = (ConfirmOrderBean) o;
//                if (orderListBean != null) {
//                    orderListBean.setTotalPrice(dataBean.getTotalPrice());
//                    orderListBean.setTotalCommodityNum(dataBean.getTotalCommodityNum());
//                    List<OrderDetailBean> orderDetailBeans = orderListBean.getData();
//                    if (orderDetailBeans != null && orderDetailBeans.size() > 0) {
//                        orderDetailBe = orderDetailBeans.get(0);
//                        mPresenter.payByPubAccount(getBaseBuilder().add("orderNumber", orderDetailBe.getTotalOrderFormNumber()).build(), AppHttpPath.ORDER_PAY_PUB_ACCOUNT);
//                    } else {
//                        ToastUtils.toast(mContext, "订单提交异常");
//                    }
//
//
//                }
                break;
            case AppHttpPath.ORDER_PAY_PUB_ACCOUNT:
                startActivity(new Intent(mContext, PaySuccessActivity.class).putExtra(BASE_PARCELABLE, orderDetailBe));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == BASE_RSULT) {
            if (data != null) {
                AddressListBean.DataBean dataBean = data.getParcelableExtra(BASE_PARCELABLE);
                mSelectAddrTv.setVisibility(View.GONE);
                mDefaultAddrLl.setVisibility(View.VISIBLE);
                initDefaultAddr(dataBean);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.confirm_order_addr_cl:
                // : 2022/5/11 选择收货地址
                startToAddressListActivity(1);
                break;
            case R.id.send_time_cl:
                // : 选择配送时间
                Calendar startCalendar = Calendar.getInstance();
                startCalendar.add(Calendar.DAY_OF_MONTH,1);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.add(Calendar.DAY_OF_MONTH,7);
                PickerManager.getInstance().showTimePickerViewIncludeRangDate(mContext, new boolean[]{true, true, true, false, false, false}, "选择配送日期", new PickerManager.OnTimePickerTimeSelectedListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = CalendarUtil.getCurrentTime("yyyy-MM-dd", date);
                        mSendTimeTv.setText(String.format("%s 06:00~16:00",time));
                    }
                },startCalendar,endCalendar);
                break;
            case R.id.commit_order_tv:
                // : 2022/5/11 提交订单
                if (!Hawk.contains(HawkProperty.getDefaultAddrKey(UserInfoManager.getUserId()))) {
                    ToastUtils.toast(mContext, "请选择收货地址");
                    return;
                }
                dataBean.setName(getTextViewValue(mReceiverNameTv));
                dataBean.setPhone(getTextViewValue(mReceiverPhoneTv));
                dataBean.setDetailedAddress(getTextViewValue(mAddrDesTv));

                List<CreatOrderBean.DataBean.ShopListBean> arrays = orderShopAdapter.getData();
                dataBean.setShopList(arrays);
                if (TextUtils.isEmpty(getTextViewValue(mSendTimeTv))) {
                    ToastUtils.toast(mContext, "请选择配送时间");
                    return;
                }
                mPresenter.commitOrder(getBaseBuilder()
                        .add("shipmentsTime",getTextViewValue(mSendTimeTv))
                        .add("json", GsonTools.createGsonString(dataBean)).build(), AppHttpPath.COMMIT_ORDER
                );
                break;
        }
    }

}
