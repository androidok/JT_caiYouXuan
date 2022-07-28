package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.beans.BaseAdapterDataBean;
import com.juntai.project.sell.mall.home.shop.BaseShopActivity;

/**
 * @aouther tobato
 * @description 描述  添加商品溯源
 * @date 2022/7/28 9:56
 */
public class AddCommoditySourceActivity extends BaseShopActivity {


    @Override
    protected String getTitleName() {
        return "添加商品溯源";
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setNewData(mPresenter.getCommoditySourceData(null, false));


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
        View view = LayoutInflater.from(mContext).inflate(R.layout.sell_footview_commit, null);
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAdapterDataBean baseAdapterDataBean = getBaseOfAdapterData();
                if (baseAdapterDataBean != null) {
                }


            }
        });
        commitTv.setText("提交");
        return view;
    }
}
