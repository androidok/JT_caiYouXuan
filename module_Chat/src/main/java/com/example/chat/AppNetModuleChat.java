package com.example.chat;


import com.juntai.disabled.basecomponent.net.ApiRetrofit;

/**
 * 网络请求
 */
public class AppNetModuleChat {
    static AppServerChat appServer ;
    public static AppServerChat createrRetrofit() {
        if (appServer == null){
            appServer = ApiRetrofit.getInstance().getApiService(AppServerChat.class);
        }
        return appServer;
    }
}
