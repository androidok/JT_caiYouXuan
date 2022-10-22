package com.juntai.wisdom.project.mall.shoppingCart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.CartListBean;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.CommodityDetailActivity;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述 v  购物车适配器
 * @CreateDate: 2022/5/5 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/5 9:49
 */
public class ShopCartAdapter extends BaseQuickAdapter<CartListBean.DataBean, BaseViewHolder> {

    private boolean isEdit;

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public ShopCartAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartListBean.DataBean item) {

        helper.addOnClickListener(R.id.shop_selected_iv);
        if (item.isShopSelect()) {
            helper.setImageResource(R.id.shop_selected_iv, R.mipmap.select_icon);
        } else {
            helper.setImageResource(R.id.shop_selected_iv, R.mipmap.unselect_icon);
        }

        helper.addOnClickListener(R.id.shop_name_tv);
        helper.setGone(R.id.shop_name_notice_tv,1 == item.getIsEnd());
        helper.setText(R.id.shop_name_tv,   item.getShopName());

        RecyclerView recyclerView = helper.getView(R.id.shop_cart_commodities_rv);
        ShopCartCommodityAdapter shopCartCommodityAdapter = new ShopCartCommodityAdapter(R.layout.shop_cart_commodity_item);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(shopCartCommodityAdapter);
        recyclerView.setLayoutManager(manager);
        shopCartCommodityAdapter.setNewData(item.getCommodityList());
        shopCartCommodityAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // : 2022/5/6 跳转到商品详情界面
                CartListBean.DataBean.CommodityListBean commodityListBean = (CartListBean.DataBean.CommodityListBean) adapter.getItem(position);
                mContext.startActivity(new Intent(mContext, CommodityDetailActivity.class)
                        .putExtra(BaseActivity.BASE_ID, commodityListBean.getCommodityId()));
            }
        });

        shopCartCommodityAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CartListBean.DataBean.CommodityListBean commodityListBean = (CartListBean.DataBean.CommodityListBean) adapter.getItem(position);

                switch (view.getId()) {
                    case R.id.commodity_property_tv:
                        // : 2022/5/6 展示商品属性
                        EventManager.getEventBus().post(new EventBusObject(EventBusObject.GET_COMMODITY_DETAIL_INFO, commodityListBean));
                        break;
                    case R.id.commodity_selected_iv:
                        if (!isEdit&&1 == commodityListBean.getIsEnd()) {
                            ToastUtils.toast(mContext, "该商家已停止接单");
                            return;
                        }
                        // : 2022/5/7 选择商品
                        if (commodityListBean.isSelected()) {
                            commodityListBean.setSelected(false);
                        } else {
                            commodityListBean.setSelected(true);
                        }
                        adapter.notifyItemChanged(position);
                        // : 2022/5/7 如果所有的条目都选中了 需要更改present的选中状态
                        if (isAllCommoditySelected(adapter)) {
                            helper.setImageResource(R.id.shop_selected_iv, R.mipmap.select_icon);
                            item.setShopSelect(true);
                        } else {
                            helper.setImageResource(R.id.shop_selected_iv, R.mipmap.unselect_icon);
                            item.setShopSelect(false);
                        }
                        // : 2022/5/7 全选按钮监听状态
                        EventManager.getEventBus().post(new EventBusObject(EventBusObject.CHANGE_SELECT_ALL_BUTTON_STATUS, ""));

                        break;
                    default:
                        break;
                }


            }
        });
    }

    /**
     * 检测是否本商店下的所有商品都被选中了
     *
     * @param adapter
     * @return
     */
    private boolean isAllCommoditySelected(BaseQuickAdapter adapter) {
        List<CartListBean.DataBean.CommodityListBean> arrays = adapter.getData();
        for (CartListBean.DataBean.CommodityListBean array : arrays) {
            if (!array.isSelected()) {
                return false;
            }
        }
        return true;
    }
}
