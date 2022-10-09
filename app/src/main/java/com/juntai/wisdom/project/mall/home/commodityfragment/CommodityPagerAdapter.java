package com.juntai.wisdom.project.mall.home.commodityfragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.appbase.bean.IdNameBean;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.home.shop.ShopCommodityListFragment;
import com.juntai.wisdom.project.mall.live.LiveCommodityListFragment;

import java.util.List;

/**
 * 商品 直播
 */
public class CommodityPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
   private List<IdNameBean.DataBean>  titles;
    /**
     * 0代表首页商品列表  1代表直播列表
     */
   private int  type;


    public void setTitles(List<IdNameBean.DataBean> titles) {
        this.titles = titles;
        notifyDataSetChanged();
    }

    public CommodityPagerAdapter(FragmentManager fm, Context mContext,int type) {
        super(fm);
        this.mContext = mContext;
        this.type = type;
    }

    @Override
    public Fragment getItem(int position) {
        IdNameBean.DataBean label = titles.get(position);
        int lableId = label.getId();
        if (type==0) {
            return HomePageCommodityFragment.newInstance(lableId);
        } else if (type == 1) {
            return LiveCommodityListFragment.newInstance(lableId);
        } else if (type == 2) {
            return ShopCommodityListFragment.newInstance(lableId);
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    /**
     * 自定义底部消息tab
     * @param position
     * @param isMsg
     * @return
     */
    public View getTabView(int position,boolean isMsg) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tabitem, null);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles.get(position).getName());
        return v;
    }
    /**
     * 自定义底部消息tab
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_home_shop_tabitem, null);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles.get(position).getName());
        return v;
    }




}
