package com.example.appbase.base;


import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.mvp.IPresenter;

/**
 * @aouther tobato
 * @description 描述  app的fragment的基类
 * @date 2020/7/18 16:43
 */
public abstract class BaseAppModuleFragment<P extends IPresenter> extends BaseMvpFragment<P> {


    /**
     * 获取activity
     *
     * @return
     */
    public BaseAppModuleActivity getBaseAppActivity() {
        return (BaseAppModuleActivity) getActivity();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }
}
