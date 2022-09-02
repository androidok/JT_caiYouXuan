package com.juntai.wisdom.project.mall.mine.collect;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.CollectDataBean;
import com.juntai.disabled.basecomponent.utils.ImageLoadUtil;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/10 10:33
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/10 10:33
 */
public class CollectShopAdapter extends BaseQuickAdapter<CollectDataBean.DataDTO, BaseViewHolder> {
    public CollectShopAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectDataBean.DataDTO item) {
        helper.setGone(R.id.collect_iv,true);
        ImageLoadUtil.loadHeadCirclePic(mContext,item.getPhoto(), helper.getView(R.id.commodity_cover_iv));
        helper.setText(R.id.linearlayout_commodity_des_tv,item.getName());
        helper.addOnClickListener(R.id.collect_iv);
    }
}
