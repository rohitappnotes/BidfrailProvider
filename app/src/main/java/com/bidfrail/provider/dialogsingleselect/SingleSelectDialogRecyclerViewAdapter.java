package com.bidfrail.provider.dialogsingleselect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;
import java.util.ArrayList;

public class SingleSelectDialogRecyclerViewAdapter extends BaseSingleItemAdapter<SingleSelect, BaseViewHolder> implements Filterable {

    private static final String TAG = SingleSelectDialogRecyclerViewAdapter.class.getSimpleName();

    public Context context;
    private final boolean enableItemImage;
    private final boolean enableRadioButton;

    public ArrayList<SingleSelect> filterList;
    public SingleSelectDialogCustomFilter customFilter;

    public SingleSelectDialogRecyclerViewAdapter(Context context, boolean enableItemImage, boolean enableRadioButton) {
        this.context = context;
        this.enableItemImage = enableItemImage;
        this.enableRadioButton = enableRadioButton;

        filterList = (ArrayList<SingleSelect>) data;

        addChildClickViewIds(R.id.dialogSingleSelectItemRelativeLayout);
        addChildClickViewIds(R.id.singleSelectItemRadioButton);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.dialog_single_select_item;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, SingleSelect item, int position) {
        ImageView singleSelectItemImageView = viewHolder.findView(R.id.singleSelectItemImageView);
        TextView singleSelectItemNameTextView = viewHolder.findView(R.id.singleSelectItemNameTextView);
        CheckBox singleSelectItemRadioButton = viewHolder.findView(R.id.singleSelectItemRadioButton);

        if (enableItemImage) {
            singleSelectItemImageView.setVisibility(View.VISIBLE);
            GlideImageLoader.load(
                    context,
                    item.getImage(),
                    singleSelectItemImageView,
                    new GlideImageLoadingListener() {
                        @Override
                        public void imageLoadSuccess() {
                        }

                        @Override
                        public void imageLoadError() {
                        }
                    });
        } else {
            singleSelectItemImageView.setVisibility(View.GONE);
        }

        if (enableRadioButton) {
            singleSelectItemRadioButton.setVisibility(View.VISIBLE);
        } else {
            singleSelectItemRadioButton.setVisibility(View.GONE);
        }

        singleSelectItemNameTextView.setText(item.getName());
        singleSelectItemRadioButton.setChecked(item.isSelected());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void checkCheckBox(int position, SingleSelect singleSelect) {
        data.set(position, singleSelect);
        notifyDataSetChanged();
        notifyItemChanged(position);
    }

    @Override
    public Filter getFilter() {
        if (customFilter == null) {
            customFilter = new SingleSelectDialogCustomFilter(filterList, this);
        }
        return customFilter;
    }
}
