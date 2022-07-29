package com.juntai.project.sell.mall.home.commodityManager.allCommodity.commoditySource;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.util.UserInfoManager;
import com.juntai.disabled.basecomponent.utils.GsonTools;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.project.sell.mall.AppHttpPathMall;
import com.juntai.project.sell.mall.R;
import com.example.appbase.bean.multiBean.BaseAdapterDataBean;
import com.example.appbase.bean.CommoditySourceDetailBean;
import com.juntai.project.sell.mall.home.shop.BaseShopActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述  添加商品溯源
 * @date 2022/7/28 9:56
 */
public class AddCommoditySourceActivity extends BaseShopActivity {


    private CommoditySourceAdapter sourceAdapter;
    private CommoditySourceDetailBean.DataBean.PhotoListBean billBean;
    private int currentViewId;
    private int commodityId;

    @Override
    protected String getTitleName() {
        return "添加商品溯源";
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setNewData(mPresenter.getCommoditySourceData(null, false));
        commodityId = getIntent().getIntExtra(BASE_ID, 0);

    }

    @Override
    protected boolean isDetail() {
        return false;
    }

    @Override
    protected View getAdapterHeadView() {
        return null;
    }

    @Override
    protected View getAdapterFootView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sell_commodity_source_footview_commit, null);
        initSourceBillInfo(view);
        TextView commitTv = view.findViewById(R.id.commit_tv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseAdapterDataBean baseAdapterDataBean = getBaseOfAdapterData();
                if (baseAdapterDataBean != null) {
                    CommoditySourceDetailBean.DataBean sourceBean = baseAdapterDataBean.getSourceBean();
                    if (sourceBean != null) {
                        List<CommoditySourceDetailBean.DataBean.PhotoListBean> bills = sourceAdapter.getData();
                        sourceBean.setPhotoList(bills);
                        sourceBean.setAccount(UserInfoManager.getAccount());
                        sourceBean.setToken(UserInfoManager.getUserToken());
                        sourceBean.setCommodityId(commodityId);
                        mPresenter.addCommoditySource(getJsonRequestBody(GsonTools.createGsonString(sourceBean)), AppHttpPathMall.ADD_COMMODITY_SOURCE);

                    }
                }


            }
        });
        commitTv.setText("提交");
        return view;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        if (AppHttpPathMall.ADD_COMMODITY_SOURCE.equals(tag)) {
            ToastUtils.toast(mContext, "提交成功");
            finish();
        }else if(AppHttpPathMall.UPLOAD_ONE_PIC.equals(tag)){
            List<String> paths = (List<String>) o;
            if (paths != null && !paths.isEmpty()) {
                String path = paths.get(0);
                if (currentViewId == R.id.commodity_bill_iv1) {
                    billBean.setPhotoOne(path);
                    sourceAdapter.notifyDataSetChanged();
                } else if (currentViewId == R.id.commodity_bill_iv2) {
                    billBean.setPhotoTwo(path);
                    sourceAdapter.notifyDataSetChanged();
                } else if (currentViewId == R.id.commodity_bill_iv3) {
                    billBean.setPhotoThree(path);
                    sourceAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            mPresenter.uploadFile(AppHttpPathMall.UPLOAD_ONE_PIC, path);

        }
    }

    private void initSourceBillInfo(View view) {
        RecyclerView sourceRv = view.findViewById(R.id.commodity_source_rv);
        sourceAdapter = new CommoditySourceAdapter(R.layout.sell_commodity_source_item);
        LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        sourceRv.setLayoutManager(manager);
        sourceRv.setAdapter(sourceAdapter);
        CommoditySourceDetailBean.DataBean.PhotoListBean photoListBean = new CommoditySourceDetailBean.DataBean.PhotoListBean();
        sourceAdapter.addData(photoListBean);
        sourceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                billBean = (CommoditySourceDetailBean.DataBean.PhotoListBean) adapter.getItem(position);
                currentViewId = view.getId();
                if (currentViewId == R.id.commodity_bill_iv1 || currentViewId == R.id.commodity_bill_iv2 || currentViewId == R.id.commodity_bill_iv3) {
                    choseImage(0, AddCommoditySourceActivity.this, 1);
                } else if (currentViewId == R.id.delete_commodity_bill_iv1) {
                    billBean.setPhotoOne("");
                    adapter.notifyItemChanged(position);
                } else if (currentViewId == R.id.delete_commodity_bill_iv2) {
                    billBean.setPhotoTwo("");
                    adapter.notifyItemChanged(position);
                } else if (currentViewId == R.id.delete_commodity_bill_iv3) {
                    billBean.setPhotoThree("");
                    adapter.notifyItemChanged(position);
                } else if (currentViewId == R.id.add_item_tv) {
                    CommoditySourceDetailBean.DataBean.PhotoListBean photoListBean = new CommoditySourceDetailBean.DataBean.PhotoListBean();
                    adapter.addData(photoListBean);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }
}
