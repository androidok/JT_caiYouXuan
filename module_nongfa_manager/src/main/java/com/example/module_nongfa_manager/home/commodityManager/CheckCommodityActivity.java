package com.example.module_nongfa_manager.home.commodityManager;

import android.text.TextUtils;
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
public class CheckCommodityActivity extends BaseMultiRecyclerActivity {

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
        return "商品审核";
    }


    @Override
    public void initData() {
        super.initData();
        commodityId = getIntent().getIntExtra(BASE_ID, 0);
        baseQuickAdapter.setNewData(mPresenter.checkCommodity(null, true));
        mPresenter.getManagerCommodityDetail(getBaseBuilder().add("commodityId",String.valueOf(commodityId)).build(), AppHttpPath.MANAGER_COMMODITY_DETAIL);

    }

    @Override
    protected View getAdapterFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nf_manager_check_footview_commit, null);
        mAgreeRb = (RadioButton) view.findViewById(R.id.agree_rb);
        mDisagreeRb = (RadioButton) view.findViewById(R.id.disagree_rb);
        mItemRadioG = (RadioGroup) view.findViewById(R.id.item_radio_g);
        mRejectReasonEt = (EditText) view.findViewById(R.id.reject_reason_et);
        LinearLayout rejectLl = (LinearLayout) view.findViewById(R.id.reject_ll);
        mCommitTv = (TextView) view.findViewById(R.id.commit_tv);
        mCommitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDisagreeRb.isChecked()) {
                    if (TextUtils.isEmpty(getTextViewValue(mRejectReasonEt))) {
                        ToastUtils.toast(mContext, "请输入拒绝原因");
                        return;
                    }
                }
                mPresenter.updateCommodityStatus(getBaseBuilder()
                        .add("state", String.valueOf(checkStatus))
                        .add("content", getTextViewValue(mRejectReasonEt))
                        .add("commodityId", String.valueOf(commodityId)).build(), AppHttpPath.UPDATE_COMMODITY_STATUS);

            }
        });

        mItemRadioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.agree_rb) {
                    rejectLl.setVisibility(View.GONE);
                    checkStatus = 2;
                } else if (checkedId == R.id.disagree_rb) {
                    rejectLl.setVisibility(View.VISIBLE);
                    checkStatus = 3;
                }
            }
        });
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
                        baseQuickAdapter.setNewData(mPresenter.checkCommodity(bean, true));
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
