package com.juntai.project.sell.mall.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.PicTextBean;
import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.scan.QRScanActivity;
import com.example.live_moudle.live.LivePrepareActivity;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.eventbus.EventBusObject;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.juntai.project.sell.mall.base.BaseRecyclerviewFragment;
import com.juntai.project.sell.mall.beans.sell.ShopHomeInfoBean;
import com.juntai.project.sell.mall.home.assets.AssetsActivity;
import com.juntai.project.sell.mall.home.commodityManager.CommodityManagerActivity;
import com.juntai.project.sell.mall.home.shop.ShopFlowAdapter;
import com.juntai.project.sell.mall.home.shopFurnish.ShopFurnishActivity;
import com.juntai.project.sell.mall.home.systemNotice.SystemNoticeActivity;
import com.juntai.project.sell.mall.mine.MyCenterActivity;
import com.juntai.project.sell.mall.search.SearchActivity;
import com.juntai.project.sell.mall.share.ShareActivity;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;
import com.orhanobut.hawk.Hawk;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述  店铺 首页
 * @CreateDate: 2022/5/9 17:26
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/9 17:26
 */
public class HomeShopFragment extends BaseRecyclerviewFragment<HomePagePresent> implements HomePageContract.IHomePageView, View.OnClickListener {
    private LinearLayout mSearchLl, mSystemNoticeLl;
    private ImageView mShareShopIv;
    private ImageView mShopOwnerHeadIv;
    /**
     * 店铺名称
     */
    private TextView mShopNameTv;
    /**
     * 开店时间
     */
    private TextView mShopCreatTimeTv;
    /**
     * 店铺得分
     */
    private TextView mShopScoreTv;
    /**
     * 描述
     */
    private TextView mShopDesTv, mShopTypeTv;
    private RecyclerView mShopFlowRv;
    private ShopFlowAdapter shopFlowAdapter;
    private MarqueeView mMarqueeView;
    private ShopHomeInfoBean.DataBean dataBean;

    @Override
    protected HomePagePresent createPresenter() {
        return new HomePagePresent();
    }


    private List<TextKeyValueBean> getFlowAdapterData() {
        List<TextKeyValueBean> arrays = new ArrayList<>();
        arrays.add(new TextKeyValueBean(HomePageContract.SHOP_FLOW_ORDER, "0"));
        arrays.add(new TextKeyValueBean(HomePageContract.SHOP_FLOW_BUSINESS, "0"));
        arrays.add(new TextKeyValueBean(HomePageContract.SHOP_FLOW_VISIT, "0"));
        return arrays;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        mPresenter.getShopHomeInfo(getBaseAppActivity().getBaseBuilder()
                .add("shopId", String.valueOf(UserInfoManagerMall.getShopId())).build(), AppHttpPathMall.SHOP_HOME_INFO);


    }

