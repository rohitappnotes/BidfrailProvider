package com.bidfrail.provider.ui.chat.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.bidfrail.provider.ui.chat.model.ChatMessage;
import com.bidfrail.provider.ui.chat.model.ChatMessageMultiItem;
import com.library.adapter.recyclerview.adapter.BaseMultipleItemAdapter;
import com.library.adapter.recyclerview.adapter.BaseViewHolder;

public class ChatMessageRecyclerViewAdapter extends BaseMultipleItemAdapter<ChatMessageMultiItem, BaseViewHolder> {

    private Context context;

    public ChatMessageRecyclerViewAdapter(Context context) {
        this.context = context;
        addItemType(1, R.layout.chat_item_outgoing_message);
        addItemType(2, R.layout.chat_item_incoming_message);

        addChildClickViewIds(R.id.outgoingImage);
        addChildClickViewIds(R.id.outgoingAudioPlayStopImageView);
        addChildClickViewIds(R.id.incomingImage);
        addChildClickViewIds(R.id.incomingAudioPlayStopImageView);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, ChatMessageMultiItem messageMultiItem, int position) {
        ChatMessage message = messageMultiItem.getMessage();
        int itemViewType = viewHolder.getItemViewType();
        switch (itemViewType) {
            case 1:
                switch (message.getMessageType()) {
                    case "text": {
                        LinearLayout outgoingTextLinearLayout = viewHolder.findView(R.id.outgoingTextLinearLayout);
                        LinearLayout outgoingImageLinearLayout = viewHolder.findView(R.id.outgoingImageLinearLayout);
                        LinearLayout outgoingAudioLinearLayout = viewHolder.findView(R.id.outgoingAudioLinearLayout);

                        outgoingTextLinearLayout.setVisibility(View.VISIBLE);
                        outgoingImageLinearLayout.setVisibility(View.GONE);
                        outgoingAudioLinearLayout.setVisibility(View.GONE);

                        viewHolder.setText(R.id.outgoingText, message.getMessage());

                        viewHolder.setText(R.id.outgoingTextDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                    }
                    case "image": {
                        LinearLayout outgoingTextLinearLayout = viewHolder.findView(R.id.outgoingTextLinearLayout);
                        LinearLayout outgoingImageLinearLayout = viewHolder.findView(R.id.outgoingImageLinearLayout);
                        LinearLayout outgoingAudioLinearLayout = viewHolder.findView(R.id.outgoingAudioLinearLayout);

                        outgoingTextLinearLayout.setVisibility(View.GONE);
                        outgoingImageLinearLayout.setVisibility(View.VISIBLE);
                        outgoingAudioLinearLayout.setVisibility(View.GONE);

                        ImageView outgoingImage = viewHolder.findView(R.id.outgoingImage);
                        ProgressBar imageLoadingProgressBar = viewHolder.findView(R.id.imageLoadingProgressBar);
                        imageLoadingProgressBar.setVisibility(View.VISIBLE);
                        GlideImageLoader.load(
                                context,
                                message.getMessage(),
                                R.color.placeholder,
                                R.color.placeholder,
                                outgoingImage,
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

                        viewHolder.setText(R.id.outgoingImageDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                    }
                    case "audio":
                        LinearLayout outgoingTextLinearLayout = viewHolder.findView(R.id.outgoingTextLinearLayout);
                        LinearLayout outgoingImageLinearLayout = viewHolder.findView(R.id.outgoingImageLinearLayout);
                        LinearLayout outgoingAudioLinearLayout = viewHolder.findView(R.id.outgoingAudioLinearLayout);

                        outgoingTextLinearLayout.setVisibility(View.GONE);
                        outgoingImageLinearLayout.setVisibility(View.GONE);
                        outgoingAudioLinearLayout.setVisibility(View.VISIBLE);

                        ImageView outgoingAudioPlayStopImageView = viewHolder.findView(R.id.outgoingAudioPlayStopImageView);
                        ImageViewCompat.setImageTintList(outgoingAudioPlayStopImageView, ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

                        TextView outgoingAudioDurationTextView = viewHolder.findView(R.id.outgoingAudioDurationTextView);
                        outgoingAudioDurationTextView.setTextColor(context.getResources().getColor(R.color.white));

                        SeekBar outgoingAudioSeekBar = viewHolder.findView(R.id.outgoingAudioSeekBar);
                        setSeekBarColor(outgoingAudioSeekBar, context.getResources().getColor(R.color.white));

                        viewHolder.setText(R.id.outgoingAudioDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                }
                break;
            case 2:
                switch (message.getMessageType()) {
                    case "text": {
                        LinearLayout incomingTextLinearLayout = viewHolder.findView(R.id.incomingTextLinearLayout);
                        LinearLayout incomingImageLinearLayout = viewHolder.findView(R.id.incomingImageLinearLayout);
                        LinearLayout incomingAudioLinearLayout = viewHolder.findView(R.id.incomingAudioLinearLayout);

                        incomingTextLinearLayout.setVisibility(View.VISIBLE);
                        incomingImageLinearLayout.setVisibility(View.GONE);
                        incomingAudioLinearLayout.setVisibility(View.GONE);

                        viewHolder.setText(R.id.incomingText, message.getMessage());

                        viewHolder.setText(R.id.incomingTextDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                    }
                    case "image": {
                        LinearLayout incomingTextLinearLayout = viewHolder.findView(R.id.incomingTextLinearLayout);
                        LinearLayout incomingImageLinearLayout = viewHolder.findView(R.id.incomingImageLinearLayout);
                        LinearLayout incomingAudioLinearLayout = viewHolder.findView(R.id.incomingAudioLinearLayout);

                        incomingTextLinearLayout.setVisibility(View.GONE);
                        incomingImageLinearLayout.setVisibility(View.VISIBLE);
                        incomingAudioLinearLayout.setVisibility(View.GONE);

                        ImageView incomingImage = viewHolder.findView(R.id.incomingImage);
                        ProgressBar imageLoadingProgressBar = viewHolder.findView(R.id.imageLoadingProgressBar);
                        imageLoadingProgressBar.setVisibility(View.VISIBLE);
                        GlideImageLoader.load(
                                context,
                                message.getMessage(),
                                R.color.placeholder,
                                R.color.placeholder,
                                incomingImage,
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

                        viewHolder.setText(R.id.incomingImageDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                    }
                    case "audio":
                        LinearLayout incomingTextLinearLayout = viewHolder.findView(R.id.incomingTextLinearLayout);
                        LinearLayout incomingImageLinearLayout = viewHolder.findView(R.id.incomingImageLinearLayout);
                        LinearLayout incomingAudioLinearLayout = viewHolder.findView(R.id.incomingAudioLinearLayout);

                        incomingTextLinearLayout.setVisibility(View.GONE);
                        incomingImageLinearLayout.setVisibility(View.GONE);
                        incomingAudioLinearLayout.setVisibility(View.VISIBLE);

                        ImageView incomingAudioPlayStopImageView = viewHolder.findView(R.id.incomingAudioPlayStopImageView);
                        ImageViewCompat.setImageTintList(incomingAudioPlayStopImageView, ColorStateList.valueOf(context.getResources().getColor(R.color.black)));

                        TextView incomingAudioDurationTextView = viewHolder.findView(R.id.incomingAudioDurationTextView);
                        incomingAudioDurationTextView.setTextColor(context.getResources().getColor(R.color.black));

                        SeekBar incomingAudioSeekBar = viewHolder.findView(R.id.incomingAudioSeekBar);
                        setSeekBarColor(incomingAudioSeekBar, context.getResources().getColor(R.color.black));

                        viewHolder.setText(R.id.incomingAudioDateTimeTextView, message.getDate() + " " + message.getTime());
                        break;
                }
                break;
            default:
                break;
        }
    }

    public static void setSeekBarColor(SeekBar seekBar, int color) {
        seekBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}
