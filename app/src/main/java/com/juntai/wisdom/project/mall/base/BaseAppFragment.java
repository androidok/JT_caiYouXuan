package com.juntai.wisdom.project.mall.base;


import android.content.Intent;
import android.text.TextUtils;

import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.objectboxbean.MessageBodyBean;
import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.utils.NotificationTool;
import com.juntai.wisdom.project.mall.home.shop.ShopActivity;
import com.juntai.wisdom.project.mall.order.orderDetail.OrderDetailActivity;
import com.juntai.wisdom.project.mall.order.orderPay.OrderPayActivity;

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
    public FormBody.Builder getBaseBuilderWithoutParama() {
        FormBody.Builder builder = new FormBody.Builder();
        return builder;
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
                .add("userId", String.valueOf(UserInfoManager.getUserId()))
                .add("schoolId", String.valueOf(UserInfoManager.getSchoolId()));


        return builder;
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

    /**
     * 跳入 申请退款界面
     */
    public void startToOrderRefundRequestActivity(String shopTel) {
        getBaseActivity().showAlertDialogOfKnown(String.format("请联系店铺负责人\n电话：%s",shopTel));
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
     * @param orderId
     * @param orderStatus
     */
    public void startToOrderDetailActivity(int orderId, int orderStatus) {
        startActivity(new Intent(mContext, OrderDetailActivity.class)
                .putExtra(BaseActivity.BASE_ID, orderId)
                .putExtra(BaseActivity.BASE_ID2, orderStatus));
    }

    /**
     * 跳转到店铺首页
     *
     * @param shopId
     */
    public void startToShop(int shopId) {
        startActivityForResult(new Intent(mContext, ShopActivity.class).putExtra(BaseActivity.BASE_ID, shopId), BaseActivity.BASE_REQUEST_RESULT);

    }




}
