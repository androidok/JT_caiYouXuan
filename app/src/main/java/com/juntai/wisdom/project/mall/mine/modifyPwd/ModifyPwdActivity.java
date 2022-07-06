package com.juntai.wisdom.project.mall.mine.modifyPwd;

import com.example.app_basemodule.utils.UserInfoManager;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.example.app_basemodule.net.AppHttpPath;
import com.juntai.wisdom.project.mall.base.BaseWithSmsActivity;

/**
 * @aouther tobato
 * @description 描述  修改密码
 * @date 2021-10-13 15:09
 */
public class ModifyPwdActivity extends BaseWithSmsActivity {

    @Override
    protected String getPwdHint() {
        return "请输入新密码";
    }

    @Override
    protected String getCommitTextName() {
        return "提交";
    }

    @Override
    protected String getTitleName() {
        return "修改密码";
    }

    @Override
    protected void commit() {
        if (!UserInfoManager.getPhoneNumber().equals(getTextViewValue(mRegistPhoneEt))) {
            ToastUtils.toast(mContext, "请输入注册的手机号");
            return;
        }
// : 2022/5/10 调用修改密码的接口
        mPresenter.modifyPwd(
                getBaseBuilder().add("phone", getTextViewValue(mRegistPhoneEt))
                        .add("code", getTextViewValue(mRegistCheckCodeEt))
                        .add("newPwd", MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt), getTextViewValue(mPasswordEt)))).build(), AppHttpPath.MODIFY_PWD);
    }

}
