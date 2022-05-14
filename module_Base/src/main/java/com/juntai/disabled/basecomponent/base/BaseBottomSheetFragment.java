package com.juntai.disabled.basecomponent.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.juntai.disabled.basecomponent.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/5 14:57
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/5 14:57
 */
public class BaseBottomSheetFragment extends BottomSheetDialogFragment {
    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private BottomSheetBehavior<FrameLayout> behavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
//         <!--   解决bottomsheetdialog顶部不能圆角的问题-->
        getDialog().getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
//        // 设置软键盘不自动弹出
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
//        FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
//        if (bottomSheet != null) {
//            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
//            layoutParams.height = getHeight();
//            behavior = BottomSheetBehavior.from(bottomSheet);
//            // 初始为展开状态
//            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        }
    }

    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }
}
