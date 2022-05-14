package com.juntai.wisdom.project.live;


import android.view.View;

import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.home.HomePagePresent;
import com.juntai.wisdom.project.home.commodityfragment.CommodityPresent;
import com.juntai.wisdom.project.mine.MyCenterContract;

/**
 * @aouther tobato
 * @description 描述  直播
 * @date
 */
public class LiveFragment extends BaseMvpFragment<CommodityPresent> implements HomePageContract.IHomePageView,
        View.OnClickListener {

    @Override
    protected int getLayoutRes() {
        return R.layout.live_fg;
    }

    @Override
    protected void initView() {



    }

    @Override
    protected void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }


    @Override
    public void onSuccess(String tag, Object o) {
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
