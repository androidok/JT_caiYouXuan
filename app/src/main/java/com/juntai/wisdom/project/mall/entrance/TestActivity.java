package com.juntai.wisdom.project.mall.entrance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.appbase.base.customview.selectPics.SelectPicRv;
import com.juntai.wisdom.project.mall.R;

public class TestActivity extends AppCompatActivity {

    private SelectPicRv mCustomSpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mCustomSpr = (SelectPicRv) findViewById(R.id.custom_spr);
    }
}
