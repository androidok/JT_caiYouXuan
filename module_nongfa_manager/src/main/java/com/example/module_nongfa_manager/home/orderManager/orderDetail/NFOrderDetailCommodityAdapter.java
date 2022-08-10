package com.example.module_nongfa_manager.home.orderManager.orderDetail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.nong_fa_manager.SortDetailBean;
import com.example.module_nongfa_manager.R;
import com.example.module_nongfa_manager.home.orderManager.NfOrderTextValueAdapter;
import com.juntai.disabled.basecomponent.bean.TextKeyValueBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/11 10:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/11 10:38
 */
public class NFOrderDetailCommodityAdapter extends BaseQuickAdapter<SortDetailBean.DataBean.CommodityListBean, BaseViewHolder> {
    public NFOrderDetailCommodityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortDetailBean.DataBean.CommodityListBean item) {
        ImageLoadUtil.loadSquareImageHasCorner(mContext, item.getCoverImg(), helper.getView(R.id.commodity_pic_iv));
        helper.setText(R.id.commodity_name_tv, item.getCommodityName());
        helper.setText(R.id.commodity_property_tv, item.getCartInfo());
        helper.setText(R.id.all_price_tv, String.format("￥%s", item.getPrices()));
        helper.setText(R.id.amount_tv, String.format("x%s", item.getCommodityNum()));


        RecyclerView sourceRv = helper.getView(R.id.source_info_rv);
        NfOrderTextValueAdapter sourceAdapter = new NfOrderTextValueAdapter(R.layout.nf_manager_base_info_item);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        sourceRv.setAdapter(sourceAdapter);
        sourceRv.setLayoutManager(manager);
        List<TextKeyValueBean> arraysSource = new ArrayList<>();
        arraysSource.add(new TextKeyValueBean("供货商\u3000：", item.getSupplierName()));
        arraysSource.add(new TextKeyValueBean("进货时间：", item.getPurchaseTime()));
        arraysSource.add(new TextKeyValueBean("进货人\u3000：", item.getPurchaseName()));
        sourceAdapter.setNewData(arraysSource);

        RecyclerView sourcePicRv = helper.getView(R.id.source_info_pics_rv);
        OrderCommoditySourceAdapter sourcePicAdapter = new OrderCommoditySourceAdapter(R.layout.nf_manager_source_pic_item);
        LinearLayoutManager sourcePicmanager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        sourcePicRv.setLayoutManager(sourcePicmanager);
        sourcePicRv.setAdapter(sourcePicAdapter);
        List<SortDetailBean.DataBean.CommodityListBean.TraceabilityListBean> traceabilityListBeanList = item.getTraceabilityList();
        sourcePicAdapter.setNewData(traceabilityListBeanList);


    }
}
