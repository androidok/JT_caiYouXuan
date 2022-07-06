package com.juntai.wisdom.project.mall.search;

import com.example.app_basemodule.utils.UserInfoManager;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.wisdom.project.mall.base.search.BaseSearchHeadFragment;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @CreateDate: 2022/5/28 14:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/5/28 14:44
 */
public class HomeSearchHeadFragment extends BaseSearchHeadFragment {
    @Override
    protected String getHiskey() {
        return HawkProperty.getHomeSearchHisKey(UserInfoManager.getUserId());
    }
}
