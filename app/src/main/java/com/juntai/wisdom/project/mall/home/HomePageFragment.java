package com.juntai.wisdom.project.mall.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.HomeMenuBean;
import com.example.appbase.scan.QRScanActivity;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseAppFragment;
import com.juntai.wisdom.project.mall.home.shop.ShopListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  首页
 * @UpdateUser: 更新者
 */
public class HomePageFragment extends BaseAppFragment<HomePagePresent> implements HomePageContract.IHomePageView, View.OnClickListener {
    private LinearLayout mSearchLl;
    private ImageView mSwitchModeIv;
    private RecyclerView mHomeMenuRv;
    private HomePageMenuAdapter homePageMenuAdapter;

    @Override
    protected HomePagePresent createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.app_home_page_fg;
    }

    @Override
    protected void initView() {
        mSearchLl = (LinearLayout) getView(R.id.search_ll);
        mSwitchModeIv = (ImageView) getView(R.id.switch_mode_iv);
        ImageView mAppLogoIv = (ImageView) getView(R.id.app_logo_iv);
        ImageLoadUtil.loadSquareImage(mContext, R.mipmap.app_icon, mAppLogoIv);
        mSwitchModeIv.setOnClickListener(this);
        mSearchLl.setOnClickListener(this);
        mSearchLl.setBackgroundResource(R.drawable.sp_filled_white_circle);
        getView(R.id.scan_iv).setOnClickListener(this);
        initRv();

    }

    private void initRv() {
        mHomeMenuRv = (RecyclerView) getView(R.id.home_menu_rv);
        homePageMenuAdapter = new HomePageMenuAdapter(R.layout.homepage_menu_item);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mHomeMenuRv.setAdapter(homePageMenuAdapter);
        mHomeMenuRv.setLayoutManager(manager);
        homePageMenuAdapter.setNewData(getAdapterData());
        homePageMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                HomeMenuBean homeMenuBean = (HomeMenuBean) adapter.getItem(position);
                if (0==homeMenuBean.getCategoryId()) {
                    return;
                }
                ShopListActivity.startShopListActivity(mContext, homeMenuBean.getTitle(), homeMenuBean.getCategoryId());
            }
        });
    }

    private List<HomeMenuBean> getAdapterData() {
        List<HomeMenuBean> arrays = new ArrayList<>();
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_COMPLEX, "(蔬菜、水果、面食、甜点、豆制品、冷冻食品、餐厨用品等)", R.mipmap.home_menu_complex, R.color.home_menu_text_green, 1));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_RICE, "", R.mipmap.home_menu_rice, R.color.home_menu_text_pink, 2));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_FLOUR, "", R.mipmap.home_menu_flour, R.color.home_menu_text_orange, 3));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_OIL, "", R.mipmap.home_menu_oil, R.color.home_menu_text_orange_lighter, 4));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_POULTRY, "", R.mipmap.home_menu_poultry, R.color.home_menu_text_blue, 6));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_MEAT, "", R.mipmap.home_menu_meat, R.color.home_menu_text_violet, 5));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_MILK, "", R.mipmap.home_menu_milk, R.color.home_menu_text_pink, 8));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_EGG, "", R.mipmap.home_menu_egg, R.color.home_menu_text_orange, 9));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_CONDIMENT, "", R.mipmap.home_menu_condiment, R.color.home_menu_text_violet, 7));
        arrays.add(new HomeMenuBean(HomeMenuBean.HOME_MENU_OTHER, "", R.mipmap.home_menu_other, R.color.home_menu_text_orange_lighter, 0));
        return arrays;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onSuccess(String tag, Object o) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_mode_iv:
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.HOME_PAGE_DISPLAY_MODE, 1));
                break;
            case R.id.search_ll:
                getBaseAppActivity().startToSearchActivity(0);
                break;
            case R.id.scan_iv:
                // : 2022/5/31 扫码
                startActivity(new Intent(mContext, QRScanActivity.class));

                break;
            default:
                break;
        }
    }
}
