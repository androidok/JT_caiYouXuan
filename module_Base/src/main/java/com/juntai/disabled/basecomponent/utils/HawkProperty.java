package com.juntai.disabled.basecomponent.utils;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/27 10:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 10:59
 */
public class HawkProperty {
    public final static String SP_KEY_USER = "SP_KEY_USER";
    public final static String SP_KEY_TOKEN = "token";
    public final static String LOCAL_LABEL = "localLabel";
    public final static String SHOP_DETAIL = "shopDetail";
    public final static String HOME_SEARCH = "home_search";
    public static String DEV_REGID = "dev_regid";
    public static String ALL_PROVINCE_KEY = "ALL_PROVINCE_KEY";
    public static String DEFAULT_ADDR = "DEFAULT_ADDR";


    /**
     * 所有联系人
     */
    public static String All_CONTACT = "All_CONTACT";


    /**
     * 获取商铺的key
     *
     * @return
     */
    public static String getShopKey(int shopId) {
        return SHOP_DETAIL + shopId;
    }
    /**
     * 获取首页搜索历史记录的key
     *
     * @return
     */
    public static String getHomeSearchHisKey(int userId) {
        return HOME_SEARCH + userId;
    }

    public static String getAllProvinceKey(String acode) {
        return ALL_PROVINCE_KEY + acode;
    }




}
