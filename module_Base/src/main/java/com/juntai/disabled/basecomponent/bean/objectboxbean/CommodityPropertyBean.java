package com.juntai.disabled.basecomponent.bean.objectboxbean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/4 16:37
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/4 16:37
 */
@Entity
public class CommodityPropertyBean {
    /**
     * image : https://www.juntaikeji.com:21900/2022-04-26/1650947938399.png
     * price : 10.0
     * unique : 5b874f185f8343bda79e67bf890b3947
     * detail : {"颜色":"红色","尺码":"M"}
     * stock : 500
     */
    @Id
    public long id;
    //商品ID
    private int commodityId;
    //店铺ID
    private int shopId;
    private String commodityName;
    private String sku;
    private int stock;
    private int sales;
    private String unique;
    private double price;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }


    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getCommodityName() {
        return commodityName == null ? "" : commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName == null ? "" : commodityName;
    }

    public String getSku() {
        return sku == null ? "" : sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? "" : sku;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }
}
