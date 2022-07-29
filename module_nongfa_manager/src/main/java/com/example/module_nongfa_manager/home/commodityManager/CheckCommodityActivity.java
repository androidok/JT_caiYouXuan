package com.example.module_nongfa_manager.home.commodityManager;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.appbase.base.multi.BaseMultiRecyclerActivity;
import com.example.module_nongfa_manager.R;

/**
 * @aouther tobato
 * @description 描述  审核商品
 * @date 2022/7/29 16:23
 */
public class CheckCommodityActivity extends BaseMultiRecyclerActivity {


    @Override
    protected boolean isDetail() {
        return false;
    }

    @Override
    protected String getTitleName() {
        return "商品审核";
    }


    @Override
    public void initData() {
        super.initData();

       baseQuickAdapter.setNewData( mPresenter.checkCommodity(null,false));
    }

    @Override
    protected View getAdapterFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nf_manager_footview_commit, null);
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
