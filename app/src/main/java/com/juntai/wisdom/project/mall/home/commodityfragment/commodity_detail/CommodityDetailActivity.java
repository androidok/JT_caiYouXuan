package com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail;

import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.PicTextAdapter;
import com.example.appbase.base.selectPics.SelectPhotosFragment;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.CommodityEvaluationBean;
import com.example.appbase.bean.PicTextBean;
import com.example.appbase.bean.UserBean;
import com.example.appbase.bean.order.CreatOrderBean;
import com.example.appbase.util.UserInfoManager;
import com.example.live_moudle.live.commodity.selectCommodityProperty.SelectCommodityPropertyDialogFragment;
import com.example.live_moudle.util.ObjectBoxUtil;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.ARouterPath;
import com.juntai.disabled.basecomponent.base.BaseResult;
import com.juntai.disabled.basecomponent.bean.objectboxbean.CommodityPropertyBean;
import com.juntai.disabled.basecomponent.mvp.IView;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseAppActivity;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.commodityfragment.CommodityPresent;
import com.juntai.wisdom.project.mall.home.commodityfragment.commodity_detail.evaluation.AllEvaluateFragment;
import com.juntai.wisdom.project.mall.share.ShareActivity;

import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  商品详情
 * @date 2022/5/3 15:55
 */

@Route(path = ARouterPath.appCommodityDetailActivity)
public class CommodityDetailActivity extends BaseAppActivity<CommodityPresent> implements IView, View.OnClickListener, SelectPhotosFragment.OnPhotoItemClick {

    public int commodityId;
    private RecyclerView mCommodityBottomRv;
    /**
     * 加入购物车
     */
    private TextView mAddToCartTv;
    /**
     * 立即购买
     */
    private TextView mBuyNowTv;
    private CommodityDetailFragment commodityDetailFragment;
    private AllEvaluateFragment mEvaluateFragment;
    private CommodityDetailBean.DataBean dataBean;
    private PicTextAdapter picTextAdapter;
    private int collectId = 0;
    //店铺状态
    private boolean shopStatusIsOk = true;
    private LinearLayout bottomLl;


    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    public int getLayoutView() {
        return R.layout.activity_commodity_detail;
    }


