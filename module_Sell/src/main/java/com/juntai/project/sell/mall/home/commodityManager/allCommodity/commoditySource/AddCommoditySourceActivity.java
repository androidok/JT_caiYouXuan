package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.appbase.base.customview.selectPics.SelectPicVideoRv;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.example.appbase.bean.multiBean.BaseAdapterDataBean;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.utils.GsonTools;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.home.shop.BaseShopActivity;

/**
 * @aouther tobato
 * @description 描述  添加商品溯源
 * @date 2022/7/28 9:56
 */
public class AddCommoditySourceActivity extends BaseShopActivity {

    private CommoditySourceDetailBean.DataBean.PhotoListBean billBean;
    private int currentViewId;
    private int commodityId;
    private SelectPicVideoRv selectPicVideoRv;

    @Override
    protected String getTitleName() {
        return "添加商品溯源";
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setNewData(mPresenter.getCommoditySourceData(null, false));
        commodityId = getIntent().getIntExtra(BASE_ID, 0);
        mPresenter.getCommoditySource(getBaseBuilder().add("commodityId",String.valueOf(commodityId)).build(), AppHttpPathMall.GET_COMMODITY_SOURCE_DETAIL);
    }

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
        View view = LayoutInflater.from(mContext).inflate(R.layout.sell_commodity_source_footview_commit, null);
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAdapterDataBean baseAdapterDataBean = getBaseOfAdapterData();
                if (baseAdapterDataBean != null) {
                    CommoditySourceDetailBean.DataBean sourceBean = baseAdapterDataBean.getSourceBean();
                    if (sourceBean != null) {
                        sourceBean.setAccount(UserInfoManager.getAccount());
                        sourceBean.setToken(UserInfoManager.getUserToken());
                        sourceBean.setCommodityId(commodityId);
                        mPresenter.addCommoditySource(getJsonRequestBody(GsonTools.createGsonString(sourceBean)), AppHttpPathMall.ADD_COMMODITY_SOURCE);

                    }
                }


            }
        });
        commitTv.setText("提交");
        return view;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPathMall.ADD_COMMODITY_SOURCE:
                ToastUtils.toast(mContext, "提交成功");
                finish();
                break;
            case AppHttpPathMall.GET_COMMODITY_SOURCE_DETAIL:
                CommoditySourceDetailBean commoditySourceDetailBean = (CommoditySourceDetailBean) o;
                if (commoditySourceDetailBean != null) {
                    CommoditySourceDetailBean.DataBean dataBean = commoditySourceDetailBean.getData();
                    if (dataBean != null) {
                        baseQuickAdapter.setNewData(mPresenter.getCommoditySourceData(dataBean, false));
                    }
                }

                break;
            default:
                break;
        }
    }

}
