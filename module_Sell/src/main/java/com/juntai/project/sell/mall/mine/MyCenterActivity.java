package com.juntai.project.sell.mall.mine;

import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.base.BaseAppActivity;

/**
 * @aouther tobato
 * @description 描述 个人中心
 * @date 2022/7/27 14:56
 */
public class MyCenterActivity extends BaseAppActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.sell_activity_my_center;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
