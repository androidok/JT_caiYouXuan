package com.example.chat.util;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

import java.util.HashMap;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/2/27 10:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/27 10:59
 */
public class HawkPropertyChat {
    public final static String SP_KEY_USER = "userBean";
    public final static String SP_KEY_TOKEN = "token";
    public static String DEV_REGID = "dev_regid";

    public static String UNREAD = "unreadCount";


    //主要用于缓存联系人对应的备注名
    private static HashMap<Integer, String> remarkNameMap;
    //主要用于缓存和联系人的未读消息数
    public static HashMap<Integer, Integer> privateUnreadMsgMap = new HashMap<>();
    //主要用于缓存和群组的未读消息数
    public static HashMap<Integer, Integer> groupUnreadMsgMap = new HashMap<>();




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
