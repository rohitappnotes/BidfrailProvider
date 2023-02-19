package com.bidfrail.provider.ui.base.view;

import android.view.View;
import androidx.annotation.StringRes;

public interface BaseActivityView {
    void networkAvailable();
    void networkUnavailable();

    void showProgressBar();
    void hideProgressBar();

    void showProgressDialog();
    void hideProgressDialog();

    void showShortToast(String message);
    void showShortToast(@StringRes int messageResId);
    void showLongToast(String message);
    void showLongToast(@StringRes int messageResId);

    void showIndefiniteSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener);
    void showIndefiniteSnackBar(View parent, @StringRes int messageResId, String actionText, View.OnClickListener onClickListener);
    void showShortSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener);
    void showShortSnackBar(View parent, @StringRes int messageResId, String actionText, View.OnClickListener onClickListener);
    void showLongSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener);
    void showLongSnackBar(View parent, @StringRes int messageResId, String actionText, View.OnClickListener onClickListener);

    void setVisible(View... views);
    void setInVisible(View... views);
    void setGone(View... views);

    void hideKeyboard();
}
