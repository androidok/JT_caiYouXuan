package com.juntai.wisdom.project.mall.base;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbase.util.UserInfoManager;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseActivity;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.RuleTools;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.sendcode.SmsCheckCodeActivity;
import com.juntai.wisdom.project.mall.mine.myinfo.HeadCropActivity;

import java.util.List;

/**
 * @aouther tobato
 * @description 描述
 * @date 2021-10-13 14:49
 */
public abstract class BaseWithSmsActivity extends SmsCheckCodeActivity implements View.OnClickListener {
    public static String REGIST_PHONE = "regist_phone";

    /**
     * 注册
     */
    public TextView mRegistTv;
    /**
     * 注册手机号
     */
    public EditText mRegistPhoneEt;
    /**
     * 短信验证码
     */
    public EditText mRegistCheckCodeEt;
    /**
     * 获取验证码
     */
    public TextView mRegistSendCheckCodeTv;
    /**
     * 密码
     */
    public EditText mPasswordEt;
    public ImageView mHideShowIv;
    public boolean isHide = true;//默认隐藏
    protected String headPicPath;

    @Override
    public int getLayoutView() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void initView() {
        setTitleName(getTitleName());
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setText(getCommitTextName());
        mRegistTv.setOnClickListener(this);
        mRegistPhoneEt = (EditText) findViewById(R.id.regist_phone_et);
        mRegistCheckCodeEt = (EditText) findViewById(R.id.regist_check_code_et);
        mRegistSendCheckCodeTv = (TextView) findViewById(R.id.regist_send_check_code_tv);
        mRegistSendCheckCodeTv.setOnClickListener(this);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
        mPasswordEt.setHint(getPwdHint());
        mHideShowIv = (ImageView) findViewById(R.id.hide_show_iv);
        mHideShowIv.setOnClickListener(this);
        String phone = getIntent().getStringExtra(BASE_STRING);
        if (!TextUtils.isEmpty(phone)) {
            mRegistPhoneEt.setText(phone);
        }
    }

    protected abstract String getPwdHint();

    protected abstract String getCommitTextName();

    protected abstract String getTitleName();

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.regist_tv) {
            if (checkMobileNo()) return;

            if (TextUtils.isEmpty(getTextViewValue(mRegistCheckCodeEt))) {
                ToastUtils.warning(mContext, "请输入验证码");
                return;
            }
            if (TextUtils.isEmpty(getTextViewValue(mPasswordEt))) {
                ToastUtils.warning(mContext, "请输入密码");
                return;
            }
            if (!PubUtil.checkPwdMark(getTextViewValue(mPasswordEt))) {
                ToastUtils.warning(mContext, "密码仅支持最少6位(字母数字下划线）");
                return;
            }
            commit();
        } else if (id == R.id.regist_send_check_code_tv) {// 注册界面发送验证码
            if (checkMobileNo()) return;
            sendCheckCode(getTextViewValue(mRegistPhoneEt));
        } else if (id == R.id.hide_show_iv) {
            if (isHide) {
                isHide = false;
                //设置EditText的密码为可见的
                mHideShowIv.setImageResource(R.mipmap.show_icon);
                mPasswordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                isHide = true;
                //设置EditText的密码为隐藏
                mHideShowIv.setImageResource(R.mipmap.hide_icon);
                mPasswordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            mPasswordEt.setSelection(mPasswordEt.getText().length());
        }
    }

    /**
     *
     * @return
     */
    private boolean checkMobileNo() {
        if (!RuleTools.isMobileNO(getTextViewValue(mRegistPhoneEt))) {
            ToastUtils.warning(mContext, "手机号码格式不正确");
            return true;
        }
        if ("修改密码".equals(getTitleName())) {
            if (!UserInfoManager.getPhoneNumber().equals(getTextViewValue(mRegistPhoneEt))) {
                ToastUtils.toast(mContext, "手机号码和注册号码不一致");
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPicsAndEmpressed(List<String> icons) {
        if (icons.size() > 0) {
            String path = icons.get(0);
            //跳转到裁剪头像的界面
            startActivityForResult(new Intent(this, HeadCropActivity.class).putExtra(HeadCropActivity.HEAD_PIC,
                    path), BaseActivity.BASE_REQUEST_RESULT);

        }
    }

    protected abstract void commit();

    @Override
    protected TextView getSendCodeTv() {
        return mRegistSendCheckCodeTv;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag,o);
        switch (tag) {
            case AppHttpPath.MODIFY_PWD:
                ToastUtils.toast(mContext,"修改成功");
                reLogin(getTextViewValue(mRegistPhoneEt));

                break;
            default:
                break;
        }

    }



}
