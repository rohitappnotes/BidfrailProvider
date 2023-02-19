package com.bidfrail.provider.ui.base.view;

import androidx.annotation.CallSuper;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseFragment<VB extends ViewBinding, VM extends BaseViewModel> extends Fragment implements BaseFragmentView {

    private BaseActivity mActivity;

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
     * protected String getFragmentClassName() {
     *   return HomeFragment.class.getSimpleName();
     * }
     */
    protected abstract String getFragmentClassName();
    protected abstract ActivityNavigator getActivityNavigator();
    protected abstract FragmentNavigator getFragmentNavigator();
    protected abstract void doInOnCreate(Bundle savedInstanceState);
    /**
     * You need override this method.
     * And you need to set ViewModel
     *
     * Example :
     *
     * @Override
     * protected HomeViewModel getViewModel() {
     *  LocalRepository localRepository = new LocalRepository(getContext());
     *  RemoteRepository remoteRepository = new RemoteRepository(getContext());
     *  HomeViewModelFactory factory = new HomeViewModelFactory(localRepository, remoteRepository);
     *  return viewModelProvider(HomeViewModel.class, factory);
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
     * protected FragmentHomeBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
     *   return FragmentHomeBinding.inflate(inflater, container, false);
     * }
     */
    protected abstract VB getViewBinding(LayoutInflater inflater, ViewGroup container);
    protected abstract void setupToolbar();
    protected abstract void init();
    protected abstract void initView();
    protected abstract void addTextChangedListener();
    protected abstract void setOnFocusChangeListener();
    protected abstract void setupObservers();
    protected abstract void setupListeners();

    private void initTag() {
        mTag = getFragmentClassName();
        if (mTag.length() > 23) {
            mTag = mTag.substring(0, 22);
        }
    }

    private void initViewModel() {
        mViewModel = getViewModel();
    }

    private void initViewBinding(LayoutInflater inflater, ViewGroup container) {
        mViewBinding = getViewBinding(inflater, container);
        rootView = mViewBinding.getRoot();
    }

    /**
     * Use reflection to get the ViewBinding class
     */
    private void initViewBindingUsingReflection(LayoutInflater inflater, ViewGroup container) {
        Type superclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
        assert parameterizedType != null;
        Class<?> cls = (Class<?>) parameterizedType.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mViewBinding = (VB) inflate.invoke(null, inflater, container, false);
            rootView = mViewBinding.getRoot();
        }  catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /*
     * OnAttach(Context context) is not call, If API level of the android version is lower than 23.
     * Because OnAttach(Context context) is added in API level 23.
     */
    @TargetApi(23)
    @Override
    @CallSuper
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        initTag();
        Log.i(mTag, "onAttach(@NonNull Context context)");

        if (context instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) context;
            this.mActivity = baseActivity;
        }
    }

    /*
     * OnAttach(Activity activity) is not call, If API level of the android version is greater than 22.
     * Because OnAttach(Activity activity) is deprecated in API level 23, but must remain to allow compatibility with api<23
     */
    @Override
    @CallSuper
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        initTag();
        Log.i(mTag, "onAttach(@NonNull Activity activity)");

        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            this.mActivity = baseActivity;
        }
    }

    @Override
    @CallSuper
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(mTag, "onCreate(Bundle savedInstanceState)");
        activityNavigator = getActivityNavigator();
        fragmentNavigator = getFragmentNavigator();
        doInOnCreate(savedInstanceState);
    }

    @Override
    @CallSuper
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(mTag, "onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)");
        initViewModel();
        initViewBinding(inflater, container);
        //initViewBindingUsingReflection(inflater, container);
        return rootView;
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(mTag, "onViewCreated(@NonNull View view, Bundle savedInstanceState)");

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(mTag, "onActivityCreated(@Nullable Bundle savedInstanceState)");
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        Log.i(mTag, "onStart()");
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        Log.i(mTag, "onResume()");
    }

    @Override
    @CallSuper
    public void onPause() {
        super.onPause();
        Log.i(mTag, "onPause()");
    }

    @Override
    @CallSuper
    public void onStop() {
        super.onStop();
        Log.i(mTag, "onStop()");
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        rootView = null;
        mViewBinding = null;
        mViewModel = null;
        super.onDestroyView();
        Log.i(mTag, "onDestroyView()");
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
        Log.i(mTag, "onDestroy()");
    }

    @Override
    @CallSuper
    public void onDetach() {
        super.onDetach();
        Log.i(mTag, "onDetach()");
        mActivity = null;
    }
    /***********************************************************************************************
     *************************************Implemented Method****************************************
     **********************************************************************************************/
    @Override
    public void showShortToast(String message) {
        Log.i(mTag, message);
        getBaseActivity().showShortToast(message);
    }

    @Override
    public void showShortToast(int messageResId) {
        Log.i(mTag, getString(messageResId));
        getBaseActivity().showShortToast(getString(messageResId));
    }

    @Override
    public void showLongToast(String message) {
        Log.i(mTag, message);
        getBaseActivity().showShortToast(message);
    }

    @Override
    public void showLongToast(int messageResId) {
        Log.i(mTag, getString(messageResId));
        getBaseActivity().showLongToast(getString(messageResId));
    }

    @Override
    public void showIndefiniteSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        getBaseActivity().showIndefiniteSnackBar(parent, message, actionText, onClickListener);
    }

    @Override
    public void showIndefiniteSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        getBaseActivity().showIndefiniteSnackBar(parent, getString(messageResId), actionText, onClickListener);
    }

    @Override
    public void showShortSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        getBaseActivity().showShortSnackBar(parent, message, actionText, onClickListener);
    }

    @Override
    public void showShortSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        getBaseActivity().showShortSnackBar(parent, getString(messageResId), actionText, onClickListener);
    }

    @Override
    public void showLongSnackBar(View parent, String message, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, message);
        getBaseActivity().showLongSnackBar(parent, message, actionText, onClickListener);
    }

    @Override
    public void showLongSnackBar(View parent, int messageResId, String actionText, View.OnClickListener onClickListener) {
        Log.i(mTag, getString(messageResId));
        getBaseActivity().showLongSnackBar(parent, getString(messageResId), actionText, onClickListener);
    }

    @Override
    public void setVisible(View... views) {
        getBaseActivity().setVisible(views);
    }

    @Override
    public void setInVisible(View... views) {
        getBaseActivity().setInVisible(views);
    }

    @Override
    public void setGone(View... views) {
        getBaseActivity().setGone(views);
    }

    @Override
    public void hideKeyboard() {
        getBaseActivity().hideKeyboard();
    }
    /***********************************************************************************************
     ****************************************ViewModel Helper***************************************
     **********************************************************************************************/
    protected <VMC extends ViewModel> VMC viewModelProvider(@NonNull Class<VMC> viewModelClass) {
        return new ViewModelProvider(this).get(viewModelClass);
    }

    protected <VMC extends ViewModel> VMC viewModelProvider(@NonNull Class<VMC> viewModelClass, @NonNull ViewModelProvider.Factory factory) {
        return new ViewModelProvider(this, factory).get(viewModelClass);
    }
    /***********************************************************************************************
     ****************************************Toolbar Helper*****************************************
     **********************************************************************************************/
    public BaseActivity getBaseActivity() {
        return mActivity;
    }
}
