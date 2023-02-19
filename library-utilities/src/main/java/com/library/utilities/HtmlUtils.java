package com.library.utilities;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

public class HtmlUtils {

    private HtmlUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static Spanned stripHtml(String html) {
        if (!TextUtils.isEmpty(html)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(html);
            }
        }
        return null;
    }

    public static String textFromHtml(String text){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            result = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        else
            result = Html.fromHtml(text);

        return result.toString();
    }
}