package com.juntai.wisdom.project.mall.mine.myinfo;

import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.wisdom.project.mall.R;
import com.example.app_basemodule.utils.UserInfoManager;
import com.orhanobut.hawk.Hawk;

/**
 * @aouther tobato
 * @description 描述  修改昵称
 * @date 2021-11-13 15:37
 */
public class ModifyNickNameActivity extends BaseModifyActivity {


    @Override
    protected String getWarnContent() {
        return getString(R.string.warn_mofify_name);
    }

    @Override
    protected String getTitleName() {
        return "更改昵称";
    }

    @Override
    protected void commit(String textViewValue) {

// 修改昵称
        mPresenter.modifyUserInfo(getBaseBuilder().add("nickname", textViewValue).build(), AppHttpPath.MODIFY_USER_ACCOUNT);


    }

    @Override
    public void onSuccess(String tag, Object o) {
        ContactBean contact = UserInfoManager.getUser();
        contact.setNickname(getTextViewValue(mNickNameEt));
        Hawk.put(HawkProperty.SP_KEY_USER, contact);
        finish();
    }
}
