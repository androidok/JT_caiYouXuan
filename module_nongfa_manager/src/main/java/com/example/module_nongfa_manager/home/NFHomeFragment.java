package com.example.module_nongfa_manager.home;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.BaseRecyclerviewFragment;
import com.example.appbase.bean.PicTextBean;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.commodityManager.CommodityManagerActivity;
import com.example.module_nongfa_manager.home.orderManager.NFOrderManagerActivity;
import com.example.module_nongfa_manager.home.shopManager.ShopesManagerActivity;
import com.juntai.disabled.basecomponent.mvp.IPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class NFHomeFragment extends BaseRecyclerviewFragment {

    @Override
    protected IPresenter createPresenter() {
        return null;
    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        baseQuickAdapter.setNewData(getAdapterDate());
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PicTextBean picTextBean = (PicTextBean) adapter.getItem(position);
                switch (picTextBean.getTextName()) {
                    case NfHomeContact.SHOP_MANAGER:
                        // : 2022/7/29 店铺管理
                        startActivity(new Intent(mContext, ShopesManagerActivity.class));
                        break;
                    case NfHomeContact.COMMODITY_MANAGER:
                        // : 2022/7/29 商品管理
                        startActivity(new Intent(mContext, CommodityManagerActivity.class));
                        break;
                    case NfHomeContact.ORDER_MANAGER:
                        // : 2022/7/29 订单管理
                        startActivity(new Intent(mContext, NFOrderManagerActivity.class));

                        break;
                    default:
                        break;
                }
            }
        });
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
        return new NfManagerHomeAdapter(R.layout.nf_manager_home_item);
    }

    private List<PicTextBean> getAdapterDate() {
        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.mipmap.nf_home_menu_shop_icon, R.mipmap.menu_blue_bg, NfHomeContact.SHOP_MANAGER, "DianPu"));
        arrays.add(new PicTextBean(R.mipmap.nf_home_menu_commodity_icon, R.mipmap.menu_green_bg, NfHomeContact.COMMODITY_MANAGER, "ShangPin"));
        arrays.add(new PicTextBean(R.mipmap.nf_home_menu_order_icon, R.mipmap.menu_yellow_bg, NfHomeContact.ORDER_MANAGER, "FenJian"));
        return arrays;
    }

    @Override
    protected View getAdapterHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.nf_manager_home_head, null);
        return view;
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }


    @Override
    public void onSuccess(String tag, Object o) {

    }
}
