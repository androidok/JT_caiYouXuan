package com.juntai.wisdom.project.home.shop.ijkplayer;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.mvp.IModel;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/5/30 9:49
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/5/30 9:49
 */
public class PlayPresent extends BasePresenter<IModel, PlayContract.IPlayView>  {
    @Override
    protected IModel createModel() {
        return null;
    }

    /**
     * 配置view的margin属性
     */
    public void setMarginOfConstraintLayout(View view, Context context, int left, int top, int right, int bottom) {
        left = DisplayUtil.dp2px(context, left);
        top = DisplayUtil.dp2px(context, top);
        right = DisplayUtil.dp2px(context, right);
        bottom = DisplayUtil.dp2px(context, bottom);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(layoutParams);
    }

}
