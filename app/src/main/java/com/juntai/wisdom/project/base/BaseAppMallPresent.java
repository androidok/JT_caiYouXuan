package com.juntai.wisdom.project.base;

import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.wisdom.project.home.HomePageContract;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/8 8:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/8 8:52
 */
public abstract class BaseAppMallPresent extends BaseAppPresent<IModel, HomePageContract.IHomePageView> {

    @Override
    protected IModel createModel() {
        return null;
    }
}
