package com.library.utilities;

import android.os.Build;

public class VersionUtils {

    private VersionUtils() {
        throw new UnsupportedOperationException("You can't create instance of Util class. Please use as static..");
    }

    public static boolean atLeastApiLevel(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    /* Checks if the device has Donut or higher version. */
    public static boolean hasDonutOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
    }

    /* Checks if the device has Eclair or higher version. */
    public static boolean hasEclairOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
    }

    /* Checks if the device has Froyo or higher version. */
    public static boolean hasFroyoOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /* Checks if the device has Gingerbread or higher version. */
    public static boolean hasGingerbreadOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /* Checks if the device has Honeycomb or higher version. */
    public static boolean hasHoneycombOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /* Checks if the device has HoneycombMR1 or higher version. */
    public static boolean hasHoneycombMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /* Checks if the device has IceCreamSandwich or higher version. */
    public static boolean hasIceCreamSandwichOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /* Checks if the device has JellyBean or higher version. */
    public static boolean hasJellyBeanOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasJellyBeanMR1OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    public static boolean hasJellyBeanMR2OrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /* Checks if the device has KitKat or higher version. */
    public static boolean hasKitKatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasKitkatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    /* Checks if the device has Lollipop or higher version. */
    public static boolean hasLollipopOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /* Checks if the device has Lollipop or higher version. */
    public static boolean hasMarshmallowOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean hasNougatOrHigher() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }
}
