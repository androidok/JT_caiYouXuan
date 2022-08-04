package com.juntai.wisdom.project.mall.home.commodityfragment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.wisdom.project.mall.home.shop.ShopActivity;

/**
 * @Author: tobato
 * @Description: 作用描述  首页的商品列表
 * @CreateDate: 2022/5/20 15:59
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/20 15:59
 */
public class HomePageCommodityFragment extends BaseCommodityListFragment {

    public static HomePageCommodityFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("label", type);
        HomePageCommodityFragment fragment = new HomePageCommodityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void itemClick(BaseQuickAdapter adapter, int position) {
        CommodityBean bean = (CommodityBean) adapter.getItem(position);
        startActivityForResult(new Intent(mContext, ShopActivity.class).putExtra(BaseActivity.BASE_ID, bean.getId()), BaseActivity.BASE_REQUEST_RESULT);

    }
}
