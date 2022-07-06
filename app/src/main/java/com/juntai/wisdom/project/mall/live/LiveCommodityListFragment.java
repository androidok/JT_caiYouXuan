package com.juntai.wisdom.project.mall.live;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.live_moudle.live.LiveRoomActivity;
import com.juntai.disabled.basecomponent.bean.LiveListBean;
import com.example.app_basemodule.net.AppHttpPath;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.BaseRecyclerviewFragment;
import com.juntai.wisdom.project.mall.home.HomePageContract;
import com.juntai.wisdom.project.mall.home.commodityfragment.CommodityPresent;
import com.example.app_basemodule.utils.UserInfoManager;

import java.util.List;

import okhttp3.FormBody;

/**
 * @Author: tobato
 * @Description: 作用描述  商品列表
 * @CreateDate: 2022/4/29 17:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/4/29 17:37
 */
public class LiveCommodityListFragment extends BaseRecyclerviewFragment<CommodityPresent> implements HomePageContract.IHomePageView {

    private int labelId;

    public static LiveCommodityListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("label", type);
        LiveCommodityListFragment fragment = new LiveCommodityListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void lazyLoad() {
        labelId = getArguments().getInt("label");

        super.lazyLoad();


    }


    @Override
    protected BaseQuickAdapter getBaseQuickAdapter() {
        return new LiveListAdapter(R.layout.live_list_item);
    }

    @Override

    protected boolean enableLoadMore() {
        return true;
    }

    @Override
    protected CommodityPresent createPresenter() {
        return new CommodityPresent();
    }

    @Override
    protected void initView() {
        super.initView();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(manager);
        mRecyclerview.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray_lighter));
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LiveListBean.DataBean.ListBean bean = (LiveListBean.DataBean.ListBean) adapter.getItem(position);
// : 2022/7/4 进入直播间
                LiveRoomActivity.startToLiveRoomActivity(mContext, bean);

            }
        });

    }


    @Override
    protected LinearLayoutManager getBaseAdapterManager() {
        return null;
    }

    @Override
    protected void getRvAdapterData() {
        // : 2022/5/4 获取直播列表
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("page", String.valueOf(page))
                .add("userId",String.valueOf(UserInfoManager.getUserId()))
                .add("limit", String.valueOf(limit));
        mPresenter.getLiveList(0 == labelId ? builder.build() : builder
                .add("type", String.valueOf(labelId)).build(), AppHttpPath.GET_LIVE_LIST);
    }

    @Override
    protected boolean enableRefresh() {
        return true;
    }


    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);

        switch (tag) {
            case AppHttpPath.GET_LIVE_LIST:
                LiveListBean liveListBean = (LiveListBean) o;
                if (liveListBean != null) {
                    LiveListBean.DataBean dataBean = liveListBean.getData();
                    if (dataBean != null) {
                        List<LiveListBean.DataBean.ListBean> data = dataBean.getList();
                        setData(data, dataBean.getTotalCount());

                    }
                }
                break;
            default:
                break;
        }
    }
}
