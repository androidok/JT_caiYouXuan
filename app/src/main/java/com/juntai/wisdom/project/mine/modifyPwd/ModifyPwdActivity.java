package com.juntai.wisdom.project.mine.modifyPwd;

import com.juntai.wisdom.project.base.BaseWithSmsActivity;

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

// TODO: 2022/5/10 调用修改密码的接口
//        mPresenter.modifyPwd(UserInfoManager.getUserId(), getTextViewValue(mRegistPhoneEt), MD5.md5(String.format("%s#%s", getTextViewValue(mRegistPhoneEt), getTextViewValue(mPasswordEt)))
//                , getTextViewValue(mRegistCheckCodeEt), AppHttpPath.MODIFY_PWD);
    }

}