    @Override
    protected void initData() {
        super.initData();
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (UserInfoManagerMall.getShopStatus() == 1) {
//                    // : 2022/6/8 审核中
//                    getBaseAppActivity().showAlertDialogOfKnown("店铺认证审核中,请耐心等待");
//                    return;
//                }
                PicTextBean picTextBean = (PicTextBean) adapter.getItem(position);
                switch (picTextBean.getTextName()) {
                    case HomePageContract.SHOP_MANAGER_COMMODITY:
                        // : 2022/6/7 商品管理
                        startActivity(new Intent(mContext, CommodityManagerActivity.class));
                        break;
                    case HomePageContract.SHOP_MANAGER_ORDER:
                        // : 2022/6/7 订单管理
                        getBaseAppActivity().startToAllOrderActivity(1, 0);
                        break;
                    case HomePageContract.SHOP_MANAGER_LIVE:
                        // : 2022/6/7 直播
                        //初始化直播
                        startActivity(new Intent(mContext, LivePrepareActivity.class));
                        break;
                    case HomePageContract.SHOP_MANAGER_ASSENT:
                        // : 2022/6/7 收入资产
                        startActivity(new Intent(mContext, AssetsActivity.class));

                        break;
                    case HomePageContract.SHOP_MANAGER_FURNISH:
                        // : 2022/6/7 店铺装修
                        startActivity(new Intent(mContext, ShopFurnishActivity.class));

                        break;
                    case HomePageContract.SHOP_MANAGER_SHOP:
                        // : 2022/6/7 店铺管理
                        mPresenter.getShopDetail(getBaseAppActivity().getBaseBuilder().add("shopId", String.valueOf(UserInfoManagerMall.getShopId())).build(), AppHttpPath.SHOP_DETAIL);


                        break;
                    case HomePageContract.SHOP_MANAGER_CENTER:
                        // : 2022/6/7 个人中心
// : 2022/7/27 跳转到个人中心
                        startActivity(new Intent(mContext, MyCenterActivity.class));

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private View getHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sell_shop_head_view, null);
        mSearchLl = (LinearLayout) view.findViewById(R.id.search_ll);
        mSystemNoticeLl = (LinearLayout) view.findViewById(R.id.system_notice_ll);
        mShareShopIv = (ImageView) view.findViewById(R.id.share_shop_iv);
        mShareShopIv.setOnClickListener(this);
        // TODO: 2022/7/7 暂时将分享店铺隐藏
        mShareShopIv.setVisibility(View.GONE);
        mSearchLl.setOnClickListener(this);
        view.findViewById(R.id.scan_iv).setOnClickListener(this);
        mShopOwnerHeadIv = (ImageView) view.findViewById(R.id.shop_owner_head_iv);
        mShopNameTv = (TextView) view.findViewById(R.id.shop_name_tv);
        mShopCreatTimeTv = (TextView) view.findViewById(R.id.shop_creat_time_tv);
        mShopScoreTv = (TextView) view.findViewById(R.id.shop_score_tv);
        mShopDesTv = (TextView) view.findViewById(R.id.shop_des_tv);
        mMarqueeView = (MarqueeView) view.findViewById(R.id.marqueeView);
        mMarqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                // : 2022/6/8 跳转到系统消息界面
                startActivity(new Intent(mContext, SystemNoticeActivity.class).putExtra(BASE_ID, 1));
            }
        });
        mShopTypeTv = (TextView) view.findViewById(R.id.shop_type_tv);
        mShopFlowRv = (RecyclerView) view.findViewById(R.id.shop_flow_rv);
        shopFlowAdapter = new ShopFlowAdapter(R.layout.sell_text_key_value_item_vertical);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mShopFlowRv.setLayoutManager(manager);
        mShopFlowRv.setAdapter(shopFlowAdapter);
        shopFlowAdapter.setNewData(getFlowAdapterData());
        return view;
    }

    @Override
    protected View getAdapterHeadView() {
        return getHeadView();
    }

    @Override
    protected View getAdapterFootView() {
        return null;
    }

    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return new GridLayoutManager(mContext, 3);
    }

    @Override
    protected void getRvAdapterData() {
        baseQuickAdapter.setNewData(getManagerMenus());
    }

    private List<PicTextBean> getManagerMenus() {
        List<PicTextBean> arrays = new ArrayList<>();
        arrays.add(new PicTextBean(R.mipmap.homemenu_commodity_manager, HomePageContract.SHOP_MANAGER_COMMODITY));
        arrays.add(new PicTextBean(R.mipmap.homemenu_order_manager, HomePageContract.SHOP_MANAGER_ORDER));
        arrays.add(new PicTextBean(R.mipmap.homemenu_live, HomePageContract.SHOP_MANAGER_LIVE));
        arrays.add(new PicTextBean(R.mipmap.homemenu_assent, HomePageContract.SHOP_MANAGER_ASSENT));
        arrays.add(new PicTextBean(R.mipmap.homemenu_furnish, HomePageContract.SHOP_MANAGER_FURNISH));
        arrays.add(new PicTextBean(R.mipmap.homemenu_shop_manager, HomePageContract.SHOP_MANAGER_SHOP));
        arrays.add(new PicTextBean(R.mipmap.homemenu_personal_center, HomePageContract.SHOP_MANAGER_CENTER));
        return arrays;
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
        return new PicTextAdapter(R.layout.sell_shop_manager_item);
    }

    @Override
    public void onSuccess(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.SHOP_DETAIL:
                ShopDetailSellBean shopDetailBean = (ShopDetailSellBean) o;
                if (shopDetailBean != null) {
                    ShopDetailSellBean.DataBean dataBean = shopDetailBean.getData();
                    if (dataBean != null) {
                        getBaseAppActivity().startToShopAuthActivity(dataBean);
                    }
                }
                break;
            case AppHttpPathMall.SHOP_HOME_INFO:
                ShopHomeInfoBean infoBean = (ShopHomeInfoBean) o;
                if (infoBean != null) {
                    dataBean = infoBean.getData();
                    if (dataBean != null) {
                        int shipmentsOrder = dataBean.getShipmentsOrder();
                        int afterOrder = dataBean.getAfterOrder();
                        int orderAmount = shipmentsOrder + afterOrder;
                        if (orderAmount > 0) {
                            PicTextBean picTextBean = (PicTextBean) baseQuickAdapter.getItem(1);
                            picTextBean.setUnReadAmount(orderAmount);
                            baseQuickAdapter.notifyDataSetChanged();
                        }

                        mShopNameTv.setText(dataBean.getName());
                        Hawk.put(HawkProperty.SHOP_NAME, dataBean.getName());
                        mShopCreatTimeTv.setText(String.format("开店时间：%s", dataBean.getCreateTime()));
                        mShopDesTv.setText(String.format("店铺简介：%s", dataBean.getIntroduction()));
                        mShopScoreTv.setText(String.format("在售商品：%s", dataBean.getCommodityCount()));
                        ImageLoadUtil.loadHeadCirclePic(mContext, dataBean.getHeadPortrait(), mShopOwnerHeadIv);
                        List<ShopHomeInfoBean.DataBean.CategoryListBean> categoryListBeans = dataBean.getCategoryList();
                        if (categoryListBeans == null || categoryListBeans.isEmpty()) {
                            mShopTypeTv.setVisibility(View.GONE);
                        } else {
                            mShopTypeTv.setVisibility(View.VISIBLE);
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i < categoryListBeans.size(); i++) {
                                ShopHomeInfoBean.DataBean.CategoryListBean category = categoryListBeans.get(i);
                                if (i != categoryListBeans.size() - 1) {
                                    sb.append(category.getName() + "/");
                                } else {
                                    sb.append(category.getName());
                                }
                            }
                            mShopTypeTv.setText(sb.toString());
                        }
                        List<TextKeyValueBean> arrays = shopFlowAdapter.getData();
                        for (int i = 0; i < arrays.size(); i++) {
                            TextKeyValueBean keyValueBean = arrays.get(i);
                            switch (i) {
                                case 0:
                                    keyValueBean.setValue(String.valueOf(dataBean.getTodayOrderNum()));
                                    break;
                                case 1:
                                    keyValueBean.setValue(String.valueOf(dataBean.getTodayMoney()));
                                    break;
                                case 2:
                                    keyValueBean.setValue(String.valueOf(dataBean.getTodayVisitor()));
                                    break;
                                default:
                                    break;
                            }

                        }
                        shopFlowAdapter.notifyDataSetChanged();

                        List<ShopHomeInfoBean.DataBean.SysNoticeListBean> noticeListBeans = dataBean.getSysNoticeList();
                        if (noticeListBeans == null || noticeListBeans.isEmpty()) {
                            mSystemNoticeLl.setVisibility(View.GONE);
                        } else {
                            mSystemNoticeLl.setVisibility(View.VISIBLE);
                            mMarqueeView.startWithList(noticeListBeans);
                        }

                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onEvent(EventBusObject eventBusObject) {
        switch (eventBusObject.getEventKey()) {
            case EventBusObject.TO_HANDLER_ORDER:
                lazyLoad();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.share_shop_iv) {// : 2022/6/7 分享店铺
            ShareActivity.startShareActivity(mContext, 0, dataBean.getHeadPortrait(), dataBean.getIntroduction(), dataBean.getShareUrl());
        } else if (id == R.id.search_ll) {// : 2022/7/9 搜索
            startActivity(new Intent(mContext, SearchActivity.class));
        } else if (id == R.id.scan_iv) {// : 2022/5/31 扫码
            startActivity(new Intent(mContext, QRScanActivity.class));
        }
    }
}
