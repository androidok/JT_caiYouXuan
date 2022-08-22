package com.juntai.wisdom.project.mall.entrance;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.appbase.bean.nong_fa_manager.SchoolListBean;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.base.BaseWebViewActivity;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.PickerManager;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.sendcode.SmsCheckCodeActivity;
import com.juntai.wisdom.project.mall.utils.StringTools;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  注册
 * @date 2022/4/28 17:16
 */
public class RegistActivity extends SmsCheckCodeActivity implements View.OnClickListener {


    private static  String  ACCOUNT_TYPE_SCHOOL = "学校";
    private static  String  ACCOUNT_TYPE_SHOP = "商户";
    /**
     * 输入手机号
     */
    private EditText mRegistPhoneEt;
    /**
     * 输入验证码
     */
    private EditText mCodeEt;
    /**
     * 获取验证码
     */
    private TextView mGetCodeTv;
    /**
     * 输入密码
     */
    private EditText mPasswordEt;
    /**
     * 确认密码
     */
    private EditText mRePasswordEt;
    private RadioButton mRegistAgreeProtocalRb;
    private TextView mRegistProtocalSecrecyTv;
    private TextView mRegistProtocaUserTv;
    /**
     * 注册
     */
    private TextView mRegistTv;
    /**
     * 立即登录
     */
    private TextView mLoginTv;
    //是否同意协议
    private boolean isAgreeProtocal = false;
    /**
     * 所属学校
     */
    private TextView mBelongSchoolTv;
    private SchoolListBean.DataBean schoolBean = null;
    private TextView mAccountTypeTv;
    SelectSchoolFragmentDialog selectSchoolFragmentDialog;

    @Override
    public int getLayoutView() {
        return R.layout.shop_activity_regist;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        mRegistPhoneEt = (EditText) findViewById(R.id.regist_phone_et);
        mCodeEt = (EditText) findViewById(R.id.code_et);
        mGetCodeTv = (TextView) findViewById(R.id.get_code_tv);
        mGetCodeTv.setOnClickListener(this);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
        mRePasswordEt = (EditText) findViewById(R.id.re_password_et);
        mRegistAgreeProtocalRb = (RadioButton) findViewById(R.id.regist_agree_protocal_rb);
        mRegistAgreeProtocalRb.setOnClickListener(this);
        mRegistProtocalSecrecyTv = (TextView) findViewById(R.id.regist_protocal_secrecy_tv);
        mRegistProtocalSecrecyTv.setOnClickListener(this);
        mRegistProtocaUserTv = (TextView) findViewById(R.id.regist_protoca_user_tv);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistProtocaUserTv.setOnClickListener(this);
        mAccountTypeTv = (TextView) findViewById(R.id.account_type_tv);
        mAccountTypeTv.setOnClickListener(this);
        mRegistTv.setOnClickListener(this);
        mLoginTv = (TextView) findViewById(R.id.login_tv);
        mLoginTv.setOnClickListener(this);
        mBelongSchoolTv = (TextView) findViewById(R.id.belong_school_tv);
        mBelongSchoolTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String content = getString(R.string.protocal_notice1);
        StringTools.setTextPartColor(mRegistProtocalSecrecyTv, content, content.lastIndexOf("《"), content.length(),
                "#00c750");
        String content_user = getString(R.string.protocal_notice2);
        StringTools.setTextPartColor(mRegistProtocaUserTv, content_user, content_user.lastIndexOf("《"),
                content_user.length(), "#00c750");
    }


