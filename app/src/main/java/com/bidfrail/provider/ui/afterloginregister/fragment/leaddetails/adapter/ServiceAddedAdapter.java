package com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.remote.ApiConfiguration;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.bidfrail.provider.model.Cart;
import com.library.adapter.recyclerview.adapter.BaseSingleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

import java.util.Objects;

public class ServiceAddedAdapter extends BaseSingleItemAdapter<Cart, BaseViewHolder> {

    private Context context;
    private String orderType;

    public ServiceAddedAdapter(Context context, String orderType) {
        this.context = context;
        this.orderType = orderType;
    }

    @Override
    protected int getViewHolderLayoutResId() {
        return R.layout.sub_service_added_row_item;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, Cart cart, int position) {
        ImageView subServiceImageView = viewHolder.findView(R.id.subServiceImageView);
        ProgressBar imageLoadingProgressBar = viewHolder.findView(R.id.imageLoadingProgressBar);
        GlideImageLoader.load(
                context,
                ApiConfiguration.IMAGE_URL + cart.getSubService().getImage(),
                subServiceImageView,
                new GlideImageLoadingListener() {
                    @Override
                    public void imageLoadSuccess() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void imageLoadError() {
                        imageLoadingProgressBar.setVisibility(View.GONE);
                    }
                });
        TextView subServiceNameTextView = viewHolder.findView(R.id.subServiceNameTextView);
        subServiceNameTextView.setText(cart.getSubService().getName());
        TextView quantityTextView = viewHolder.findView(R.id.quantityTextView);
        quantityTextView.setText("Quantity : "+cart.getQuantity());
        TextView totalAmountTextView = viewHolder.findView(R.id.totalAmountTextView);
        totalAmountTextView.setText("\u20B9 "+cart.getTotalPrice());

        if (Objects.equals(orderType, "get_bids"))
        {
            totalAmountTextView.setVisibility(View.GONE);
        }
        else
        {
            totalAmountTextView.setVisibility(View.VISIBLE);
        }
    }
}
