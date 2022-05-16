package com.juntai.wisdom.project.beans;

import com.juntai.wisdom.project.utils.HawkProperty;
import com.orhanobut.hawk.Hawk;

/**
 * @Author: tobato
 * @Description: 作用描述  用户信息管理类
 * @CreateDate: 2020/12/19 14:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/19 14:04
 */
public class UserInfoManagerMall {
    public static String QQ_ID = null;//qqid
    /**
     * 设备登陆类型
     */
    public static String DEVICE_TYPE = "app_buy";
    public static String WECHAT_ID = null;//wechatid
    public static String OTHER_NICK_NAME = null;//第三方昵称

    /**
     * 获取账号的状态  0 代表游客登录 1代表手机号登录 2代表第三方登录（未绑定手机号）
     *
     * @return
     */
    public static int getAccountStatus() {
        int status = -1;
        if (isLogin()) {
            String phoneNum = getUser().getData().getPhoneNumber();
            if ("未绑定".equals(phoneNum)) {
                status = 2;
            } else {
                status = 1;
            }
        } else {
            status = 0;
        }
        return status;
    }

    /**
     * 退出登录清理缓存配置
     */
    public static void clearUserData() {
        Hawk.delete(HawkProperty.SP_KEY_USER);
        Hawk.delete(HawkProperty.SP_KEY_TOKEN);
//        Hawk.delete(HawkPropertyChat.SP_KEY_UNREAD_COUNT);
        //ws退出连接
//        MyWsManager.getInstance().disconnect();
//        AliPushManager.getInstance().unbindAccount(UserInfoManager.getUserUUID());

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserBeanMall getUser() {
        UserBeanMall userBean = Hawk.get(HawkProperty.SP_KEY_USER);
        return userBean;
    }

    /**
     * 判定用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return Hawk.contains(HawkProperty.SP_KEY_USER) && Hawk.contains(HawkProperty.SP_KEY_TOKEN);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getPhoneNumber() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getPhoneNumber() : "";
    }
    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getAccount() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getAccount() : "";
    }

    public  static  boolean canUsePubAccount(){
        return (getUser() != null && getUser().getData() != null) && getUser().getData().getPaymentType() == 1;
    }
    /**
     * 获取用户信息
     *
     * @return
     */
    public static String getSchoolName() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getSchoolName() : "";
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


    /**
     * 获取usertoken
     *
     * @return
     */
    public static String getUserToken() {
        return Hawk.get(HawkProperty.SP_KEY_TOKEN);
    }

    /**
     * 获取getUserId
     *
     * @return
     */
    public static int getUserId() {
        return getUser() != null && getUser().getData() != null ? getUser().getData().getUserId() : -1;
    }




}
