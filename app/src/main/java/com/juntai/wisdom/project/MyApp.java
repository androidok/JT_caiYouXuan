package com.juntai.wisdom.project;


import android.app.Activity;
import android.text.TextUtils;

import com.example.chat.MyChatApp;
import com.juntai.disabled.video.ModuleVideo_Init;
import com.mob.MobSDK;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @aouther Ma
 * @date 2019/3/12
 */
public class MyApp extends MyChatApp {
    public static MyApp app;
    public boolean isFinish = false;
    public static long lastClickTime;//上次点击按钮时间
    public static int timeLimit = 1000;




    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        MobSDK.init(this);
        //Video模块初始化
        ModuleVideo_Init.init();
    }


    @Override
    public void appBackground(boolean isBackground, Activity activity) {
        if (isBackground && !isFinish) {
            //            NitoficationTool.sendNotif(activity,
            //                    11,
            //                    "挂起",
            //                    BaseAppUtils.getAppName() + "服务正在运行",
            //                    R.mipmap.app_icon,
            //                    true,
            //                    new Intent(activity, ChatMainActivity.class));
        } else {
            //变为前台
            //            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //            manager.cancelAll();
        }
    }


    /**
     * 防止暴力点击
     */
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < timeLimit) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

}
