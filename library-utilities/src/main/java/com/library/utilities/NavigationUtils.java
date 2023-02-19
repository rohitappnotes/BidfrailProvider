package com.library.utilities;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

/**
 * @description: NavigationView工具类
 * @author: zhukai
 * @date: 2018/3/5 10:06
 */
public class NavigationUtils {

    /**
     * 去除NavigationView中menu的滚动条
     *
     * @param navigationView
     */
    public static void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView
                    .getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
