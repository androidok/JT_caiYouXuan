package com.juntai.wisdom.project.mall.entrance;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.appbase.base.adapter.SelectTextAdapter;
import com.example.appbase.bean.SelectTextBean;
import com.example.appbase.bean.nong_fa_manager.SchoolListBean;
import com.juntai.disabled.basecomponent.utils.DisplayUtil;
import com.juntai.wisdom.project.mall.MyApp;
import com.juntai.wisdom.project.mall.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: tobato
 * @Description: 作用描述
 * @UpdateUser: 更新者
 */
public class SelectSchoolFragmentDialog extends DialogFragment implements View.OnClickListener {


    private RecyclerView mRecyclerview;
    private SelectTextAdapter selectTextAdapter;
    private List<SchoolListBean.DataBean> schoolListBeans;
    private FragmentManager manager;
    private OnSchoolClickCallBack onSchoolClickCallBack;

    public void setOnSchoolClickCallBack(OnSchoolClickCallBack onSchoolClickCallBack) {
        this.onSchoolClickCallBack = onSchoolClickCallBack;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.select_school_fg, container);
        initView(view);
        return view;
    }


    @Override
    public void show(FragmentManager manager, String tag) {
        this.manager = manager;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed())
                return;
        }
        try {
            //在每个add事务前增加一个remove事务，防止连续的add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            //同一实例使用不同的tag会异常，这里捕获一下
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void setData(List<SchoolListBean.DataBean> schoolListBeans) {
        this.schoolListBeans = schoolListBeans;
        if (selectTextAdapter != null) {
            selectTextAdapter.setNewData(getAdapterData(schoolListBeans));
        }

    }

    private List<SelectTextBean> getAdapterData(List<SchoolListBean.DataBean> schoolListBeans) {
        List<SelectTextBean> arrays = new ArrayList<>();
        for (SchoolListBean.DataBean schoolListBean : schoolListBeans) {
            arrays.add(new SelectTextBean(SelectTextBean.SCHOOL_KEY, schoolListBean));
        }
        return arrays;
    }

    private void initView(View view) {
        setCancelable(false);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        view.findViewById(R.id.alert_title_tv).setOnClickListener(this);
        mRecyclerview.setMinimumHeight(DisplayUtil.dp2px(MyApp.app, 100));

        selectTextAdapter = new SelectTextAdapter(R.layout.select_text_item);
        mRecyclerview.setAdapter(selectTextAdapter);
        selectTextAdapter.setNewData(getAdapterData(schoolListBeans));
        LinearLayoutManager manager = new LinearLayoutManager(MyApp.app, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(manager);


        SmartRefreshLayout mSmartrefreshlayout = (SmartRefreshLayout) view.findViewById(R.id.smartrefreshlayout);
        mSmartrefreshlayout.setEnableRefresh(false);
        mSmartrefreshlayout.setEnableLoadMore(false);
        SearchView mSearchContentSv = (SearchView) view.findViewById(R.id.search_content_sv);
        SearchView.SearchAutoComplete textView = (SearchView.SearchAutoComplete) mSearchContentSv.findViewById(R.id.search_src_text);
        textView.setTextSize(12);
        mSearchContentSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!StringTools.isStringValueOk(mSearchContentSv.getQuery().toString().trim())) {
//                    ToastUtils.warning(mContext, "请输入要搜索的内容");
//                    return;
//                }
//                startSearch(mSearchContentSv.getQuery().toString().trim());
            }
        });
        mSearchContentSv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
//                if (!StringTools.isStringValueOk(s)) {
//                    ToastUtils.warning(mContext, "请输入要搜索的内容");
//                    return false;
//                }
                // 调用搜索接口
//                startSearch(mSearchContentSv.getQuery().toString().trim());

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (!TextUtils.isEmpty(s)) {
                    selectTextAdapter.setNewData(filterData(s));
                }else {
                    selectTextAdapter.setNewData(getAdapterData(schoolListBeans));
                }
                mSearchContentSv.setFocusable(false);
                mSearchContentSv.setFocusableInTouchMode(false);
                mSearchContentSv.clearFocus();
                return false;
            }
        });

        selectTextAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SelectTextBean dataBean = (SelectTextBean) adapter.getItem(position);
                if (onSchoolClickCallBack != null) {
                    dismiss();
                    onSchoolClickCallBack.onSchoolClick((SchoolListBean.DataBean) dataBean.getObject());
                }
            }
        });
    }

    private List<SelectTextBean> filterData(String filterData) {
        List<SchoolListBean.DataBean> arrays = new ArrayList<>();
        if (schoolListBeans != null) {
            for (SchoolListBean.DataBean schoolListBean : schoolListBeans) {
                if (schoolListBean != null&&!TextUtils.isEmpty(schoolListBean.getName())) {
                    if (schoolListBean.getName().contains(filterData)) {
                        arrays.add(schoolListBean);
                    }
                }

            }
        }
        return getAdapterData(arrays);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alert_title_tv:
                dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager = null;
    }


    public interface  OnSchoolClickCallBack{
        void  onSchoolClick(SchoolListBean.DataBean dataBean);
    }

}
