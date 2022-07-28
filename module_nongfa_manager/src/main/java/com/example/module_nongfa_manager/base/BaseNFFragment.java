package com.example.module_nongfa_manager.base;

import com.example.appbase.base.BaseAppModuleFragment;
import com.example.appbase.base.BaseAppPresent;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public abstract class BaseNFFragment<P extends BaseAppPresent> extends BaseAppModuleFragment<P> {

    @Override
    protected void lazyloadGone() {

    }


}
