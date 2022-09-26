package com.example.live_moudle.live.commodity.selectCommodityProperty;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityDetailBean;
import com.example.appbase.bean.CommodityPropertyListBean;
import com.example.live_moudle.R;
import com.example.live_moudle.util.ObjectBoxUtil;
import com.juntai.disabled.basecomponent.base.BaseBottomSheetFragment;
import com.juntai.disabled.basecomponent.base.view.NumberButton;
import com.juntai.disabled.basecomponent.bean.objectboxbean.CommodityPropertyBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/5 14:51
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/5 14:51
 */
public class SelectCommodityPropertyDialogFragment extends BaseBottomSheetFragment implements View.OnClickListener {

    private ImageView mCommodityPicIv;
    /**
     * ￥1016.69
     */
    private TextView mAllPriceTv;
    private ImageView mCloseDialogIv;
    private RecyclerView mCommodityPropertyRv;
    private NumberButton mNumberButton;
    /**
     * 确定
     */
    private TextView mComfirmTv;

    private OnConfirmCallBack onConfirmCallBack;
    private CommodityPropertyAdapter commodityPropertyAdapter;
    private List<CommodityPropertyListBean> propertyListBeans = new ArrayList<>();
    private CommodityDetailBean.DataBean dataBean;
    private LinkedHashMap<String, String> propertyMap = new LinkedHashMap<>();
    private CommodityPropertyBean commodityPropertyBean;

    public void setOnConfirmCallBack(OnConfirmCallBack onConfirmCallBack) {
        this.onConfirmCallBack = onConfirmCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shop_join_cart, null);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mCommodityPicIv = (ImageView) view.findViewById(R.id.commodity_pic_iv);
        mAllPriceTv = (TextView) view.findViewById(R.id.all_price_tv);
        mCloseDialogIv = (ImageView) view.findViewById(R.id.close_dialog_iv);
        mCloseDialogIv.setOnClickListener(this);
        mCommodityPropertyRv = (RecyclerView) view.findViewById(R.id.commodity_property_rv);
        mNumberButton = (NumberButton) view.findViewById(R.id.number_button);
        mComfirmTv = (TextView) view.findViewById(R.id.comfirm_tv);
        mComfirmTv.setOnClickListener(this);
        commodityPropertyAdapter = new CommodityPropertyAdapter(R.layout.commodity_property_item);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mCommodityPropertyRv.setLayoutManager(manager);
        mCommodityPropertyRv.setAdapter(commodityPropertyAdapter);
        commodityPropertyAdapter.setNewData(propertyListBeans);
        commodityPropertyAdapter.setOnAdapterItemCallBack(new CommodityPropertyAdapter.OnAdapterItemCallBack() {


            @Override
            public void propertySelected(CommodityPropertyListBean.PropertyContentBean propertyContentBean) {
                // : 2022/7/15 获取选中的属性
                initCommodityPrice();


            }
        });
        ImageLoadUtil.loadSquareImageHasCorner(getContext(), dataBean.getCoverImg(), mCommodityPicIv);
        mAllPriceTv.setText(String.valueOf(dataBean.getPrice()));
        mNumberButton
                .setmBuyMin(Double.valueOf(dataBean.getDelivery()).intValue())
                .setCurrentNumber(Double.valueOf(dataBean.getDelivery()).intValue());

        initCommodityPrice();
    }

    private void initCommodityPrice() {
        getSelectedProperties();
        if (propertyMap.size()==propertyListBeans.size()) {
            // : 2022/5/5  获取对应的图片和价格
            commodityPropertyBean = ObjectBoxUtil.getCommodityProperty(dataBean, propertyMap);
            if (commodityPropertyBean != null) {
                ImageLoadUtil.loadSquareImageHasCorner(getContext(), commodityPropertyBean.getImage(), mCommodityPicIv);
                mAllPriceTv.setText(String.valueOf(commodityPropertyBean.getPrice()));
            }
        }
    }

    /**
     * 获取选中的属性
     */
    private void getSelectedProperties() {
        propertyMap.clear();
        for (CommodityPropertyListBean propertyListBean : propertyListBeans) {
           List<CommodityPropertyListBean.PropertyContentBean>  propertyContentBeans = propertyListBean.getPropertyContent();
            for (CommodityPropertyListBean.PropertyContentBean propertyContentBean : propertyContentBeans) {
                if (propertyContentBean.isSelected()) {
                    propertyMap.put(propertyContentBean.getPresentName(), propertyContentBean.getContent());
                }
            }
        }

    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return null;
    }

    @Override
    protected int getBottomSheetDialogLayout() {
        return 0;
    }

    /**
     * @param dataBean
     */
    public void setData(CommodityDetailBean.DataBean dataBean) {
        this.dataBean = dataBean;
        if (dataBean != null) {
            List<CommodityDetailBean.DataBean.ResultBean> resultBeans = dataBean.getResult();
            if (resultBeans != null) {
                for (CommodityDetailBean.DataBean.ResultBean resultBean : resultBeans) {
                    String propertyName = resultBean.getValue();
                    List<String> propertys = resultBean.getDetail();
                    List<CommodityPropertyListBean.PropertyContentBean> propertyContentBeans = new ArrayList<>();
                    for (int i = 0; i < propertys.size(); i++) {
                        String property = propertys.get(i);
                        if (0==i) {
                            propertyContentBeans.add(new CommodityPropertyListBean.PropertyContentBean(propertyName, property,true));
                        }else {
                            propertyContentBeans.add(new CommodityPropertyListBean.PropertyContentBean(propertyName, property,false));
                        }
                    }
                    propertyListBeans.add(new CommodityPropertyListBean(propertyName, propertyContentBeans));
                }
            }
        }
        if (commodityPropertyAdapter != null) {
            commodityPropertyAdapter.setNewData(propertyListBeans);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.close_dialog_iv) {
            dismiss();
        } else if (id == R.id.comfirm_tv) {// : 2022/5/5  加入购物车
            if (mNumberButton.getNumber()<dataBean.getDelivery()) {
                ToastUtils.toast(getContext(), "该商品最小起送量为"+dataBean.getDelivery());
                return;
            }
            if (onConfirmCallBack != null) {
                if (commodityPropertyBean == null || propertyMap.size() != propertyListBeans.size()) {
                    ToastUtils.toast(getContext(), "请选择商品属性");
                    return;
                }
                onConfirmCallBack.confirm(commodityPropertyBean, mNumberButton.getNumber());
                dismiss();
            }
        }
    }

    public interface OnConfirmCallBack {

        void confirm(CommodityPropertyBean commodityPropertyBean, int amount);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        propertyMap.clear();
        propertyMap = null;
    }


}
