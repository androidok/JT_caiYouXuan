package com.juntai.wisdom.project.mine.collect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.wisdom.project.AppHttpPathMall;
import com.juntai.wisdom.project.R;
import com.juntai.wisdom.project.base.BaseSearchAndListActivity;
import com.juntai.wisdom.project.beans.CollectDataBean;
import com.juntai.wisdom.project.mine.MyCenterContract;
import com.juntai.wisdom.project.mine.MyCenterPresent;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 收藏的店铺
 * @date 2022/5/10 10:41
 */
public class CollectShopesActivity extends BaseSearchAndListActivity<MyCenterPresent> implements MyCenterContract.ICenterView {


    @Override
    protected void startSearch(String s) {
        // : 2022/5/10 查找所有收藏的店铺
        mPresenter.getShopCollectList(getBaseBuilder().add("key", s).build(), AppHttpPathMall.SHOP_COLLECT_LIST);
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // : 2022/5/10 跳转到店铺首页
                CollectDataBean.DataDTO dataDTO = (CollectDataBean.DataDTO) adapter.getItem(position);
                startToShop(dataDTO.getScId());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BASE_REQUEST_RESULT==requestCode) {
            startSearch(mSearchContentSv.getQuery().toString().trim());
        }
    }

    @Override
    protected String getTitleName() {
        return "店铺收藏";
    }

    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new CollectShopAdapter(R.layout.mall_collect_shop_item);
    }

    @Override
    protected MyCenterPresent createPresenter() {
        return new MyCenterPresent();
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPathMall.SHOP_COLLECT_LIST:
                CollectDataBean collectDataBean = (CollectDataBean) o;
                if (collectDataBean != null) {
                    List<CollectDataBean.DataDTO> arrays = collectDataBean.getData();
                    baseQuickAdapter.setNewData(arrays);
                }


                break;
            default:
                break;
        }
    }
}
