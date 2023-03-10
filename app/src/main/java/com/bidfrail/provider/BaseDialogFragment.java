package com.bidfrail.provider;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 所有 DialogFragment 的父类
 *
 * @author Richie on 2017.09.26
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        initWindowParams();
        initView(view);
        return view;
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initWindowParams() {
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            windowAttributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
            windowAttributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(windowAttributes);
        }
    }

    /**
     * 获取布局资源
     *
     * @return 布局 ID
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化视图
     *
     * @param rootView 根视图
     */
    protected void initView(View rootView) {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

}
