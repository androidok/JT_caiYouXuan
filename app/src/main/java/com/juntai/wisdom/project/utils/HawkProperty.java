package com.juntai.wisdom.project.utils;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/27 10:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 10:59
 */
public class HawkProperty {
    public final static String SP_KEY_USER = "userBean";
    public final static String SP_KEY_TOKEN = "token";
    public final static String LOCAL_LABEL = "localLabel";
    public final static String SHOP_DETAIL = "shopDetail";
    public static String DEV_REGID = "dev_regid";
    public static String ALL_PROVINCE_KEY = "ALL_PROVINCE_KEY";
    public static String DEFAULT_ADDR = "DEFAULT_ADDR";

    /**
     * 好友请求
     */
    public static String FRIEND_APPLY = "friendApply";
    public static String UNREAD = "unreadCount";


    /**
     * 获取商铺的key
     * @return
     */
    public static String  getShopKey(int shopId){
        return SHOP_DETAIL+shopId;
    }
    public static String  getAllProvinceKey(String acode){
        return ALL_PROVINCE_KEY+acode;
    }
}
