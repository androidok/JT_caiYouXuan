package com.juntai.wisdom.project.mall.mine.collect;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CollectDataBean;
import com.example.net.AppHttpPath;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.search.BaseSearchAndListActivity;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.mine.MyCenterContract;
import com.juntai.wisdom.project.mall.mine.MyCenterPresent;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 收藏的店铺
 * @date 2022/5/10 10:41
 */
public class CollectShopesActivity extends BaseSearchAndListActivity<MyCenterPresent> implements MyCenterContract.ICenterView {


    private int currentPosition;

    @Override
    protected void startSearch(String s) {
        // : 2022/5/10 查找所有收藏的店铺
        mPresenter.getShopCollectList(getBaseBuilder().add("key", s).build(), AppHttpPath.SHOP_COLLECT_LIST);
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }

    @Override
    public void initData() {
        super.initData();
        baseQuickAdapter.setEmptyView(getAdapterEmptyView("一件收藏的店铺也没有..", -1));

        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                // : 2022/5/10 跳转到店铺首页
                CollectDataBean.DataDTO dataDTO = (CollectDataBean.DataDTO) adapter.getItem(position);
                startToShop(dataDTO.getScId());
            }
        });
        baseQuickAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                currentPosition = position;
                // : 2022/9/2 收藏店铺或者取消收藏店铺
                CollectDataBean.DataDTO dataDTO = (CollectDataBean.DataDTO) adapter.getItem(position);
                showAlertDialog("确定移除收藏？", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.collectShop(getBaseBuilder()
                                .add("isCollect", "1")
                                .add("id", String.valueOf(dataDTO.getId()))
                                .add("shopId", String.valueOf(dataDTO.getScId())).build(), HomePageContract.UN_COLLECT_COMMODITY_SHOP
                        );
                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (BASE_REQUEST_RESULT == requestCode) {
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
            case AppHttpPath.SHOP_COLLECT_LIST:
                CollectDataBean collectDataBean = (CollectDataBean) o;
                if (collectDataBean != null) {
                    List<CollectDataBean.DataDTO> arrays = collectDataBean.getData();
                    baseQuickAdapter.setNewData(arrays);
                }


                break;
            case HomePageContract.UN_COLLECT_COMMODITY_SHOP:
                baseQuickAdapter.remove(currentPosition);

                break;
            default:
                break;
        }
    }
}
