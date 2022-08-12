package com.juntai.project.sell.mall.base;


import android.content.Intent;
import android.text.TextUtils;

import com.example.appbase.bean.ShopDetailSellBean;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.utils.NotificationTool;
import com.juntai.project.sell.mall.home.commodityManager.allCommodity.AllCommodityActivity;
import com.juntai.project.sell.mall.home.commodityManager.allCommodity.commodityProperty.CommodityFormatPropertyActivity;
import com.juntai.project.sell.mall.home.commodityManager.allCommodity.editCommodity.SellCommodityDetailActivity;
import com.juntai.project.sell.mall.home.shop.ShopManagerActivity;
import com.juntai.project.sell.mall.home.shopFurnish.ShopFurnishActivity;
import com.juntai.project.sell.mall.news.ChatActivity;
import com.juntai.project.sell.mall.order.allOrder.OrderManagerActivity;
import com.juntai.project.sell.mall.order.orderDetail.OrderDetailActivity;
import com.juntai.project.sell.mall.order.send.SendActivity;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  app的fragment的基类
 * @date 2020/7/18 16:43
 */
public abstract class BaseAppFragment<P extends IPresenter> extends BaseMvpFragment<P> {

    @Override
    protected boolean canCancelLoadingDialog() {
        return true;
    }

    @Override
    protected void lazyloadGone() {

    }
    /**
     * 获取builder
     *
     * @return
     */
    public FormBody.Builder getBaseBuilder() {
        FormBody.Builder builder = new FormBody.Builder()
                .add("account", UserInfoManagerMall.getAccount())
                .add("token", UserInfoManagerMall.getUserToken())
                .add("typeEnd", UserInfoManagerMall.DEVICE_TYPE)
                .add("userId", String.valueOf(UserInfoManagerMall.getUserId()));
        if (UserInfoManagerMall.getShopId()>0) {
            builder.add("shopId", String.valueOf(UserInfoManagerMall.getShopId()));
        }
        return builder;
    }
    /**
     * 跳转到店铺首页
     *
     * @param shopId
     */
    public void startToShop(int shopId) {
        startActivityForResult(new Intent(mContext, ShopFurnishActivity.class).putExtra(BASE_ID, shopId), BaseActivity.BASE_REQUEST_RESULT);

    }


    /**
     * 所有订单
     * enterType  0代表支付成功之后  1代表个人中心进入
     */
    public void startToAllOrderActivity(int enterType, int tabPosition) {
        startActivity(new Intent(mContext, OrderManagerActivity.class)
                .putExtra(BASE_ID2, tabPosition)
                .putExtra(BASE_ID, enterType));
    }

    /**
     * @param orderId
     * @param orderStatus
     */
    public void startToOrderDetailActivity(int orderId, int orderStatus) {
        startActivity(new Intent(mContext, OrderDetailActivity.class)
                .putExtra(BASE_ID, orderId)
                .putExtra(BASE_ID2, orderStatus));
    }


    /**
     * 进入聊天界面
     */
    public void startToChatActivity(ContactBean contactBean) {
        startActivity(new Intent(mContext, ChatActivity.class)
                .putExtra(BASE_PARCELABLE, contactBean));
    }
    /**
     * 进入商品管理列表
     */
    public void startAllCommodityActivity() {
        startActivity(new Intent(mContext, AllCommodityActivity.class));
    }
    /**
     * 进入商品管理列表
     */
    public void startSendActivity(int orderId) {
        startActivity(new Intent(mContext, SendActivity.class).putExtra(BASE_ID,orderId));
    }
    /**
     * 进入商品规格
     */
    public void startCommodityPropertyActivity(int commodityId) {
        startActivity(new Intent(mContext, CommodityFormatPropertyActivity.class).putExtra(BASE_ID,commodityId));
    }
    /**
     * 跳转到商品详情
     *
     * @param commodityId
     */
    public void startToCommodityDetail(int commodityId) {
        startActivityForResult(new Intent(mContext, SellCommodityDetailActivity.class)
                .putExtra(BASE_ID, commodityId), BaseActivity.BASE_REQUEST_RESULT);
    }
    /**
     * 进入店铺认证
     */
    public void startToShopAuthActivity(ShopDetailSellBean.DataBean dataBean) {
        // : 2022/6/8 进入到店铺认证界面
        startActivity(new Intent(mContext, ShopManagerActivity.class)
                .putExtra(BASE_PARCELABLE,dataBean));
    }
    /**
     * 获取文件名称  后缀
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileNameWithoutSuffix(MessageBodyBean messageBodyBean){
        String content = messageBodyBean.getContent();
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.lastIndexOf("."));
        }
        return content;
    }
    /**
     * 获取文件名称  后缀
     * @return
     */
    public String getSavedFileNameWithoutSuffix(String content){
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.lastIndexOf("."));
        }
        return content;
    }
    /**
     * 获取文件名称  带后缀   图片的缓存需要
     * @param messageBodyBean
     * @return
     */
    public String getSavedFileName(MessageBodyBean messageBodyBean){
        String content = messageBodyBean.getContent();
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.length());
        }
        return content;
    }
    /**
     * 获取文件名称  带后缀   图片的缓存需要
     * @return
     */
    public String getSavedFileName(String  content){
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        if (content.contains("/")) {
            content = content.substring(content.lastIndexOf("/")+1,content.length());
        }
        return content;
    }
    @Override
    protected void lazyLoad() {
        NotificationTool.SHOW_NOTIFICATION = true;
    }

    /**
     * 获取activity
     *
     * @return
     */
    public BaseAppActivity getBaseAppActivity() {
        return (BaseAppActivity) getActivity();
    }
}
