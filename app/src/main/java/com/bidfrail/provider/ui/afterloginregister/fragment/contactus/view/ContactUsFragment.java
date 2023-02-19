package com.bidfrail.provider.ui.afterloginregister.fragment.contactus.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.databinding.FragmentContactUsBinding;
import com.bidfrail.provider.model.ContactUs;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.base.view.BaseFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.contactus.viewmodel.ContactUsViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ContactUsFragment extends BaseFragment<FragmentContactUsBinding, ContactUsViewModel> {

    @Override
    protected String getFragmentClassName() {
        return ContactUsFragment.class.getSimpleName();
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
    protected ContactUsViewModel getViewModel() {
        return viewModelProvider(ContactUsViewModel.class);
    }

    @Override
    protected FragmentContactUsBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentContactUsBinding.inflate(inflater, container, false);
    }

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void init() {
        mViewBinding.contactNumberTextView.setText(AppConstants.AppSupport.MOBILE);
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
        Observer<String> contactUsErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.contactUsError().observe(this, contactUsErrorObserver);

        final Observer<ContactUs> contactUsSuccessObserver = new Observer<ContactUs>() {
            @Override
            public void onChanged(ContactUs contactUs) {
                if (contactUs.getUserContactUs() != null) {
                    mViewBinding.contactNumberTextView.setText(contactUs.getUserContactUs());
                }
            }
        };
        mViewModel.contactUsSuccess().observe(this, contactUsSuccessObserver);
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
        mViewModel.contactUs();
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