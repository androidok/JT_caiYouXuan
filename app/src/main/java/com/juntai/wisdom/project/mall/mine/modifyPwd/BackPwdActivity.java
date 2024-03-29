package com.juntai.wisdom.project.mall.mine.modifyPwd;

import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.wisdom.project.mall.base.BaseWithSmsActivity;

/**
 * @aouther tobato
 * @description 描述  找回密码
 * @date 2022/5/10 10:14
 */
public class BackPwdActivity extends BaseWithSmsActivity {

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
        return "找回密码";
    }

    @Override
    protected void commit() {

// : 2022/5/10 调用修改密码的接口
        mPresenter.modifyPwd(
                getBaseBuilder().add("phone", getTextViewValue(mRegistPhoneEt))
                        .add("code", getTextViewValue(mRegistCheckCodeEt))
                        .add("newPwd", MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt), getTextViewValue(mPasswordEt)))).build(), AppHttpPath.MODIFY_PWD);
    }

}
