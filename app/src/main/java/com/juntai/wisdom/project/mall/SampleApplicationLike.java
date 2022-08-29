package com.juntai.wisdom.project.mall;


import android.app.Application;
import android.content.Intent;

import com.example.chat.MyChatApp;
import com.juntai.disabled.video.ModuleVideo_Init;
import com.mob.MobSDK;

/**
 * @aouther Ma
 * @date 2019/3/12
 */
public class SampleApplicationLike extends MyChatApp {

    public SampleApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Video模块初始化
        ModuleVideo_Init.init();
        MobSDK.init(getApplication());
        MobSDK.submitPolicyGrantResult(true);
    }




}
