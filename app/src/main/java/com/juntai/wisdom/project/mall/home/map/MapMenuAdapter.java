package com.juntai.wisdom.project.mall.home.map;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.appbase.bean.PicTextBean;
import com.juntai.wisdom.project.mall.R;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/4/18 15:39
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/4/18 15:39
 */
public class MapMenuAdapter extends BaseQuickAdapter<PicTextBean, BaseViewHolder> {
    public MapMenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PicTextBean item) {
        helper.setImageResource(R.id.home_page_menu_iv,item.getPicRes());

    }
}
