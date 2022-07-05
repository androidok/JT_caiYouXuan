package com.example.live_moudle.live.commodity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.base.BaseMvpFragment;
import com.juntai.disabled.basecomponent.bean.CommodityBean;
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
        if (moreBottomDialog == null){
            moreBottomDialog = new CommodityBottomDialog(mContext);
            moreBottomDialog.setData(commodityBeans);
        }
        moreBottomDialog.setOnBottomDialogCallBack(new CommodityBottomDialog.OnItemClick() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                moreBottomDialog.dismiss();
            }
        });
        moreBottomDialog.show();
    }



}
