package com.bidfrail.provider.ui.afterloginregister.fragment.aboutus.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.R;
import com.bidfrail.provider.databinding.FragmentAboutUsBinding;
import com.bidfrail.provider.model.AboutUs;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.base.view.BaseFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.aboutus.viewmodel.AboutUsViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.HtmlUtils;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AboutUsFragment extends BaseFragment<FragmentAboutUsBinding, AboutUsViewModel> {

    @Override
    protected String getFragmentClassName() {
        return AboutUsFragment.class.getSimpleName();
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(getActivity());
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected AboutUsViewModel getViewModel() {
        return viewModelProvider(AboutUsViewModel.class);
    }

    @Override
    protected FragmentAboutUsBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentAboutUsBinding.inflate(inflater, container, false);
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
        Observer<String> aboutUsErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressBar();
                mViewBinding.errorTextView.setVisibility(View.VISIBLE);
                mViewBinding.aboutUsTextView.setVisibility(View.GONE);

                if (error == null) {
                    mViewBinding.errorTextView.setText(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    mViewBinding.errorTextView.setText(error);
                }
            }
        };
        mViewModel.aboutUsError().observe(this, aboutUsErrorObserver);

        final Observer<AboutUs> aboutUsSuccessObserver = new Observer<AboutUs>() {
            @Override
            public void onChanged(AboutUs aboutUs) {
                hideProgressBar();
                mViewBinding.errorTextView.setVisibility(View.GONE);
                mViewBinding.aboutUsTextView.setVisibility(View.VISIBLE);

                if (aboutUs.getUserAboutUs() != null) {
                    mViewBinding.aboutUsTextView.setText(HtmlUtils.stripHtml(aboutUs.getUserAboutUs()));
                }
            }
        };
        mViewModel.aboutUsSuccess().observe(this, aboutUsSuccessObserver);
    }

    @Override
    protected void setupListeners() {

    }

    @Override
    public void showProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.GONE) {
            mViewBinding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressDialog() {
    }

    @Override
    public void hideProgressDialog() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
        if (getActivity() instanceof AfterLoginActivity) {
            AfterLoginActivity afterLoginActivity = (AfterLoginActivity) getActivity();
            afterLoginActivity.showBottomNavigation();
        }
        showProgressBar();
        mViewBinding.aboutUsTextView.setVisibility(View.GONE);
        mViewModel.aboutUs();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() instanceof AfterLoginActivity) {
            AfterLoginActivity afterLoginActivity = (AfterLoginActivity) getActivity();
            afterLoginActivity.showBottomNavigation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}