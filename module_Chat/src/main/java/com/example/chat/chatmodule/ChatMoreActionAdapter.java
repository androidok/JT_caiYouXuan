package com.example.chat.chatmodule;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chat.R;
import com.juntai.disabled.basecomponent.bean.MyMenuBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;

/**
 * @Author: tobato
 * @Description: 作用描述   更多功能适配器
 * @CreateDate: 2021-12-04 16:31
 * @UpdateUser: 更新者
 * @UpdateDate: 2021-12-04 16:31
 */
public class ChatMoreActionAdapter extends BaseQuickAdapter<MyMenuBean, BaseViewHolder> {
    public ChatMoreActionAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyMenuBean item) {

        helper.setText(R.id.more_action_tv, item.getName());
        ImageLoadUtil.loadSquareImageWithoutCatch(mContext, item.getImageId(), (ImageView)helper.getView(R.id.more_action_iv));
    }
}
