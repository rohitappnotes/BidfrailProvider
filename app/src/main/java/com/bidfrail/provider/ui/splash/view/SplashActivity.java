package com.bidfrail.provider.ui.splash.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.databinding.ActivitySplashBinding;
import com.bidfrail.provider.model.Provider;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.bidfrail.provider.ui.splash.viewmodel.SplashViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    private Handler handler;
    private Runnable runnable;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getActivityClassName() {
        return SplashActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(SplashActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected SplashViewModel getViewModel() {
        return viewModelProvider(SplashViewModel.class);
    }

    @Override
    protected ActivitySplashBinding getViewBinding(LayoutInflater inflater) {
        return ActivitySplashBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void init() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (sharedPreferencesHelper.getProviderId() == 0) {
                    activityNavigator
                            .clearBackStack()
                            .startActivity(LoginActivity.class);
                } else {
                    mViewModel.providerDetails(sharedPreferencesHelper.getProviderId());
                }
            }
        };
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
        Observer<String> providerErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    activityNavigator
                            .clearBackStack()
                            .startActivity(LoginActivity.class);
                }
            }
        };
        mViewModel.providerError().observe(this, providerErrorObserver);

        final Observer<Provider> providerSuccessObserver = new Observer<Provider>() {
            @Override
            public void onChanged(Provider provider) {
                sharedPreferencesHelper.setProviderId(provider.getProviderId());
                sharedPreferencesHelper.setPicture(provider.getProfilePicture());
                sharedPreferencesHelper.setName(provider.getName());
                sharedPreferencesHelper.setPhoneNumber(provider.getPhoneNumber());
                sharedPreferencesHelper.setEmail(provider.getEmail());
                sharedPreferencesHelper.setPassword(provider.getPassword());
                sharedPreferencesHelper.setLatitude(provider.getLatitude());
                sharedPreferencesHelper.setLongitude(provider.getLongitude());
                sharedPreferencesHelper.setLatLonAddress(provider.getLatLonAddress());
                sharedPreferencesHelper.setCategoryId(provider.getCategoryId());
                sharedPreferencesHelper.setCategoryName(provider.getCategoryName());
                sharedPreferencesHelper.setSubCategoryId(provider.getSubCategoryId());
                sharedPreferencesHelper.setSubCategoryName(provider.getSubCategoryName());
                sharedPreferencesHelper.setAboutYou(provider.getAboutYou());
                sharedPreferencesHelper.setGallery(provider.getGallery());
                sharedPreferencesHelper.setAadhaarCardFront(provider.getAadhaarCardFront());
                sharedPreferencesHelper.setAadhaarCardBack(provider.getAadhaarCardBack());
                sharedPreferencesHelper.setPanCard(provider.getPanCard());
                sharedPreferencesHelper.setIsActive(provider.getIsActive());
                sharedPreferencesHelper.setIsOnline(provider.getIsOnline());
                sharedPreferencesHelper.setCredit(provider.getCredit());
                sharedPreferencesHelper.setFcmToken(provider.getFcmToken());
                sharedPreferencesHelper.setCreatedAt(provider.getCreatedAt());
                sharedPreferencesHelper.setUpdatedAt(provider.getUpdatedAt());

                if (sharedPreferencesHelper.getIsActive() == 1) {
                    if (sharedPreferencesHelper.getRemember()) {
                        activityNavigator
                                .clearBackStack()
                                .startActivity(AfterLoginActivity.class);
                    } else {
                        activityNavigator
                                .clearBackStack()
                                .startActivity(LoginActivity.class);
                    }
                } else {
                    activityNavigator
                            .clearBackStack()
                            .startActivity(LoginActivity.class);
                }
            }
        };
        mViewModel.providerSuccess().observe(this, providerSuccessObserver);
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
        if (handler != null) {
            handler.postDelayed(runnable, AppConstants.Delay.SPLASH);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}