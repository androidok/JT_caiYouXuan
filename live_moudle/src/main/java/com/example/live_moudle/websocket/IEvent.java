package com.example.live_moudle.websocket;


import com.example.appbase.bean.LiveMsgBean;

/**
 * Created by dds on 2019/7/26.
 * ddssingsong@163.com
 */
public interface IEvent {


    void onOpen();


    void onNewPeer(LiveMsgBean eventBean);

    void onLeave(LiveMsgBean eventBean);
    void onLiveFinished(LiveMsgBean eventBean);

    void onDisConnect(LiveMsgBean eventBean);
    void onNewTalk(LiveMsgBean eventBean);

    void reConnect();

}
