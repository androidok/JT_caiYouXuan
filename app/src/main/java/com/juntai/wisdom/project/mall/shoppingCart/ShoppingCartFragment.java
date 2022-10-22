package com.juntai.wisdom.project.mall.shoppingCart;


import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CartListBean;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.appbase.bean.order.ToCommitSelectedCommoditiesBean;
import com.example.live_moudle.live.commodity.selectCommodityProperty.SelectCommodityPropertyDialogFragment;
import com.example.live_moudle.util.ObjectBoxUtil;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.objectboxbean.CommodityPropertyBean;
import com.juntai.disabled.basecomponent.utils.GsonTools;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.disabled.basecomponent.utils.eventbus.EventManager;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.commodityfragment.CommodityPresent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @aouther tobato
 * @description 描述  购物车
 * @date
 */
public class ShoppingCartFragment extends BaseRecyclerviewFragment<CommodityPresent> implements HomePageContract.IHomePageView,
        View.OnClickListener {


    /**
     * 管理
     */
    private TextView mStartEditTv;
    /**
     * 全选
     */
    private CheckBox mSelectAllCb;
    /**
     * ￥1016.69
     */
    private TextView mAllPriceTv;
    /**
     * 结算
     */
    private TextView mSettleTv;
    private boolean isEdit = false;
    private CartListBean.DataBean.CommodityListBean commodityListBean;
    private LinearLayout mBottomLl;
    //有截单的商铺
    private boolean hasShopEnd;

    @Override
    protected int getLayoutRes() {
        return R.layout.shop_cart_fg;
    }

    @Override
    protected void initView() {
        super.initView();
        baseQuickAdapter.setEmptyView(getBaseActivity().getAdapterEmptyView("购物车空空如也,快去选购吧~", -1));
        mStartEditTv = (TextView) getView(R.id.start_edit_tv);
        mStartEditTv.setOnClickListener(this);
        mSelectAllCb = (CheckBox) getView(R.id.select_all_cb);
        mAllPriceTv = (TextView) getView(R.id.all_price_tv);
        mSettleTv = (TextView) getView(R.id.settle_tv);
        mBottomLl = (LinearLayout) getView(R.id.cart_bottom_ll);
        mSettleTv.setOnClickListener(this);
        mSelectAllCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit && hasShopEnd) {
                    ToastUtils.toast(mContext, "有停止接单的商铺，不能全选");
                    mSelectAllCb.setChecked(false);
                    return;
                }
                if (mSelectAllCb.isChecked()) {
                    mSelectAllCb.setChecked(true);
                    // : 2022/5/7 将所有的店铺全选
                    initAllShopStatus(true);
                } else {
                    mSelectAllCb.setChecked(false);
                    // : 2022/5/7 将所有的店铺取消全选
                    initAllShopStatus(false);
                }
            }
        });
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CartListBean.DataBean dataBean = (CartListBean.DataBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.shop_selected_iv:
                        if (!isEdit && 1 == dataBean.getIsEnd()) {
                            ToastUtils.toast(mContext, "该商家已停止接单");
                            return;
                        }
                        // : 2022/5/7 店铺全选
                        if (dataBean.isShopSelect()) {
                            dataBean.setShopSelect(false);
                            // : 2022/5/7  将店铺下所有的商品取消选中
                            initAllCommodityStatus(false, dataBean);
                        } else {
                            dataBean.setShopSelect(true);
                            // : 2022/5/7  将店铺下所有的商品选中
                            initAllCommodityStatus(true, dataBean);
                        }
                        adapter.notifyItemChanged(position);
                        EventManager.getEventBus().post(new EventBusObject(EventBusObject.CHANGE_SELECT_ALL_BUTTON_STATUS, ""));
                        break;
                    case R.id.shop_name_tv:
                        // : 2022/5/6 跳转到店铺页面
                        getBaseAppActivity().startToShop(dataBean.getShopId());
                        break;
                    default:
                        break;
                }

            }
        });
    }

    /**
     * 所有店铺的状态
     *
     * @param status
     */
    private void initAllShopStatus(boolean status) {
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        for (CartListBean.DataBean dataBean : dataBeans) {
            dataBean.setShopSelect(status);
            initAllCommodityStatus(status, dataBean);
        }
        baseQuickAdapter.notifyDataSetChanged();
        setSelectedCommoditiesPrice();
    }

    /**
     * 所有商品的状态
     *
     * @param status
     * @param dataBean
     */
    private void initAllCommodityStatus(boolean status, CartListBean.DataBean dataBean) {
        List<CartListBean.DataBean.CommodityListBean> childs = dataBean.getCommodityList();
        for (CartListBean.DataBean.CommodityListBean child : childs) {
            child.setSelected(status);
        }
    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }


    @Override
    protected void getRvAdapterData() {
        hasShopEnd = false;
        if (mSelectAllCb != null && mAllPriceTv != null) {
            mSelectAllCb.setChecked(false);
            mAllPriceTv.setText("0.00");
        }
        mPresenter.getCartList(getBaseAppActivity().getBaseBuilder().build(), AppHttpPath.CART_LIST);

    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }

    @Override
    protected boolean enableLoadMore() {
        return false;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new ShopCartAdapter(R.layout.shop_cart_item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        getRvAdapterData();
    }


    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_edit_tv:
                // : 2022/5/6 管理
                if (isEdit) {
                    mStartEditTv.setText("管理");
                    mSettleTv.setText("结算");
                    mStartEditTv.setTextColor(ContextCompat.getColor(mContext, R.color.gray_deeper));
                    mSettleTv.setBackgroundResource(R.drawable.app_bt_bg_accent);
                    //如果全选按钮选中了  取消全选
                    if (mSelectAllCb.isChecked()) {
                        mSelectAllCb.setChecked(false);
                    }
                    //如果有选中的商品 需要全部取消掉
                    unSelectedCommodities();
                } else {
                    mStartEditTv.setText("退出管理");
                    mSettleTv.setText("删除");
                    mStartEditTv.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    mSettleTv.setBackgroundResource(R.drawable.app_bt_bg_red);

                }
                isEdit = !isEdit;
                ((ShopCartAdapter) baseQuickAdapter).setEdit(isEdit);

                break;
            case R.id.settle_tv:
                // : 2022/5/6 结算或者删除
                if (isEdit) {
                    //删除
                    List<Integer> ids = getSelectedCommodities(isEdit);
                    if (ids.isEmpty()) {
                        ToastUtils.toast(mContext, "请选择要删除的商品");
                        return;
                    }

                    getBaseActivity().showAlertDialog("确定从购物车列表删除吗", "确定", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.deleteCartCommodity(ids, AppHttpPath.DELETE_CART_COMMODITY);
                        }
                    });

                } else {
                    // 结算
                    List<Integer> ids = getSelectedCommodities(isEdit);
                    if (ids.isEmpty()) {
                        ToastUtils.toast(mContext, "请选择要结算的有效商品");
                        return;
                    }
                    // : 2022/5/7 结算  生成订单
                    mPresenter.creatOrderCart(getBaseAppActivity().getBaseBuilder().add("json", getSelectedCommodities()).build(), AppHttpPath.CREAT_ORDER_CART);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取选中的商品
     *
     * @return
     */
    private String getSelectedCommodities() {
        ToCommitSelectedCommoditiesBean toCommitSelectedCommoditiesBean = new ToCommitSelectedCommoditiesBean();
        List<ToCommitSelectedCommoditiesBean.TrolleyBean> trolleyBeans = new ArrayList<>();
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        for (CartListBean.DataBean dataBean : dataBeans) {
            List<CartListBean.DataBean.CommodityListBean> childs = dataBean.getCommodityList();
            List<Integer> commodityList = new ArrayList<>();
            for (CartListBean.DataBean.CommodityListBean child : childs) {
                if (child.isSelected() && !TextUtils.isEmpty(child.getSku())) {
                    commodityList.add(child.getId());
                }
            }
            if (!commodityList.isEmpty()) {
                trolleyBeans.add(new ToCommitSelectedCommoditiesBean.TrolleyBean(dataBean.getId(), commodityList));
            }
        }
        toCommitSelectedCommoditiesBean.setTrolley(trolleyBeans);
        return GsonTools.createGsonString(toCommitSelectedCommoditiesBean);
    }

    /**
     * 获取选中的商品
     *
     * @return
     */
    private void unSelectedCommodities() {
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        for (CartListBean.DataBean dataBean : dataBeans) {
            dataBean.setShopSelect(false);
            List<CartListBean.DataBean.CommodityListBean> childs = dataBean.getCommodityList();
            for (CartListBean.DataBean.CommodityListBean child : childs) {
                child.setSelected(false);
            }
        }
        baseQuickAdapter.notifyDataSetChanged();
        return;
    }

    /**
     * 获取选中的商品
     *
     * @param isEdit
     * @return
     */
    private List<Integer> getSelectedCommodities(boolean isEdit) {
        List<Integer> ids = new ArrayList<>();
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        for (CartListBean.DataBean dataBean : dataBeans) {
            List<CartListBean.DataBean.CommodityListBean> childs = dataBean.getCommodityList();
            for (CartListBean.DataBean.CommodityListBean child : childs) {
                if (isEdit) {
                    if (child.isSelected()) {
                        ids.add(child.getId());
                    }
                } else {
                    if (child.isSelected() && !TextUtils.isEmpty(child.getSku()) && null != child.getPutAwayStatus() && 1 != child.getPutAwayStatus()) {
                        ids.add(child.getId());
                    }
                }

            }
        }
        return ids;
    }


    public void onEvent(EventBusObject eventBusObject) {

        switch (eventBusObject.getEventKey()) {
            case EventBusObject.CHANGE_SHOP_CART_PROPERTY_AMOUNT:
                CartListBean.DataBean.CommodityListBean commodityBean = (CartListBean.DataBean.CommodityListBean) eventBusObject.getEventObj();
                mPresenter.editCart(getBaseAppActivity().getBaseBuilder().add("shopId", String.valueOf(commodityBean.getShopId()))
                        .add("commodityId", String.valueOf(commodityBean.getCommodityId()))
                        .add("attributeUnique", commodityBean.getUnique())
                        .add("commodityNum", String.valueOf(commodityBean.getCommodityNum()))
                        .add("commodityAttr", commodityBean.getSku())
                        .add("id", String.valueOf(commodityBean.getId()))
                        .build(), AppHttpPath.EDIT_CART
                );
                setSelectedCommoditiesPrice();

                break;

            case EventBusObject.REFRESH_SHOP_CART:
                getRvAdapterData();
                break;
            case EventBusObject.GET_COMMODITY_DETAIL_INFO:
                commodityListBean = (CartListBean.DataBean.CommodityListBean) eventBusObject.getEventObj();
                mPresenter.getCommodityDetail(getBaseAppActivity().getBaseBuilderWithoutParama().add("commodityId", String.valueOf(commodityListBean.getCommodityId())).build(), AppHttpPath.COMMODIFY_DETAIL);


                break;
            case EventBusObject.CHANGE_SELECT_ALL_BUTTON_STATUS:
                //全选按钮的选中状态
                if (isAllShopSelect()) {
                    mSelectAllCb.setChecked(true);
                } else {
                    mSelectAllCb.setChecked(false);
                }
// : 2022/5/10 计算总价格
                setSelectedCommoditiesPrice();
                break;
            default:
                break;
        }


    }

    /**
     * 计算所有商品的价格
     */
    private void setSelectedCommoditiesPrice() {
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        double totalPrice = 0.00;
        for (CartListBean.DataBean dataBean : dataBeans) {
            List<CartListBean.DataBean.CommodityListBean> commodityListBeans = dataBean.getCommodityList();
            if (commodityListBeans != null && !commodityListBeans.isEmpty()) {
                for (CartListBean.DataBean.CommodityListBean listBean : commodityListBeans) {
                    if (listBean.isSelected()) {
                        totalPrice += listBean.getPrice() * listBean.getCommodityNum();
                    }

                }
            }
        }
        BigDecimal bigDecimal = new BigDecimal(totalPrice);
//保留2位小数
        double price = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        mAllPriceTv.setText(String.valueOf(price));
    }

    /**
     * @return
     */
    private boolean isAllShopSelect() {
        List<CartListBean.DataBean> dataBeans = baseQuickAdapter.getData();
        for (CartListBean.DataBean dataBean : dataBeans) {
            if (!dataBean.isShopSelect()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.CART_LIST:
                CartListBean cartListBean = (CartListBean) o;
                if (cartListBean != null) {
                    List<CartListBean.DataBean> dataBeans = cartListBean.getData();
                    if (dataBeans != null && !dataBeans.isEmpty()) {
                        mBottomLl.setVisibility(View.VISIBLE);
                        for (CartListBean.DataBean dataBean : dataBeans) {
                            List<CartListBean.DataBean.CommodityListBean> commodityListBeans = dataBean.getCommodityList();
                            if (1 == dataBean.getIsEnd()) {
                                hasShopEnd = true;
                            }
                            for (CartListBean.DataBean.CommodityListBean listBean : commodityListBeans) {
                                listBean.setIsEnd(dataBean.getIsEnd());
                            }
                        }
                    } else {
                        mBottomLl.setVisibility(View.GONE);
                    }

                    baseQuickAdapter.setNewData(dataBeans);
                }
                break;
            case AppHttpPath.COMMODIFY_DETAIL:

                CommodityDetailBean commodityDetailBean = (CommodityDetailBean) o;
                if (commodityDetailBean != null) {
                    CommodityDetailBean.DataBean dataBean = commodityDetailBean.getData();
                    List<CommodityPropertyBean> commodityPropertyBeans = dataBean.getValue();
                    ObjectBoxUtil.addCommodityProperty(dataBean, commodityPropertyBeans);
                    SelectCommodityPropertyDialogFragment selectCommodityPropertyFragment = new SelectCommodityPropertyDialogFragment();
                    selectCommodityPropertyFragment.show(getFragmentManager(), "selectCommodityPropertyFragment");
                    selectCommodityPropertyFragment.setOnConfirmCallBack(new SelectCommodityPropertyDialogFragment.OnConfirmCallBack() {
                        @Override
                        public void confirm(CommodityPropertyBean commodityPropertyBean, double amount) {
                            mPresenter.editCart(getBaseAppActivity().getBaseBuilder().add("shopId", String.valueOf(dataBean.getShopId()))
                                    .add("commodityId", String.valueOf(dataBean.getId()))
                                    .add("attributeUnique", commodityPropertyBean.getUnique())
                                    .add("commodityNum", String.valueOf(amount))
                                    .add("commodityAttr", commodityPropertyBean.getSku())
                                    .add("id", String.valueOf(commodityListBean.getId()))
                                    .build(), AppHttpPath.EDIT_CART
                            );
                        }

                    });
                    selectCommodityPropertyFragment.setData(dataBean);
                }

                break;
            case AppHttpPath.DELETE_CART_COMMODITY:
                getRvAdapterData();
                break;
            case AppHttpPath.CREAT_ORDER_CART:

                CreatOrderBean creatOrderBean = (CreatOrderBean) o;
                if (creatOrderBean != null) {
                    CreatOrderBean.DataBean dataBean = creatOrderBean.getData();
                    getBaseAppActivity().startToConfirmOrder(dataBean);

                }


                break;
            default:
                break;
        }

    }
}
