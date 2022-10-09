/*
 * Copyright 2016. SHENQINCI(沈钦赐)<946736079@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.juntai.disabled.basecomponent.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

/**
 * 购物车商品数量、增加和减少控制按钮。
 * Created by 沈钦赐 on 16/4/29.
 */
public class NumberButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    //库存
    private double mInventory = Double.MAX_VALUE;
    //最大购买数，默认无限制
    private double mBuyMax = Double.MAX_VALUE;
    private EditText mCountEt;
    private OnWarnListener mOnWarnListener;

    private double mBuyMin = 1;

    private Context mContext;

    public NumberButton(Context context) {
        this(context, null);
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
//
//    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.number_layout, this);

        TextView addButton = (TextView) findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        TextView subButton = (TextView) findViewById(R.id.button_sub);
        subButton.setOnClickListener(this);

        mCountEt = ((EditText) findViewById(R.id.text_count));
        mCountEt.addTextChangedListener(this);
        mCountEt.setOnClickListener(this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        boolean editable = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1);
        int textWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textWidth, -1);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1);
        int textColor = typedArray.getColor(R.styleable.NumberButton_textColor, 0xff000000);
        typedArray.recycle();

        setEditable(editable);
        mCountEt.setTextColor(textColor);
        subButton.setTextColor(textColor);
        addButton.setTextColor(textColor);

        if (textSize > 0)
            mCountEt.setTextSize(textSize);

        if (buttonWidth > 0) {
            LayoutParams textParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
            subButton.setLayoutParams(textParams);
            addButton.setLayoutParams(textParams);
        }
        if (textWidth > 0) {
            LayoutParams textParams = new LayoutParams(textWidth, LayoutParams.MATCH_PARENT);
            mCountEt.setLayoutParams(textParams);
        }
    }

    public double getNumber() {
        try {
            return Double.parseDouble(mCountEt.getText().toString());
        } catch (NumberFormatException e) {
        }
        mCountEt.setText("1");
        return 1;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        double count = getNumber();
        if (id == R.id.button_sub) {
            mCountEt.requestFocus();
//            double amount = Double.parseDouble(mCountEt.getText().toString().trim());
            if (count > 1) {
                //正常减
                mCountEt.setText("" + (count - 1));
            }

        } else if (id == R.id.button_add) {
            mCountEt.requestFocus();

            if (count < Math.min(mBuyMax, mInventory)) {
                //正常添加
                mCountEt.setText("" + (count + 1));
            } else if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }

        }
        mCountEt.setSelection(mCountEt.getText().toString().length());

    }

    private void onNumberInput() {
        //当前数量
        double count = getNumber();
        if (count <= 0) {
            //手动输入
            mCountEt.setText("");
            return;
        }

        double limit = Math.min(mBuyMax, mInventory);
        if (count > limit) {
            //超过了数量
            mCountEt.setText(limit + "");
            if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else {
            /**
             * 小数点后只能输入一位
             */
            String str = mCountEt.getText().toString().trim();
            if (str.contains(".") && str.length() - str.indexOf(".") > 2) {
                mCountEt.setText(str.substring(0, str.length() - 1));
                mCountEt.setSelection(str.length() - 1);
            }


            if (count < mBuyMin) {
                ToastUtils.toast(mContext, "该商品最小起送量为" + mBuyMin);
                mCountEt.setText(String.valueOf(mBuyMin));
                return;
            }
            if (mOnWarnListener != null) mOnWarnListener.onTextChanged(count);
        }

    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForInventory(mInventory);
    }

    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private void warningForBuyMax() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMax(mBuyMax);
    }


    private void setEditable(boolean editable) {
        if (editable) {
            mCountEt.setFocusable(true);
//            mCountEt.setKeyListener(new DigitsKeyListener());
        } else {
            mCountEt.setFocusable(false);
            mCountEt.setKeyListener(null);
        }
    }

    public NumberButton setCurrentNumber(double currentNumber) {
        if (currentNumber < 1) mCountEt.setText("");
        mCountEt.setText("" + Math.min(Math.min(mBuyMax, mInventory), currentNumber));
        return this;
    }

    public double getInventory() {
        return mInventory;
    }

    public NumberButton setInventory(double inventory) {
        mInventory = inventory;
        return this;
    }

    public NumberButton setmBuyMin(double mBuyMin) {
        this.mBuyMin = mBuyMin;
        return this;
    }

    public double getBuyMax() {
        return mBuyMax;
    }

    public NumberButton setBuyMax(double buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public NumberButton setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mCountEt.hasFocus()) {
            onNumberInput();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWarnListener {
        void onWarningForInventory(double inventory);

        void onWarningForBuyMax(double max);


        void onTextChanged(double num);
    }
}