package com.juntai.project.sell.mall.beans.sell.adapterbean;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2021/7/3 11:03
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/7/3 11:03
 */
public class BaseNormalRecyclerviewBean {


    private  String  key ;
    private Object  object ;

    public BaseNormalRecyclerviewBean(String key, Object object) {
        this.key = key;
        this.object = object;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }


    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
