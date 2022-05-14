package com.juntai.wisdom.project.order.orderPay;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.juntai.wisdom.project.MainActivity;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseAppActivity;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.order.OrderPresent;

/**
 * @aouther tobato
 * @description 描述  支付成功
 * @date 2022/5/11 17:36
 */
public class PaySuccessActivity extends BaseAppActivity<OrderPresent> implements HomePageContract.IHomePageView, View.OnClickListener {

    /**
     * 返回首页
     */
    private TextView mBackHomeTv;
    /**
     * 查看订单
     */
    private TextView mOrderTv;

    @Override
    protected OrderPresent createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initView() {

        mBackHomeTv = (TextView) findViewById(R.id.back_home_tv);
        mBackHomeTv.setOnClickListener(this);
        mOrderTv = (TextView) findViewById(R.id.order_tv);
        mOrderTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back_home_tv:
                // : 2022/5/12 返回首页
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            case R.id.order_tv:
                // : 2022/5/12 跳转到全部订单
                startToAllOrderActivity(0,0);
                finish();
                break;
        }
    }
}
