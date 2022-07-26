package com.example.live_moudle.live.commodity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.bean.CommodityBean;
import com.example.live_moudle.R;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述 直播商品
 * @date 2022/7/5 16:30
 */
public class CommodityBottomDialog extends BottomSheetDialog implements View.OnClickListener {
    private Context mContext;
    private RecyclerView mBaseBottomDialogRv;
    private OnItemClick onItemClick;
    /**
     * 取消
     */
    private TextView mBaseBottomDialogCancelTv;
    private LiveCommodityAdapter adapter;

    public CommodityBottomDialog(@NonNull Context context) {
        super(context, R.style.dialog_2);
        this.mContext = context;
        initView();
    }

    public CommodityBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
        this.mContext = context;
        initView();
    }

    protected CommodityBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        initView();
    }

    public CommodityBottomDialog setOnBottomDialogCallBack(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        return this;
    }

    public void setData(List<CommodityBean> arrays) {
        if (adapter != null) {
            adapter.setNewData(arrays);
        }
    }

    private void initView() {
        setContentView(R.layout.more_bottom_dialog);
        mBaseBottomDialogRv = (RecyclerView) findViewById(R.id.base_bottom_dialog_rv);
        mBaseBottomDialogCancelTv = (TextView) findViewById(R.id.base_bottom_dialog_cancel_tv);
        mBaseBottomDialogCancelTv.setOnClickListener(this);
        adapter = new LiveCommodityAdapter(R.layout.live_commodity_item);
        mBaseBottomDialogRv.setAdapter(adapter);
        mBaseBottomDialogRv.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (onItemClick != null) {
                    onItemClick.onChildItemClick(adapter,view,position);
                }
            }
        });
        setCanceledOnTouchOutside(true);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.base_bottom_dialog_cancel_tv) {
            dismiss();
        }
    }

    public  interface OnItemClick{
        void onChildItemClick(BaseQuickAdapter adapter, View view, int position);
    }
}
