package com.juntai.disabled.basecomponent.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;

import com.alibaba.android.arouter.launcher.ARouter;
import com.juntai.disabled.basecomponent.BuildConfig;
import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.utils.BaseAppUtils;
import com.juntai.disabled.basecomponent.utils.CrashHandler;
import com.juntai.disabled.basecomponent.utils.LogUtil;
import com.juntai.disabled.basecomponent.utils.NavigationBarInfo;
import com.juntai.disabled.basecomponent.utils.ObjectBox;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.entry.DefaultApplicationLike;


public abstract class BaseApplication extends DefaultApplicationLike {
    public static int HEIGHT, width, statusBarH;
    public static int navigationBarH;
    public static Application app;
    public static boolean isReLoadWarn = true;//登录被顶，是否提示

    public BaseApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplication();
        getScreen(getApplication());
        ObjectBox.init(getApplication());
        CrashHandler.getInstance().init(getApplication(), BaseAppUtils.getAppName());

        if (BuildConfig.DEBUG) {
            //
            LogUtil.logInit(true);
        }
        initArouter();
        hotFix();
    }

    private void hotFix() {
        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁，默认为true
        Beta.canAutoDownloadPatch = true;
        // 设置是否自动合成补丁，默认为true
        Beta.canAutoPatch = true;
        // 设置是否提示用户重启，默认为false
        Beta.canNotifyUserRestart = false;
        // 补丁回调接口
//        Beta.betaPatchListener = new BetaPatchListener() {
//            @Override
//            public void onPatchReceived(String patchFile) {
//                Toast.makeText(getApplication(), "补丁下载地址" + patchFile, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadReceived(long savedLength, long totalLength) {
//                Toast.makeText(getApplication(), String.format(Locale.getDefault(), "%s %d%%", Beta.strNotificationDownloading, (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadSuccess(String msg) {
//                Toast.makeText(getApplication(), "补丁下载成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadFailure(String msg) {
//                Toast.makeText(getApplication(), "补丁下载失败", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onApplySuccess(String msg) {
//                Toast.makeText(getApplication(), "补丁应用成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplyFailure(String msg) {
//                Toast.makeText(getApplication(), "补丁应用失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPatchRollback() {
//
//            }
//        };

        // 设置开发设备，默认为false，上传补丁如果下发范围指定为“开发设备”，需要调用此接口来标识开发设备
        Bugly.setIsDevelopmentDevice(getApplication(), false);
        // 调试时，将第三个参数改为true
        Bugly.init(getApplication(), "6d495ac5a2", true);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    /**
     * 初始化路由
     */
    private void initArouter() {
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        ARouter.init(getApplication());
    }




    public void getScreen(Context aty) {
        //依赖于手机系统，获取到的是系统的屏幕信息；
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        HEIGHT = dm.heightPixels;
        width = dm.widthPixels;
        int resourceId = getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarH = getApplication().getResources().getDimensionPixelSize(resourceId);
        }

        if (NavigationBarInfo.hasNavBar(aty)) {
            int navigationId =getApplication(). getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (navigationId > 0) {
                navigationBarH =getApplication(). getResources().getDimensionPixelSize(navigationId);
            }
        }
    }



    /**
     * 下拉刷新
     * static 代码段可以防止内存泄露
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.black);//全局设置主题颜色
                return new ClassicsHeader(context).setTextSizeTitle(13).setTextSizeTime(10).setDrawableArrowSize(15);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(15).setTextSizeTitle(13);
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Beta.unInit();
    }


}
