package com.example.chat;


import com.example.chat.bean.ChatUserBean;
import com.example.chat.bean.ContactInfoBean;
import com.example.chat.bean.ContactListBean;
import com.example.chat.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.weather.CityBean;
import com.juntai.disabled.basecomponent.bean.weather.ResponseForcastWeather;
import com.juntai.disabled.basecomponent.bean.weather.ResponseRealTimeWeather;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServerChat {





    /**
     * 登录
     *
     * @return
     */
    @POST(AppHttpPathChat.REGIST)
    Observable<ChatUserBean> regist(@Body RequestBody requestBody);

    /**
     * 上传文件
     *
     * @return
     */
    @POST(AppHttpPathChat.UPLOAD_FILES)
    Observable<UploadFileBean> uploadFiles(@Body RequestBody requestBody);

    @POST(AppHttpPathChat.MODIFY_PWD)
    Observable<BaseResult> modifyPwd(@Query("userId") int userId, @Query("phoneNumber") String account, @Query("password") String password, @Query("code") String code
    );

    /**
     * account  手机号
     *
     * @return
     */
    @GET(AppHttpPathChat.GET_SMS_CODE + "/{phoneNumber}")
    Observable<BaseResult> getSMSCode(@Path("phoneNumber") String path);






    /*====================================================    个人中心   ==============================================================*/


    /**
     * account  手机号
     *
     * @return
     */
    @POST(AppHttpPathChat.GET_USER_INFO)
    Observable<ChatUserBean> getUserInfo(@Body RequestBody requestBody);

    /**
     * 修改账户
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPathChat.MODIFY_USER_ACCOUNT)
    Observable<BaseResult> modifyAccount(@Body RequestBody requestBody);


    @POST(AppHttpPathChat.COMMIT_SUGGESTION)
    Observable<BaseResult> commitSuggestion(@Body RequestBody requestBody);

    /**
     * 修改用户信息
     *
     * @param requestBody
     * @return
     */
    @POST(AppHttpPathChat.MODIFY_USER_INFO)
    Observable<BaseResult> modifyUserInfo(@Body RequestBody requestBody);

    @POST(AppHttpPathChat.LOGOUT)
    Observable<BaseResult> logout(@Body RequestBody requestBody);





}