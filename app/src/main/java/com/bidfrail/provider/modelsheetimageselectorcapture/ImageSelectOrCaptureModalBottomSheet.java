package com.bidfrail.provider.modelsheetimageselectorcapture;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bidfrail.provider.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ImageSelectOrCaptureModalBottomSheet extends BottomSheetDialogFragment {

    private final ImageSelectOrCaptureListener imageSelectOrCaptureListener;

    public ImageSelectOrCaptureModalBottomSheet(ImageSelectOrCaptureListener listener) {
        this.imageSelectOrCaptureListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_select_or_capture_modal_bottom_sheet, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;   // if true then on BackPressed not work, if false on BackPressed work
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout fromGalleryLinearLayout = view.findViewById(R.id.fromGalleryLinearLayout);
        LinearLayout fromCameraLinearLayout = view.findViewById(R.id.fromCameraLinearLayout);

        fromGalleryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                imageSelectOrCaptureListener.fromGallery();
            }
        });

        fromCameraLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                imageSelectOrCaptureListener.fromCamera();
            }
        });
    }
}