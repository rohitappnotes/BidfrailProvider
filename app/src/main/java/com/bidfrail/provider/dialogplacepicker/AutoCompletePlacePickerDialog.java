package com.bidfrail.provider.dialogplacepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bidfrail.provider.BuildConfig;
import com.bidfrail.provider.R;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.library.multipurpose.dialog.MultipurposeDialog;

public class AutoCompletePlacePickerDialog {

    private final Activity activity;
    private int windowAnimationStyle = com.library.utilities.R.style.BottomSheetAnimation;

    private MultipurposeDialog dialog;

    private AutoCompletePlacePickerAdapter autoCompletePlacePickerAdapter;
    private final PlacesClient placesClient;

    private AutoCompletePlacePickerListener autoCompletePlacePickerListener;

    public AutoCompletePlacePickerDialog(Activity activity) {
        this.activity = activity;
        Places.initialize(activity, BuildConfig.GOOGLE_MAPS_API_KEY);
        placesClient = Places.createClient(activity);
    }

    public AutoCompletePlacePickerDialog(Activity activity, int windowAnimationStyle) {
        this.activity = activity;
        this.windowAnimationStyle = windowAnimationStyle;
        Places.initialize(activity, BuildConfig.GOOGLE_MAPS_API_KEY);
        placesClient = Places.createClient(activity);
    }

    public AutoCompletePlacePickerDialog setAutoCompletePlacePickerListener(AutoCompletePlacePickerListener autoCompletePlacePickerListener) {
        this.autoCompletePlacePickerListener = autoCompletePlacePickerListener;
        return this;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void show(String toolbarTitle) {
        dialog = new MultipurposeDialog.Builder(activity, windowAnimationStyle)
                .setView(R.layout.dialog_auto_complete_place_picker)
                .setTheme(R.style.AutoCompletePlacePickerDialog)
                .setPadding(0, 0, 0, 0)
                .setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setGravity(Gravity.CENTER)
                .setCanceledOnTouchOutside(false)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        /* Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show(); */
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        /* Toast.makeText(activity, "Dismiss", Toast.LENGTH_SHORT).show(); */
                    }
                })
                .setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                            /*
                             * If return true then click on back dialog is not dismiss
                             * If return false then click on back dialog is dismiss
                             */
                            return false;
                        } else {
                            return false;
                        }
                    }
                })
                .applyAttribute(true)
                .build();

        ImageView navigationIconImageView = dialog.findView(R.id.autoCompletePlacePickerNavigationIconImageView);
        navigationIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView titleTextView = dialog.findView(R.id.autoCompletePlacePickerTitleTextView);
        titleTextView.setText(toolbarTitle);

        RecyclerView recyclerView = dialog.findView(R.id.autoCompletePlacePickerSelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        EditText searchEditText = dialog.findView(R.id.autoCompletePlacePickerSearchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    autoCompletePlacePickerAdapter.getFilter().filter(s.toString());
                    if (recyclerView.getVisibility() == View.GONE) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (recyclerView.getVisibility() == View.VISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        autoCompletePlacePickerAdapter = new AutoCompletePlacePickerAdapter(activity, autoCompletePlacePickerListener);
        autoCompletePlacePickerAdapter.setPlacesClient(placesClient);
        recyclerView.setAdapter(autoCompletePlacePickerAdapter);
        autoCompletePlacePickerAdapter.notifyDataSetChanged();

        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void cancel() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}

