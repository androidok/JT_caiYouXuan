package com.example.module_nongfa_manager;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.module_nongfa_manager.base.BaseNFActivity;
import com.example.module_nongfa_manager.base.NfManagerQRScanActivity;
import com.example.module_nongfa_manager.home.NFHomeFragment;
import com.juntai.disabled.basecomponent.mvp.BasePresenter;
import com.juntai.project.sell.mall.mine.MyCenterFragment;

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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.navigation_home_cl) {
            initBottomViewStatus(0);
        } else if (id == R.id.navigation_scan_iv) {
            // TODO: 2022/7/28 扫描
            startActivity(new Intent(mContext, NfManagerQRScanActivity.class));
        } else if (id == R.id.navigation_mine_ll) {
            initBottomViewStatus(1);
        }
    }
}
