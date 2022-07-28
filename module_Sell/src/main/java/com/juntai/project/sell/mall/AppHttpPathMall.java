package com.juntai.project.sell.mall;

import com.example.net.AppHttpPath;

public class AppHttpPathMall {


    public static final String BASE = AppHttpPath.BASE;

    /*==============================================  流媒体相关  =============================================*/


    //摄像头拉流地址
    public static final String BASE_CAMERA_URL = "http://www.juntaikeji.net:8060";
    /**
     * 获取视频播放地址
     */
    public static final String STREAM_OPE_ADDR = "http://61.156.157.132:35080/streamingMedia/u/app/getVideoOpenStream.shtml";

    /**
     * 上传图片或视频
     */
    public static final String UPLOAD_FILES = BASE + "/uploadFile/upload";
    public static final String UPLOAD_ONE_PIC = "/uploadFile/uploadonepic";
    public static final String UPLOAD_MORE_PIC = "/uploadFile/uploadmorepic";


    /**
     * /**
     * 检查更新
     */
    public static final String APP_UPDATE = BASE + "/member/detectionAppVersions/2";

    /**
     * 获取短信验证码
     */
    public static final String GET_SMS_CODE = BASE + "/member/getSMSCode";








    /*====================================================    个人中心   ==============================================================*/


    /**
     * 个人详情
     */
    public static final String GET_USER_INFO = BASE + "/member/getUserInfo";
    /**
     * 退出登录
     */
    public static final String LOGOUT = BASE + "/member/logout";

    /**
     * 修改密码
     */
    public static final String MODIFY_PWD = BASE + "/member/updateMemberPwd";
    /**
     * 修改手机号
     */
    public static final String MODIFY_PHONE = BASE + "/member/updateMemberPhone";
    /**
     * 修改个人信息
     */
    public static final String MODIFY_USER_INFO = BASE + "/member/updateMember";

    /**
     * 修改账户
     */
    public static final String MODIFY_USER_ACCOUNT = BASE + "/member/updateUserAccountNumber";


    /**
     * 用户实名认证
     */
    public static final String USER_AUTH = "https://www.dgjpcs.cn/server/dongGuanPoliceStation/u/webConnector/realNameAuthentication.shtml";










    /*====================================================    店铺   ==============================================================*/


    /**
     * 店铺商品列表
     */
    public static final String SHOP_COMMODITY_LIST = BASE + "/buyers/selectShopCommodityList";















    /*====================================================    订单相关   ==============================================================*/

    /**
     * 提交订单
     */
    public static final String COMMIT_ORDER = BASE + "/seller/submitOrderForm";
    /**
     * 取消订单
     */
    public static final String CANCEL_ORDER = BASE + "/buyers/cancelOrderForm";
    /**
     * 提醒发货
     */
    public static final String NOTICE_SEND = BASE + "/seller/remindDelivery";
    /**
     * 删除已取消订单
     */
    public static final String DELETE_CANCEL_ORDER = BASE + "/seller/deleteOrder";
    /**
     * 确认收货
     */
    public static final String CONFIRM_RECEIVED = BASE + "/seller/confirmOrderForm";
    /**
     * 订单状态
     */
    public static final String ORDER_STATUS = BASE + "/seller/getOrderFormStateNum";
    /**
     * 订单列表
     */
    public static final String ORDER_LIST = BASE + "/seller/selectOrderFormList";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = BASE + "/seller/selectOrderFormInfo";

    /**
     * 对公付款
     */
    public static final String ORDER_PAY_PUB_ACCOUNT = BASE + "/seller/publicPay/publicPayTradeAppPayRequest";
    /**
     * 微信支付
     */
    public static final String ORDER_PAY_PUB_WEIXIN = BASE + "/seller/weChatPay/weChatPayTradeAppPayRequest";
    /**
     * 支付宝支付
     */
    public static final String ORDER_PAY_ZHIFUBAO = BASE + "/seller/aliPay/aliPayTradeAppPayRequest";

    /**
     * 退货原因
     */
    public static final String GET_REFUND_REASON = BASE + "/seller/getSalesReturnCause";

    /**
     * 申请退款
     */
    public static final String REQUEST_REFUND = BASE + "/seller/addSalesReturn";

    /**
     * 发布评价
     */
    public static final String START_EVALUATE = BASE + "/seller/addCommodityEvaluate";





    /*====================================================    消息   ==============================================================*/


    /**
     * 发送消息
     */
    public static final String SEND_MSG = BASE + "/msg/sendMsg";
    /**
     * 消息已读
     */
    public static final String MESSAGE_READ = BASE + "/msg/updateMsgRead";
    /**
     * 未读消息详情
     */
    public static final String UNREAD_CONTACT_MSG = BASE + "/msg/selectMsgInfo";
    /**
     * 消息列表
     */
    public static final String NEWS_LIST = BASE + "/msg/selectMsgList";


