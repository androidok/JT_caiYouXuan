package com.juntai.wisdom.project.mall.base;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.example.appbase.bean.LiveListBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.appbase.bean.order.OrderDetailBean;
import com.example.appbase.util.UserInfoManager;
import com.example.chat.base.uploadFile.UploadUtil;
import com.example.chat.base.uploadFile.listener.OnUploadListener;
import com.example.live_moudle.util.ObjectBoxUtil;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.disabled.basecomponent.bean.UploadFileBean;
import com.juntai.disabled.basecomponent.bean.address.AddressListBean;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.NotificationTool;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.disabled.bdmap.BaseRequestLocationActivity;
import com.juntai.disabled.bdmap.utils.NagivationUtils;
import com.juntai.wisdom.project.mall.entrance.LoginActivity;
import com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.CommodityDetailActivity;
import com.juntai.wisdom.project.mall.home.shop.ShopActivity;
import com.juntai.wisdom.project.mall.mine.address.AddOrEditAddressActivity;
import com.juntai.wisdom.project.mall.mine.address.AddressListActivity;
import com.juntai.wisdom.project.mall.news.ChatActivity;
import com.juntai.wisdom.project.mall.order.allOrder.AllOrderActivity;
import com.juntai.wisdom.project.mall.order.confirmOrder.ConfirmOrderActivity;
import com.juntai.wisdom.project.mall.order.evaluate.EvaluateActivity;
import com.juntai.wisdom.project.mall.order.orderDetail.OrderDetailActivity;
import com.juntai.wisdom.project.mall.order.orderPay.OrderPayActivity;
import com.juntai.wisdom.project.mall.order.refund.RefundActivity;
import com.juntai.wisdom.project.mall.order.refund.RefundRequestActivity;
import com.juntai.wisdom.project.mall.search.SearchActivity;
import com.juntai.wisdom.project.mall.share.ShareActivity;
import com.juntai.wisdom.project.mall.utils.StringTools;
import com.juntai.wisdom.project.mall.webSocket.MyWsManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/4/27 8:48  app的基类
 */
public abstract class BaseAppActivity<P extends BasePresenter> extends BaseRequestLocationActivity<P> {
    public static String WX_APPID = "wx5fd6d26f7806a119";
    public UploadUtil mUploadUtil;

