package com.example.live_moudle.live.commodity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.live_moudle.R;
import com.example.live_moudle.live.commodity.selectCommodityProperty.SelectCommodityPropertyDialogFragment;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.bean.CommodityBean;
import com.juntai.disabled.basecomponent.bean.objectboxbean.CommodityPropertyBean;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 直播更多菜单基类
 * @date 2022/7/3 10:13
 */
public abstract class BaseLiveCommoditiesFragment<P extends BasePresenter> extends BaseMvpFragment<P> {
    protected CommodityBottomDialog moreBottomDialog;

    /**
     * 初始化dialog
     */
    public void initBottomDialog(List<CommodityBean> commodityBeans) {
        if (moreBottomDialog == null) {
            moreBottomDialog = new CommodityBottomDialog(mContext);
            moreBottomDialog.setData(commodityBeans);
        }
        moreBottomDialog.setOnBottomDialogCallBack(new CommodityBottomDialog.OnItemClick() {
            @Override
            public void onChildItemClick(BaseQuickAdapter adapter, View view, int position) {
                moreBottomDialog.dismiss();
// TODO: 2022/7/6 获取商品详情


                int id = view.getId();
                SelectCommodityPropertyDialogFragment selectCommodityPropertyFragment = new SelectCommodityPropertyDialogFragment();
                selectCommodityPropertyFragment.show(getFragmentManager(), "selectCommodityPropertyFragment");
                selectCommodityPropertyFragment.setOnConfirmCallBack(new SelectCommodityPropertyDialogFragment.OnConfirmCallBack() {
                    @Override
                    public void confirm(CommodityPropertyBean commodityPropertyBean, int amount) {
//                        if (id == R.id.live_add_to_cart_iv) {
//                            mPresenter.editCart(getBaseBuilder().add("shopId", String.valueOf(dataBean.getShopId()))
//                                    .add("commodityId", String.valueOf(commodityId))
//                                    .add("attributeUnique", commodityPropertyBean.getUnique())
//                                    .add("commodityNum", String.valueOf(amount))
//                                    .add("commodityAttr", commodityPropertyBean.getSku())
//                                    .build(), AppHttpPathMall.EDIT_CART
//                            );
//                        } else {
//                            mPresenter.creatOrderBuy(getBaseBuilder()
//                                    .add("shopId", String.valueOf(commodityPropertyBean.getShopId()))
//                                    .add("commodityId", String.valueOf(commodityPropertyBean.getCommodityId()))
//                                    .add("unique", commodityPropertyBean.getUnique())
//                                    .add("commodityNum", String.valueOf(amount)).build(), AppHttpPathMall.CREAT_ORDER_BUY
//                            );
//                        }

                    }
                });
                selectCommodityPropertyFragment.setData(dataBean);


            }
        });
        moreBottomDialog.show();
    }


}
