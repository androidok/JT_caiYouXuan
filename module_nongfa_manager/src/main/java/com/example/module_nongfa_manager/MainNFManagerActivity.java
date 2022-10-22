package com.example.module_nongfa_manager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appbase.scan.QRScanActivity;
import com.example.appbase.util.UserInfoManager;
import com.example.module_nongfa_manager.base.BaseNFActivity;
import com.example.module_nongfa_manager.home.NFHomeFragment;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.disabled.basecomponent.utils.ActivityManagerTool;
import com.juntai.disabled.basecomponent.utils.HawkProperty;
import com.juntai.disabled.basecomponent.utils.PubUtil;
import com.juntai.disabled.basecomponent.utils.ToastUtils;
import com.juntai.project.sell.mall.mine.MyCenterFragment;
import com.orhanobut.hawk.Hawk;

/**
 * @aouther tobato
 * @description 描述  农发管理端
 * @date 2022/7/28 16:27
 */
public class MainNFManagerActivity extends BaseNFActivity implements View.OnClickListener {

    private FrameLayout mContentFl;
    private ImageView mNavigationHomeIv;
    /**
     * 工单
     */
    private TextView mNavigationHomeTv;
    private ConstraintLayout mNavigationHomeCl;
    private ImageView mNavigationScanIv;
    private ImageView mNavigationMineIv;
    /**
     * 我的
     */
    private TextView mNavigationMineTv;
    private LinearLayout mNavigationMineLl;
    private NFHomeFragment nfHomeFragment;
    private MyCenterFragment mineFragment;
    private int clickTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            nfHomeFragment = (NFHomeFragment) getSupportFragmentManager().findFragmentByTag("home");
            mineFragment = (MyCenterFragment) getSupportFragmentManager().findFragmentByTag("mine");
        } else {
            initFragments();
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayoutView() {
        return R.layout.nf_manager_activity_main_n_f_manager;
    }

    @Override
    public void initView() {
        initToolbarAndStatusBar(false);
        mContentFl = (FrameLayout) findViewById(R.id.content_fl);
        mNavigationHomeIv = (ImageView) findViewById(R.id.navigation_home_iv);
        mNavigationHomeTv = (TextView) findViewById(R.id.navigation_home_tv);
        mNavigationHomeCl = (ConstraintLayout) findViewById(R.id.navigation_home_cl);
        mNavigationHomeCl.setOnClickListener(this);
        mNavigationScanIv = (ImageView) findViewById(R.id.navigation_scan_iv);
        mNavigationScanIv.setOnClickListener(this);
        mNavigationMineIv = (ImageView) findViewById(R.id.navigation_mine_iv);
        mNavigationMineTv = (TextView) findViewById(R.id.navigation_mine_tv);
        mNavigationMineLl = (LinearLayout) findViewById(R.id.navigation_mine_ll);
        mNavigationMineLl.setOnClickListener(this);
        initBottomViewStatus(0);
        //检测密码是否为弱口令
        if (Hawk.get(HawkProperty.SP_KEY_PWD)!=null&&!PubUtil.checkPwdMark(Hawk.get(HawkProperty.SP_KEY_PWD))) {
            showAlertDialog("检测到当前账号的密码过于简单,需要重新设置复杂密码", "前往更改", "下次再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityManagerTool.getInstance().startToModifyPwd(UserInfoManager.getPhoneNumber());
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 初始化所以得fragment
     */
    private void initFragments() {
        if (nfHomeFragment == null) {
            nfHomeFragment = new NFHomeFragment();
        }
        if (mineFragment == null) {
            mineFragment = new MyCenterFragment();
            mineFragment.setShowGuide(false);
        }
    }

    /**
     * 初始化fragment
     *
     * @param i
     */
    private void initFragmentSelected(int i) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hindFragments(fragmentTransaction);
        initFragments();
        switch (i) {
            case 0:
                if (!nfHomeFragment.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, nfHomeFragment, "home");
                }
                fragmentTransaction.show(nfHomeFragment);
                break;
            case 1:
                if (!mineFragment.isAdded()) {
                    fragmentTransaction.add(R.id.content_fl, mineFragment, "mine");
                }
                fragmentTransaction.show(mineFragment);
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的fragment
     *
     * @param fragmentTransaction
     */
    private void hindFragments(FragmentTransaction fragmentTransaction) {
        if (nfHomeFragment != null) {
            fragmentTransaction.hide(nfHomeFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }

    /**
     * 初始化底部控件的状态
     *
     * @param i
     */
    private void initBottomViewStatus(int i) {
        mNavigationHomeIv.setImageResource(R.mipmap.home_index);
        mNavigationMineIv.setImageResource(R.mipmap.mine_index);
        mNavigationHomeTv.setTextColor(ContextCompat.getColor(this, R.color.black));
        mNavigationMineTv.setTextColor(ContextCompat.getColor(this, R.color.black));

        switch (i) {
            case 0:
                initFragmentSelected(0);
                //状态栏字体颜色是否为深色
                mImmersionBar.statusBarDarkFont(true, 0.2f).init();
                mNavigationHomeIv.setImageResource(R.mipmap.home_index_sel);
                mNavigationHomeTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            case 1:
                initFragmentSelected(1);
                mImmersionBar.statusBarDarkFont(true, 0.2f).init();
                mNavigationMineIv.setImageResource(R.mipmap.mine_sel);
                mNavigationMineTv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        clickTimes++;
        if (2 == clickTimes) {
            ActivityManagerTool.getInstance().finishApp();
        } else {
            ToastUtils.toast(mContext, "再点一次退出菜优选");
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(2000);//休眠2秒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    clickTimes = 0;
                }
            }.start();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.navigation_home_cl) {
            initBottomViewStatus(0);
        } else if (id == R.id.navigation_scan_iv) {
            // : 2022/7/28 扫描
            startActivity(new Intent(mContext, QRScanActivity.class));
        } else if (id == R.id.navigation_mine_ll) {
            initBottomViewStatus(1);
        }
    }
}
