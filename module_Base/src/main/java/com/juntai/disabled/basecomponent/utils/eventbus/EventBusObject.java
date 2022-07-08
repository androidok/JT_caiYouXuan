package com.juntai.disabled.basecomponent.utils.eventbus;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/6 14:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/6 14:10
 */
public class EventBusObject {
    /**
     * 处理接收到的消息
     */
    public final static String HANDLER_MESSAGE = "HANDLER_MESSAGE";
    /**
     * 更改购物车商品属性  数量或者规格
     */
    public final static String CHANGE_SHOP_CART_PROPERTY_AMOUNT = "change_cart_property_amount";
    /**
     * 获取商品详情接口
     */
    public final static String GET_COMMODITY_DETAIL_INFO = "GET_COMMODITY_DETAIL_INFO";
    /**
     * 购物车 全选按钮的状态
     */
    public final static String CHANGE_SELECT_ALL_BUTTON_STATUS = "CHANGE_SELECT_ALL_BUTTON_STATUS";
    /**
     * 更改商品排列方式
     */
    public final static String CHANGE_COMMODITY_ARRAY_TYPE = "CHANGE_COMMODITY_ARRAY_TYPE";
    /**
     * 首页切换module
     */
    public final static String HOME_PAGE_DISPLAY_MODE = "HOME_PAGE_DISPLAY_MODE";


    /**
     * 账户被顶之后
     */
    public final static String RE_LOAD = "reload";
    /**
     * 当获取到定位信息
     */
    public final static String ON_LOCATION_RECEIVED = "onLocationReceived";
    /**
     * 刷新订单列表
     */
    public final static String REFRESH_ORDER_LIST = "REFRESH_ORDER_LIST";

    /**
     * 首页商品列表刷新
     */
    public final static String REFRESH_HOMEPAGE_COMMODITY_LIST = "REFRESH_HOMEPAGE_COMMODITY_LIST";
    public final static String REFRESH_LIVE_COMMODITY_LIST = "REFRESH_LIVE_COMMODITY_LIST";

    /**
     * 刷新消息列表
     */
    public final static String REFRESH_NEWS_LIST = "REFRESH_NEWS_LIST";

    public final static String EVALUATE = "评价";
    /**
     * 创建订单
     */
    public final static String CREAT_ORDER = "CREAT_ORDER";
    /**
     * 分享
     */
    public final static String LIVE_SHARE = "LIVE_SHARE";
    public final static String WEIXIN_PAY_SUCCESS = "WEIXIN_PAY_SUCCESS";
    /**
     * 未读消息总数
     */
    public final static String UNREAD_MSG_AMOUNT = "UNREAD_MSG_AMOUNT";
    /**
     * 聊天内容
     */
    public final static String MESSAGE_BODY = "MESSAGE_BODY";
//    /**
//     * 进入订单详情
//     */
//    public final static  String START_TO_ORDER_INFO = "START_TO_ORDER_INFO";


    private String eventKey;

    private Object eventObj;

    public EventBusObject(String eventKey, Object eventObj) {
        this.eventKey = eventKey;
        this.eventObj = eventObj;
    }

    public String getEventKey() {
        return eventKey == null ? "" : eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? "" : eventKey;
    }

    public Object getEventObj() {
        return eventObj;
    }

    public void setEventObj(Object eventObj) {
        this.eventObj = eventObj;
    }
}
