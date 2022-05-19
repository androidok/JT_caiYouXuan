package com.example.chat.util;

import com.example.chat.bean.ContactBean;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述  用户信息管理类
 * @CreateDate: 2020/12/19 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/19 14:04
 */
public class ChatUserInfoManager {
    public static String QQ_ID = null;//qqid
    public static String WECHAT_ID = null;//wechatid
    public static String OTHER_NICK_NAME = null;//第三方昵称


    /**
     * 退出登录清理缓存配置
     */
    public static void clearUserData() {
//        Hawk.delete(HawkPropertyChat.SP_KEY_USER);
//        Hawk.delete(HawkPropertyChat.SP_KEY_TOKEN);
//        Hawk.delete(HawkPropertyChat.SP_KEY_UNREAD_COUNT);
        //ws退出连接
//        MyWsManager.getInstance().disconnect();
//        AliPushManager.getInstance().unbindAccount(ChatUserInfoManager.getUserUUID());

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static ContactBean getUser() {
        ContactBean userBean = Hawk.get(HawkPropertyChat.SP_KEY_USER);
        return userBean;
    }

    /**
     * 判定用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return Hawk.contains(HawkPropertyChat.SP_KEY_USER) && Hawk.contains(HawkPropertyChat.SP_KEY_TOKEN);
    }



    /**
     * 获取用户的备注信息
     *
     * @return
     */
    public static String getContactRemarkName(MessageBodyBean messageBodyBean) {
        return "1231231";
    }



    public static String getHeadPic() {
        return getUser() != null && getUser() != null ? getUser().getHeadPortrait() : "";
    }

    /**
     * 获取账户
     *
     * @return
     */
    public static String getUserNickName() {
        return getUser() != null && getUser() != null ? getUser().getNickname() : "";
    }

    /**
     * 获取usertoken
     *
     * @return
     */
    public static String getUserToken() {
        return Hawk.get(HawkPropertyChat.SP_KEY_TOKEN);
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static int getUserId() {
        return getUser().getUserId();
    }





    /**
     * 可以@所有人
     *
     * @param groupDetailInfoBean
     * @return
     */



}
