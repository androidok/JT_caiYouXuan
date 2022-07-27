package com.juntai.wisdom.project.mall.entrance;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbase.bean.ShopDetailSellBean;
import com.example.appbase.bean.UserBean;
import com.example.chat.MyChatApp;
import com.example.net.AppHttpPath;
import com.juntai.disabled.basecomponent.bean.ContactBean;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.MD5;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.project.sell.mall.SellMainActivity;
import com.juntai.project.sell.mall.home.shop.ShopManagerActivity;
import com.juntai.project.sell.mall.utils.UserInfoManagerMall;
import com.juntai.wisdom.project.mall.MainActivity;
import com.juntai.wisdom.project.mall.R;
import com.juntai.wisdom.project.mall.base.sendcode.SmsCheckCodeActivity;
import com.juntai.wisdom.project.mall.mine.modifyPwd.BackPwdActivity;
import com.orhanobut.hawk.Hawk;

import okhttp3.FormBody;

/**
 * @aouther tobato
 * @description 描述  登录
 * @date 2020/3/6 9:12
 */
public class LoginActivity extends SmsCheckCodeActivity implements
        View.OnClickListener {
    /**
     * 登录
     */
    private TextView mLoginTv;
    /**
     * 密码
     */
    private EditText mPasswordEt;
    String account, password;
    /**
     * 找回密码
     */
    private TextView mRebackPwdTv;
    private ImageView mLoginByWchatIv;
    private ImageView mLoginByZfbIv;

    /**
     * 注册
     */
    private TextView mRegistTv;
    /**
     * 输入手机号
     */
    private EditText mRegistPhoneEt;
    /**
     * 获取验证码
     */
    private TextView mGetCodeTv;
    /**
     * 密码登录
     */
    private TextView mSwitchLoginTypeTv;
    //默认是密码登录
    private int loginType = 0;


    @Override
    public int getLayoutView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void initData() {

    }


    @Override
    protected TextView getSendCodeTv() {
        return mGetCodeTv;
    }

    @Override
    public void onSuccess(String tag, Object o) {
        super.onSuccess(tag, o);
        switch (tag) {
            //登录成功
            case AppHttpPath.LOGIN:
                UserBean loginBean = (UserBean) o;
                if (loginBean != null) {
                    ContactBean contactBean = loginBean.getData();
                    MyChatApp.isReLoadWarn = true;
                    Hawk.put(HawkProperty.SP_KEY_USER, contactBean);
                    Hawk.put(HawkProperty.SP_KEY_TOKEN, contactBean.getToken());
                    //账号类型（1学校人员；2商户人员；3农发人员）
                    int type = contactBean.getType();
                    switch (type) {
                        case 1:
                            //买家
                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                            break;
                        case 2:
                            //卖家
                            int shopStatus = contactBean.getShopState();
                            switch (shopStatus) {
                                case 0:
                                    // : 2022/6/8 跳转到店铺提交页面
                                    // : 2022/6/8 进入到店铺认证界面
                                    startActivity(new Intent(mContext, ShopManagerActivity.class));
                                    finish();
                                    break;
                                case 1:
                                    //审核中
                                    showAlertDialogOfKnown("店铺正在审核中,请耐心等待", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    break;
                                case 2:
                                    // : 2022/6/8 审核通过
                                    startActivity(new Intent(mContext, SellMainActivity.class));
                                    finish();
                                    break;
                                case 3:
                                    // : 2022/6/8 审核失败  是否需要加上原因
                                    showAlertDialog(String.format("%s,请重新提交", UserInfoManagerMall.getUser().getShopContent()), "去认证", "", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // : 2022/6/10 获取店铺信息 跳转到店铺管理页面
                                            mPresenter.getSellShopDetail(getBaseBuilder().add("shopId", String.valueOf(UserInfoManagerMall.getShopId())).build(), AppHttpPath.SHOP_DETAIL);
                                        }
                                    });

                                    break;
                                default:

                                    break;
                            }
                            break;
                        case 3:
                            //todo 管理端
                            break;
                        default:
                            break;
                    }




                }
                break;

            case AppHttpPath.SHOP_DETAIL:
                ShopDetailSellBean shopDetailBean = (ShopDetailSellBean) o;
                if (shopDetailBean != null) {
                    ShopDetailSellBean.DataBean dataBean = shopDetailBean.getData();
                    if (dataBean != null) {
                        // : 2022/6/8 进入到店铺认证界面
                        startActivity(new Intent(mContext, ShopManagerActivity.class)
                                .putExtra(BASE_PARCELABLE,dataBean));
                        finish();
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
            case R.id.login_tv:
                account = mRegistPhoneEt.getText().toString();
                password = mPasswordEt.getText().toString();

                if (!mPresenter.checkMobile(account)) {
                    return;
                }
                if (password.isEmpty()) {
                    ToastUtils.toast(mContext, loginType % 2 == 0 ? "登录密码不能为空" : "验证码不能为空");
                    return;
                }
                mPresenter.login(new FormBody.Builder().add("account", account)
                        .add("password", MD5.md5(String.format("%s#%s", account, password)))
                        .build(), AppHttpPath.LOGIN);
                break;
            case R.id.reback_pwd_tv:
                // : 2022/4/28 跳转到找回密码的界面
                startActivity(new Intent(this, BackPwdActivity.class));
                break;
            case R.id.regist_tv:
                // : 2022/4/28 跳转到注册的界面
                startActivity(new Intent(this, RegistActivity.class));
                break;
            case R.id.get_code_tv:
                sendCheckCode(getTextViewValue(mRegistPhoneEt));
                break;
            case R.id.switch_login_type_tv:
                //切换登录方式
                loginType++;
                switchLoginType();

                break;
        }
    }

    /**
     * 切换登录方式
     */
    private void switchLoginType() {

        switch (loginType % 2) {
            case 0:
                //密码登录
                setViewVisibleOrGone(false, mGetCodeTv);
                mSwitchLoginTypeTv.setText("短信验证码登录");
                mPasswordEt.setHint("请输入密码");
                break;
            case 1:
                setViewVisibleOrGone(true, mGetCodeTv);
                mSwitchLoginTypeTv.setText("密码登录");
                mPasswordEt.setHint("请输入验证码");

                break;
            default:
                break;
        }
    }


    @Override
    public void initView() {

        initToolbarAndStatusBar(false);
        mImmersionBar.statusBarColor(R.color.transparent).statusBarDarkFont(true).init();
        mLoginTv = (TextView) findViewById(R.id.login_tv);
        mLoginTv.setOnClickListener(this);
        mRebackPwdTv = (TextView) findViewById(R.id.reback_pwd_tv);
        mRebackPwdTv.setOnClickListener(this);
        mLoginByWchatIv = (ImageView) findViewById(R.id.login_by_wchat_iv);
        mLoginByWchatIv.setOnClickListener(this);
        mLoginByZfbIv = (ImageView) findViewById(R.id.login_by_zfb_iv);
        mLoginByZfbIv.setOnClickListener(this);
        mRegistTv = (TextView) findViewById(R.id.regist_tv);
        mRegistTv.setOnClickListener(this);
        mRegistPhoneEt = (EditText) findViewById(R.id.regist_phone_et);
        mPasswordEt = (EditText) findViewById(R.id.password_et);
        mGetCodeTv = (TextView) findViewById(R.id.get_code_tv);
        mGetCodeTv.setOnClickListener(this);
        mSwitchLoginTypeTv = (TextView) findViewById(R.id.switch_login_type_tv);
        mSwitchLoginTypeTv.setOnClickListener(this);
        switchLoginType();
        String phoneNum = getIntent().getStringExtra(BASE_STRING);
        if (!TextUtils.isEmpty(phoneNum)) {
            mRegistPhoneEt.setText(phoneNum);
        }
    }





    @Override
    protected boolean canCancelLoadingDialog() {
        return false;
    }
}
