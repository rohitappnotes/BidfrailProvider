package com.bidfrail.provider.ui.afterloginregister.fragment.wallet.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bidfrail.provider.databinding.FragmentWalletBinding;
import com.bidfrail.provider.ui.afterloginregister.fragment.wallet.viewmodel.WalletViewModel;
import com.bidfrail.provider.ui.base.view.BaseFragment;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WalletFragment extends BaseFragment<FragmentWalletBinding, WalletViewModel> {

    @Override
    protected String getFragmentClassName() {
        return WalletFragment.class.getSimpleName();
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
    protected WalletViewModel getViewModel() {
        return viewModelProvider(WalletViewModel.class);
    }

    @Override
    protected FragmentWalletBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentWalletBinding.inflate(inflater, container, false);
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
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
    public void onStart() {
        super.onStart();
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}