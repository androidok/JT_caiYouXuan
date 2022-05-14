package com.example.chat;

import com.juntai.disabled.basecomponent.mvp.IModel;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021-11-07 14:27
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-11-07 14:27
 */
public class ChatPresent extends BaseChatAppPresent<IModel, MainContract.IBaseView> {

    public final static String MORE_ACTION_PIC = "照片";
    public final static String MORE_ACTION_TAKE_PHOTO = "拍照";
    public final static String MORE_ACTION_VIDEO_CALL = "视频通话";
    public final static String MORE_ACTION_LOCATION = "位置";
    public final static String MORE_ACTION_SECRET_CHAT = "密聊";
    public final static String MORE_ACTION_FILE = "文件";
    public final static String MORE_ACTION_CARD = "名片";
    public final static String EDIT_MSG_DELETE = "删除";
    public final static String EDIT_MSG_FORWARD = "转发";

    @Override
    protected IModel createModel() {
        return null;
    }
}