    private OnFileUploadStatus onFileUploadStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update(false);
        NotificationTool.SHOW_NOTIFICATION = true;
        initUploadUtil();
    }

    public void setOnFileUploadStatus(OnFileUploadStatus onFileUploadStatus) {
        this.onFileUploadStatus = onFileUploadStatus;
    }
    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public void copy(String content) {
// 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        ToastUtils.toast(mContext,"已复制");
    }
    protected void initUploadUtil() {
        //上传文件工具类
        mUploadUtil = new UploadUtil();
        mUploadUtil.setOnUploadListener(new OnUploadListener() {
            @Override
            public void onAllSuccess() {
                ToastUtils.toast(mContext, "onAllSuccess");
            }

            @Override
            public void onAllFailed() {

            }

            @Override
            public void onThreadProgressChange(UploadFileBean uploadFileBean, int position, int percent) {
                Log.d("onThreadProgressChange", "onThreadProgressChange" + uploadFileBean.getFilePath() + "---------" + percent);

                if (onFileUploadStatus != null) {
                    onFileUploadStatus.onUploadProgressChange(uploadFileBean, percent);
                }
            }

            @Override
            public void onThreadFinish(UploadFileBean uploadFileBean, int position) {
                if (onFileUploadStatus != null) {
                    onFileUploadStatus.onUploadFinish(uploadFileBean);
                }
            }

            @Override
            public void onThreadInterrupted(int position) {

            }
        });
    }


    public interface OnFileUploadStatus {
        void onUploadProgressChange(UploadFileBean uploadFileBean, int percent);

        void onUploadFinish(UploadFileBean uploadFileBean);

    }

    /**
     * 重新登录
     */
    public void reLogin(String regPhone) {
        UserInfoManager.clearUserData();//清理数据
        //ws退出连接
        HawkProperty.clearRedPoint(mContext.getApplicationContext());
        ActivityManagerTool.getInstance().finishApp();
        startActivity(new Intent(this, LoginActivity.class).putExtra(BaseActivity.BASE_STRING, regPhone
        ));
    }

    /**
     * 第三方分享
     *
     * @return
     */
    public boolean initThirdShareLogic(Intent intent, Context context, Class cls) {
        if (intent != null) {
            String shareTitle = intent.getStringExtra("title");
            String shareUrl = intent.getStringExtra("shareUrl");
            String sharePic = intent.getStringExtra("picPath");
            String shareContent = intent.getStringExtra("content");
            String shareFromApp = intent.getStringExtra("shareFromApp");
            if (!TextUtils.isEmpty(shareUrl) && !TextUtils.isEmpty(shareTitle)) {
                Intent toIntent = new Intent();
                toIntent.putExtra("title", shareTitle);
                toIntent.putExtra("shareUrl", shareUrl);
                toIntent.putExtra("picPath", sharePic);
                toIntent.putExtra("content", shareContent);
                toIntent.putExtra("shareFromApp", shareFromApp);
                toIntent.setClass(context, cls);
                startActivity(toIntent);
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件名称
     *
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileName(MessageBodyBean messageBodyBean) {
        String content = messageBodyBean.getContent();
        return getSavedFileName(content);
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    public String getSavedFileName(String content) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/") + 1, content.length());
        }
        return content;
    }

    /**
     * 获取文件名称  后缀
     *
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileNameWithoutSuffix(MessageBodyBean messageBodyBean) {
        String content = messageBodyBean.getContent();
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/") + 1, content.lastIndexOf("."));
        }
        return content;
    }


    @Override
    protected boolean canCancelLoadingDialog() {
        return true;
    }

    @Override
    protected String getUpdateHttpUrl() {
        return AppHttpPath.APP_UPDATE;
    }

    /**
     * 导航
     *
     * @param endLatlng 目的地
     * @param endName   目的地名称
     */
    public void navigationLogic(LatLng endLatlng, String endName) {
        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        final String item_list[] = {"腾讯地图", "高德地图", "百度地图"};
        build.setItems(item_list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (item_list[which]) {
                    case "腾讯地图":
                        NagivationUtils.getInstant().openTencent(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    case "高德地图":
                        NagivationUtils.getInstant().openGaoDeMap(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    case "百度地图":
                        NagivationUtils.getInstant().openBaiduMap(mContext, endLatlng.latitude, endLatlng.longitude,
                                endName);
                        break;
                    default:
                        break;
                }
            }
        });
        build.setTitle("请选择导航方式");
        AlertDialog alertDialog = build.create();
        alertDialog.show();
    }


    /**
     * 发送更新头像的广播
     */
    public void broadcasetRefreshHead() {
        Intent intent = new Intent();
        intent.setAction("action.refreshHead");
        sendBroadcast(intent);
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManager.getAccount())
                .add("token", UserInfoManager.getUserToken())
                .add("typeEnd", UserInfoManager.getDevType())
                .add("userId", String.valueOf(UserInfoManager.getUserId()));
        return builder;
    }

    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilderWithoutParama() {
        FormBody.Builder builder = new FormBody.Builder();
        return builder;
    }
//    /**
//     * 获取builder
//     *
//     * @return
//     */
//    public FormBody.Builder getBaseBuilderWithoutUserId() {
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("account", UserInfoManagerChat.getUserAccount());
//        builder.add("token", UserInfoManagerChat.getUserToken());
//        return builder;
//    }


    //    /**
    //     * 是否是内部账号
    //     *
    //     * @return
    //     */
    //    public boolean isInnerAccount() {
    //        return UserInfoManagerChat.isTest();

    //    }

    @Override
    public void onLocationReceived(BDLocation bdLocation) {

    }

    /**
     * 将list中的数据转成字符串  并以逗号隔开
     *
     * @return
     */
    public String listToString(List<String> arrays) {
        if (arrays == null || arrays.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(arrays.size());
        for (String selectedServicePeople : arrays) {
            sb.append(selectedServicePeople + ",");
        }
        String people = sb.toString();
        if (StringTools.isStringValueOk(people)) {
            people = people.substring(0, people.length() - 1);
        }
        return people;
    }

    /**
     * 复制电话号码
     */
    public void copyTelephoneNum(String text) {
        //获取剪贴版
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        //第一个参数只是一个标记，随便传入。
        //第二个参数是要复制到剪贴版的内容
        ClipData clip = ClipData.newPlainText("simple text", text);
        //传入clipdata对象.
        clipboard.setPrimaryClip(clip);
    }

    @Override
    public boolean requestLocation() {
        return false;
    }


    /**
     * 加密密码
     *
     * @param pwd
     * @return
     */
    protected String encryptPwd(String account, String pwd) {
        return MD5.md5(String.format("%s#%s", account, pwd));
    }

    @Override
    protected void onPicsAndEmpressed(List<String> icons) {

    }


    @Override
    protected String getDownloadTitleRightName() {
        return null;
    }

    @Override
    protected String getDownLoadPath() {
        return null;
    }


    public String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public void onBackPressed() {
        BaseActivity.hideKeyboard(mBaseRootCol);
        super.onBackPressed();
    }


    /**
     * 跳转到商品详情
     *
     * @param commodityId
     */
    public void startToCommodityDetail(int commodityId) {
        startActivityForResult(new Intent(mContext, CommodityDetailActivity.class)
                .putExtra(BaseActivity.BASE_ID, commodityId), BaseActivity.BASE_REQUEST_RESULT);
    }

    /**
     * 跳转到店铺首页
     *
     * @param shopId
     */
    public void startToShop(int shopId) {
        startActivityForResult(new Intent(mContext, ShopActivity.class).putExtra(BaseActivity.BASE_ID, shopId), BaseActivity.BASE_REQUEST_RESULT);

    }

    /**
     * 跳转到支付界面
     * enterType 0 代表直接购买的时候
     * 1 代表购物车结算的时候
     * 2. 在待支付订单进入
     */
    public void startToOrderPayActivity(BaseResult orderListBean, int enterType) {
        startActivity(new Intent(mContext, OrderPayActivity.class)
                .putExtra(BaseActivity.BASE_STRING, enterType)
                .putExtra(BaseActivity.BASE_PARCELABLE, orderListBean));

    }

    /**
     * 跳转到确认订单
     */
    public void startToConfirmOrder(CreatOrderBean.DataBean dataBean) {
        startActivity(new Intent(mContext, ConfirmOrderActivity.class).putExtra(BaseActivity.BASE_PARCELABLE, dataBean));

    }

    /**
     * 跳转到地址列表
     * type  1是选择地址  0是地址管理
     */
    public void startToAddressListActivity(int type) {
        startActivityForResult(new Intent(mContext, AddressListActivity.class).putExtra(BaseActivity.BASE_ID, type), BaseActivity.BASE_REQUEST_RESULT);


    }

    /**
     * 添加(编辑地址)
     */
    public void startToAddAddress(AddressListBean.DataBean dataBean) {
        startActivityForResult(new Intent(mContext, AddOrEditAddressActivity.class).putExtra(BaseActivity.BASE_PARCELABLE, dataBean), BaseActivity.BASE_REQUEST_RESULT);
    }

    /**
     * 所有订单
     * enterType  0代表支付成功之后  1代表个人中心进入
     */
    public void startToAllOrderActivity(int enterType, int tabPosition) {
        startActivity(new Intent(mContext, AllOrderActivity.class)
                .putExtra(BaseActivity.BASE_ID2, tabPosition)
                .putExtra(BaseActivity.BASE_ID, enterType));
    }

    /**
     * @param orderId
     * @param orderStatus
     */
    public void startToOrderDetailActivity(int orderId, int orderStatus) {
        startActivity(new Intent(mContext, OrderDetailActivity.class)
                .putExtra(BaseActivity.BASE_ID, orderId)
                .putExtra(BaseActivity.BASE_ID2, orderStatus));
    }

    /**
     * 跳入 申请退款界面
     */
    public void startToOrderRefundRequestActivity(OrderDetailBean orderDetailBean) {
        startActivity(new Intent(mContext, RefundRequestActivity.class)
                .putExtra(BaseActivity.BASE_PARCELABLE, orderDetailBean)
        );

    }

    @Override
    public void onEvent(EventBusObject eventBusObject) {
        super.onEvent(eventBusObject);
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.HANDLER_MESSAGE:
                MessageBodyBean messageBody = (MessageBodyBean) eventBusObject.getEventObj();
                if (mContext instanceof ChatActivity) {
                    EventManager.getEventBus().post(new EventBusObject(EventBusObject.MESSAGE_BODY, messageBody));
                } else {
                    ObjectBoxUtil.addMessage(messageBody);
                    EventManager.getEventBus().post(new EventBusObject(EventBusObject.REFRESH_NEWS_LIST, messageBody));

                }
                break;
            case EventBusObject.EVALUATE:
                if (this instanceof AllOrderActivity) {
                    OrderDetailBean.CommodityListBean commodityBean = (OrderDetailBean.CommodityListBean) eventBusObject.getEventObj();
                    startToEvaluateActivity(commodityBean);
                }
                break;
            case EventBusObject.CREAT_ORDER:
                CreatOrderBean.DataBean dataBean  = (CreatOrderBean.DataBean) eventBusObject.getEventObj();
                startToConfirmOrder(dataBean);
                break;
            case EventBusObject.LIVE_SHARE:
                LiveListBean.DataBean.ListBean bean = (LiveListBean.DataBean.ListBean) eventBusObject.getEventObj();
                ShareActivity.startShareActivity(mContext, 2,bean);

                break;
            default:
                break;
        }
    }

    /**
     * 跳入 评价
     */
    public void startToEvaluateActivity(OrderDetailBean.CommodityListBean commodityBean) {
        OrderDetailBean orderDetailBean = new OrderDetailBean();
        List<OrderDetailBean.CommodityListBean> listBeans = new ArrayList<>();
        listBeans.add(commodityBean);
        orderDetailBean.setShopName(commodityBean.getShopName());
        orderDetailBean.setCommodityList(listBeans);
        startActivity(new Intent(mContext, EvaluateActivity.class)
                .putExtra(BaseActivity.BASE_PARCELABLE, orderDetailBean)
        );

    }

    /**
     * 跳入 评价
     * receivedStatus 是否收到货物  1未收到 2 收到
     */
    public void startToRefundActivity(OrderDetailBean orderDetailBean, int receivedStatus) {
        startActivity(new Intent(mContext, RefundActivity.class)
                .putExtra(BaseActivity.BASE_ID, receivedStatus)
                .putExtra(BaseActivity.BASE_PARCELABLE, orderDetailBean));
    }

    /**
     * 进入聊天界面
     */
    public void startToChatActivity(ContactBean contactBean) {
        startActivity(new Intent(mContext, ChatActivity.class)
                .putExtra(BaseActivity.BASE_PARCELABLE, contactBean));
    }

    /**
     * 进入聊天界面
     */
    public void startToSearchActivity(int type) {
        SearchActivity.startSearchActivity(mContext,type);
    }

}
