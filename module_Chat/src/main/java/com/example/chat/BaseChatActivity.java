package com.example.chat;


import android.content.Intent;
import android.view.View;

import com.example.chat.base.BaseChatAppActivity;
import com.example.chat.chatmodule.ChatPresent;
import com.negier.emojifragment.fragment.EmojiFragment;

/**
 * @aouther tobato
 * @description 描述  1对1聊天
 * @date 2020/11/10 16:44
 */
public abstract class BaseChatActivity extends BaseChatAppActivity<ChatPresent> implements View.OnClickListener,
        MainContract.IBaseView, EmojiFragment.OnEmojiClickListener, BaseChatAppActivity.OnFileUploadStatus {

    @Override

    public int getLayoutView() {
        return R.layout.activity_chat;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