    @Override
    protected TextView getSendCodeTv() {
        return mGetCodeTv;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            case AppHttpPath.REGIST:
                ToastUtils.toast(mContext, "注册成功");
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case AppHttpPath.SCHOOL_LIST:
                SchoolListBean schoolListBean = (SchoolListBean) o;
                if (schoolListBean != null) {
                    List<SchoolListBean.DataBean> schoolListBeans = schoolListBean.getData();
                    if (schoolListBeans != null&&!schoolListBeans.isEmpty()) {
                        if (selectSchoolFragmentDialog == null) {
                            selectSchoolFragmentDialog = new SelectSchoolFragmentDialog();
                        }
                        selectSchoolFragmentDialog.show(getSupportFragmentManager(),"SelectSchoolFragmentDialog");
                        selectSchoolFragmentDialog.setData(schoolListBeans);
                        selectSchoolFragmentDialog.setOnSchoolClickCallBack(new SelectSchoolFragmentDialog.OnSchoolClickCallBack() {
                            @Override
                            public void onSchoolClick(SchoolListBean.DataBean dataBean) {
                                schoolBean = dataBean;
                                mBelongSchoolTv.setText(dataBean.getName());
                            }
                        });
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            //用户协议
            case R.id.regist_protoca_user_tv:
                startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url",
                        "file:///android_asset/user.html"));
                break;
            //隐私协议
            case R.id.regist_protocal_secrecy_tv:
                startActivity(new Intent(mContext, BaseWebViewActivity.class).putExtra("url",
                        "file:///android_asset/yinsi.html"));
                break;
            case R.id.get_code_tv:
                sendCheckCode(getTextViewValue(mRegistPhoneEt));
                break;
            case R.id.account_type_tv:
                // : 2022/8/18 账号类型
                List<String> arrays = new ArrayList<>();
                arrays.add(ACCOUNT_TYPE_SCHOOL);
                arrays.add(ACCOUNT_TYPE_SHOP);
                PickerManager.getInstance().showOptionPicker(mContext, arrays, new PickerManager.OnOptionPickerSelectedListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        String type = arrays.get(options1);
                        mAccountTypeTv.setText(type);
                        if (0==options1) {
                            mBelongSchoolTv.setVisibility(View.VISIBLE);
                        }else {
                            mBelongSchoolTv.setVisibility(View.GONE);

                        }
                    }
                });
                break;
            case R.id.regist_tv:
                String account = getTextViewValue(mRegistPhoneEt);
                if (!mPresenter.checkMobile(account)) {
                    return;
                }
                if (!StringTools.isStringValueOk(getTextViewValue(mCodeEt))) {
                    ToastUtils.warning(mContext, "验证码不能为空");
                    return;
                }
                String pwd = getTextViewValue(mPasswordEt);
                if (!StringTools.isStringValueOk(pwd)) {
                    ToastUtils.warning(mContext, "登录密码不能为空");
                    return;
                } else {
                    if (!PubUtil.checkPwdMark(pwd)) {
                        ToastUtils.warning(mContext, "登录密码仅支持最少6位(字母数字下划线）");
                        return;
                    } else {
                        //查看确认密码
                        if (!pwd.equals(getTextViewValue(mRePasswordEt))) {
                            ToastUtils.warning(mContext, "两次输入的密码不一致");
                            return;
                        }
                    }
                }
                if (!isAgreeProtocal) {
                    ToastUtils.toast(mContext, "请阅读并同意相关协议");
                    return;
                }

                // : 2022/4/29 调用注册的接口
                FormBody.Builder builder = new FormBody.Builder();
                builder.add("phoneNumber", account);
                builder.add("password", MD5.md5(String.format("%s#%s", account, getTextViewValue(mPasswordEt))));
                builder.add("code", getTextViewValue(mCodeEt));
                if (ACCOUNT_TYPE_SCHOOL.equals(getTextViewValue(mAccountTypeTv))) {
                    if (schoolBean == null) {
                        ToastUtils.toast(mContext, "请选择所属学校");
                        return;
                    }
                    builder.add("type", "1")
                            .add("schoolId",String.valueOf(schoolBean.getId()));
                }else {
                    builder.add("type", "2");
                }
                mPresenter.regist(AppHttpPath.REGIST, builder.build());

                break;
            case R.id.regist_agree_protocal_rb:
                if (isAgreeProtocal) {
                    mRegistAgreeProtocalRb.setChecked(false);
                    isAgreeProtocal = false;
                } else {
                    mRegistAgreeProtocalRb.setChecked(true);
                    isAgreeProtocal = true;
                }
                break;
            case R.id.login_tv:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.belong_school_tv:
                mPresenter.getSchoolList(AppHttpPath.SCHOOL_LIST);

                break;
        }
    }

}
