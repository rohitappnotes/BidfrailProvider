package com.library.utilities;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

public class ViewUtils {

    private ViewUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static void setBackgroundColor(View view, @ColorInt int backgroundColor) {
        if (view == null)
            return;
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * Sets the background of a view. Depending on the device's API level, different methods are
     * used for setting the background.
     *
     * @param view       The view, whose background should be set, as an instance of the class {@link View}.
     *                   The view may not be null
     * @param background The background, which should be set, as an instance of the class {@link Drawable}, or
     *                   null, if no background should be set
     */
    @SuppressLint("ObsoleteSdkInt")
    @SuppressWarnings("deprecation")
    public static void setBackgroundDrawable(View view, Drawable background) {
        if (view == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * setImage(imageView, R.drawable.icon)
     */
    public static void setImage(ImageView imageView, @DrawableRes int image) {
        if (imageView == null)
            return;
        imageView.setImageResource(image);
    }

    /**
     * imageView.setColorFilter(getResources().getColor(R.color.YOUR_COLOR)); // Add tint color
     *
     * To remove tint color again we can pass null as color filter. Like
     * imageView.setColorFilter(null); // Remove tint color
     *
     * setTintColor(imageView, R.color.black)
     */
    public static void setTintColor(ImageView imageView, @ColorInt int tintColor) {
        if (imageView == null)
            return;
        imageView.setColorFilter(tintColor);
    }
}
