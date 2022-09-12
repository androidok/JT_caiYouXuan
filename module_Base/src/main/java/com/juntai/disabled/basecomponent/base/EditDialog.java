package com.juntai.disabled.basecomponent.base;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juntai.disabled.basecomponent.R;
import com.juntai.disabled.basecomponent.utils.ToastUtils;


/**
 * @aouther tobato
 * @description 描述  编辑的弹窗
 * @date 2022/8/30 14:27
 */
public class EditDialog {
    TextView titleTv;
    EditText contentEt;
    TextView mConfirmTv;

    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private DisplayMetrics display;

    public EditDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = context.getResources().getDisplayMetrics();
    }

    public EditDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.base_edit_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        contentEt = view.findViewById(R.id.edit_dialog_content_et);
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 29) {
                    ToastUtils.toast(context, "只能输入30个字");
                }

            }
        });
        mConfirmTv = view.findViewById(R.id.edit_dialog_confirm_btn);
        titleTv = view.findViewById(R.id.edit_dialog_title_tv);
        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                EventManager.getEventBus().post(new EventBusObject(EventBusObject.HIDE_SOFT_KEY,""));
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.shop_ActionSheetDialogStyle);
        dialog.setContentView(view);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.widthPixels * 0.8f),
                LinearLayout.LayoutParams.WRAP_CONTENT));

        return this;
    }

    /**
     * 设置提示窗字体样式
     *
     * @return
     */
    public EditDialog setTextStyle() {
        titleTv.getPaint().setFakeBoldText(true);
        mConfirmTv.getPaint().setFakeBoldText(true);
        contentEt.setTextSize(16);
        contentEt.setAlpha(0.5f);
        contentEt.setGravity(Gravity.CENTER);
        return this;
    }

    /**
     * 标题
     *
     * @param title
     * @return
     */
    public EditDialog setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(title);
        }
        return this;
    }

    /**
     * shezh 内容
     *
     * @param content
     * @return
     */
    public EditDialog setContent(String content) {
        contentEt.setText(content);
        contentEt.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(contentEt, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }

        contentEt.setSelection(content.length());
        return this;
    }

    public TextView getContentTextView() {
        return contentEt;
    }

    /**
     * 设置确认监听
     *
     * @param text
     * @param listener
     * @return
     */
    public EditDialog setOnConfirmListener(String text, final View.OnClickListener listener) {
        if (text != null && !text.equals("")) {
            mConfirmTv.setText(text);
        }
        mConfirmTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(contentEt);
                InputMethodManager imm = (InputMethodManager) contentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(contentEt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        return this;
    }

    public void releaseDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

    }

    /**
     * 是否点击外部消失
     *
     * @param isCan
     * @return
     */
    public EditDialog setCanceledOnTouchOutside(boolean isCan) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isCan);
            dialog.setCancelable(isCan);
        }

        return this;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();

        }
    }
}
