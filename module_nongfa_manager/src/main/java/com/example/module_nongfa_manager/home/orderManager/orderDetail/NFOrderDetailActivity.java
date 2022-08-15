package com.example.module_nongfa_manager.home.orderManager.orderDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.BaseRecyclerviewActivity;
import com.example.appbase.bean.nong_fa_manager.SortDetailBean;
import com.example.appbase.util.bannerImageLoader.BannerObject;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.HomePresent;
import com.example.module_nongfa_manager.home.orderManager.NfOrderTextValueAdapter;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.FileCacheUtils;
import com.juntai.disabled.basecomponent.utils.ScreenUtils;
import com.example.appbase.base.displayPicVideo.DisplayPicAndVideosActivity;
import com.king.zxing.util.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  农发 订单管理
 * @date 2022/8/2 10:43
 */

@Route(path = ARouterPath.nf_NFOrderDetailActivity)
public class NFOrderDetailActivity extends BaseRecyclerviewActivity<HomePresent> implements IView {

    private String qrCodeImg = "orderQrImage.png";
    /**
     * 订单状态
     */
    private TextView mOrderStatusTv;
    private RecyclerView mClientInfoRv;
    private RecyclerView mTradeInfoRv;
    private ImageView mOrderQrIv;
    private int orderId;
    private NfOrderTextValueAdapter clientAdapter;
    private NfOrderTextValueAdapter tradeAdapter;
    private TextView mOrderRemarkTv;

    @Override
    protected HomePresent createPresenter() {
        return new HomePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.nf_manager_order_detail;
    }

    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        mPresenter.getSortOrderDetail(new FormBody.Builder().add("orderId", String.valueOf(orderId)).build(), AppHttpPath.SORT_ORDER_DETAIL);
    }

    @Override
    public void initData() {
        super.initData();
        setTitleName("订单详情");
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
        return new NFOrderDetailCommodityAdapter(R.layout.nf_manager_order_detail_commodity_item);
    }


    public void initView() {
        super.initView();
        mOrderStatusTv = (TextView) findViewById(R.id.order_status_tv);
        mOrderRemarkTv = (TextView) findViewById(R.id.older_remark_tv);
        mOrderQrIv = (ImageView) findViewById(R.id.order_detail_qr_iv);
        mOrderQrIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<BannerObject> bannerObjects = new ArrayList<>();
                bannerObjects.add(new BannerObject(BannerObject.BANNER_TYPE_IMAGE, FileCacheUtils.getAppImagePath(true) + qrCodeImg));
                startActivity(new Intent(mContext, DisplayPicAndVideosActivity.class).putParcelableArrayListExtra(BASE_PARCELABLE, (ArrayList<BannerObject>) bannerObjects)
                        .putExtra(DisplayPicAndVideosActivity.IMAGEITEM, 0));
            }
        });
        mClientInfoRv = (RecyclerView) findViewById(R.id.client_info_rv);
        mTradeInfoRv = (RecyclerView) findViewById(R.id.trade_info_rv);
        orderId = getIntent().getIntExtra(BASE_ID, 0);
        clientAdapter = new NfOrderTextValueAdapter(R.layout.nf_manager_base_info_item);
        tradeAdapter = new NfOrderTextValueAdapter(R.layout.nf_manager_base_info_item);
        initRecyclerviewNoScroll(mClientInfoRv, clientAdapter, LinearLayoutManager.VERTICAL);
        initRecyclerviewNoScroll(mTradeInfoRv, tradeAdapter, LinearLayoutManager.VERTICAL);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPath.SORT_ORDER_DETAIL:
                SortDetailBean sortDetailBean = (SortDetailBean) o;
                SortDetailBean.DataBean dataBean = sortDetailBean.getData();
                if (dataBean != null) {
                    mOrderRemarkTv.setVisibility(TextUtils.isEmpty(dataBean.getRemark())?View.GONE:View.VISIBLE);
                    mOrderRemarkTv.setText(String.format("备注信息：%s",dataBean.getRemark()));
                    mOrderStatusTv.setText(String.format("订单状态:%s", getOrderStatus(dataBean.getState())));
                    Bitmap bitmap = CodeUtils.createQRCode(dataBean.getQrCodeUrl(), ScreenUtils.getInstance(mContext).getScreenWidth());
                    FileCacheUtils.saveBitmap(bitmap, qrCodeImg, true);
                    mOrderQrIv.setImageBitmap(bitmap);
                    List<TextKeyValueBean> arraysClient = new ArrayList<>();
                    arraysClient.add(new TextKeyValueBean("学校名称：", dataBean.getSchoolName()));
                    arraysClient.add(new TextKeyValueBean("学校编码：", dataBean.getSchoolNumber()));
                    arraysClient.add(new TextKeyValueBean("收货人\u3000：", dataBean.getName()));
                    arraysClient.add(new TextKeyValueBean("联系电话：", dataBean.getPhone()));
                    arraysClient.add(new TextKeyValueBean("收货地址：", dataBean.getAddress()));
                    clientAdapter.setNewData(arraysClient);
                    //交易
                    List<TextKeyValueBean> arraysTrade = new ArrayList<>();
                    arraysTrade.add(new TextKeyValueBean("订单编号：", dataBean.getOrderFormNumber()));
                    arraysTrade.add(new TextKeyValueBean("支付方式：", getPayTypeName(dataBean.getPayType())));
                    arraysTrade.add(new TextKeyValueBean("创建时间：", dataBean.getEstablishTime()));
                    arraysTrade.add(new TextKeyValueBean("付款时间：", dataBean.getPaymentTime()));
                    tradeAdapter.setNewData(arraysTrade);

                    List<SortDetailBean.DataBean.CommodityListBean> commodityListBeans = dataBean.getCommodityList();
                    baseQuickAdapter.setNewData(commodityListBeans);


                }

                break;
            default:
                break;
        }
    }

    /**
     * 获取支付方式
     *
     * @param payType
     * @return /**
     * * 支付方式（0：未支付）（1：支付宝）（2：微信）（3：银行卡）（4：对公账户）
     */
    private String getPayTypeName(int payType) {
        if (payType == 0) {
            return "未支付";
        } else if (payType == 1) {
            return "支付宝";
        } else if (payType == 2) {
            return "微信";
        } else if (payType == 3) {
            return "银行卡";
        } else if (payType == 4) {
            return "对公账户";
        }
        return "";
    }

    /**
     * 订单状态(0：待付款）（1：待发货）（2：待收货）（3：待评价）（4：退款中）（5：完成）（6:订单取消）（7：退款完成）
     *
     * @param state
     * @return
     */
    private String getOrderStatus(int state) {
        String status = null;
        switch (state) {
            case 0:
                status = "等待买家付款";
                break;
            case 1:
                status = "待发货";
                break;
            case 2:
                status = "等待买家收货";
                break;
            case 3:
                status = "等待买家评价";
                break;
            case 4:
                status = "退款中";
                break;
            case 5:
                status = "已完成";
                break;
            case 6:
                status = "交易关闭";
                break;
            case 7:
                status = "退款完成";
                break;
            case 8:
                status = "退款申请已拒绝";
                break;
            default:
                break;
        }
        return status;
    }

}
