package com.example.chat.util;

import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.example.chat.bean.ChatUserBean;
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
    public static ChatUserBean getUser() {
        ChatUserBean userBean = Hawk.get(HawkPropertyChat.SP_KEY_USER);
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
        return getUser() != null && getUser().getData() != null ? getUser().getData().getHeadPortrait() : "";
    }

    /**
     * 获取账户
     *
     * @return
     */
    public static String getUserNickName() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getNickname() : "";
    }

    public static String getQRCode() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getQrCode() : "";
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
        return getUser().getData().getId();
    }

    public static boolean getUserVerifyFriend() {
        return getUser() == null || getUser().getData() == null || getUser().getData().isAddFriendVerification();
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static int getSexId() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getGender() : 2;
    }

    /**
     * 获取账户
     *
     * @return
     */
    public static String getUserAccount() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getAccountNumber() : "unregistered";
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static String getUserUUID() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getUuid() : "unregistered";
    }

    /**
     * 可以@所有人
     *
     * @param groupDetailInfoBean
     * @return
     */



}
