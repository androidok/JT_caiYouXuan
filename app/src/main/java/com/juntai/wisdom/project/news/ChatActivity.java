package com.juntai.wisdom.project.news;

import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chat.bean.ContactBean;
import com.example.chat.chatmodule.ChatAdapter;
import com.juntai.wisdom.project.base.BaseRecyclerviewActivity;
import com.juntai.wisdom.project.beans.UserInfoManagerMall;
import com.juntai.wisdom.project.home.HomePageContract;

/**
 * @aouther tobato
 * @description 描述  聊天界面
 * @date 2022/5/19 14:48
 */
public class ChatActivity extends BaseRecyclerviewActivity<NewsPresent> implements HomePageContract.IHomePageView {

    @Override
    protected NewsPresent createPresenter() {
        return new NewsPresent();
    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        ContactBean contactBean = getIntent().getParcelableExtra(BASE_PARCELABLE);
        UserInfoManagerMall.initContacts(contactBean);
// TODO: 2022/5/19 获取所有的聊天记录


    }

    @Override
    protected boolean enableRefresh() {
        return false;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new ChatAdapter(null);
    }
}
