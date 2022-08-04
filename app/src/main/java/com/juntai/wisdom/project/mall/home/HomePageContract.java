package com.juntai.wisdom.project.mall.home;

import com.juntai.disabled.basecomponent.mvp.IPresenter;
import com.juntai.disabled.basecomponent.mvp.IView;

/**
 * @aouther tobato
 * @description 描述
 * @date 2020/3/12 16:00
 */
public interface HomePageContract {

    String GET_WEATHER_REAL_TIME = "get_real_time";//实时数据
    String GET_FORCAST_WEATHER = "get_forcast";//预报

    String GET_PRIVINCE = "get_privince";//省份
    String GET_CITY = "get_city";//市
    String GET_TOWN = "get_town";//县
    String GET_STREET = "get_street";//街道
    


    String MENUE_MAP_TYPE = "地图";//地图类型
    String MENUE_WEATHER = "天气";//天气







    String  SHOP = "店铺";
    String  CUSTOMER = "客服";
    String  COLLECT = "收藏";
    String COLLECT_COMMODITY_SHOP = "COLLECT_COMMODITY";
    String UN_COLLECT_COMMODITY_SHOP = "UN_COLLECT_COMMODITY";




        /*====================================================    订单   ==============================================================*/


    String  ORDER_CANCEL = "取消订单";
    String  ORDER_PAY = "立即付款";
    String  ORDER_REFUND = "申请退款";
    String  ORDER_SEND = "提醒发货";
    String  ORDER_RECEIVE = "确认收货";
    String  ORDER_PROGRESS = "查看进度";
    String  ORDER_DELETE = "删除订单";
    String  ORDER_REBUY = "再来一单";
    String  ORDER_REFUND_AGREE = "商家已同意";
    String  ORDER_REFUND_UNAGREE = "商家不同意";





    interface IHomePageView extends IView {

    }

    interface IHomePagePresent extends IPresenter<IHomePageView> {
    }

}
