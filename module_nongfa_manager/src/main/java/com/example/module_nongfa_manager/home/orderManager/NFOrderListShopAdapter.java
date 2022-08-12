package com.example.module_nongfa_manager.home.orderManager;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.SellOrderDetailBean;
import com.example.module_nongfa_manager.R;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/12 14:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/12 14:32
 */
public class NFOrderListShopAdapter extends BaseQuickAdapter<SellOrderDetailBean, BaseViewHolder> {
    public NFOrderListShopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SellOrderDetailBean item) {

        helper.setText(R.id.order_shop_name_tv, item.getShopName());
        helper.addOnClickListener(R.id.shop_bottom_cl);

        RecyclerView recyclerView = helper.getView(R.id.order_commodities_rv);
        NFOrderListCommodityAdapter orderCommodityAdapter = new NFOrderListCommodityAdapter(R.layout.nf_manager_order_commodity_item);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(orderCommodityAdapter);
        recyclerView.setLayoutManager(manager);
        List<SellOrderDetailBean.CommodityListBean> arrays = item.getCommodityList();
        if (!arrays.isEmpty()) {
            for (SellOrderDetailBean.CommodityListBean array : arrays) {
                array.setOrderStatus(item.getState());
            }
        }
        orderCommodityAdapter.setNewData(arrays);

        helper.addOnClickListener(R.id.order_detail_tv);

        if (1==item.getSorting()) {
            helper.addOnClickListener(R.id.order_left_tv);
            //未分拣
            helper.setText(R.id.order_left_tv,"分拣");
            helper.setBackgroundRes(R.id.order_left_tv,R.drawable.app_bt_bg_accent);
            helper.setTextColor(R.id.order_left_tv, ContextCompat.getColor(mContext,R.color.white));
        }else {
            helper.setText(R.id.order_left_tv,"已分拣");
            helper.setBackgroundRes(R.id.order_left_tv,R.drawable.app_bt_bg_gray);
            helper.setTextColor(R.id.order_left_tv, ContextCompat.getColor(mContext,R.color.black));
        }
        if (1==item.getDelivery()) {
            helper.addOnClickListener(R.id.order_right_tv);
            //未分拣
            helper.setText(R.id.order_right_tv,"配送");
            helper.setBackgroundRes(R.id.order_right_tv,R.drawable.app_bt_bg_accent);
            helper.setTextColor(R.id.order_right_tv, ContextCompat.getColor(mContext,R.color.white));
        }else {
            helper.setText(R.id.order_right_tv,"已配送");
            helper.setBackgroundRes(R.id.order_right_tv,R.drawable.app_bt_bg_gray);
            helper.setTextColor(R.id.order_right_tv, ContextCompat.getColor(mContext,R.color.black));
        }

    }



}
