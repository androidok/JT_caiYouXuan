package com.juntai.wisdom.project.home.commodityfragment.commodity_detail;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.chat.util.MultipleItem;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.beans.CommodityDetailBean;
import com.juntai.wisdom.project.beans.CommodityEvaluationBean;
import com.juntai.wisdom.project.home.HomePageContract;
import com.juntai.wisdom.project.home.commodityfragment.CommodityPresent;

import java.util.List;
import java.util.Objects;

/**
 * @Author: tobato
 * @Description: 作用描述  商品详情
 * @CreateDate: 2022/5/3 18:01
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/3 18:01
 */
public class CommodityDetailFragment extends BaseRecyclerviewFragment<CommodityPresent> implements HomePageContract.IHomePageView {
    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
    }


    @Override
    protected void getRvAdapterData() {

    }

    /**
     * 填充数据
     */
    public void initAdapterData(CommodityDetailBean.DataBean dataBean) {
        if (dataBean != null) {
            baseQuickAdapter.addData(new MultipleItem(MultipleItem.ITEM_COMMODITY_BASE_INFO, dataBean));
        }
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItem multipleItem = (MultipleItem) adapter.getItem(position);
                switch (multipleItem.getItemType()) {
                    case MultipleItem.ITEM_COMMODITY_EVALUTA:
                        switch (view.getId()) {
                            case R.id.all_evaluation_tv:
                                // : 2022/5/4 查看全部评价 切换到评价的fragment中
                                ((CommodityDetailActivity) Objects.requireNonNull(getActivity())).setTitleName("商品评价");
                                ((CommodityDetailActivity) Objects.requireNonNull(getActivity())).initFragmentSelected(1);
                                break;
                            default:
                                break;
                        }

                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 评价数据
     */
    public void addEvaluationData(List<CommodityEvaluationBean.DataBean> arrays) {
        baseQuickAdapter.addData(new MultipleItem(MultipleItem.ITEM_COMMODITY_EVALUTA, arrays));
    }
    /**
     * 评价数据
     */
    public void addDetailData(String url) {
        baseQuickAdapter.addData(new MultipleItem(MultipleItem.ITEM_COMMODITY_DETAIL, url));
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
        return new CommodityAdapter(getFragmentManager(),null);
    }

    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((CommodityAdapter)baseQuickAdapter).releaseVideo();
    }
}