    @Override
    public void initView() {
        setTitleName("商品详情");
        initFragmentSelected(0);
        // : 2022/5/3 这个地方的分享图标需要更换
        setRightTvDrawable(R.mipmap.share_icon);
        getTitleRightTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // : 2022/5/3 跳转到 分享商品的界面
                if (dataBean == null) {
                    ToastUtils.toast(mContext, "商品信息获取失败 无法分享");
                    return;
                }
                if (!shopStatusIsOk) {
                    ToastUtils.toast(mContext, "店铺不存在");
                    return;
                }

                ShareActivity.startShareActivity(mContext, 1, dataBean.getCoverImg(), dataBean.getName(), dataBean.getShareUrl());
            }
        });
        commodityId = getIntent().getIntExtra(BASE_ID, 0);
        mCommodityBottomRv = (RecyclerView) findViewById(R.id.commodity_bottom_rv);
        picTextAdapter = new PicTextAdapter(R.layout.custom_tabitem);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        mCommodityBottomRv.setLayoutManager(manager);
        mCommodityBottomRv.setAdapter(picTextAdapter);
        mAddToCartTv = (TextView) findViewById(R.id.add_to_cart_tv);
        bottomLl = (LinearLayout) findViewById(R.id.commodity_bottom_ll);
        mAddToCartTv.setOnClickListener(this);
        mBuyNowTv = (TextView) findViewById(R.id.buy_now_tv);
        mBuyNowTv.setOnClickListener(this);
        picTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!shopStatusIsOk) {
                    ToastUtils.toast(mContext, "店铺不存在");
                    return;
                }

                PicTextBean picTextBean = (PicTextBean) adapter.getItem(position);
                switch (picTextBean.getTextName()) {
                    case HomePageContract.SHOP:
                        // : 2022/5/3 进入店铺
                        startToShop(dataBean.getShopId());

                        break;
                    case HomePageContract.CUSTOMER:
                        // : 2022/5/3 联系店铺客服
                        // : 2022/5/19 获取客服人员的信息
                        FormBody.Builder builder = new FormBody.Builder()
                                .add("account", UserInfoManager.getAccount())
                                .add("token", UserInfoManager.getUserToken())
                                .add("typeEnd", UserInfoManager.getDevType())
                                .add("userId", String.valueOf(dataBean.getUserId()));
                        mPresenter.getUserInfo(builder.build(), AppHttpPath.GET_USER_INFO);

                        break;
                    case HomePageContract.COLLECT:
                        // : 2022/5/3 收藏商品
                        if (collectId > 0) {
                            //取消收藏

                            showAlertDialog("确定移除收藏？", "确定", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.collectCommodity(getBaseBuilder()
                                            .add("isCollect", "1")
                                            .add("id", String.valueOf(collectId))
                                            .add("commodityId", String.valueOf(commodityId)).build(), HomePageContract.UN_COLLECT_COMMODITY_SHOP
                                    );
                                }
                            });


                        } else {
                            //收藏
                            showAlertDialog("确定添加到收藏？", "确定", "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.collectCommodity(getBaseBuilder()
                                            .add("isCollect", "0")
                                            .add("commodityId", String.valueOf(commodityId)).build(), HomePageContract.COLLECT_COMMODITY_SHOP
                                    );
                                }
                            });


                        }


                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        mPresenter.getCommodityDetail(getBaseBuilderWithoutParama()
                .add("userId", String.valueOf(UserInfoManager.getUserId()))
                .add("commodityId", String.valueOf(commodityId)).build(), AppHttpPath.COMMODIFY_DETAIL);
    }


    /**
     * 获取各个fragment对象
     */
    private void initFragments() {
        if (commodityDetailFragment == null) {
            commodityDetailFragment = new CommodityDetailFragment();
        }
        if (mEvaluateFragment == null) {
            mEvaluateFragment = new AllEvaluateFragment();
        }
    }

    /**
     * 隐藏所有的fragment
     *
     * @param fragmentTransaction
     */
    private void hindFragments(FragmentTransaction fragmentTransaction) {
        if (commodityDetailFragment != null) {
            fragmentTransaction.hide(commodityDetailFragment);
        }
        if (mEvaluateFragment != null) {
            fragmentTransaction.hide(mEvaluateFragment);
        }
    }

    /**
     * 初始化fragment
     *
     * @param i
     */
    public void initFragmentSelected(int i) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hindFragments(fragmentTransaction);
        initFragments();
        switch (i) {
            case 0:
                if (!commodityDetailFragment.isAdded()) {
                    fragmentTransaction.add(R.id.commodity_detail_fl, commodityDetailFragment, "commodityDetailFragment");
                }
                fragmentTransaction.show(commodityDetailFragment);

                break;
            case 1:
                if (!mEvaluateFragment.isAdded()) {
                    fragmentTransaction.add(R.id.commodity_detail_fl, mEvaluateFragment, "mapfragment");
                }
                fragmentTransaction.show(mEvaluateFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        if (!shopStatusIsOk) {
            ToastUtils.toast(mContext, "店铺不存在");
            return;
        }

        switch (v.getId()) {
            default:
                break;
            case R.id.add_to_cart_tv:
                // : 2022/5/5 加入购物车
            case R.id.buy_now_tv:
                // : 2022/5/5 立即购买


                SelectCommodityPropertyDialogFragment selectCommodityPropertyFragment = new SelectCommodityPropertyDialogFragment();
                selectCommodityPropertyFragment.show(getSupportFragmentManager(), "selectCommodityPropertyFragment");
                selectCommodityPropertyFragment.setOnConfirmCallBack(new SelectCommodityPropertyDialogFragment.OnConfirmCallBack() {
                    @Override
                    public void confirm(CommodityPropertyBean commodityPropertyBean, double amount) {
                        if (v.getId() == R.id.add_to_cart_tv) {
                            mPresenter.editCart(getBaseBuilder().add("shopId", String.valueOf(dataBean.getShopId()))
                                    .add("commodityId", String.valueOf(commodityId))
                                    .add("attributeUnique", commodityPropertyBean.getUnique())
                                    .add("commodityNum", String.valueOf(amount))
                                    .add("commodityAttr", commodityPropertyBean.getSku())
                                    .build(), AppHttpPath.EDIT_CART
                            );
                        } else {
                            mPresenter.creatOrderBuy(getBaseBuilder()
                                    .add("shopId", String.valueOf(commodityPropertyBean.getShopId()))
                                    .add("commodityId", String.valueOf(commodityPropertyBean.getCommodityId()))
                                    .add("unique", commodityPropertyBean.getUnique())
                                    .add("commodityNum", String.valueOf(amount)).build(), AppHttpPath.CREAT_ORDER_BUY
                            );
                        }

                    }
                });
                selectCommodityPropertyFragment.setData(dataBean);
                break;
        }
    }

    @Override
    public void onVedioPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onPicClick(BaseQuickAdapter adapter, int position) {

    }

    @Override
    public void onBackPressed() {
        if ("商品评价".equals(getTextViewValue(getTitleTv()))) {
            initFragmentSelected(0);
            setTitleName("商品详情");
            return;
        }
        super.onBackPressed();


    }

    @Override
    public void onError(String tag, Object o) {
        switch (tag) {
            case AppHttpPath.COMMODIFY_DETAIL:
                bottomLl.setVisibility(View.GONE);
              showAlertDialogOfKnown((String)o, new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      finish();
                  }
              });
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(String tag, Object o) {

        switch (tag) {
            case AppHttpPath.COMMODIFY_DETAIL:
                CommodityDetailBean commodityDetailBean = (CommodityDetailBean) o;
                if (commodityDetailBean != null) {
                    dataBean = commodityDetailBean.getData();
                    shopStatusIsOk = dataBean.getShopState() == 2;
                    collectId = dataBean.getIsCollect();
                    picTextAdapter.setNewData(mPresenter.getCommodityBottomMenus(dataBean.getIsCollect() > 0));
                    commodityDetailFragment.initAdapterData(dataBean);
//                    //todo 暂时去掉获取商品评价
//                    mPresenter.getCommodityEvaluation(getBaseBuilderWithoutParama()
//                            .add("type", "8")
//                            .add("commodityId", String.valueOf(commodityId)).build(), AppHttpPath.COMMODIFY_EVALUATION);

                    List<CommodityPropertyBean> commodityPropertyBeans = dataBean.getValue();
                    ObjectBoxUtil.addCommodityProperty(dataBean, commodityPropertyBeans);
                    commodityDetailFragment.addDetailData(dataBean.getDescription());

                }
                break;

            case AppHttpPath.COMMODIFY_EVALUATION:
                CommodityEvaluationBean commodityEvaluationBean = (CommodityEvaluationBean) o;
                if (commodityEvaluationBean != null) {
                    List<CommodityEvaluationBean.DataBean> arrays = commodityEvaluationBean.getData();
                    commodityDetailFragment.addEvaluationData(arrays);
                }
                commodityDetailFragment.addDetailData(dataBean.getDescription());

                break;

            case AppHttpPath.EDIT_CART:
                ToastUtils.toast(mContext, "已加入购物车");
                break;
            case HomePageContract.UN_COLLECT_COMMODITY_SHOP:
                collectId = 0;
                picTextAdapter.setNewData(mPresenter.getCommodityBottomMenus(false));
                break;
            case HomePageContract.COLLECT_COMMODITY_SHOP:
                BaseResult baseResult = (BaseResult) o;
                collectId = Integer.parseInt(baseResult.getMessage());
                picTextAdapter.setNewData(mPresenter.getCommodityBottomMenus(true));

                break;
            case AppHttpPath.CREAT_ORDER_BUY:
                CreatOrderBean creatOrderBean = (CreatOrderBean) o;
                if (creatOrderBean != null) {
                    CreatOrderBean.DataBean dataBean = creatOrderBean.getData();
                    startToConfirmOrder(dataBean);

                }
                break;

            case AppHttpPath.GET_USER_INFO:
                UserBean loginBean = (UserBean) o;
                if (loginBean != null) {
                    // : 2022/5/19 进入到聊天的界面
                    startToChatActivity(loginBean.getData());

                }
                break;
            default:
                break;
        }
    }
}
