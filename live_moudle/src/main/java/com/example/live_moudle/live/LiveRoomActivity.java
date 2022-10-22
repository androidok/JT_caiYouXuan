package com.example.live_moudle.live;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.appbase.base.selectPics.BaseSelectPicsActivity;
import com.example.appbase.bean.LiveDetailBean;
import com.example.appbase.bean.LiveListBean;
import com.example.live_moudle.LivePresent;
import com.example.live_moudle.R;
import com.example.live_moudle.websocket.SocketManager;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.base.WarnDialog;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  进入直播间
 * @date 2022/7/4 17:12
 */
@Route(path = ARouterPath.live_LiveRoomActivity)
public class LiveRoomActivity extends BaseSelectPicsActivity<LivePresent> implements IView,
        View.OnClickListener, CommentFragment.OnLineUsersListener {

    private PLVideoTextureView mVideoView;
    private ImageView mInfoUserImage;
    /**
     * 姓名
     */
    private TextView mInfoUserName;
    /**
     * 0粉丝
     */
    private TextView mInfoFansCount;
    /**
     * 关注
     */
    private TextView mInfoGuanzhuBtn;
    private ConstraintLayout mUserInfoLayout;
    private ImageView mLiveCloseBtn;
    private FrameLayout mCameraFl;
    private LinearLayout mLoadingView;

    private CommentFragment cameraCommentFragment;
    private String liveNumber;
    protected String livePath;//直播间地址
    private boolean canReconnection = true;
    private int collectId;
    private LiveDetailBean.DataBean dataBean;

    public static void startToLiveRoomActivity(Context mContext, String liveNumber) {
        Intent intent = new Intent(mContext, LiveRoomActivity.class);
        intent.putExtra(BASE_STRING, liveNumber);
        mContext.startActivity(intent);

    }

    @Override
    protected LivePresent createPresenter() {
        return new LivePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_live_room;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        mImmersionBar.reset().statusBarColor(R.color.transparent)
                .statusBarDarkFont(true)
                .init();

        liveNumber = getIntent().getStringExtra(BASE_STRING);
        mVideoView = (PLVideoTextureView) findViewById(R.id.VideoView);
        mInfoUserImage = (ImageView) findViewById(R.id.info_user_image);
        mInfoUserImage.setOnClickListener(this);
        mInfoUserName = (TextView) findViewById(R.id.info_user_name);
        mInfoUserName.setOnClickListener(this);
        mInfoFansCount = (TextView) findViewById(R.id.info_fans_count);
        mInfoGuanzhuBtn = (TextView) findViewById(R.id.info_guanzhu_btn);
        mInfoGuanzhuBtn.setOnClickListener(this);
        mUserInfoLayout = (ConstraintLayout) findViewById(R.id.user_info_layout);
        mLiveCloseBtn = (ImageView) findViewById(R.id.live_close_btn);
        mLiveCloseBtn.setOnClickListener(this);
        mCameraFl = (FrameLayout) findViewById(R.id.camera_fl);
        mLoadingView = findViewById(R.id.loading_view);

        initLiveSetting();
    }

    /**
     * 关注
     */
    private void initCollectTvStatus() {
        if (collectId > 0) {
            //已关注
            mInfoGuanzhuBtn.setText("已关注");
            mInfoGuanzhuBtn.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            mInfoGuanzhuBtn.setBackgroundResource(R.drawable.sp_filled_gray_circle);
        } else {
            mInfoGuanzhuBtn.setText("关注");
            mInfoGuanzhuBtn.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            mInfoGuanzhuBtn.setBackgroundResource(R.drawable.sp_filled_accent_circle);
        }
    }

    @Override
    public void initData() {
// TODO: 2022/7/15 获取直播间详情
        mPresenter.getLiveDetail(mPresenter.getBaseBuilderWithoutToken().add("number",liveNumber).build(),AppHttpPath.LIVE_DETAIL);
    }

    /**
     * 初始化直播设置
     */
    private void initLiveSetting() {
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 5 * 1000);
        //是否开启直播优化，1 为开启，默认0 为关闭。若开启，视频暂停后再次开始播放时会触发追帧机制
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, 1);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        //默认硬编码
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_HW_DECODE);
        //5代表关闭日志  0是打开日志
        options.setInteger(AVOptions.KEY_LOG_LEVEL, 5);
        // 快开模式，启用后会加快该播放器实例再次打开相同协议的视频流的速度
        options.setInteger(AVOptions.KEY_FAST_OPEN, 1);
        // 打开重试次数，设置后若打开流地址失败，则会进行重试
        options.setInteger(AVOptions.KEY_OPEN_RETRY_TIMES, 2);

        mVideoView.setAVOptions(options);
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
//        mVideoView.setDisplayOrientation(90); // 旋转90度
        mVideoView.setBufferingIndicator(mLoadingView);


    }

    /**
     * 重新链接
     */
    private void reStartLive() {
        if (!TextUtils.isEmpty(livePath)) {
            mVideoView.start();
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.SHOP_UNCOLLECT:
                collectId = 0;
                initCollectTvStatus();
                break;

            case AppHttpPath.LIVE_DETAIL:
                LiveDetailBean liveDetailBean = (LiveDetailBean) o;
                if (liveDetailBean != null) {
                    dataBean = liveDetailBean.getData();
                    if (dataBean != null) {
                        collectId = dataBean.getIsCollect();
                        initCollectTvStatus();
                        livePath = dataBean.getRtmpUrl();
                        if (!TextUtils.isEmpty(livePath)) {
                            mVideoView.setVideoPath(livePath);
                            mVideoView.start();
                        }
                        mInfoUserName.setText(dataBean.getShopName());
                        ImageLoadUtil.loadHeadCirclePic(mContext, dataBean.getHeadPortrait(), mInfoUserImage);
                        LiveListBean.DataBean.ListBean  liveListBean = new LiveListBean.DataBean.ListBean(dataBean.getLiveNumber(),dataBean.getTitle(),dataBean.getCoverImg(),"",dataBean.getShareLiveUrl());

                        cameraCommentFragment = CommentFragment.newInstance(liveListBean).setCanLike(false)
                                .setCanShare(true).setOnLineUsersListener(this).setShareCallBack(true);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.camera_fl, cameraCommentFragment);
                        fragmentTransaction.commit();
                    }
                }

                break;

            case AppHttpPath.SHOP_COLLECT:
                BaseResult baseResult = (BaseResult) o;
                collectId = Integer.parseInt(baseResult.getMessage());
                initCollectTvStatus();
                break;
            case AppHttpPath.SHOP_COLLECT_FINISH:
                finish();
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_LIVE_COMMODITY_LIST, ""));
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.live_close_btn) {
            onBackPressed();
        } else if (v.getId() == R.id.info_guanzhu_btn) {
            // : 2022/7/5 关注
            if (collectId > 0) {
                new WarnDialog(mContext).builder()
                        .setTitle("确认不再关注？")
                        .setCancelButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setOkButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (dataBean != null) {
                                    //取消
                                    mPresenter.collectShop(mPresenter.getBaseBuilder()
                                            .add("isCollect", "1")
                                            .add("id", String.valueOf(collectId))
                                            .add("shopId", String.valueOf(dataBean.getShopId())).build(), AppHttpPath.SHOP_UNCOLLECT
                                    );
                                }

                            }
                        }).show();


            } else {
                //收藏
                if (dataBean != null) {
                    mPresenter.collectShop(mPresenter.getBaseBuilder()
                            .add("isCollect", "0")
                            .add("shopId", String.valueOf(dataBean.getShopId())).build(), AppHttpPath.SHOP_COLLECT
                    );
                }

            }
        }
    }

    @Override
    public void onBackPressed() {
        WarnDialog agreementDialog = new WarnDialog(this).builder();
        agreementDialog.getContentTextView().setMovementMethod(LinkMovementMethod.getInstance());
        agreementDialog.setCanceledOnTouchOutside(true)
                .setTitle("退出直播间")
                .setContent("关注主播不再错过精彩直播")
                .setCancelButton("退出", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_LIVE_COMMODITY_LIST, ""));
                        SocketManager.getInstance().unConnect();
                    }
                });

        if (collectId<1) {
            agreementDialog.setOkButton("关注并退出", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SocketManager.getInstance().unConnect();
                    if (collectId < 1) {
                        if (dataBean != null) {
                            mPresenter.collectShop(mPresenter.getBaseBuilder()
                                    .add("isCollect", "0")
                                    .add("shopId", String.valueOf(dataBean.getShopId())).build(), AppHttpPath.SHOP_COLLECT_FINISH
                            );
                        }

                    } else {
                        finish();
                        EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_LIVE_COMMODITY_LIST, ""));
                    }


                }
            });
        }else {
            agreementDialog.setOkButton("同意", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_LIVE_COMMODITY_LIST, ""));
                    SocketManager.getInstance().unConnect();
                }
            });
        }
        agreementDialog.show();



    }

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
            if (what != 10005) {
            }
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    //开始缓冲
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                    //停止缓冲
                    canReconnection = true;
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_RENDERING_START:
                    //第一帧视频已成功渲染
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    //第一帧音频已成功播放
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    //视频帧的时间戳
                    break;
                case PLOnInfoListener.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    //音频帧的时间戳
