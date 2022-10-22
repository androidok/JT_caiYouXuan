package com.example.live_moudle.live.commodity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.live_moudle.LivePresent;
import com.example.live_moudle.R;
import com.example.live_moudle.live.commodity.selectCommodityProperty.SelectCommodityPropertyDialogFragment;
import com.example.live_moudle.util.ObjectBoxUtil;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.bean.objectboxbean.CommodityPropertyBean;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 直播更多菜单基类
 * @date 2022/7/3 10:13
 */
public abstract class BaseLiveCommoditiesFragment extends BaseMvpFragment<LivePresent> implements IView {
    protected CommodityBottomDialog moreBottomDialog;
    private CommodityDetailBean.DataBean dataBean;
    private int childViewId;

    @Override
    protected LivePresent createPresenter() {
        return new LivePresent();
    }

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
                CommodityBean commodityBean = (CommodityBean) adapter.getItem(position);
                moreBottomDialog.dismiss();
// : 2022/7/6 获取商品详情
                mPresenter.getCommodityDetail(mPresenter.getBaseBuilderWithoutParama().add("commodityId", String.valueOf(commodityBean.getId())).build(), AppHttpPath.COMMODIFY_DETAIL);
                childViewId = view.getId();



            }
        });
        moreBottomDialog.show();
    }


    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.COMMODIFY_DETAIL:
                CommodityDetailBean commodityDetailBean = (CommodityDetailBean) o;
                if (commodityDetailBean != null) {
                    dataBean = commodityDetailBean.getData();
                    List<CommodityPropertyBean> commodityPropertyBeans = dataBean.getValue();
                    ObjectBoxUtil.addCommodityProperty(dataBean, commodityPropertyBeans);
                    SelectCommodityPropertyDialogFragment selectCommodityPropertyFragment = new SelectCommodityPropertyDialogFragment();
                    selectCommodityPropertyFragment.show(getFragmentManager(), "selectCommodityPropertyFragment");
                    selectCommodityPropertyFragment.setOnConfirmCallBack(new SelectCommodityPropertyDialogFragment.OnConfirmCallBack() {
                        @Override
                        public void confirm(CommodityPropertyBean commodityPropertyBean, double amount) {
                            if (childViewId == R.id.live_add_to_cart_iv) {
                                mPresenter.editCart(mPresenter.getBaseBuilder().add("shopId", String.valueOf(dataBean.getShopId()))
                                        .add("commodityId", String.valueOf(dataBean.getId()))
                                        .add("attributeUnique", commodityPropertyBean.getUnique())
                                        .add("commodityNum", String.valueOf(amount))
                                        .add("commodityAttr", commodityPropertyBean.getSku())
                                        .build(), AppHttpPath.EDIT_CART
                                );
                            } else {
                                mPresenter.creatOrderBuy(mPresenter.getBaseBuilder()
                                        .add("shopId", String.valueOf(commodityPropertyBean.getShopId()))
                                        .add("commodityId", String.valueOf(commodityPropertyBean.getCommodityId()))
                                        .add("unique", commodityPropertyBean.getUnique())
                                        .add("commodityNum", String.valueOf(amount)).build(), AppHttpPath.CREAT_ORDER_BUY
                                );
                            }

                        }
                    });
                    selectCommodityPropertyFragment.setData(dataBean);
                }
                break;
            case AppHttpPath.EDIT_CART:
                ToastUtils.toast(mContext, "已加入购物车");
                break;
            case AppHttpPath.CREAT_ORDER_BUY:
                CreatOrderBean creatOrderBean = (CreatOrderBean) o;
                if (creatOrderBean != null) {
                    CreatOrderBean.DataBean dataBean = creatOrderBean.getData();
                    EventManager.getEventBus().post(new EventBusObject(EventBusObject.CREAT_ORDER,dataBean));


                }
                break;
            default:
                break;
        }
    }
}
