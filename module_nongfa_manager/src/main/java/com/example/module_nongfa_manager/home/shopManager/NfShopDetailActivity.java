package com.example.module_nongfa_manager.home.shopManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.appbase.base.multi.BaseMultiRecyclerActivity;
import com.example.appbase.bean.nong_fa_manager.ShopManagerDetailBean;
import com.example.module_nongfa_manager.R;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

/**
 * @aouther tobato
 * @description 描述  审核店铺
 * @date 2022/7/29 16:23
 */
public class NfShopDetailActivity extends BaseMultiRecyclerActivity {


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
    private int shopId;
    //（2审核通过；3审核失败）
    private int labelId;

    @Override
    protected boolean isDetail() {
        return true;
    }
    @Override
    protected String getTitleName() {
        return "店铺审核";
    }

    @Override
    public void initView() {
        labelId = getIntent().getIntExtra(BASE_ID, 0);
        shopId = getIntent().getIntExtra(BASE_ID2, 0);
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setNewData(mPresenter.checkShop(null, 1!= labelId));
        mPresenter.getManagerShopDetail(getBaseBuilder().add("shopId", String.valueOf(shopId)).build(), AppHttpPath.MANAGER_SHOP_DETAIL);
    }

    @Override
    protected View getAdapterFootView() {
        if (1== labelId) {
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
        rejectLl.setVisibility(View.GONE);
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
        if (2== labelId) {
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
            case AppHttpPath.MANAGER_SHOP_DETAIL:
                ShopManagerDetailBean detailBean = (ShopManagerDetailBean) o;
                if (detailBean != null) {
                    ShopManagerDetailBean.DataBean bean = detailBean.getData();
                    if (bean != null) {
                        baseQuickAdapter.setNewData(mPresenter.checkShop(bean, 1!= labelId));
                        // : 2022/8/6  展示失败原因
                        if (3== labelId) {
                            mRejectReasonEt.setText(bean.getStateContent());
                        }
                    }
                }
                break;
            case AppHttpPath.COMMIT_SHOP_CHECK:
                ToastUtils.toast(mContext, "已处理");
                setResult(BaseActivity.BASE_RSULT);
                finish();
                break;
            default:
                break;
        }
    }

}
