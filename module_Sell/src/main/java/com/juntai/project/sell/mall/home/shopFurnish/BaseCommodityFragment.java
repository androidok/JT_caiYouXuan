package com.juntai.project.sell.mall.home.shopFurnish;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.appbase.base.customview.CustomViewPager;
import com.example.appbase.bean.IdNameBean;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.util.UserInfoManager;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.base.BaseAppFragment;
import com.juntai.project.sell.mall.home.HomePageContract;
import com.juntai.project.sell.mall.home.shop.ShopPresent;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  homepage
 * @date 2021/4/18 14:59
 */
public abstract class BaseCommodityFragment extends BaseAppFragment<ShopPresent> implements HomePageContract.IHomePageView, View.OnClickListener {

    private TabLayout mShopTablayout;
    private ImageView mMoreMenuBtn;
    private CustomViewPager mShopViewpager;
    private CommodityPagerAdapter adapter;

    private boolean commodityArrayTypeLinearlayout = false;


    @Override
    protected int getLayoutRes() {
        return R.layout.sell_homepage_commidity_fg;
    }

    @Override
    protected void initView() {
        mShopTablayout = (TabLayout) getView(R.id.mall_tablayout);
        mMoreMenuBtn = (ImageView) getView(R.id.more_menu_btn);
        mMoreMenuBtn.setOnClickListener(this);
        mShopViewpager = (CustomViewPager) getView(R.id.shop_viewpager);
        initTab();
        initTabData();
        // TODO: 2022/6/24 菜单编辑标签 暂时隐藏
        mMoreMenuBtn.setVisibility(View.GONE);
    }

    private void initTab() {
        adapter = new CommodityPagerAdapter(getChildFragmentManager(), mContext,
                getLabels(), getType());
        mShopViewpager.setAdapter(adapter);
        /*viewpager切换监听，包含滑动点击两种*/
        mShopViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mShopTablayout.setupWithViewPager(mShopViewpager);

    }

    @Override
    protected void initData() {

    }

    protected abstract int getType();

    private void initTabData() {
        adapter.setTitles(getLabels());
        mShopViewpager.setOffscreenPageLimit(getLabels().size());
        /**
         * 添加自定义tab布局
         * */
        for (int i = 0; i < mShopTablayout.getTabCount(); i++) {
            TabLayout.Tab tab = mShopTablayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }
        /*viewpager切换默认第一个*/
        mShopViewpager.setCurrentItem(0);
    }

    /**
     * 获取标签
     *
     * @return
     */
    private List<IdNameBean.DataBean> getLabels() {
        List<IdNameBean.DataBean> arrays = new ArrayList<>();
        //店铺
        ShopDetailSellBean.DataBean shopBean = Hawk.get(HawkProperty.getShopSellKey((UserInfoManager.getShopId())));
        if (shopBean != null) {
            List<ShopDetailSellBean.DataBean.ClassifyListBean> classifyListBeans = shopBean.getClassifyList();
            if (classifyListBeans != null && !classifyListBeans.isEmpty()) {
                for (ShopDetailSellBean.DataBean.ClassifyListBean classifyListBean : classifyListBeans) {
                    arrays.add(new IdNameBean.DataBean(classifyListBean.getId(), classifyListBean.getShopClassifyName()));
                }
            }
        }

        return arrays;
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
        mPresenter.getShopDetail(getBaseAppActivity().getBaseBuilder().add("shopId", String.valueOf(UserInfoManagerMall.getShopId())).build(), AppHttpPath.SHOP_DETAIL);

    }

    @Override
    protected void lazyloadGone() {

    }

    @Override
    protected ShopPresent createPresenter() {
        return new ShopPresent();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.SHOP_DETAIL:
                ShopDetailSellBean shopDetailBean = (ShopDetailSellBean) o;
                if (shopDetailBean != null) {
                    ShopDetailSellBean.DataBean shopBean = shopDetailBean.getData();
                    if (shopBean != null) {
                        Hawk.put(HawkProperty.getShopSellKey(UserInfoManagerMall.getShopId()), shopBean);
                        initTabData();
                        ((ShopFurnishActivity) getActivity()).initOwnerBaseInfo(shopBean);
                    }

                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.more_menu_btn) {// TODO: 2022/5/4 编辑标签
        }
    }
}
