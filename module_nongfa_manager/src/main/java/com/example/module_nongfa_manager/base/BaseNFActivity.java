package com.example.module_nongfa_manager.base;

import com.example.appbase.base.BaseAppModuleActivity;
import com.example.appbase.base.BaseAppPresent;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public abstract class BaseNFActivity<P extends BaseAppPresent> extends BaseAppModuleActivity<P> {


    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
