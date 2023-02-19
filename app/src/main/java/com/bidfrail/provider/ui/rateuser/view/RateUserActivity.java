package com.bidfrail.provider.ui.rateuser.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.databinding.ActivityRateUserBinding;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.rateuser.viewmodel.RateUserViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RateUserActivity extends BaseActivity<ActivityRateUserBinding, RateUserViewModel> {

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getActivityClassName() {
        return RateUserActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(RateUserActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected RateUserViewModel getViewModel() {
        return viewModelProvider(RateUserViewModel.class);
    }

    @Override
    protected ActivityRateUserBinding getViewBinding(LayoutInflater inflater) {
        return ActivityRateUserBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void addTextChangedListener() {
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
    }

    @Override
    protected void setupListeners() {
    }

    @Override
    public void showProgressBar() {
    }

    @Override
    public void hideProgressBar() {
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void hideProgressDialog() {
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}