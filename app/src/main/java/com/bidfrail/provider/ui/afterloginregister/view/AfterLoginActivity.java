package com.bidfrail.provider.ui.afterloginregister.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.data.remote.ApiConfiguration;
import com.bidfrail.provider.data.remote.glide.GlideImageLoader;
import com.bidfrail.provider.data.remote.glide.GlideImageLoadingListener;
import com.bidfrail.provider.databinding.ActivityAfterLoginBinding;
import com.bidfrail.provider.ui.afterloginregister.fragment.leads.view.LeadsFragment;
import com.bidfrail.provider.ui.afterloginregister.viewmodel.AfterLoginViewModel;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.afterloginregister.fragment.aboutus.view.AboutUsFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.contactus.view.ContactUsFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.home.view.HomeFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.profile.view.ProfileFragment;
import com.bidfrail.provider.ui.afterloginregister.fragment.wallet.view.WalletFragment;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.customview.CircleImageView;
import com.library.utilities.string.StringUtils;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AfterLoginActivity extends BaseActivity<ActivityAfterLoginBinding, AfterLoginViewModel> {

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getActivityClassName() {
        return AfterLoginActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(AfterLoginActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return new FragmentNavigator(getSupportFragmentManager(), R.id.fragmentContainer);
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected AfterLoginViewModel getViewModel() {
        return viewModelProvider(AfterLoginViewModel.class);
    }

    @Override
    protected ActivityAfterLoginBinding getViewBinding(LayoutInflater inflater) {
        return ActivityAfterLoginBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setSupportActionBar(mViewBinding.appBar.toolbar);
    }

    public void goToHomeFragment() {
        fragmentNavigator.popAll();
        HomeFragment homeFragment = new HomeFragment();
        fragmentNavigator.push(homeFragment, false, true);
        mViewBinding.appBar.content.fabBottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }

    @Override
    protected void init() {
        goToHomeFragment();
        handleIntent(getIntent());
    }

    @Override
    protected void initView() {
        configureActionBarDrawerToggleForLeftNavDrawer(mViewBinding.drawerLayout, mViewBinding.appBar.toolbar);
        mViewBinding.leftNavigationView.setItemIconTintList(null);
        configureLeftNavDrawerHeaderView(mViewBinding.leftNavigationView);
        configureNavigationViewForLeftNavDrawer(mViewBinding.leftNavigationView, mViewBinding.drawerLayout);
        configureBottomNavigationView(mViewBinding.appBar.content.fabBottomNavigationView);
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
        fragmentNavigator.getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        Fragment current = fragmentNavigator.getVisibleFragment();
                        if (current instanceof HomeFragment) {
                            mViewBinding.leftNavigationView.setCheckedItem(R.id.left_nav_drawer_nav_home);
                        }
                    }
                });
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();

            if (bundle != null) {

                String navigateToScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO);
                String title= bundle.getString(AppConstants.Screen.Extras.EXTRA_TITLE);
                String message= bundle.getString(AppConstants.Screen.Extras.EXTRA_MESSAGE);
                String orderType= bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_TYPE);
                String orderId= bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_ID);
                String dateRequiredForScreen= bundle.getString(AppConstants.Screen.Extras.EXTRA_DATA_REQUIRED);

                if (navigateToScreen != null) {
                    switch (navigateToScreen) {
                        case "Home":
                            goToHomeFragment();
                            break;
                        case "LeadsFragment":
                            setToolbarTitle(R.string.leads_toolbar_title);
                            LeadsFragment leadsFragment = new LeadsFragment();
                            fragmentNavigator.push(leadsFragment, false, true);
                            mViewBinding.appBar.content.fabBottomNavigationView.setSelectedItemId(R.id.bottom_nav_leads);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mViewBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentNavigator.getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else {
                if (mViewBinding.appBar.content.fabBottomNavigationView.getSelectedItemId() == R.id.bottom_nav_home) {
                    if (mViewBinding.leftNavigationView.getCheckedItem().getItemId() == R.id.bottom_nav_home) {
                        alertDialogConfirmExit(AfterLoginActivity.this);
                    } else {
                        goToHomeFragment();
                    }
                } else {
                    goToHomeFragment();
                }
            }
        }
    }
    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    private void configureLeftNavDrawerHeaderView(NavigationView navigationView) {
        View navigationDrawerHeaderView = navigationView.getHeaderView(0);

        CircleImageView circleImageView = navigationDrawerHeaderView.findViewById(R.id.profilePictureCircleImageView);
        TextView name = navigationDrawerHeaderView.findViewById(R.id.nameTextView);
        ImageView navigationIconImageView = navigationDrawerHeaderView.findViewById(R.id.navigationIconImageView);

        navigationIconImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        if (!StringUtils.isEmpty(sharedPreferencesHelper.getPicture())) {
            GlideImageLoader.load(
                    getApplicationContext(),
                    ApiConfiguration.BASE_URL + sharedPreferencesHelper.getPicture(),
                    R.drawable.user_placeholder,
                    R.drawable.user_placeholder,
                    circleImageView,
                    new GlideImageLoadingListener() {
                        @Override
                        public void imageLoadSuccess() {
                        }

                        @Override
                        public void imageLoadError() {
                        }
                    });
        }

        if (!StringUtils.isEmpty(sharedPreferencesHelper.getName())) {
            name.setText(sharedPreferencesHelper.getName());
        } else {
            name.setText(getResources().getString(R.string.after_login_left_nav_drawer_header_update_profile));
        }

        MaterialButton logoutMaterialButton = mViewBinding.leftNavigationView.findViewById(R.id.logoutMaterialButton);
        logoutMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesHelper.setRemember(false);
                activityNavigator
                        .clearBackStack()
                        .startActivity(LoginActivity.class);
            }
        });
    }

    private void setToolbarTitle(@StringRes int toolbarTitle) {
        mViewBinding.appBar.toolbar.setTitle(ResourcesUtils.getString(getApplicationContext(), toolbarTitle));
    }

    private void configureActionBarDrawerToggleForLeftNavDrawer(DrawerLayout drawerLayout, Toolbar toolbar) {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.after_login_left_nav_drawer_open, R.string.after_login_left_nav_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void configureNavigationViewForLeftNavDrawer(NavigationView navigationView, DrawerLayout drawerLayout) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.left_nav_drawer_nav_home:
                        setToolbarTitle(R.string.home_toolbar_title);
                        goToHomeFragment();
                        break;
                    case R.id.left_nav_drawer_nav_completed_orders:
                        setToolbarTitle(R.string.completed_orders_toolbar_title);
                        break;
                    case R.id.left_nav_drawer_nav_learn_how_to_use:
                        setToolbarTitle(R.string.learn_how_to_use_toolbar_title);
                        break;
                    case R.id.left_nav_drawer_nav_about_us:
                        setToolbarTitle(R.string.about_us_toolbar_title);
                        AboutUsFragment aboutUsFragment = new AboutUsFragment();
                        fragmentNavigator.push(aboutUsFragment, false, true);
                        break;
                    case R.id.left_nav_drawer_nav_contact_us:
                        setToolbarTitle(R.string.contact_us_toolbar_title);
                        ContactUsFragment contactUsFragment = new ContactUsFragment();
                        fragmentNavigator.push(contactUsFragment, false, true);
                        break;
                    case R.id.left_nav_drawer_nav_terms_and_conditions:
                        setToolbarTitle(R.string.terms_and_condition_toolbar_title);
                        break;
                    case R.id.left_nav_drawer_nav_privacy_policy:
                        setToolbarTitle(R.string.privacy_policy_toolbar_title);
                        break;
                    case R.id.left_nav_drawer_nav_share:
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void configureBottomNavigationView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        setToolbarTitle(R.string.home_toolbar_title);
                        fragmentNavigator.popAll();
                        HomeFragment homeFragment = new HomeFragment();
                        fragmentNavigator.push(homeFragment, false, true);
                        return true;
                    case R.id.bottom_nav_leads:
                        setToolbarTitle(R.string.leads_toolbar_title);
                        LeadsFragment leadsFragment = new LeadsFragment();
                        fragmentNavigator.push(leadsFragment, false, true);
                        return true;
                    case R.id.bottom_nav_wallet:
                        setToolbarTitle(R.string.wallet_toolbar_title);
                        WalletFragment walletFragment = new WalletFragment();
                        fragmentNavigator.push(walletFragment, false, true);
                        return true;
                    case R.id.bottom_nav_profile:
                        setToolbarTitle(R.string.profile_toolbar_title);
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentNavigator.push(profileFragment, false, true);
                        return true;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    public void showBottomNavigation() {
        mViewBinding.appBar.content.fabBottomNavigationView.setVisibility(View.VISIBLE);
    }

    public void hideBottomNavigation() {
        mViewBinding.appBar.content.fabBottomNavigationView.setVisibility(View.GONE);
    }

    private void alertDialogConfirmExit(Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setIcon(R.drawable.ic_black_question_mark);
        alertDialogBuilder.setTitle("Confirm Exit");
        alertDialogBuilder.setMessage("Are you sure you want to Exit?");


        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                finishAffinity();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        positiveButton.setTextColor(Color.parseColor("#FFFFFF"));
        positiveButton.setBackgroundColor(Color.parseColor("#000000"));

        negativeButton.setTextColor(Color.parseColor("#FFFFFF"));
        negativeButton.setBackgroundColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(20, 0, 0, 0);
        positiveButton.setLayoutParams(params);
    }
}