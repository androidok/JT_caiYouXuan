package com.juntai.wisdom.project.mall.order.orderPay;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.PicTextBean;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/11 15:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/11 15:48
 */
public class OrderPayTypeAdapter extends BaseQuickAdapter<PicTextBean, BaseViewHolder> {
    public OrderPayTypeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicTextBean item) {
        helper.setImageResource(R.id.pay_type_iv,item.getPicRes());
        helper.setText(R.id.pay_type_name_tv,item.getTextName());
        if (item.isSelect()) {
            helper.setImageResource(R.id.pay_status_iv,R.mipmap.select_icon);
        }else {
            helper.setImageResource(R.id.pay_status_iv,R.mipmap.unselect_icon);

        }
    }
}
