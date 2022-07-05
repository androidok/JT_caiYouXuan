package com.example.live_moudle.net;

import com.juntai.disabled.basecomponent.net.AppHttpPath;

public class AppHttpPathLive {





    /*====================================================    live   ==============================================================*/


    public static final String BASE_LIVE_URL ="ws://www.juntaikeji.com:21970/jt-mall/liveSocket";
//    public static final String BASE_LIVE_URL ="ws://42.192.40.58:5000/ws";


    /**
     * 直播类型
     */
    public static final String GET_LIVE_TYPE = AppHttpPath.BASE + "/live/getLiveType";
    /**
     * 开启直播
     */
    public static final String START_LIVE = AppHttpPath.BASE + "/live/openLive";


    /**
     * 上传图片
     */
    public static final String UPLOAD_PICS =AppHttpPath.BASE + "/uploadFile/upload";
    /**
     * 直播间商品列表
     */
    public static final String LIVE_ROOM_COMMODITIES =AppHttpPath.BASE + "/buyers/selectLiveCommodityList";


    /**
     * 店铺收藏或取消
     */
    public static final String SHOP_COLLECT = AppHttpPath.BASE + "/buyers/addCollectShop";
    public static final String SHOP_COLLECT_FINISH = AppHttpPath.BASE + "/buyers/addCollectShop///finish";
    public static final String SHOP_UNCOLLECT = AppHttpPath.BASE + "/buyers/unCollectShop";





}