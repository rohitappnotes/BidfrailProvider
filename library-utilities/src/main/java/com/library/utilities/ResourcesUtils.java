package com.library.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class ResourcesUtils {

    private ResourcesUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Return a drawable object associated with a particular resource ID.
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    public static Drawable getDrawable(@NonNull Context context, @DrawableRes int resourceId) {
        Drawable returnDrawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                returnDrawable = AppCompatResources.getDrawable(context, resourceId);
            } catch (Resources.NotFoundException e1) {
                try {
                    returnDrawable = ContextCompat.getDrawable(context, resourceId);
                } catch (Resources.NotFoundException e2) {
                    returnDrawable = VectorDrawableCompat.create(context.getResources(), resourceId, context.getTheme());
                }
            }
        } else {
            returnDrawable = context.getResources().getDrawable(resourceId);
        }
        return returnDrawable;
    }

    public static int getColor(@NonNull Context context, int resourceId) {
        int returnColor;
        try {
            returnColor = ContextCompat.getColor(context, resourceId);
        } catch (NullPointerException e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                returnColor = context.getColor(resourceId);
            } else {
                returnColor = context.getResources().getColor(resourceId);
            }
        }
        return returnColor;
    }

    public static String getString(@NonNull Context context, int resourceId) {
        return context.getResources().getString(resourceId);
    }
}
