package com.bidfrail.provider;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


public class ImageLoader {

    public static void loadNormalImage(Context context, ImageView ivTarget, String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadNormalImage(Context context, ImageView ivTarget, int resId) {
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, String url) {
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, String url, @DrawableRes int resId) {
        Glide.with(context)
                .load(url)
                .placeholder(resId)
                .error(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }

    public static void loadCircleImage(Context context, ImageView ivTarget, int resId) {
        Glide.with(context)
                .load(resId)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(ivTarget);
    }
}
