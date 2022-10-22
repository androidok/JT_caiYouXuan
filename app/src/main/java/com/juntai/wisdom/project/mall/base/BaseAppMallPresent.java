package com.juntai.wisdom.project.mall.base;

import com.example.appbase.base.BaseAppPresent;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.wisdom.project.mall.home.HomePageContract;

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