    /**
     * 搜索
     */
    public static final String SEARCH_COMMODITY = BASE + "/member/search";

    /**
     * 搜索
     */
    public static final String SEARCH_SHOP = BASE + "/member/search";







    /*====================================================    卖家端   ==============================================================*/


    /**
     * 首页
     */
    public static final String SHOP_HOME_INFO = BASE + "/seller/selectShopHome";
    /**
     * 系统公告和通知
     */
    public static final String GET_SYSTEM_NOTICE = BASE + "/seller/getSysNoticeList";

    /**
     * 系统公告和通知
     */
    public static final String GET_SYSTEM_NOTICE_DETAIL = BASE + "/seller/getSysNoticeInfo";




    /*====================================================    卖家店铺   ==============================================================*/


    /**
     * 店铺申请
     */
    public static final String SHOP_APPLY = BASE + "/seller/applyShop";
    /**
     * 编辑店铺申请
     */
    public static final String EDIT_SHOP_APPLY = BASE + "/seller/updateApplyShop";

    /**
     * 经营类目
     */
    public static final String ALL_SHOP_CATEGORY = BASE + "/seller/selectShopCategoryList";




    /*====================================================    商品类目   ==============================================================*/
    /**
     * 商品类目
     */
    public static final String ALL_COMMODITY_CATEGORY = BASE + "/seller/selectShopClassifyList";
    /**
     * 添加商品类目
     */
    public static final String ADD_COMMODITY_CATEGORY = BASE + "/seller/addShopClassify";
    /**
     * 修改商品类目
     */
    public static final String MODIFY_COMMODITY_CATEGORY = BASE + "/seller/updateShopClassify";
    /**
     * 删除商品类目
     */
    public static final String DELETE_COMMODITY_CATEGORY = BASE + "/seller/deleteShopClassify";




    /*====================================================    商品管理   ==============================================================*/

    /**
     * 商品列表
     */
    public static final String GET_ALL_COMMODITY = BASE + "/seller/selectCommodityList";
    /**
     * 商品详情
     */
    public static final String GET_COMMODITY_DETAIL = BASE + "/seller/selectCommodityInfo";
    /**
     * 添加商品基础信息
     */
    public static final String ADD_COMMODITY_BASE_INFO = BASE + "/seller/addCommodity";
    /**
     * 更新商品基础信息
     */
    public static final String UPDATE_COMMODITY_BASE_INFO = BASE + "/seller/updateCommodity";
    /**
     * 删除商品基础信息
     */
    public static final String DELETE_COMMODITY = BASE + "/seller/deleteCommodity";
    /**
     * 生成商品属性
     */
    public static final String CREATE_COMMODITY_FORMAT = BASE + "/seller/isFormatAttr";
    /**
     * 获取商品属性
     */
    public static final String GET_COMMODITY_FORMAT = BASE + "/seller/getCommodityAttr";
    /**
     * 添加修改 商品属性
     */
    public static final String EDIT_COMMODITY_FORMAT = BASE + "/seller/addCommodityAttr";
    /**
     * 添加修改 商品属性
     */
    public static final String MODIFY_COMMODITY_PRICE_STOCK= BASE + "/seller/updateCommodityAttrStockPrice";
    /**
     * 上架
     */
    public static final String COMMODITY_ON_SALE = BASE + "/seller/onSale";
    public static final String COMMODITY_ON_SALE_ = BASE + "/seller/onSale_";
    /**
     * 发货
     */
    public static final String SEND_GOODS = BASE + "/seller/addPhysicalDistribution";
    /**
     * 是否同意退货
     */
    public static final String REFUND_REQUEST = BASE + "/seller/orderRefund";




        /*====================================================    店铺装修   ==============================================================*/

    /**
     * 上传店铺背景图及形象图片
     */
    public static final String ADD_SHOP_BANNERS = BASE + "/seller/addShopPhoto";





        /*====================================================    财务管理   ==============================================================*/


    /**
     * 账单明细列表
     */
    public static final String BILL_LIST = BASE + "/seller/getBillList";

    /**
     * bill基础信息
     */
    public static final String BILL_BASE_INFO = BASE + "/seller/getFinanceInfo";
    /**
     * 提现列表
     */
    public static final String BILL_WITHDRAW = BASE + "/seller/getWithdrawList";


    /**
     * 月收入统计
     */
    public static final String MONTH_STATISTICS = BASE + "/seller/getMonthIncome";
    /**
     * 提现
     */
    public static final String WITHDRAW = BASE + "/seller/toWithdrawFunds";
    /**
     * 绑定银行卡
     */
    public static final String BIND_BANK_CARD = BASE + "/seller/bindingBankCard";


    /**
     * 添加商品溯源
     */
    public static final String ADD_COMMODITY_SOURCE = BASE + "/seller/addCommodityTraceability";












}