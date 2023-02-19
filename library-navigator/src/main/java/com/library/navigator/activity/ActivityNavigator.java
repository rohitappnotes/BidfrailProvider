package com.library.navigator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.activity.result.ActivityResultLauncher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActivityNavigator {

    private final Activity activity;

    private Bundle bundle;

    private String serializableKey;
    private Serializable serializable;

    private String parcelableKey;
    private Parcelable parcelable;


    private List<Integer> flagArrayList = new ArrayList<>();

    private boolean clearBackStack = false;

    public ActivityNavigator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Pass Bundle
     *
     * @param bundle
     */
    public ActivityNavigator setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }

    public Bundle getBundle() {
        return activity.getIntent().getExtras();
    }

    /**
     * Pass Serializable Model class
     *
     * @param key
     * @param serializable
     */
    public ActivityNavigator setSerializable(String key, Serializable serializable) {
        this.serializableKey = key;
        this.serializable = serializable;
        return this;
    }

    public Serializable getSerializable(String key) {
        return activity.getIntent().getSerializableExtra(key);
    }

    /**
     * Pass Parcelable Model class
     *
     * @param key
     * @param parcelable
     */
    public ActivityNavigator setParcelable(String key, Parcelable parcelable) {
        this.parcelableKey = key;
        this.parcelable = parcelable;
        return this;
    }

    public Parcelable getParcelable(String key) {
        return activity.getIntent().getParcelableExtra(key);
    }

    private void addFlag(int flag) {
        flagArrayList.add(flag);
    }

    /**
     * If set, the new activity is not kept in the history stack. As soon as the user navigates away
     * from it, the activity is finished. This may also be set with the noHistory attribute.
     */
    public ActivityNavigator noHistory(){
        addFlag(Intent.FLAG_ACTIVITY_NO_HISTORY);
        return this;
    }

    public ActivityNavigator clearBackStack() {
        this.clearBackStack = true;
        return this;
    }

    private void navigate(Class<? extends Activity> navigateToThisActivity) {
        navigate(navigateToThisActivity, null);
    }

    private void navigate(Class<? extends Activity> navigateToThisActivity, ActivityResultLauncher<Intent> activityResultLauncher) {
        Intent intent = new Intent(activity, navigateToThisActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (serializable != null) {
            intent.putExtra(serializableKey, serializable);
        }

        if (parcelable != null) {
            intent.putExtra(parcelableKey, parcelable);
        }

        /* clear back stack by tag */
        if (clearBackStack)
        {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        if (activityResultLauncher != null)
        {
            activityResultLauncher.launch(intent);
        }
        else
        {
            activity.startActivity(intent);
        }
    }

    public void startActivity(Class<? extends Activity> navigateToThisActivity) {
        navigate(navigateToThisActivity);
    }

    public void startActivityForResult(ActivityResultLauncher<Intent> activityResultLauncher, Class<? extends Activity> navigateToThisActivity) {
        navigate(navigateToThisActivity, activityResultLauncher);
    }

    public void goBack() {
        goBack(-1);
    }

    public void goBack(int requestCode) {
        if (-1 == requestCode)
        {
            activity.onBackPressed();
        }
        else
        {
            Intent intent = new Intent();

            if (bundle != null) {
                intent.putExtras(bundle);
            }

            if (serializable != null) {
                intent.putExtra(serializableKey, serializable);
            }

            if (parcelable != null) {
                intent.putExtra(parcelableKey, parcelable);
            }

            activity.setResult(requestCode, intent);
            activity.onBackPressed();
        }
    }

    /**
     * Method to finish activity
     */
    public void finishActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.finishAfterTransition();
        } else {
            activity.finish();
        }
    }
}
