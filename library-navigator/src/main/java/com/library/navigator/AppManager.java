package com.library.navigator;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by on 2016/9/10.
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    public static String appStartName;

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
            activityStack = new Stack<>();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束指定个数Activity（按压入堆栈中的顺序）
     */
    public void finishActivity(int size) {
        for (int i = 0; i < size; i++) {
            if (activityStack.lastElement()!=null){
                Activity activity = activityStack.lastElement();
                finishActivity(activity);
            }

        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();

        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                return;
            }
        }
    }

    /**
     * 结束指定Activity之间Activity
     *
     * @param cls
     */
    public void finishAtActivity(Class<?> cls) {
        int current = 0;

        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                break;
            }
            current++;
        }
        int size = activityStack.size();
        for (int i = 0; i < current; i++) {
            finishActivity(getLastActivity());
        }
    }

    /**
     * 获取上一个Activity
     *
     * @return
     */
    public Activity getLastActivity() {
        int size = activityStack.size();
        if (size > 1) {
            return activityStack.get(size - 2);
        }
        return null;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 除了某两个activity 其他的都结束
     */
    public void finishOtherTwoActivity(Class cls1, Class cls2) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity temp = activityStack.get(i);
            if (temp != null) {
                if (!(cls1.isInstance(temp) || cls2.isInstance(temp))) {
                    activityStack.remove(temp);
                    size--;
                    i--;
                    temp.finish();
                }
            }
        }
    }

    /**
     * @return
     */
    public Stack<Activity> getAllActivity() {
        return activityStack;
    }

    /**
     * 获取指定的Activity
     *
     * @param cls
     * @return
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}