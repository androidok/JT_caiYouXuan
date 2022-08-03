package com.example.appbase.base.customview.selectPics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;
import com.example.appbase.base.selectPics.SelectPhotosFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/6/12 16:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/6/12 16:30
 */
public class SelectPicRv extends RecyclerView {

    private int mSpanCount = 4;//一行的个数，默认4
    private int mMaxCount = 9;//最大个数，默认9个
    private boolean isDetail = false;//不是详情 可删除
    private SelectPhotosFragment.OnPhotoItemClick onPhotoItemClick;


    private GridLayoutManager gridLayoutManager;
    private SelectedPicsAdapter selectedPicsAdapter;


    public SelectPicRv(Context context) {
        super(context);
        initView(context);
    }

    public SelectPicRv(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public SelectPicRv(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }


    private void initView(Context context) {
        gridLayoutManager = new GridLayoutManager(context, mSpanCount);
        selectedPicsAdapter = new SelectedPicsAdapter(R.layout.selected_pic_item);
        selectedPicsAdapter.setDetail(isDetail);
        setLayoutManager(gridLayoutManager);
        setAdapter(selectedPicsAdapter);
        selectedPicsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        selectedPicsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String icon_path = (String) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.select_pic_icon_iv) {
                    if ("-1".equals(icon_path)) {
//                        int count = mMaxCount - (icons.size() - 1);
//                        choseImageFromFragment(type, SelectPhotosFragment.this, count, SELECT_PIC_RESULT);
                    } else {
                        if (icon_path.contains(".mp4")) {
                            //视频路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onVedioPicClick(adapter, position);
                            }
                        } else {
                            //图片路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onPicClick(adapter, position);
                            }
                        }
                    }
                } else if (id == R.id.delete_pushed_news_iv) {
                    adapter.remove(position);
                }
            }
        });
    }

    public void setData(List<String> arrays) {
        if (selectedPicsAdapter != null) {
            if (arrays == null) {
                arrays = new ArrayList<>();
            }
            if (arrays.size() < mMaxCount + 1 && !isDetail) {
                arrays.add("-1");
                arrays.add("-1");
                arrays.add("-1");
            }
            selectedPicsAdapter.setNewData(arrays);
        }
    }
    /**
     * 视频图片和普通图片的点击事件
     */
    public interface OnPhotoItemClick {
        void onVedioPicClick(BaseQuickAdapter adapter, int position);

        void onPicClick(BaseQuickAdapter adapter, int position);
    }


}
