package com.juntai.wisdom.project.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseAppFragment;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/9 17:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/9 17:26
 */
public class HomeCommodityFragment extends BaseAppFragment<HomePagePresent> implements HomePageContract.IHomePageView, View.OnClickListener{
    private LinearLayout mSearchLl;
    private ImageView mSwitchModeIv;
    @Override
    protected HomePagePresent createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.home_commodity_fragment;
    }

    @Override
    protected void initView() {
        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSwitchModeIv = (ImageView) getView(R.id.switch_mode_iv);
        mSwitchModeIv.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_mode_iv:
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.HOME_PAGE_DISPLAY_MODE,1));
                break;
            default:
                break;
        }
    }
}
