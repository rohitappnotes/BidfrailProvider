package com.library.multipurpose.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

public class MultipurposeDialog extends Dialog {

    public static final String TAG = MultipurposeDialog.class.getSimpleName();

    private final Activity activity;
    protected View rootView;

    private final int windowAnimationStyle;

    private final int paddingLeft;
    private final int paddingTop;
    private final int paddingRight;
    private final int paddingBottom;

    private final int width;
    private final int height;
    private final int gravity;
    private final float backgroundDimmingAmount;

    private final boolean cancelable;
    private final boolean canceledOnTouchOutside;

    private final boolean applyAttribute;

    public MultipurposeDialog(@NonNull Builder builder) {
        super(builder.activity);
        activity                    = builder.activity;
        rootView                    = builder.rootView;

        windowAnimationStyle        = builder.windowAnimationStyle;

        paddingLeft                 = builder.paddingLeft;
        paddingTop                  = builder.paddingTop;
        paddingRight                = builder.paddingRight;
        paddingBottom               = builder.paddingBottom;

        width                       = builder.width;
        height                      = builder.height;
        gravity                     = builder.gravity;
        backgroundDimmingAmount     = builder.backgroundDimmingAmount;

        cancelable                  = builder.cancelable;
        canceledOnTouchOutside      = builder.canceledOnTouchOutside;

        applyAttribute              = builder.applyAttribute;
    }

    public MultipurposeDialog(@NonNull Builder builder, int themeResId) {
        super(builder.activity, themeResId);

        activity                    = builder.activity;
        rootView                    = builder.rootView;

        windowAnimationStyle        = builder.windowAnimationStyle;

        paddingLeft                 = builder.paddingLeft;
        paddingTop                  = builder.paddingTop;
        paddingRight                = builder.paddingRight;
        paddingBottom               = builder.paddingBottom;

        width                       = builder.width;
        height                      = builder.height;
        gravity                     = builder.gravity;
        backgroundDimmingAmount     = builder.backgroundDimmingAmount;

        cancelable                  = builder.cancelable;
        canceledOnTouchOutside      = builder.canceledOnTouchOutside;

        applyAttribute              = builder.applyAttribute;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
         * when press back button
         *
         * if true then dialog cancel
         * if false then dialog not cancel
         */
        setCancelable(cancelable);
        /*
         * when touch outside of dialog or press back button dialog cancel
         *
         * if true then dialog cancel
         * if false then dialog not cancel
         */
        setCanceledOnTouchOutside(canceledOnTouchOutside);

        setContentView(rootView);

        if (applyAttribute)
        {
            Window window = getWindow();

            if (window != null) {
                window.getDecorView().setMinimumWidth(activity.getResources().getDisplayMetrics().widthPixels);
                window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
                window.getDecorView().setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());
                layoutParams.width = width;
                layoutParams.height = height;
                layoutParams.gravity = gravity;
                layoutParams.dimAmount = backgroundDimmingAmount;
                layoutParams.windowAnimations = windowAnimationStyle;

                window.setAttributes(layoutParams);
            }
        }
    }

    public <E extends View> E findView(int viewId) {
        if (rootView != null) {
            return (E) rootView.findViewById(viewId);
        }
        return null;
    }

    public static class Builder {

        private final Activity activity;
        protected View rootView;

        private int themeResId                                      = 0;
        private int windowAnimationStyle;

        private int paddingLeft                                     = 0;
        private int paddingTop                                      = 0;
        private int paddingRight                                    = 0;
        private int paddingBottom                                   = 0;

        private int width                                           = ViewGroup.LayoutParams.MATCH_PARENT;
        private int height                                          = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int gravity                                         = Gravity.CENTER; /* Centered by default */
        private float backgroundDimmingAmount                       = 0.5f;

        private boolean cancelable                                  = true;
        private boolean canceledOnTouchOutside                      = true;

        private boolean applyAttribute                              = false;

        private OnCancelListener onCancelListener   = null;
        private OnDismissListener onDismissListener = null;
        private OnKeyListener onKeyListener         = null;

        public Builder(Activity activity, int windowAnimationStyle) {
            this.activity = activity;
            this.windowAnimationStyle = windowAnimationStyle;
        }

        public Builder setView(@LayoutRes int layout) {
            rootView = LayoutInflater.from(activity).inflate(layout, null);
            return this;
        }

        public Builder setTheme(@StyleRes int theme) {
            this.themeResId = theme;
            return this;
        }

        public Builder setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
            this.paddingLeft    = paddingLeft;
            this.paddingTop     = paddingTop;
            this.paddingRight   = paddingRight;
            this.paddingBottom  = paddingBottom;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setBackgroundDimmingAmount(float backgroundDimmingAmount) {
            this.backgroundDimmingAmount = backgroundDimmingAmount;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder applyAttribute(boolean applyAttribute) {
            this.applyAttribute = applyAttribute;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            this.onDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            this.onKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener, int viewId) {
            View view = rootView.findViewById(viewId);
            if (view != null) {
                view.setOnClickListener(onClickListener);
            }
            return this;
        }

        public Builder setOnClickListener(View.OnClickListener onClickListener, int... viewIds) {
            for(int viewId : viewIds) {
                View view = rootView.findViewById(viewId);
                if (view != null) {
                    view.setOnClickListener(onClickListener);
                }
            }
            return this;
        }

        public Builder setOnLongClickListener(View.OnLongClickListener onLongClickListener, int viewId) {
            View view = rootView.findViewById(viewId);
            if (view != null) {
                view.setOnLongClickListener(onLongClickListener);
            }
            return this;
        }

        public Builder setOnLongClickListener(View.OnLongClickListener onLongClickListener, int... viewIds) {
            for(int viewId : viewIds) {
                View view = rootView.findViewById(viewId);
                if (view != null) {
                    view.setOnLongClickListener(onLongClickListener);
                }
            }
            return this;
        }

        public MultipurposeDialog build() {
            if (themeResId != 0) {
                MultipurposeDialog multipurposeDialog = new MultipurposeDialog(Builder.this, themeResId);

                if (onCancelListener != null)
                {
                    multipurposeDialog.setOnCancelListener(onCancelListener);
                }

                if (onDismissListener != null)
                {
                    multipurposeDialog.setOnDismissListener(onDismissListener);
                }

                if (onKeyListener != null)
                {
                    multipurposeDialog.setOnKeyListener(onKeyListener);
                }

                return multipurposeDialog;
            }
            else
            {
                MultipurposeDialog multipurposeDialog = new MultipurposeDialog(Builder.this);

                if (onCancelListener != null)
                {
                    multipurposeDialog.setOnCancelListener(onCancelListener);
                }

                if (onDismissListener != null)
                {
                    multipurposeDialog.setOnDismissListener(onDismissListener);
                }

                if (onKeyListener != null)
                {
                    multipurposeDialog.setOnKeyListener(onKeyListener);
                }

                return multipurposeDialog;
            }
        }
    }

    public static void dismiss(MultipurposeDialog multipurposeDialog) {
        if (multipurposeDialog != null && multipurposeDialog.isShowing()) {
            multipurposeDialog.dismiss();
        }
    }

    public static void cancel(MultipurposeDialog multipurposeDialog) {
        if (multipurposeDialog != null && multipurposeDialog.isShowing()) {
            multipurposeDialog.cancel();
        }
    }
}
