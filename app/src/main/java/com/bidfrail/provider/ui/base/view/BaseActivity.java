package com.bidfrail.provider.ui.base.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.networkconnectivity.NetworkReceiver;
import com.library.networkconnectivity.NetworkStateChangeListener;
import com.library.networkconnectivity.NetworkStateChangeReceiver;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<VB extends ViewBinding, VM extends BaseViewModel> extends AppCompatActivity implements BaseActivityView {

    /**
     * TAG
     */
    protected String mTag;

    /**
     * ViewBinding
     */
    protected VB mViewBinding;

    /**
     * View
     */
    protected View rootView;

    /**
     * ViewModel
     */
    protected VM mViewModel;

    /**
     * Activity Navigator
     */
    protected ActivityNavigator activityNavigator;

    /**
     * Fragment Navigator
     */
    protected FragmentNavigator fragmentNavigator;

    /**
     * You need override this method.
     * And you need to set Logcat tag
     *
     * Example :
     *
     * @Override
     * protected String getActivityClassName() {
     *   return TestActivity.class.getSimpleName();
     * }
     */
    protected abstract String getActivityClassName();
    protected abstract void doBeforeSetContentView();
    /**
     * You need override this method.
     * And you use it for navigate to another activity, send bundle, serializable, parcelable to another
     * activity and get result back from activity.
     */
    protected abstract ActivityNavigator getActivityNavigator();
    /**
     * You need override this method.
     * And you use it for add and replace fragment, send bundle, serializable, parcelable to another
     * fragment.
     */
    protected abstract FragmentNavigator getFragmentNavigator();
    /**
     * You need override this method.
     * And you use it for bundle, handleIntent(intent) etc.,
     */
    protected abstract void doInOnCreate(Bundle savedInstanceState);
    /**
     * You need override this method.
     * And you need to set ViewModel
     *
     * Example :
     *
     * @Override
     * protected TestViewModel getViewModel() {
     *  LocalRepository localRepository = new LocalRepository(getApplicationContext());
     *  RemoteRepository remoteRepository = new RemoteRepository(getApplicationContext());
     *  TestViewModelFactory factory = new TestViewModelFactory(localRepository, remoteRepository);
     *  return viewModelProvider(TestViewModel.class, factory);
     * }
     */
    protected abstract VM getViewModel();
    /**
     * You need override this method.
     * And you need to set ViewBinding
     *
     * Example :
     *
     * @Override
     * protected ActivityTestBinding getViewBinding(LayoutInflater layoutInflater) {
     *   return ActivityTestBinding.inflate(layoutInflater);
     * }
     */
    protected abstract VB getViewBinding(LayoutInflater inflater);
    /**
     * You need override this method.
     * And you use it for only toolbar related things
     *
     * Example :
     *
     * setSupportActionBar(mViewBinding.includeIdAppBar.toolbar)
     * toolbar title, subtitle etc.,
     */
    protected abstract void setupToolbar();
    /**
     * You need override this method.
     * And you use it for adapter initialize etc.,
     */
    protected abstract void init();
    /**
     * You need override this method.
     * And you use it for set view default data
     *
     * Example :
     *
     * mViewBinding.youNameTextView.setText("default data");
     */
    protected abstract void initView();
    protected abstract void addTextChangedListener();
    protected abstract void setOnFocusChangeListener();
    protected abstract void setupObservers();
    protected abstract void setupListeners();

    private void initTag() {
        mTag = getActivityClassName();
        if (mTag.length() > 23) {
            mTag = mTag.substring(0, 22); // first 22 chars
        }
    }

    private void initViewModel() {
        mViewModel = getViewModel();
    }

    private void initViewBinding(LayoutInflater inflater) {
        mViewBinding = getViewBinding(inflater);
        rootView = mViewBinding.getRoot();
    }

    /**
     * Use reflection to get the ViewBinding class
     */
    private void initViewBindingUsingReflection(LayoutInflater inflater) {
        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        assert parameterizedType != null;
        Class<?> cls = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        try {
            Method method = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            mViewBinding = (VB) method.invoke(null, inflater);
            rootView = mViewBinding.getRoot();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private NetworkReceiver networkReceiver;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        initTag();
        Log.i(mTag, "onCreate(Bundle savedInstanceState)");

        doBeforeSetContentView();

        super.onCreate(savedInstanceState);

        activityNavigator = getActivityNavigator();
        fragmentNavigator = getFragmentNavigator();
        doInOnCreate(savedInstanceState);

        initViewModel();
        initViewBinding(getLayoutInflater());
        //initViewBindingUsingReflection(getLayoutInflater());
        setContentView(rootView);

        networkReceiver = new NetworkReceiver(this);
        checkInternetConnection();

        setupToolbar();
        init();
        initView();
        addTextChangedListener();
        setOnFocusChangeListener();
        setupObservers();
        setupListeners();
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        Log.i(mTag, "onStart()");
    }

    @Override
    @CallSuper
    protected void onRestart() {
        super.onRestart();
        Log.i(mTag, "onRestart()");
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(mTag, "onResume()");
        networkReceiver.registerNetworkReceiver(this);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        Log.i(mTag, "onPause()");
        networkReceiver.unregisterNetworkReceiver(this);
    }

    @Override
    @CallSuper
    protected void onStop() {
        super.onStop();
        Log.i(mTag, "onStop()");
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        mViewBinding = null;
        mViewModel = null;
        super.onDestroy();
        Log.i(mTag, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i(mTag, "onBackPressed()");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(com.library.utilities.R.anim.slide_in_from_right, com.library.utilities.R.anim.slide_out_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(com.library.utilities.R.anim.slide_in_from_left, com.library.utilities.R.anim.slide_out_to_right);
    }
    /***********************************************************************************************
     *************************************Implemented Method****************************************
     **********************************************************************************************/
    @Override
    public void networkAvailable() {
        Log.i(mTag, "INTERNET CONNECT");
    }

    @Override
    public void networkUnavailable() {
        Log.i(mTag, "INTERNET DISCONNECT");
        showShortToast("No Internet");
    }

    @Override
    public void showShortToast(String message) {
        Log.i(mTag, message);
        toast(message, Toast.LENGTH_SHORT);
    }

    @Override
    public void showShortToast(int messageResId) {
        Log.i(mTag, getString(messageResId));
        toast(getString(messageResId), Toast.LENGTH_SHORT);
    }

    @Override
    public void showLongToast(String message) {
        Log.i(mTag, message);
        toast(message, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongToast(int messageResId) {
        Log.i(mTag, getString(messageResId));
        toast(getString(messageResId), Toast.LENGTH_LONG);
    }

    @Override
    public void showIndefiniteSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        snackBar(parent, message, Snackbar.LENGTH_INDEFINITE, actionText, onClickListener);
    }

    @Override
    public void showIndefiniteSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        snackBar(parent, getString(messageResId), Snackbar.LENGTH_INDEFINITE, actionText, onClickListener);
    }

    @Override
    public void showShortSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        snackBar(parent, message, Snackbar.LENGTH_SHORT, actionText, onClickListener);
    }

    @Override
    public void showShortSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        snackBar(parent, getString(messageResId), Snackbar.LENGTH_SHORT, actionText, onClickListener);
    }

    @Override
    public void showLongSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        snackBar(parent, message, Snackbar.LENGTH_LONG, actionText, onClickListener);
    }

    @Override
    public void showLongSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        snackBar(parent, getString(messageResId), Snackbar.LENGTH_LONG, actionText, onClickListener);
    }

    @Override
    public void setVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setInVisible(View... views) {
        for (View v : views) {
            v.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setGone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }

    protected void viewGone(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.GONE);
            }
        }
    }

    protected void viewVisible(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    protected void viewInvisible(View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
    /***********************************************************************************************
     ******************************************App Language*****************************************
     **********************************************************************************************/

    /***********************************************************************************************
     ****************************************ViewModel Helper***************************************
     **********************************************************************************************/
    protected <VMC extends ViewModel> VM viewModelProvider(@NonNull Class<VM> viewModelClass) {
        return new ViewModelProvider(this).get(viewModelClass);
    }

    protected <VMC extends ViewModel> VM viewModelProvider(@NonNull Class<VM> viewModelClass, @NonNull ViewModelProvider.Factory factory) {
        return new ViewModelProvider(this, factory).get(viewModelClass);
    }
    /***********************************************************************************************
     ****************************************Toolbar Helper*****************************************
     **********************************************************************************************/
    protected void setupToolBar(Toolbar toolbar, @ColorInt int backgroundColor, int navigationIcon, int title) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            toolbar.setBackgroundColor(backgroundColor);

            toolbar.setNavigationIcon(AppCompatResources.getDrawable(getApplicationContext(), navigationIcon));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            toolbar.setTitle(getResources().getString(title));
        }
    }
    /***********************************************************************************************
     ********************************************Helper*********************************************
     **********************************************************************************************/
    private void checkInternetConnection() {
        NetworkStateChangeReceiver.setNetworkStateChangeListener(new NetworkStateChangeListener() {
            @Override
            public void networkAvailable() {
                BaseActivity.this.networkAvailable();
            }

            @Override
            public void networkUnavailable() {
                BaseActivity.this.networkUnavailable();
            }
        });
    }

    private void toast(String message, int length) {
        Toast toast = Toast.makeText(getApplicationContext(), message, length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void snackBar(View parent, String snackBarText, int length, String actionText, View.OnClickListener onClickListener) {
        Snackbar snackbar = Snackbar.make(parent, snackBarText, length);
        snackbar.setBackgroundTint(Color.parseColor("#000000")); // background red
        snackbar.setTextColor(Color.parseColor("#FFFFFF")); // snackBarText white
        if (actionText != null && onClickListener != null) {
            snackbar.setActionTextColor(Color.parseColor("#008505")); // actionText green
            snackbar.setAction(actionText, onClickListener);
        }
        snackbar.show();
    }
}
