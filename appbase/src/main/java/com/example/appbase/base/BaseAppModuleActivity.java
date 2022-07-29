package com.example.appbase.base;

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.example.appbase.base.selectPics.BaseSelectPicsActivity;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppModuleActivity<P extends BasePresenter> extends BaseSelectPicsActivity<P> {


    @Override
    protected String getUpdateHttpUrl() {
        return null;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManager.getAccount())
                .add("token", UserInfoManager.getUserToken())
                .add("typeEnd", UserInfoManager.getDevType())
                .add("userId", String.valueOf(UserInfoManager.getUserId()))
                .add("schoolId", String.valueOf(UserInfoManager.getSchoolId()));


        return builder;
    }


    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }



    @Override
    public boolean requestLocation() {
        return false;
    }



    @Override
    protected void selectedPicsAndEmpressed(List<String> icons) {

    }
    @Override
    protected void onPicsAndEmpressed(List<String> icons) {

    }

    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

}
