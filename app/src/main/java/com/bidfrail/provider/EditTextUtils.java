package com.bidfrail.provider;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * <pre>
 *     author : 残渊
 *     time   : 2018/12/12
 *     desc   : 输入框相关工具
 * </pre>
 */


public class EditTextUtils {
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showKeyboard(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 关闭软键盘
     * @param mEditText 输入框
     * @param context 上下文
     */
    public static void closeKeyboard(Context context,EditText mEditText) {
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
