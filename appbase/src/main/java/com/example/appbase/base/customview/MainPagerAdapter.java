package com.example.appbase.base.customview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbase.R;


/**
 * 首页tablayout适配器
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    SparseArray<Fragment> mFragments;
    private int[] images;
    private String[] titles;
    private TextView msgUnReadCountTv;
    public MainPagerAdapter(FragmentManager fm, Context contexts, String[] title, int[] img, SparseArray<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        images = img;
        mFragments = fragments;
        this.titles = title;
    }

    public MainPagerAdapter(FragmentManager fm, Context contexts, String[] title, SparseArray<Fragment> fragments) {
        super(fm);
        mContext = contexts;
        mFragments = fragments;
        this.titles = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    /**
     * 自定义底部消息tab
     * @param position
     * @param isMsg
     * @return
     */
    public View getTabView(int position,boolean isMsg) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.custom_tabitem, null);
        ImageView img = v.findViewById(R.id.tabitem_image);
        img.setImageResource(images[position]);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles[position]);
        if (isMsg){
            msgUnReadCountTv = v.findViewById(R.id.tabitem_count);
        }
        return v;
    }
    /**
     * 自定义顶部消息tab
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View v = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.custom_top_tabitem, null);
        TextView title = v.findViewById(R.id.tabitem_text);
        title.setText(titles[position]);
        return v;
    }
}
