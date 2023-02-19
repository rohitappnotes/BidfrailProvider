package com.bidfrail.provider.dialogmultiselect;

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

public class MultiSelectDialog {

    private MultipurposeDialog dialog;

    private final Activity activity;
    private int windowAnimationStyle = com.library.utilities.R.style.BottomSheetAnimation;

    private boolean isHide = false;
    private boolean enableSearchBar = false;
    private boolean enableItemImage = false;
    private MultiSelectListener multiSelectListener;

    private MultiSelectDialogRecyclerViewAdapter multiSelectDialogRecyclerViewAdapter;
    private ArrayList<MultiSelect> multiSelectArrayList = new ArrayList<>();

    public MultiSelectDialog(Activity activity) {
        this.activity = activity;
    }

    public MultiSelectDialog(Activity activity, int windowAnimationStyle) {
        this.activity = activity;
        this.windowAnimationStyle = windowAnimationStyle;
    }

    public MultiSelectDialog isHide(boolean isHide) {
        this.isHide = isHide;
        return this;
    }

    public MultiSelectDialog enableSearchBar(boolean enableSearchBar) {
        this.enableSearchBar = enableSearchBar;
        return this;
    }

    public MultiSelectDialog enableItemImage(boolean enableItemImage) {
        this.enableItemImage = enableItemImage;
        return this;
    }

    public MultiSelectDialog setMultiSelectListener(MultiSelectListener multiSelectListener) {
        this.multiSelectListener = multiSelectListener;
        return this;
    }

    public MultiSelectDialog setMultiSelectArrayList(ArrayList<MultiSelect> multiSelectArrayList) {
        this.multiSelectArrayList = multiSelectArrayList;
        return this;
    }

    public void show(String toolbarTitle) {
        dialog = new MultipurposeDialog.Builder(activity, windowAnimationStyle)
                .setView(R.layout.dialog_multi_select)
                .setTheme(R.style.MultiSelectDialog)
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

        ImageView navigationIconImageView = dialog.findView(R.id.multiSelectNavigationIconImageView);
        navigationIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isHide)
                {
                    hide();
                }
                else
                {
                    dismiss();
                }
            }
        });

        TextView titleTextView = dialog.findView(R.id.multiSelectTitleTextView);
        titleTextView.setText(toolbarTitle);

        RelativeLayout multiSelectSearchBarRelativeLayout   = dialog.findView(R.id.multiSelectSearchBarRelativeLayout);
        if (enableSearchBar)
        {
            EditText searchEditText   = dialog.findView(R.id.multiSelectSearchEditText);
            searchEditText.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    multiSelectDialogRecyclerViewAdapter.getFilter().filter(s);
                }
            });
        }
        else
        {
            multiSelectSearchBarRelativeLayout.setVisibility(View.GONE);
        }

        RecyclerView recyclerView = dialog.findView(R.id.multiSelectRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(activity));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        multiSelectDialogRecyclerViewAdapter = new MultiSelectDialogRecyclerViewAdapter(activity, enableItemImage);
        multiSelectDialogRecyclerViewAdapter.replaceArrayList(multiSelectArrayList);
        recyclerView.setAdapter(multiSelectDialogRecyclerViewAdapter);

        multiSelectDialogRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<MultiSelect>() {
            @Override
            public void OnItemChildClick(View viewChild, MultiSelect multiSelect, int position) {
                if (viewChild.getId() == R.id.multiSelectItemCheckBox || viewChild.getId() == R.id.dialogMultiSelectItemRelativeLayout) {

                    if (multiSelect.isSelected()) {
                        multiSelect.setSelected(false);
                        multiSelectDialogRecyclerViewAdapter.checkCheckBox(position, multiSelect);
                    } else {
                        multiSelect.setSelected(true);
                        multiSelectDialogRecyclerViewAdapter.checkCheckBox(position, multiSelect);
                    }

                    String commaSeparatedIds = null;
                    String commaSeparatedNames = null;

                    ArrayList<MultiSelect> selectedArrayList = new ArrayList<MultiSelect>();
                    for (int i = 0; i < multiSelectArrayList.size(); i++) {
                        if (multiSelectArrayList.get(i).isSelected()) {
                            selectedArrayList.add(multiSelectArrayList.get(i));
                        }
                    }

                    if (selectedArrayList.size() > 0) {
                        StringBuilder idsStringBuilder = new StringBuilder();
                        StringBuilder namesStringBuilder = new StringBuilder();
                        int lastIdx = selectedArrayList.size() - 1;

                        for (int i = 0; i < selectedArrayList.size(); i++) {
                            String id = selectedArrayList.get(i).getId();
                            String name = selectedArrayList.get(i).getName();
                            if (i == lastIdx) {
                                idsStringBuilder.append(id);
                                namesStringBuilder.append(name);
                            } else {
                                idsStringBuilder.append(id).append(",");
                                namesStringBuilder.append(name).append(",");
                            }
                        }
                        commaSeparatedIds = idsStringBuilder.toString();
                        commaSeparatedNames = namesStringBuilder.toString();
                    }

                    multiSelectListener.onMultiSelected(position, multiSelect, commaSeparatedIds, commaSeparatedNames);
                }
            }
        });

        TextView doneButtonTextView = dialog.findView(R.id.multiSelectDoneButtonTextView);
        doneButtonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHide)
                {
                    hide();
                }
                else
                {
                    dismiss();
                }
            }
        });

        dialog.show();
    }


    public void hide() {
        if (dialog != null && dialog.isShowing()) {
            dialog.hide();
        }
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
        for (int i = 0; i < multiSelectArrayList.size(); i++) {
            multiSelectArrayList.get(i).setSelected(true);
        }

        for (int i = 0; i < multiSelectDialogRecyclerViewAdapter.data.size(); i++) {
            MultiSelect multiSelect = new MultiSelect();
            multiSelect = multiSelectDialogRecyclerViewAdapter.data.get(i);
            multiSelect.setSelected(true);
            multiSelectDialogRecyclerViewAdapter.checkCheckBox(i, multiSelect);
        }
    }

    public void deselectAll() {
        for (int i = 0; i < multiSelectArrayList.size(); i++) {
            multiSelectArrayList.get(i).setSelected(false);
        }

        for (int i = 0; i < multiSelectDialogRecyclerViewAdapter.data.size(); i++) {
            MultiSelect multiSelect = new MultiSelect();
            multiSelect = multiSelectDialogRecyclerViewAdapter.data.get(i);
            multiSelect.setSelected(false);
            multiSelectDialogRecyclerViewAdapter.checkCheckBox(i, multiSelect);
        }
    }
}
