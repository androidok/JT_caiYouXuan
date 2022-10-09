package com.juntai.wisdom.project.mall.shoppingCart;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.CartListBean;
import com.juntai.disabled.basecomponent.base.view.NumberButton;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述  购物车商品适配器
 * @CreateDate: 2022/5/5 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/5 9:49
 */
public class ShopCartCommodityAdapter extends BaseQuickAdapter<CartListBean.DataBean.CommodityListBean, BaseViewHolder> {
    public ShopCartCommodityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartListBean.DataBean.CommodityListBean item) {
        ImageLoadUtil.loadSquareImageHasCorner(mContext, item.getImage(), helper.getView(R.id.commodity_pic_iv));
        helper.setText(R.id.commodity_name_tv, item.getCommodityName());
        helper.addOnClickListener(R.id.commodity_property_tv);
        helper.addOnClickListener(R.id.commodity_selected_iv);


        if (TextUtils.isEmpty(item.getSku()) || null == item.getPutAwayStatus() || 1 == item.getPutAwayStatus()) {
            helper.setText(R.id.commodity_property_tv, "宝贝已失效");
            helper.setTextColor(R.id.commodity_property_tv, ContextCompat.getColor(mContext, R.color.red));
        } else {
            helper.setText(R.id.commodity_property_tv, item.getSku());
            helper.setTextColor(R.id.commodity_property_tv, ContextCompat.getColor(mContext, R.color.gray_deeper));
        }
        helper.setText(R.id.all_price_tv, String.valueOf(item.getPrice()));
        NumberButton numberButton = helper.getView(R.id.number_button);
        numberButton
                .setmBuyMin(Double.valueOf(item.getDelivery()).intValue())
                .setCurrentNumber(item.getCommodityNum());
        numberButton.setOnWarnListener(new NumberButton.OnWarnListener() {
            @Override
            public void onWarningForInventory(double inventory) {

            }

            @Override
            public void onWarningForBuyMax(double max) {

            }

            @Override
            public void onTextChanged(double num) {

                item.setCommodityNum(num);
                // : 2022/5/6 更改购物车商品属性
                EventManager.getEventBus().post(new EventBusObject(EventBusObject.CHANGE_SHOP_CART_PROPERTY_AMOUNT, item));

            }
        });
        if (item.isSelected()) {
            helper.setImageResource(R.id.commodity_selected_iv, R.mipmap.select_icon);
        } else {
            helper.setImageResource(R.id.commodity_selected_iv, R.mipmap.unselect_icon);

        }
    }
}
