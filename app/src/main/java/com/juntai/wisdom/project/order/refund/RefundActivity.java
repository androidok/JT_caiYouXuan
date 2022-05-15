package com.juntai.wisdom.project.order.refund;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chat.MainContract;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseAppActivity;
import com.juntai.wisdom.project.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.project.beans.order.OrderDetailBean;
import com.juntai.wisdom.project.beans.order.RefundReasonBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.order.OrderPresent;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  我要退款
 * @CreateDate: 2022/5/15 9:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/15 9:48
 */
public class RefundActivity extends BaseAppActivity<OrderPresent> implements HomePageContract.IHomePageView, View.OnClickListener, SelectPhotosFragment.OnPhotoItemClick {
    private TextView mItemMyinfoName;
    private TextView mItemMyinfoValue;
    private LinearLayout mKeyValueLl;
    /**
     * dfad
     */
    private TextView mRefundAmountTv;
    /**
     * 选填,说明原因
     */
    private EditText mRefundReasonEt;
    /**
     * 提交申请
     */
    private TextView mCommitRefundTv;
    private SelectPhotosFragment selectPhotosFragment;
    private OrderDetailBean orderDetailBean;

    private int reasonId = 0;

    @Override
    public int getLayoutView() {
        return R.layout.activity_mall_refund;
    }

    @Override
    public void initView() {
        orderDetailBean = getIntent().getParcelableExtra(BASE_PARCELABLE);
        setTitleName("我要退款");
        mItemMyinfoName = (TextView) findViewById(R.id.item_myinfo_name);
        mItemMyinfoName.setText("退款原因");
        mItemMyinfoValue = (TextView) findViewById(R.id.item_myinfo_value);
        mItemMyinfoValue.setText("请选择");
        mItemMyinfoValue.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mKeyValueLl = (LinearLayout) findViewById(R.id.key_value_ll);
        mKeyValueLl.setOnClickListener(this);
        mRefundAmountTv = (TextView) findViewById(R.id.refund_amount_tv);
        mRefundAmountTv.setText(String.format("¥%s", orderDetailBean.getTotalPrices()));
        mRefundReasonEt = (EditText) findViewById(R.id.refund_reason_et);
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.select_pic_fg);
        selectPhotosFragment.setMaxCount(3).setPhotoDelateable(true);
        mCommitRefundTv = (TextView) findViewById(R.id.commit_refund_tv);
        mCommitRefundTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected OrderPresent createPresenter() {
        return new OrderPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case MainContract.UPLOAD_IMAGES:
                //发送图片
                List<String> picPaths = (List<String>) o;
                // TODO: 2022/5/15 调用提交申请的接口


                break;
            case AppHttpPathMall.GET_REFUND_REASON:

                RefundReasonBean refundReasonBean = (RefundReasonBean) o;
                if (refundReasonBean != null) {
                    List<RefundReasonBean.DataDTO> dataDTOS = refundReasonBean.getData();
                    if (reasonId>0) {
                        for (RefundReasonBean.DataDTO dataDTO : dataDTOS) {
                            if (reasonId==dataDTO.getId()) {
                                dataDTO.setSelect(true);
                            }
                        }
                    }

                    SelectRefundReasonFragment selectRefundReasonFragment = new SelectRefundReasonFragment();
                    selectRefundReasonFragment.show(getSupportFragmentManager(), "SelectRefundReasonFragment");
                    selectRefundReasonFragment.setDatas(dataDTOS);
                    selectRefundReasonFragment.setOnReasonSelectedCallBack(new SelectRefundReasonFragment.OnReasonSelectedCallBack() {
                        @Override
                        public void selectedReason(RefundReasonBean.DataDTO dataDTO) {
                            mItemMyinfoValue.setText(dataDTO.getCauseName());
                            reasonId = dataDTO.getId();
                        }
                    });
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
            case R.id.key_value_ll:
                // : 2022/5/15 退款原因
                //获取退款原因
                mPresenter.getRefundReasons(getBaseBuilder().add("typeId", "1").build(), AppHttpPathMall.GET_REFUND_REASON);


                break;
            case R.id.commit_refund_tv:
                if (reasonId==0) {
                     ToastUtils.toast(mContext,"请选择退款原因");
                    return;
                }
                if (TextUtils.isEmpty(getTextViewValue(mRefundReasonEt))) {
                    ToastUtils.toast(mContext, "请输入原因");
                    return;
                }

                // : 2022/5/15 提交申请
                //首先上传图片
                //将图片上传
                List<String> icons = selectPhotosFragment.getPhotosPath();
                if (icons.size()>0) {
                    mPresenter.uploadFile(icons, MainContract.UPLOAD_IMAGES);
                }else {
                    // TODO: 2022/5/15 调用提交申请的接口
                }
                break;
        }
    }


    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }
}
