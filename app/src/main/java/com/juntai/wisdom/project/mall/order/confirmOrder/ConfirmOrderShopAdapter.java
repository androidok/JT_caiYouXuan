package com.juntai.wisdom.project.mall.order.confirmOrder;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.order.CreatOrderBean;
import com.juntai.wisdom.project.mall.R;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/11 10:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/11 10:38
 */
public class ConfirmOrderShopAdapter extends BaseQuickAdapter<CreatOrderBean.DataBean.ShopListBean, BaseViewHolder> {
    public ConfirmOrderShopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CreatOrderBean.DataBean.ShopListBean item) {
        helper.setText(R.id.order_shop_name_tv,item.getShopName());
        EditText remarkEt = helper.getView(R.id.older_remark_et);
        remarkEt.setTag(item);
        RecyclerView recyclerView = helper.getView(R.id.order_shop_commodities_rv);
        ConfirmOrderCommodityAdapter orderCommodityAdapter = new ConfirmOrderCommodityAdapter(R.layout.comfirm_order_commodity_item);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(orderCommodityAdapter);
        recyclerView.setLayoutManager(manager);
        orderCommodityAdapter.setNewData(item.getCommodities());
        helper.setText(R.id.older_remark_et,item.getRemark());
        remarkEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CreatOrderBean.DataBean.ShopListBean bean = (CreatOrderBean.DataBean.ShopListBean) remarkEt.getTag();
                bean.setRemark(s.toString().trim());

            }
        });
    }
}
