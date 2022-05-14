package com.juntai.wisdom.project.utils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2020/4/15 10:08
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/15 10:08
 */
public class MultipleItem implements MultiItemEntity {


    public static final int ITEM_COMMODITY_BASE_INFO = 100;//  商品基本信息
    public static final int ITEM_COMMODITY_EVALUTA = 101;//  商品评价
    public static final int ITEM_COMMODITY_DETAIL = 102;//  商品详情




    private int itemType;
    private Object object;

    public MultipleItem(int itemType, Object object) {
        this.itemType = itemType;
        this.object = object;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
