/*
 * Copyright (C) 2014-2020,Qiniu Tech. Co., Ltd.
 * Author: Tony Dylan
 * Date: 2014-12-5
 * Description: Activity工具类
 * Others:
 */
package com.library.navigator;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Activity管理
 */
public class ActivityManagerUtil {
    /**
     * ActivityManagerUtil实例
     */
    private static ActivityManagerUtil instance;
    /**
     * activity列表
     */
    private static List<Activity> activities = new ArrayList<Activity>();

    public static ActivityManagerUtil getInstance() {
        if (instance == null) {
            synchronized (ActivityManagerUtil.class) {
                if (instance == null) {
                    instance = new ActivityManagerUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 弹出activity
     *
     * @param activity 需要弹出栈的Activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    /**
     * 退出栈顶N个Activity
     *
     * @param count 需要弹出栈的Activity数量
     */
    public void popActivity(int count) {
        for (int i = 0; i < count; i++) {
            popActivity(currentActivity());
        }
    }

    /**
     * 获取栈顶的Activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        int size = activities.size();
        if (!activities.isEmpty()) {
            activity = activities.get(size - 1);
        }
        return activity;
    }

    /**
     * 当前Activity压入栈中
     *
     * @param activity 需要压入栈的Activity
     */
    public void pushActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 除了指定activity外退出所有activity
     *
     * @param cls Activity对应的类名
     */
    public void popAllExceptionOne(Class cls) {

        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (!activity.getClass().equals(cls)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 除指定两个class外全部清除
     *
     * @param cls Activity对应的类名
     */
    public void popAllExcept(Class cls, Class cls2) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(cls)
                    && !activity.getClass().equals(cls2)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 除指定四个个class外全部清除
     *
     * @param cls Activity对应的类名
     */
    public void popAllExceptFour(Class cls, Class cls2, Class cls3, Class cls4) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(cls)
                    && !activity.getClass().equals(cls2) && !activity.getClass().equals(cls3) && !activity.getClass().equals(cls4)) {
                activity.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 判断当前activity是否为当前应用的唯一一个栈中activity
     */
    public boolean isOnlyExistActivity(Activity activity) {
        return (activities.size() == 1)
                && (activities.get(0) == activity);
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activities.size(); i < size; i++) {
            if (null != activities.get(i)) {
                activities.get(i).finish();
            }
        }
        activities.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取除cls类外,栈中最顶的Activity
     */
    public Activity getCurActivityExceptOne(Class cls) {
        Activity activity = null;
        int size = activities.size();
        if (!activities.isEmpty()) {
            for (int i = size - 1; i >= 0; i--) {
                if (!activities.get(i).getClass().equals(cls)) {
                    return activities.get(i);
                }
            }
        }
        return activity;
    }

    /**
     * 判断栈中是否存在某个Activity
     *
     * @param cls
     * @return
     */
    public boolean isExistInTask(Class cls) {
        Iterator<Activity> iterator = activities.iterator();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除指定class以及它上面的所有Activity
     *
     * @param cls Activity对应的类名
     */
    public void popTopActivity(Class cls) {
        int size = activities.size();
        int pos = -1;
        if (!activities.isEmpty()) {
            for (int i = size - 1; i >= 0; i--) {
                if (activities.get(i).getClass().equals(cls)) {
                    pos = i;
                    break;
                }
            }
            if (pos > 0) {
                for (int i = size - 1; i >= pos; i--) {
                    activities.get(i).finish();
                    activities.remove(activities.get(i));
                }
            }
        }
    }

    /**
     * 移除栈中cls Activity
     */
    public Activity finishActivityOne(Class cls) {
        Activity activity = null;
        int size = activities.size();
        if (!activities.isEmpty()) {
            for (int i = size - 1; i >= 0; i--) {
                if (activities.get(i).getClass().equals(cls)) {
                    activities.get(i).finish();
                    activities.remove(activities.get(i));
                    break;
                }
            }
        }
        return activity;
    }
}