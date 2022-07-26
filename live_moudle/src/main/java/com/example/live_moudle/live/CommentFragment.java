package com.example.live_moudle.live;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbase.bean.LiveListBean;
import com.example.appbase.bean.LiveMsgBean;
import com.example.appbase.util.UserInfoManager;
import com.example.live_moudle.R;
import com.example.live_moudle.base.InputTextMsgDialog;
import com.example.live_moudle.live.commodity.BaseLiveCommoditiesFragment;
import com.example.live_moudle.websocket.IEvent;
import com.example.live_moudle.websocket.SocketManager;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.CommodityBean;
import com.juntai.disabled.basecomponent.bean.shop.ShopCommodityListBean;
import com.juntai.disabled.basecomponent.utils.MultipleItem;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;

import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  评论
 * @date 2022/7/2 15:07
 */
public class CommentFragment extends BaseLiveCommoditiesFragment implements View.OnClickListener, IEvent {
    private static final String TAG = "ChatRoomFragment";
    private static final int REQUEST_LOGIN = 0;
    private static final int TYPING_TIMER_LENGTH = 600;

    private RecyclerView mMsgRv;
    private TextView mInputMessageView;
    private ImageView mLiveShareIv;
    private ImageView mLiveCommoditiesIv;
    private String liveRoomId;
    private boolean isShareCallBack = false;//是否分享回调


    private InputTextMsgDialog inputTextMsgDialog;

    private boolean isCanShare = true, isCanLike = true;
    private MessageAdapter mAdapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int shopId;

  private   LiveListBean.DataBean.ListBean  bean ;

    public static CommentFragment newInstance(LiveListBean.DataBean.ListBean dataBean) {
        Bundle args = new Bundle();
        args.putParcelable("liveBean", dataBean);
        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(null);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView() {

        bean = getArguments().getParcelable("liveBean");
        liveRoomId = bean.getLiveNumber();
        shopId = bean.getShopId();
        mMsgRv = (RecyclerView) getView(R.id.messages);
        mMsgRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMsgRv.setAdapter(mAdapter);

        mInputMessageView = (TextView) getView(R.id.message_input);
        mInputMessageView.setOnClickListener(this);
        mLiveShareIv = getView(R.id.live_share_iv);
        mLiveShareIv.setOnClickListener(this);
        mLiveCommoditiesIv = getView(R.id.live_commodities_iv);
        mLiveCommoditiesIv.setOnClickListener(this);
        if (isCanShare) {
            mLiveShareIv.setVisibility(View.VISIBLE);
        } else {
            mLiveShareIv.setVisibility(View.GONE);
        }

        SocketManager.getInstance().connect(AppHttpPath.BASE_LIVE_URL, String.valueOf(UserInfoManager.getUserId()), liveRoomId, "0", this);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag,o);
        switch (tag) {
            default:
                break;
            case AppHttpPath.LIVE_ROOM_COMMODITIES:
                ShopCommodityListBean shopCommodityListBean = (ShopCommodityListBean) o;
                if (shopCommodityListBean != null) {
                    List<CommodityBean> arrrays = shopCommodityListBean.getData();
                    if (arrrays != null) {
                        initBottomDialog(arrrays);
                    }
                }
                break;
        }
    }

    /**
     * 初始化输入框
     */
    private void initInputTextMsgDialog() {
        dismissInputDialog();
        if (!SocketManager.getInstance().isConnect()) {
            return;
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(mContext, R.style.dialog);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    sendMsg(msg);
                }

                @Override
                public void dismiss() {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }
            });
        }
        showInputTextMsgDialog();
    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK != resultCode) {
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    /**
     * 添加日志
     *
     * @param message
     */
    private void addLog(String message) {
        mAdapter.addData(new MultipleItem(MultipleItem.LIVE_LOG, message));
        scrollToBottom();
    }

    private void addParticipantsLog(int numUsers) {
        if (onLineUsersListener != null) {
            onLineUsersListener.onUsersCountChange(numUsers);
        }
    }

    /**
     * 添加消息
     *
     * @param username
     * @param message
     */
    private void addMessage(String username, String message) {
        mAdapter.addData(new MultipleItem(MultipleItem.LIVE_MSG, new LiveMsgBean.DataBean(username, message)));
        scrollToBottom();
    }


    /**
     * 发送消息
     */
    private void sendMsg(String message) {
        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }
        mInputMessageView.setText("");
        addMessage(UserInfoManager.getUserNickName(), message);
// : 2022/7/3 发送消息
        SocketManager.getInstance().sendMsg(liveRoomId, String.valueOf(UserInfoManager.getUserId()), message);
    }

    private void scrollToBottom() {
        mMsgRv.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.message_input) {
            initInputTextMsgDialog();
        } else if (id == R.id.live_share_iv) {//分享
            // : 2022/7/5 分享
            EventManager.getEventBus().post(new EventBusObject(EventBusObject.LIVE_SHARE,bean));
        } else if (id == R.id.live_commodities_iv) {
            // : 2022/7/5 商品
            FormBody.Builder builder = new FormBody.Builder().add("shopId", String.valueOf(shopId));
            mPresenter.getLiveRoomCommodities(builder.build(), AppHttpPath.LIVE_ROOM_COMMODITIES);

        }
    }


    public CommentFragment setCanShare(boolean canShare) {
        isCanShare = canShare;
        return this;
    }

    public CommentFragment setCanLike(boolean canLike) {
        isCanLike = canLike;
        return this;
    }

    @Override
    public void onOpen() {
        handler.post(() -> {
            SocketManager.getInstance().sendJoin(liveRoomId, String.valueOf(UserInfoManager.getUserId()));
            addLog(getString(R.string.live_welcome));
            addParticipantsLog(1);
        });

    }


    @Override
    public void onNewPeer(LiveMsgBean eventBean) {
        handler.post(() -> {
            LiveMsgBean.DataBean dataBean = eventBean.getData();
            addLog(dataBean.getNickname() + " 来了");
            addParticipantsLog(dataBean.getOnline());
        });

    }

    @Override
    public void onLeave(LiveMsgBean eventBean) {
        handler.post(() -> {
            LiveMsgBean.DataBean dataBean = eventBean.getData();
            addLog(dataBean.getNickname() + "离开了");
            addParticipantsLog(dataBean.getOnline());
        });

    }

    @Override
    public void onLiveFinished(LiveMsgBean eventBean) {
        handler.post(() -> {
            Toast.makeText(getActivity().getApplicationContext(),
                    "直播已结束", Toast.LENGTH_LONG).show();
            SocketManager.getInstance().unConnect();
            Objects.requireNonNull(getActivity()).finish();

        });
    }

    @Override
    public void onDisConnect(LiveMsgBean eventBean) {
        handler.post(() -> {
            Toast.makeText(getActivity().getApplicationContext(),
                    R.string.disconnect, Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public void onNewTalk(LiveMsgBean eventBean) {
        handler.post(() -> {
            LiveMsgBean.DataBean dataBean = eventBean.getData();
            addMessage(dataBean.getNickname(), dataBean.getContent());
        });
    }

    @Override
    public void reConnect() {

    }

    public interface OnLineUsersListener {
        void onUsersCountChange(int userCount);
    }

    private OnLineUsersListener onLineUsersListener;

    public CommentFragment setOnLineUsersListener(OnLineUsersListener onLineUsersListener) {
        this.onLineUsersListener = onLineUsersListener;
        return this;
    }

    public CommentFragment setShareCallBack(boolean shareCallBack) {
        isShareCallBack = shareCallBack;
        return this;
    }
}
