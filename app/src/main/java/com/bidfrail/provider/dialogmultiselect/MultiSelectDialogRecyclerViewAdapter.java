package com.bidfrail.provider.dialogmultiselect;

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

public class MultiSelectDialogRecyclerViewAdapter extends BaseSingleItemAdapter<MultiSelect, BaseViewHolder> implements Filterable {

    public Context context;
    private final boolean enableItemImage;

    public ArrayList<MultiSelect> filterList;
    public MultiSelectDialogCustomFilter customFilter;

    public MultiSelectDialogRecyclerViewAdapter(Context context, boolean enableItemImage) {
        this.context = context;
        this.enableItemImage = enableItemImage;

        filterList = (ArrayList<MultiSelect>) data;

        addChildClickViewIds(R.id.dialogMultiSelectItemRelativeLayout);
        addChildClickViewIds(R.id.multiSelectItemCheckBox);
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.dialog_multi_select_item;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, MultiSelect item, int position) {
        ImageView multiSelectItemImageView = viewHolder.findView(R.id.multiSelectItemImageView);
        TextView multiSelectItemNameTextView = viewHolder.findView(R.id.multiSelectItemNameTextView);
        CheckBox multiSelectItemCheckBox = viewHolder.findView(R.id.multiSelectItemCheckBox);

        if (enableItemImage) {
            multiSelectItemImageView.setVisibility(View.VISIBLE);
            GlideImageLoader.load(
                    context,
                    item.getImage(),
                    multiSelectItemImageView,
                    new GlideImageLoadingListener() {
                        @Override
                        public void imageLoadSuccess() {
                        }

                        @Override
                        public void imageLoadError() {
                        }
                    });
        } else {
            multiSelectItemImageView.setVisibility(View.GONE);
        }

        multiSelectItemNameTextView.setText(item.getName());
        multiSelectItemCheckBox.setChecked(item.isSelected());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void checkCheckBox(int position, MultiSelect multiSelect) {
        data.set(position, multiSelect);
        notifyDataSetChanged();
        notifyItemChanged(position);
    }

    @Override
    public Filter getFilter() {
        if (customFilter == null) {
            customFilter = new MultiSelectDialogCustomFilter(filterList, this);
        }
        return customFilter;
    }
}
