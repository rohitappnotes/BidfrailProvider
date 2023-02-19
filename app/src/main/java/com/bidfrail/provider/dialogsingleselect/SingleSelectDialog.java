package com.bidfrail.provider.dialogsingleselect;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.bidfrail.provider.R;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.multipurpose.dialog.MultipurposeDialog;
import java.util.ArrayList;

public class SingleSelectDialog {

    private MultipurposeDialog dialog;

    private final Activity activity;
    private int windowAnimationStyle = com.library.utilities.R.style.BottomSheetAnimation;

    private boolean enableSearchBar = false;
    private boolean enableItemImage = false;
    private boolean enableRadioButton = false;
    private SingleSelectListener singleSelectListener;

    private SingleSelectDialogRecyclerViewAdapter singleSelectDialogRecyclerViewAdapter;
    private ArrayList<SingleSelect> singleSelectArrayList = new ArrayList<>();

    public SingleSelectDialog(Activity activity) {
        this.activity = activity;
    }

    public SingleSelectDialog(Activity activity, int windowAnimationStyle) {
        this.activity = activity;
        this.windowAnimationStyle = windowAnimationStyle;
    }

    public SingleSelectDialog enableSearchBar(boolean enableSearchBar) {
        this.enableSearchBar = enableSearchBar;
        return this;
    }

    public SingleSelectDialog enableItemImage(boolean enableItemImage) {
        this.enableItemImage = enableItemImage;
        return this;
    }

    public SingleSelectDialog enableRadioButton(boolean enableRadioButton) {
        this.enableRadioButton = enableRadioButton;
        return this;
    }

    public SingleSelectDialog setMultiSelectListener(SingleSelectListener singleSelectListener) {
        this.singleSelectListener = singleSelectListener;
        return this;
    }

    public SingleSelectDialog setSingleSelectArrayList(ArrayList<SingleSelect> singleSelectArrayList) {
        this.singleSelectArrayList = singleSelectArrayList;
        return this;
    }

    public void show(String toolbarTitle) {
        dialog = new MultipurposeDialog.Builder(activity, windowAnimationStyle)
                .setView(R.layout.dialog_single_select)
                .setTheme(R.style.SingleSelectDialog)
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

        ImageView navigationIconImageView = dialog.findView(R.id.singleSelectNavigationIconImageView);
        navigationIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        TextView titleTextView = dialog.findView(R.id.singleSelectTitleTextView);
        titleTextView.setText(toolbarTitle);


        RelativeLayout singleSelectSearchBarRelativeLayout   = dialog.findView(R.id.singleSelectSearchBarRelativeLayout);
        if (enableSearchBar)
        {
            EditText searchEditText   = dialog.findView(R.id.singleSelectSearchEditText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    singleSelectDialogRecyclerViewAdapter.getFilter().filter(s);
                }
            });
        }
        else
        {
            singleSelectSearchBarRelativeLayout.setVisibility(View.GONE);
        }

        RecyclerView recyclerView = dialog.findView(R.id.singleSelectRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        singleSelectDialogRecyclerViewAdapter = new SingleSelectDialogRecyclerViewAdapter(activity, enableItemImage, enableRadioButton);
        singleSelectDialogRecyclerViewAdapter.addArrayList(singleSelectArrayList);
        recyclerView.setAdapter(singleSelectDialogRecyclerViewAdapter);

        singleSelectDialogRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<SingleSelect>() {
            @Override
            public void OnItemChildClick(View viewChild, SingleSelect singleSelect, int position) {
                if (viewChild.getId() == R.id.singleSelectItemRadioButton || viewChild.getId() == R.id.dialogSingleSelectItemRelativeLayout) {
                    deselectAll();

                    if (singleSelect.isSelected()) {
                        singleSelect.setSelected(false);
                        singleSelectDialogRecyclerViewAdapter.checkCheckBox(position, singleSelect);
                    } else {
                        singleSelect.setSelected(true);
                        singleSelectDialogRecyclerViewAdapter.checkCheckBox(position, singleSelect);
                    }

                    String id = null;
                    String name = null;

                    ArrayList<SingleSelect> selectedArrayList = new ArrayList<SingleSelect>();
                    for (int i = 0; i < singleSelectArrayList.size(); i++) {
                        if (singleSelectArrayList.get(i).isSelected()) {
                            selectedArrayList.add(singleSelectArrayList.get(i));
                        }
                    }

                    if (selectedArrayList.size() > 0) {
                        id = selectedArrayList.get(0).getId();
                        name = selectedArrayList.get(0).getName();
                    }

                    singleSelectListener.onSingleSelected(position, singleSelect, id, name);

                    if (!enableRadioButton) {
                        dismiss();
                    }
                }
            }
        });

        TextView doneButtonTextView = dialog.findView(R.id.singleSelectDoneButtonTextView);
        if (enableRadioButton) {
            doneButtonTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            doneButtonTextView.setVisibility(View.GONE);
        }
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

    public void selectAll() {
        for (int i = 0; i < singleSelectArrayList.size(); i++) {
            singleSelectArrayList.get(i).setSelected(true);
        }

        for (int i = 0; i < singleSelectDialogRecyclerViewAdapter.data.size(); i++) {
            SingleSelect singleSelect = new SingleSelect();
            singleSelect = singleSelectDialogRecyclerViewAdapter.data.get(i);
            singleSelect.setSelected(true);
            singleSelectDialogRecyclerViewAdapter.checkCheckBox(i, singleSelect);
        }
    }

    public void deselectAll() {
        for (int i = 0; i < singleSelectArrayList.size(); i++) {
            singleSelectArrayList.get(i).setSelected(false);
        }

        for (int i = 0; i < singleSelectDialogRecyclerViewAdapter.data.size(); i++) {
            SingleSelect singleSelect = new SingleSelect();
            singleSelect = singleSelectDialogRecyclerViewAdapter.data.get(i);
            singleSelect.setSelected(false);
            singleSelectDialogRecyclerViewAdapter.checkCheckBox(i, singleSelect);
        }
    }
}
