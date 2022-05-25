package com.juntai.wisdom.project.mall.search;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.chat.util.MultipleItem;
import com.juntai.wisdom.project.R;

import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/20 11:58
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/20 11:58
 */
public class BaseSearchAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseSearchAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.ITEM_COMMODITY, R.layout.mall_collect_commodity_item);
        addItemType(MultipleItem.ITEM_SHOP, R.layout.mall_collect_shop_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {

    }
}
