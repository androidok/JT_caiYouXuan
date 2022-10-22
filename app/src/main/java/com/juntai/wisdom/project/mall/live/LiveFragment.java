package com.juntai.wisdom.project.mall.live;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseAppFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.commodityfragment.CommodityPresent;

/**
 * @aouther tobato
 * @description 描述  直播
 * @date
 */
public class LiveFragment extends BaseAppFragment<CommodityPresent> implements HomePageContract.IHomePageView,
        View.OnClickListener {


    private View view;
    /**
     * 搜索你想要的商品
     */
    private TextView mSearchContentTv;
    private ImageView mScanIv;
    private LinearLayout mSearchLl;

    @Override
    protected int getLayoutRes() {
        return R.layout.sell_live_fg;
    }

    @Override
    protected void initView() {


        mSearchContentTv = (TextView) getView(R.id.search_content_tv);
        mSearchContentTv.setHint("搜索你想要的直播");
        mScanIv = (ImageView) getView(R.id.scan_iv);
        mScanIv.setOnClickListener(this);
        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSearchLl.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }


    @Override
    public void onSuccess(String tag, Object o) {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.scan_iv:
                break;
            case R.id.search_ll:
//                getBaseAppActivity().startToSearchActivity(1);
                getBaseAppActivity().startToSearchActivity(0);
                break;
        }
    }

}
