package com.juntai.project.sell.mall.home.shop;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.bean.multiBean.BaseAdapterDataBean;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.base.BaseWebViewActivity;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.utils.StringTools;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述 店铺管理
 * @date 2022/6/9 14:15
 */

@Route(path = ARouterPath.sellShopManager)
public class ShopManagerActivity extends BaseShopActivity {


    private RadioButton mShopProtocalRb;
    private boolean isAgree = false;
    private ShopDetailSellBean.DataBean dataBean;
    private TextView shopPtTv;

    @Override
    protected boolean isDetail() {
        return false;
    }


    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sell_footview_commit, null);
        TextView commitTv = view.findViewById(R.id.commit_tv);
        mShopProtocalRb = view.findViewById(R.id.shop_protocal_rb);
        view.findViewById(R.id.shop_protocal_ll).setVisibility(View.VISIBLE);
        commitTv.setOnClickListener(this);
        shopPtTv = view.findViewById(R.id.open_shop_protocal_tv);
        shopPtTv.setOnClickListener(this);
        if (dataBean != null) {
            commitTv.setText("提交店铺修改");
        } else {
            commitTv.setText("提交店铺申请");

        }
        mShopProtocalRb.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        dataBean = getIntent().getParcelableExtra(BASE_PARCELABLE);
        baseQuickAdapter.setNewData(mPresenter.getShopManagerData(dataBean, isDetail()));
        String openShopPt = getString(R.string.open_shop_protocal_notice);
        StringTools.setTextPartColor(shopPtTv, openShopPt, openShopPt.indexOf("《"), openShopPt.length()-2,
                "#00c750");
    }

    @Override
    public boolean requestLocation() {
        return dataBean == null;
    }

    @Override
    protected String getTitleName() {
        return "店铺管理";
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.commit_tv) {// : 2022/6/9 提交店铺申请
            BaseAdapterDataBean baseAdapterDataBean = getBaseOfAdapterData();
            if (baseAdapterDataBean == null) {
                return;
            }
            FormBody.Builder builder = baseAdapterDataBean.getBuilder();

            if (!isAgree) {
                ToastUtils.toast(mContext, "请同意开店协议之后再提交");
                return;
            }
            showAlertDialog("是否确认提交审核？\n" +
                    "提交后由农发后台审核，审核期间店铺暂时不能使用！", "确定", "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    builder.add("isAgreement", "1");
                    if (dataBean != null) {
                        builder.add("id", String.valueOf(UserInfoManagerMall.getShopId()));
                        mPresenter.eidtShopApply(builder.build(), AppHttpPathMall.SHOP_APPLY);

                    } else {
                        mPresenter.shopApply(builder.build(), AppHttpPathMall.SHOP_APPLY);

                    }
                }
            });



        } else if (id == R.id.shop_protocal_rb) {// : 2022/6/9 协议
            if (isAgree) {
                mShopProtocalRb.setChecked(false);
            } else {
                mShopProtocalRb.setChecked(true);
            }
            isAgree = !isAgree;
        } else if (id == R.id.open_shop_protocal_tv) {
            startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url",
                    "file:///android_asset/kaidianxieyi.html"));
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPathMall.SHOP_APPLY:
                if (UserInfoManagerMall.getShopStatus() == 2) {
                    ToastUtils.toast(mContext, "已提交 等待审核");
                } else {
                    showAlertDialog("店铺认证已提交审核,请耐心等待", "知道了", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityManagerTool.getInstance().finishApp();
                            reLogin(UserInfoManager.getPhoneNumber());
                        }
                    });
                }
                break;
            default:
                break;
        }
    }
}
