package com.juntai.wisdom.project.share;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseRecyclerviewActivity;
import com.juntai.wisdom.project.beans.PicTextBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.home.HomePagePresent;
import com.juntai.wisdom.project.home.commodityfragment.commodity_detail.PicTextAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述 分享
 * @date 2022/5/21 10:41
 */
public class ShareActivity extends BaseRecyclerviewActivity<HomePagePresent> implements View.OnClickListener, HomePageContract.IHomePageView {

    private ImageView mShareLiveShopIv;
    /**
     * dfadsfa
     */
    private TextView mShopNameTv;
    private TextView mShopDesTv;
    private ConstraintLayout mShareLiveCl;
    private ImageView mShareCoverIv;
    /**
     * 描述
     */
    private TextView mShareDesTv;
    private ImageView mQrcodeIv;
    /**
     * 取消
     */
    private TextView mCancelShareTv;

    /**
     * @param mContext
     * @param type     0是店铺 1是商品 2是直播
     * @param pic
     * @param des
     */
    public static void startShareActivity(Context mContext, int type, String pic, String des) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(BASE_ID, type)
                .putExtra(BASE_STRING, pic)
                .putExtra(BASE_STRING2, des);
        mContext.startActivity(intent);

    }

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        super.initView();
        initToolbarAndStatusBar(false);
        mImmersionBar.reset().statusBarColor(R.color.gray_lighter)
                .statusBarDarkFont(true)
                .init();
        mShareLiveShopIv = (ImageView) findViewById(R.id.share_live_shop_iv);
        mShopNameTv = (TextView) findViewById(R.id.shop_name_tv);
        mShopDesTv = (TextView) findViewById(R.id.shop_des_tv);
        mShareLiveCl = (ConstraintLayout) findViewById(R.id.share_live_cl);
        mShareCoverIv = (ImageView) findViewById(R.id.share_cover_iv);
        mShareDesTv = (TextView) findViewById(R.id.share_des_tv);
        mQrcodeIv = (ImageView) findViewById(R.id.qrcode_iv);
        mCancelShareTv = (TextView) findViewById(R.id.cancel_share_tv);
        mCancelShareTv.setOnClickListener(this);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PicTextBean picTextBean = (PicTextBean) adapter.getItem(position);
                switch (picTextBean.getTextName()) {
//                    case HomePageContract.SHARE_WEIXIN:
//                        // TODO: 2022/5/21 分享到微信
//                        break;
//                    case HomePageContract.SHARE_WEIXIN_FRIENDS:
//                        // TODO: 2022/5/21 分享到微信 朋友圈
//                        break;
//                    case HomePageContract.SHARE_QQ:
//                        // TODO: 2022/5/21 分享到QQ
//                        break;
                    default:
                        ToastUtils.toast(mContext, "分享到" + picTextBean.getTextName());

                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        int type = getIntent().getIntExtra(BASE_ID, 0);
        String pic = getIntent().getStringExtra(BASE_STRING);
        String des = getIntent().getStringExtra(BASE_STRING2);
        if (TextUtils.isEmpty(pic)) {
            // TODO: 2022/5/21 展示默认图片
            mShareCoverIv.setImageResource(R.mipmap.shop_default_bg);
        } else {
            ImageLoadUtil.loadImage(mContext, pic, mShareCoverIv);
        }
        mShareDesTv.setText(des);
        if (2 == type) {
            //分享直播
            mShareLiveCl.setVisibility(View.VISIBLE);
        } else {
            mShareLiveCl.setVisibility(View.GONE);

        }
        baseQuickAdapter.setNewData(getShareMenus());
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return new GridLayoutManager(mContext, 5);
    }

    @Override
    protected void getRvAdapterData() {

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
        return new PicTextAdapter(R.layout.share_item);
    }

    public List<PicTextBean> getShareMenus() {
// TODO: 2022/5/21 这个地方需要添加功能图片
        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.mipmap.weixin_icon, HomePageContract.SHARE_WEIXIN));
        arrays.add(new PicTextBean(R.mipmap.weixin_friends, HomePageContract.SHARE_WEIXIN_FRIENDS));
        arrays.add(new PicTextBean(R.mipmap.qq_icon, HomePageContract.SHARE_QQ));
        return arrays;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.cancel_share_tv:
                finish();
                break;
        }
    }
}
