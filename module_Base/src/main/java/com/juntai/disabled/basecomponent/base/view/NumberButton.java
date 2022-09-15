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
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.base.EditDialog;
import com.juntai.disabled.basecomponent.utils.ToastUtils;

/**
 * 购物车商品数量、增加和减少控制按钮。
 * Created by 沈钦赐 on 16/4/29.
 */
public class NumberButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    //库存
    private int mInventory = Integer.MAX_VALUE;
    //最大购买数，默认无限制
    private int mBuyMax = Integer.MAX_VALUE;
    private double mBuyMin = 0;
    private TextView mCountTv;
    private OnWarnListener mOnWarnListener;

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

        mCountTv = ((TextView) findViewById(R.id.text_count));
        mCountTv.addTextChangedListener(this);
        mCountTv.setOnClickListener(this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        boolean editable = typedArray.getBoolean(R.styleable.NumberButton_editable, true);
        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1);
        int textWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textWidth, -1);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1);
        int textColor = typedArray.getColor(R.styleable.NumberButton_textColor, 0xff000000);
        typedArray.recycle();

        setEditable(editable);
        mCountTv.setTextColor(textColor);
        subButton.setTextColor(textColor);
        addButton.setTextColor(textColor);

        if (textSize > 0)
            mCountTv.setTextSize(textSize);

        if (buttonWidth > 0) {
            LayoutParams textParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
            subButton.setLayoutParams(textParams);
            addButton.setLayoutParams(textParams);
        }
        if (textWidth > 0) {
            LayoutParams textParams = new LayoutParams(textWidth, LayoutParams.MATCH_PARENT);
            mCountTv.setLayoutParams(textParams);
        }
    }

    public double getNumber() {
        try {
            return Double.parseDouble(mCountTv.getText().toString());
        } catch (NumberFormatException e) {
        }
        mCountTv.setText("");
        return 0.0;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        double count = getNumber();
        if (id == R.id.button_sub) {
            mCountTv.requestFocus();
            if (count > mBuyMin||count==mBuyMin) {
                //正常减
                mCountTv.setText("" + (count - 1.0));
            }
            if ((count-1.0) < mBuyMin) {
                ToastUtils.toast(mContext, "该商品最小起送量为" + mBuyMin);
                mCountTv.setText(String.valueOf(count));

            }

        } else if (id == R.id.button_add) {
            mCountTv.requestFocus();

            if (count < Math.min(mBuyMax, mInventory)) {
                //正常添加
                mCountTv.setText("" + (count + 1.0));
            } else if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }

        } else if (id == R.id.text_count) {
//            mCountEt.setSelection(mCountEt.getText().toString().length());

            EditDialog editDialog = new EditDialog(mContext).builder();
            editDialog
                    .setContentEtInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL)
                    .setCanceledOnTouchOutside(false)
                    .setTitle("输入数量")
                    .setContent(String.valueOf(getNumber()))
                    .setOnConfirmListener("", new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText editText = (EditText) v;
                            double amount = Double.parseDouble(editText.getText().toString().trim());
                            if (amount < mBuyMin) {
                                ToastUtils.toast(mContext, "该商品最小起送量为" + mBuyMin);
                                return;
                            }
                            editDialog.releaseDialog();
                            if (mOnWarnListener != null)
                                mOnWarnListener.onTextChanged(amount);

                        }
                    }).show();

        }
    }

    private void onNumberInput() {
        //当前数量
        double count = getNumber();
        if (count <= 0) {
            //手动输入
            mCountTv.setText("");
            return;
        }

        int limit = Math.min(mBuyMax, mInventory);
        if (count > limit) {
            //超过了数量
            mCountTv.setText(limit + "");
            if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        } else {
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
            mCountTv.setFocusable(true);
            mCountTv.setKeyListener(new DigitsKeyListener());
        } else {
            mCountTv.setFocusable(false);
            mCountTv.setKeyListener(null);
        }
    }

    public NumberButton setCurrentNumber(double currentNumber) {
        if (currentNumber < 1) {
            mCountTv.setText("");
        }
        mCountTv.setText("" + Math.min(Math.min(mBuyMax, mInventory), currentNumber));
        return this;
    }

    public int getInventory() {
        return mInventory;
    }

    public NumberButton setInventory(int inventory) {
        mInventory = inventory;
        return this;
    }

    public int getBuyMax() {
        return mBuyMax;
    }

    public NumberButton setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public NumberButton setBuyMin(double buyMin) {
        mBuyMin = buyMin;
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
        if (mCountTv.hasFocus()) {
            onNumberInput();
        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWarnListener {
        void onWarningForInventory(int inventory);

        void onWarningForBuyMax(int max);


        void onTextChanged(double num);
    }
}