//                    Logger.i(TAG, "audio frame rendering, ts = " + extra);
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_GOP_TIME:
                    //获取视频的I帧间隔
                    break;
                case PLOnInfoListener.MEDIA_INFO_SWITCHING_SW_DECODE:
                    //硬解失败，自动切换软解
                    break;
                case PLOnInfoListener.MEDIA_INFO_METADATA:
                    //读取到 metadata 信息
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_BITRATE:
                case PLOnInfoListener.MEDIA_INFO_VIDEO_FPS:
                    break;
                case PLOnInfoListener.MEDIA_INFO_CONNECTED:
                    //连接成功
                    break;
                case PLOnInfoListener.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    //获取到视频的播放角度
                    break;
                case MEDIA_INFO_CACHE_DOWN:
                    //预加载完成
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     * 网络异常
                     */
                    getConnectionStatus();
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    //播放器打开失败
                    ToastUtils.warning(mContext, "进入直播间失败，直播或已结束！");
                    finish();
                    break;
                default:
                    ToastUtils.warning(mContext, "unknown error !");
                    finish();
                    break;
            }
            return true;
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            ToastUtils.warning(mContext, "直播已结束！");
            finish();
        }
    };


    @Override
    public void onUsersCountChange(int userCount) {
        mInfoFansCount.setText(userCount + "人在观看");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reStartLive();
    }

    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }

    @Override
    protected void onDestroy() {
        mVideoView.stopPlayback();
        super.onDestroy();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }


    /**
     * 判断网络是否连接
     *
     * @returnw
     */
    private boolean isNetConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager == null) {
                return false;
            }
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo == null || !mNetworkInfo.isAvailable()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     */
    private void getConnectionStatus() {
        int flag = 0;
        /**如果申请的直播并没有在推流，或者直播过程中发生网络错误（比如：WiFi 断开），
         * 播放器在请求超时或者播放完当前缓冲区中的数据后，会触发onError回调,errorCode: ERROR_CODE_IO_ERROR
         *
         * 如何处理该情况:
         * 1.判断网络是否断开
         * 2.查询业务服务器，获知直播是否结束，如果没有结束，则可以尝试做重连
         *
         * 如果决定做重连，则 onError 回调中，请返回 true，否则会导致触发 onCompletion。
         */
        //1.检查手机网络连接
        boolean isConnected = isNetConnected(LiveRoomActivity.this);
        if (isConnected) {//网络连接是否断开
            //2.如果连接没问题,就判断推流是否在继续
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    String url = "http://9ea19420.ngrok.io/PiliServer/";
//                    checkStreamIsComplete(livePath);
//                }
//            }).start();
            if (canReconnection) {
                reStartLive();
                canReconnection = false;
            } else {
                ToastUtils.warning(mContext, "直播断开或已结束！");
                finish();
            }
        } else {
            ToastUtils.warning(mContext, "网络断开，请检查网络设置！");
            finish();
        }
    }

    @Override
    protected void onPicsAndEmpressed(List<String> icons) {

    }

    @Override
    protected String getUpdateHttpUrl() {
        return null;
    }
}
