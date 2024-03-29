package com.juntai.wisdom.project.mall.entrance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.wisdom.project.mall.MainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;

/**
 * @aouther Ma
 * @date 2019/3/13
 */
public class SplashActivity extends RxAppCompatActivity {
    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};


    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RxPermissions(this)
                .request(permissions)
                .delay(1, TimeUnit.SECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //所有权限通过
                        } else {
                            //有一个权限没通过
                        }
                        if (UserInfoManager.isLogin()) {
                            //账号类型（1学校人员；2商户人员；3农发人员）
                            ContactBean contactBean = UserInfoManager.getUser();
                            int type = contactBean.getType();
                            switch (type) {
                                case 1:
                                    //买家
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                    finish();
                                    break;
                                default:
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                    finish();
                                    break;
                            }
                        }else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                    }
                });
    }
}
