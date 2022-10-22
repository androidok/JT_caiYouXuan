package com.example.module_nongfa_manager.home.commodityManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appbase.base.multi.BaseMultiRecyclerActivity;
import com.example.appbase.bean.nong_fa_manager.CommodityManagerDetailBean;
import com.example.module_nongfa_manager.R;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

/**
 * @aouther tobato
 * @description 描述  审核商品
 * @date 2022/7/29 16:23
 */
public class NfCommodityDetailActivity extends BaseMultiRecyclerActivity {

    /**
     * 是
     */
    private RadioButton mAgreeRb;
    /**
     * 否
     */
    private RadioButton mDisagreeRb;
    private RadioGroup mItemRadioG;
    private EditText mRejectReasonEt;
    /**
     * 提交
     */
    private TextView mCommitTv;
    private int commodityId;
    //（2审核通过；3审核失败）
    private int checkStatus = 2;
    @Override
    protected boolean isDetail() {
        return true;
    }

    @Override
    protected String getTitleName() {
        return "商品详情";
    }

    @Override
    public void initView() {
        checkStatus = getIntent().getIntExtra(BASE_ID, 0);
        commodityId = getIntent().getIntExtra(BASE_ID2, 0);
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();

        baseQuickAdapter.setNewData(mPresenter.checkCommodity(null, 1!=checkStatus));
        mPresenter.getManagerCommodityDetail(getBaseBuilder().add("commodityId",String.valueOf(commodityId)).build(), AppHttpPath.MANAGER_COMMODITY_DETAIL);

    }

    @Override
    protected View getAdapterFootView() {
        if (1==checkStatus) {
            return null;
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.nf_manager_check_footview_commit, null);
        mAgreeRb = (RadioButton) view.findViewById(R.id.agree_rb);
        mDisagreeRb = (RadioButton) view.findViewById(R.id.disagree_rb);
        mItemRadioG = (RadioGroup) view.findViewById(R.id.item_radio_g);
        mRejectReasonEt = (EditText) view.findViewById(R.id.reject_reason_et);
        mRejectReasonEt.setFocusable(false);
        mRejectReasonEt.setClickable(false);
        LinearLayout rejectLl = (LinearLayout) view.findViewById(R.id.reject_ll);
        view.findViewById(R.id.commit_tv).setVisibility(View.GONE);
        mItemRadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.agree_rb) {
                    rejectLl.setVisibility(View.GONE);
                } else if (checkedId == R.id.disagree_rb) {
                    rejectLl.setVisibility(View.VISIBLE);
                }
            }
        });

        for (int i = 0; i < mItemRadioG.getChildCount(); i++) {
            mItemRadioG.getChildAt(i).setEnabled(false);
        }
        if (2== checkStatus) {
            mAgreeRb.setChecked(true);
        }else {
            mDisagreeRb.setChecked(true);
        }
        return view;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPath.MANAGER_COMMODITY_DETAIL:
                CommodityManagerDetailBean detailBean = (CommodityManagerDetailBean) o;
                if (detailBean != null) {
                    CommodityManagerDetailBean.DataBean bean = detailBean.getData();
                    if (bean != null) {
                        baseQuickAdapter.setNewData(mPresenter.checkCommodity(bean, 1!=checkStatus));
                        if (3== checkStatus) {
                            mRejectReasonEt.setText(bean.getStateContent());
                        }
                    }
                }
                break;
            case AppHttpPath.UPDATE_COMMODITY_STATUS:
                ToastUtils.toast(mContext, "已处理");
                setResult(BaseActivity.BASE_RSULT);
                finish();
                break;
            default:
                break;
        }
    }
}
