package com.library.utilities;

import android.content.Context;

public class ResourcesIdUtils {

    private static final String RES_ID = "id";
    private static final String RES_STRING = "string";
    private static final String RES_DRAWABLE = "drawable";
    private static final String RES_LAYOUT = "layout";
    private static final String RES_STYLE = "style";
    private static final String RES_COLOR = "color";
    private static final String RES_DIMEN = "dimen";
    private static final String RES_ANIM = "anim";
    private static final String RES_MENU = "menu";
    private static final String RES_RAW = "raw";

    private ResourcesIdUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    /**
     * Get the id of the resource file
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getId(Context context, String resName) {
        return getResId(context, resName, RES_ID);
    }

    /**
     * Get the id of the resource file string
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getStringId(Context context, String resName) {
        return getResId(context, resName, RES_STRING);
    }

    /**
     * Get the id of the resource file drawable
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getDrawableId(Context context, String resName) {
        return getResId(context, resName, RES_DRAWABLE);
    }

    /**
     * Get the id of the resource file layout
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getLayoutId(Context context, String resName) {
        return getResId(context, resName, RES_LAYOUT);
    }

    /**
     * Get the id of the resource file style
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getStyleId(Context context, String resName) {
        return getResId(context, resName, RES_STYLE);
    }

    /**
     * Get the id of the resource file color
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getColorId(Context context, String resName) {
        return getResId(context, resName, RES_COLOR);
    }

    /**
     * Get the id of the resource file dimen
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getDimenId(Context context, String resName) {
        return getResId(context, resName, RES_DIMEN);
    }

    /**
     * Get the id of the resource file ainm
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getAnimId(Context context, String resName) {
        return getResId(context, resName, RES_ANIM);
    }

    /**
     * Get the id of the resource file menu
     *
     * @param context
     * @param resName
     */
    public static int getMenuId(Context context, String resName) {
        return getResId(context, resName, RES_MENU);
    }

    /**
     * Get the id of the resource file raw
     *
     * @param context
     * @param resName
     */
    public static int getRawId(Context context, String resName) {
        return getResId(context, resName, RES_RAW);
    }

    /**
     * Get resource file ID
     *
     * @param context
     * @param resName
     * @param defType
     * @return
     */
    public static int getResId(Context context, String resName, String defType) {
        return context.getResources().getIdentifier(resName, defType, context.getPackageName());
    }
}
