package com.bidfrail.provider.ui.chat.modelsheetimageviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageViewerModalBottomSheet extends BottomSheetDialogFragment {

    private FrameLayout frameLayout;
    private BottomSheetBehavior<FrameLayout> bottomSheetBehavior;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_DRAGGING:
                    Log.i("FullScreenBottomSheet", "STATE_DRAGGING");
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    Log.i("FullScreenBottomSheet", "STATE_SETTLING");
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    Log.i("FullScreenBottomSheet", "STATE_EXPANDED");
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED:
                    Log.i("FullScreenBottomSheet", "STATE_COLLAPSED");
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    Log.i("FullScreenBottomSheet", "STATE_HIDDEN");
                    dismiss();
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            Log.i("FullScreenBottomSheet", "slideOffset: " + slideOffset);
        }
    };

    private final String imageUrl;

    public ImageViewerModalBottomSheet(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_viewer_modal_bottom_sheet, container);
    }

    private boolean isOutOfBounds(BottomSheetDialog dialog, Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = dialog.getWindow().getDecorView();
        return (x < -slop) || (y < -slop)
                || (x > (decorView.getWidth()+slop))
                || (y > (decorView.getHeight()+slop));
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;

        dialog.getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (dialog.isShowing() && (event.getAction() == MotionEvent.ACTION_DOWN
                        && isOutOfBounds(dialog, getContext(), event) && dialog.getWindow().peekDecorView() != null)) {
                    dialog.hide();
                }
                return false;
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false; // if true then on BackPressed not work, if false on BackPressed work
                }
                return false;
            }
        });

        frameLayout = dialog.getDelegate().findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (frameLayout != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) frameLayout.getLayoutParams();
            layoutParams.height = getHeight();
            frameLayout.setLayoutParams(layoutParams);
            bottomSheetBehavior = BottomSheetBehavior.from(frameLayout);

            bottomSheetBehavior.setPeekHeight(getHeight());

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetBehavior.setSkipCollapsed(true);
            bottomSheetBehavior.setDraggable(false);
        }
    }

    protected int getHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    private ImageView imageView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView     = view.findViewById(R.id.imageView);

        GlideImageLoader.load(
                getContext(),
                imageUrl,
                R.color.placeholder,
                R.color.placeholder,
                imageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                    }

                    @Override
                    public void imageLoadError() {
                    }
                });
    }
}