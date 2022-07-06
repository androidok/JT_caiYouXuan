package com.example.live_moudle.net;


import com.example.live_moudle.bean.CommodityDetailBean;
import com.example.live_moudle.bean.LiveResultBean;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.LiveTypeListBean;
import com.juntai.disabled.basecomponent.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.bean.shop.ShopCommodityListBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * responseBody里的数据只能调用(取出)一次，第二次为空。可赋值给新的变量使用
 */
public interface AppServerLive {



        /*====================================================    直播   ==============================================================*/

    /**
     * 获取直播类型
     * @return
     */
    @POST(AppHttpPathLive.GET_LIVE_TYPE)
    Observable<LiveTypeListBean> getLiveType();

    /**
     * 开启直播
     * @param requestBody
     * @return
     */
    @POST(AppHttpPathLive.START_LIVE)
    Observable<LiveResultBean> startLive(@Body RequestBody requestBody);


    /**
     * 上传文件
     *
     * @return
     */
    @POST(AppHttpPathLive.UPLOAD_PICS)
    Observable<UploadFileBean> uploadFiles(@Body RequestBody requestBody);
    /**
     * 上传文件
     *
     * @return
     */
    @POST(AppHttpPathLive.LIVE_ROOM_COMMODITIES)
    Observable<ShopCommodityListBean> getLiveRoomCommodities(@Body RequestBody requestBody);


    @POST(AppHttpPathLive.SHOP_COLLECT)
    Observable<BaseResult> collectShop(@Body RequestBody requestBody);

    @POST(AppHttpPathLive.COMMODIFY_DETAIL)
    Observable<CommodityDetailBean> getCommodityDetail(@Body RequestBody requestBody);
}