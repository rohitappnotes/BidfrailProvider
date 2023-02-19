package com.library.utilities;

import android.content.Context;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * @anthor weifeng
 * @time 2018/10/30 上午10:27
 */
public class AppUtils {
    private static int SCREEN_WIDTH = 0;
    private static int SCREEN_HEIGHT = 0;
    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (SCREEN_WIDTH == 0) {
            WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = w.getDefaultDisplay();
            int rotation = d.getRotation();
            // 根据屏幕方向获取手机屏幕的真实宽度
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                SCREEN_WIDTH = d.getWidth();
            } else {
                SCREEN_WIDTH = d.getHeight();
            }
        }

        return SCREEN_WIDTH;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (SCREEN_HEIGHT == 0) {
            WindowManager w = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display d = w.getDefaultDisplay();
            int rotation = d.getRotation();

            // 根据屏幕方向获取手机屏幕的真实高度
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                SCREEN_HEIGHT = d.getHeight();
            } else {
                SCREEN_HEIGHT = d.getWidth();
            }
        }

        return SCREEN_HEIGHT;
    }
}
