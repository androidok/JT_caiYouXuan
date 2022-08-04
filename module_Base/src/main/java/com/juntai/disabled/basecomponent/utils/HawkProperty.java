package com.juntai.disabled.basecomponent.utils;


import android.content.Context;

import com.orhanobut.hawk.Hawk;

import me.leolin.shortcutbadger.ShortcutBadger;

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
    public final static String SHOP_NAME = "SHOP_NAME";
    public final static String LOCAL_LABEL = "localLabel";
    public final static String LOCAL_LIVE_LABEL = "LOCAL_LIVE_LABEL";
    public final static String SHOP_DETAIL = "shopDetail";
    public final static String HOME_SEARCH = "home_search";
    public static String DEV_REGID = "dev_regid";
    public static String ALL_PROVINCE_KEY = "ALL_PROVINCE_KEY";
    public static String DEFAULT_ADDR = "DEFAULT_ADDR";
    public static String UNREAD = "unreadCount";

    public static String COMMODITY_DETAIL = "COMMODITY_DETAIL";

    /**
     * 所有联系人
     */
    public static String All_CONTACT = "All_CONTACT";

    public static String getRegid() {
        if (Hawk.contains(HawkProperty.DEV_REGID)) {
            return Hawk.get(HawkProperty.DEV_REGID);
        }
        return "";
    }


    /**
     * 获取商铺的key
     *
     * @return
     */
    public static String getShopSellKey(int shopId) {
        return SHOP_DETAIL +"sell"+ shopId;
    }
    /**
     * 获取商铺的key
     *
     * @return
     */
    public static String getShopKey(int shopId) {
        return SHOP_DETAIL +"buy"+ shopId;
    }

    /**
     * 获取首页搜索历史记录的key
     *
     * @return
     */
    public static String getHomeSearchHisKey(int userId) {
        return HOME_SEARCH + userId;
    }
    /**
     * 默认地址
     *
     * @return
     */
    public static String getDefaultAddrKey(int userId) {
        return DEFAULT_ADDR + userId;
    }

    public static String getAllProvinceKey(String acode) {
        return ALL_PROVINCE_KEY + acode;
    }


    /**
     * 配置小红点
     *
     * @param mContext
     * @param badgeCount
     */
    public static void setRedPoint(Context mContext, int badgeCount) {
        int unread = Hawk.get(UNREAD, 0);
        unread += badgeCount;
        Hawk.put(UNREAD, unread);
        ShortcutBadger.applyCount(mContext.getApplicationContext(), unread);
    }

    /**
     * 配置小红点
     *
     * @param mContext
     */
    public static void clearRedPoint(Context mContext) {
        Hawk.put(UNREAD, 0);
        ShortcutBadger.applyCount(mContext.getApplicationContext(), 0);
    }

}
