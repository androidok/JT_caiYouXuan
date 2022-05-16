package com.juntai.wisdom.project.order.evaluate;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chat.MainContract;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseRecyclerviewActivity;
import com.juntai.wisdom.project.base.selectPics.SelectPhotosFragment;
import com.juntai.wisdom.project.beans.order.OrderDetailBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.order.OrderPresent;
import com.juntai.wisdom.project.order.refund.RefundCommodityAdapter;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  开始评价
 * @CreateDate: 2022/5/15 17:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/15 17:16
 */
public class EvaluateActivity extends BaseRecyclerviewActivity<OrderPresent> implements HomePageContract.IHomePageView, SelectPhotosFragment.OnPhotoItemClick {
    private OrderDetailBean orderDetailBean;
    private SelectPhotosFragment selectPhotosFragment;

    /**
     * 店铺名称
     */
    private TextView mEvaluateShopNameTv;
    private RatingBar mCommodityEvaluateRatingBar;
    private RatingBar mShopEvaluateRatingBar;

    @Override
    public int getLayoutView() {
        return R.layout.mall_evaluate_activity;
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    public void initView() {
        super.initView();
        setTitleName("发表评价");
        orderDetailBean = getIntent().getParcelableExtra(BASE_PARCELABLE);
        getTitleRightTv().setText("发布");
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                // TODO: 2022/5/15 提交评价
                //首先上传图片
                //将图片上传
                List<String> icons = selectPhotosFragment.getPhotosPath();
                if (icons.size() > 0) {
                    mPresenter.uploadFile(icons, MainContract.UPLOAD_IMAGES);
                } else {
//                    mPresenter.requestRefund(getBaseBuilder()
//                            .add("orderId", String.valueOf(orderDetailBean.getId()))
//                            .add("orderFormNumber", orderDetailBean.getOrderFormNumber())
//                            .add("cargoStatus", String.valueOf(receivedStatus))
//                            .add("causeId", String.valueOf(reasonId))
//                            .add("remark", getTextViewValue(mRefundReasonEt)).build(), AppHttpPathMall.REQUEST_REFUND
//                    );

                }
            }
        });
        mEvaluateShopNameTv = (TextView) findViewById(R.id.evaluate_shop_name_tv);
        mEvaluateShopNameTv.setText(orderDetailBean.getShopName());
        mCommodityEvaluateRatingBar = (RatingBar) findViewById(R.id.commodity_evaluate_ratingBar);
        mShopEvaluateRatingBar = (RatingBar) findViewById(R.id.shop_evaluate_ratingBar);
        selectPhotosFragment = (SelectPhotosFragment) getSupportFragmentManager().findFragmentById(R.id.select_pic_fg);
        selectPhotosFragment.setMaxCount(3).setPhotoDelateable(true);
    }

    @Override
    protected void getRvAdapterData() {
        if (orderDetailBean == null) {
            finish();
        }
        List<OrderDetailBean.CommodityListBean> commodityListBeans = orderDetailBean.getCommodityList();
        baseQuickAdapter.setNewData(commodityListBeans);
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
        return new RefundCommodityAdapter(R.layout.mall_refund_commodity_item);
    }

    @Override
    protected OrderPresent createPresenter() {
        return null;
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case MainContract.UPLOAD_IMAGES:
                //发送图片
                List<String> picPaths = (List<String>) o;
                if (picPaths != null && picPaths.size() > 0) {
//                    FormBody.Builder builder = getBaseBuilder()
//                            .add("orderId", String.valueOf(orderDetailBean.getId()))
//                            .add("orderFormNumber", orderDetailBean.getOrderFormNumber())
//                            .add("cargoStatus", String.valueOf(receivedStatus))
//                            .add("causeId", String.valueOf(reasonId))
//                            .add("remark", getTextViewValue(mRefundReasonEt));
//

//                    for (int i = 0; i < picPaths.size(); i++) {
//                        String picPath = picPaths.get(i);
//                        if (0 == i) {
//                            builder.add("pictureOne", picPath);
//                        } else if (1 == i) {
//                            builder.add("pictureTwo", picPath);
//                        } else if (2 == i) {
//                            builder.add("pictureThree", picPath);
//                        }
//                    }
                    // : todo 调用评价的接口
//                    mPresenter.requestRefund(builder.build(), AppHttpPathMall.REQUEST_REFUND
//                    );
                }


                break;
            default:
                break;
        }
    }
}
