package com.example.appbase.base.customview.selectPics;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.R;

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


    private OnPhotoItemClick onPhotoItemClick;
    private int mSpanCount = 4;//一行的个数，默认4
    private int mMaxCount = 9;//最大个数，默认9个
    private boolean isDetail = false;//不是详情 可删除
    private GridLayoutManager layoutManager;


    public SelectPicRv setmSpanCount(int mSpanCount) {
        this.mSpanCount = mSpanCount;
        if (layoutManager != null) {
            layoutManager.setSpanCount(mSpanCount);
        }
        return  this;
    }


    public int getmMaxCount() {
        return mMaxCount;
    }

    public SelectPicRv setmMaxCount(int mMaxCount) {
        this.mMaxCount = mMaxCount;
        return  this;
    }

    public SelectPicRv setDetail(boolean detail) {
        isDetail = detail;
        if (selectedPicsAdapter != null) {
            selectedPicsAdapter.setDetail(isDetail);
        }
        return  this;
    }

    public SelectPicRv setOnPhotoItemClick(OnPhotoItemClick onPhotoItemClick) {
        this.onPhotoItemClick = onPhotoItemClick;
        return this;
    }




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
        layoutManager = new GridLayoutManager(context, mSpanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        selectedPicsAdapter = new SelectedPicsAdapter(R.layout.selected_pic_item);
        selectedPicsAdapter.setDetail(isDetail);
        setLayoutManager(layoutManager);
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
                        if (onPhotoItemClick != null) {
                            onPhotoItemClick.onSelectPic(adapter,view, position);
                        }
                    } else {
                        if (icon_path.contains(".mp4")) {
                            //视频路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onVedioPicClick(adapter,view, position);
                            }
                        } else {
                            //图片路径
                            if (onPhotoItemClick != null) {
                                onPhotoItemClick.onPicClick(adapter,view, position);
                            }
                        }
                    }
                } else if (id == R.id.delete_pushed_news_iv) {
                    adapter.remove(position);
                    addData(selectedPicsAdapter.getData());
                }
            }
        });
        setData(null);
    }

    public void setData(List<String> arrays) {
        if (selectedPicsAdapter != null) {
            if (arrays == null) {
                arrays = new ArrayList<>();
            }
            if (arrays.size() < mMaxCount + 1 && !isDetail) {
                arrays.add("-1");
            }
            selectedPicsAdapter.setNewData(arrays);
        }
    }

    public void addData(List<String> arrays) {
        if (selectedPicsAdapter != null) {
            List<String> pics = new ArrayList<>();
            for (String array : arrays) {
                if (!"-1".equals(array)) {
                    pics.add(array);
                }
            }
            if (pics.size() < mMaxCount && !isDetail) {
                pics.add("-1");
            }
            selectedPicsAdapter.setNewData(pics);
        }
    }
    public List<String> getAdapterData() {
        return selectedPicsAdapter.getData();
    }

    /**
     * 视频图片和普通图片的点击事件
     */
    public interface OnPhotoItemClick {
        void onVedioPicClick(BaseQuickAdapter adapter,View view, int position);

        void onPicClick(BaseQuickAdapter adapter, View view,int position);

        void onSelectPic(BaseQuickAdapter adapter,View view, int position);

    }


}
