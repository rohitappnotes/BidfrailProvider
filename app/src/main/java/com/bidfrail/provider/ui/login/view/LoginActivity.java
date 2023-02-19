package com.bidfrail.provider.ui.login.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.databinding.ActivityLoginBinding;
import com.bidfrail.provider.model.Provider;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.login.viewmodel.LoginViewModel;
import com.bidfrail.provider.ui.register.stepone.view.RegisterStepOneActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ValidationUtils;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private String email;
    private String password;
    private String fcmToken;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    public static final String SEND_BROADCAST = "broadcast";
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected String getActivityClassName() {
        return LoginActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(LoginActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected LoginViewModel getViewModel() {
        return viewModelProvider(LoginViewModel.class);
    }

    @Override
    protected ActivityLoginBinding getViewBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
    }

    @Override
    protected void init() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                fcmToken = sharedPreferencesHelper.getFcmToken();
            }
        };
        registerReceiver(broadcastReceiver,new IntentFilter(SEND_BROADCAST));
        getDeviceToken();
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void addTextChangedListener() {
        mViewBinding.emailTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() < 1) {
                    mViewBinding.emailTextInputLayout.setErrorEnabled(true);
                    mViewBinding.emailTextInputLayout.setError(getString(R.string.login_error_email_one));
                } else if (text.length() > 0) {
                    mViewBinding.emailTextInputLayout.setError(null);
                    mViewBinding.emailTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int emailValidCode = ValidationUtils.isValidEmail(mViewBinding.emailTextInputEditText.getText().toString());
                if (emailValidCode > 0) {
                    if (emailValidCode == 1) {
                        mViewBinding.emailTextInputLayout.setError(getString(R.string.login_error_email_one));
                    } else if (emailValidCode == 2) {
                        mViewBinding.emailTextInputLayout.setError(getString(R.string.login_error_email_two));
                    }
                }
            }
        });

        mViewBinding.passwordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    mViewBinding.passwordTextInputLayout.setErrorEnabled(true);
                    mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_one));
                }
                if (text.length() > 0) {
                    mViewBinding.passwordTextInputLayout.setError(null);
                    mViewBinding.passwordTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int passwordValidCode = ValidationUtils.isValidPassword(mViewBinding.passwordTextInputEditText.getText().toString());
                if (passwordValidCode > 0) {
                    if (passwordValidCode == 1) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_one));
                    } else if (passwordValidCode == 2) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_two));
                    } else if (passwordValidCode == 3) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_three));
                    } else if (passwordValidCode == 4) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_four));
                    } else if (passwordValidCode == 5) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_five));
                    } else if (passwordValidCode == 6) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_six));
                    } else if (passwordValidCode == 7) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_seven));
                    } else if (passwordValidCode == 8) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_eight));
                    }
                }
            }
        });
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> loginProviderErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.loginProviderError().observe(this, loginProviderErrorObserver);

        final Observer<Provider> loginProviderSuccessObserver = new Observer<Provider>() {
            @Override
            public void onChanged(Provider provider) {
                hideProgressDialog();
                openAfterLoginActivity(provider);
            }
        };
        mViewModel.loginProviderSuccess().observe(this, loginProviderSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        mViewBinding.loginRegisterMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                email = mViewBinding.emailTextInputEditText.getText().toString();
                password = mViewBinding.passwordTextInputEditText.getText().toString();

                if (loginButtonValidation(email, password))
                {
                    showProgressDialog();
                    mViewModel.loginProvider(email, password, fcmToken);
                }
            }
        });

        mViewBinding.registerLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityNavigator
                        .startActivity(RegisterStepOneActivity.class);
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
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean loginButtonValidation(String email, String password) {
        boolean isValid = true;

        int emailValidCode = ValidationUtils.isValidEmail(email);
        int passwordValidCode = ValidationUtils.isValidPassword(password);

        if (emailValidCode > 0) {
            if (emailValidCode == 1) {
                mViewBinding.emailTextInputLayout.setError(getString(R.string.login_error_email_one));
                isValid = false;
            } else if (emailValidCode == 2) {
                mViewBinding.emailTextInputLayout.setError(getString(R.string.login_error_email_two));
                isValid = false;
            }
        } else {
            mViewBinding.emailTextInputLayout.setError(null);
            mViewBinding.emailTextInputLayout.setErrorEnabled(false);
        }

        if (passwordValidCode > 0) {
            if (passwordValidCode == 1) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_one));
                isValid = false;
            } else if (passwordValidCode == 2) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_two));
                isValid = false;
            } else if (passwordValidCode == 3) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_three));
                isValid = false;
            } else if (passwordValidCode == 4) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_four));
                isValid = false;
            } else if (passwordValidCode == 5) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_five));
                isValid = false;
            } else if (passwordValidCode == 6) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_six));
                isValid = false;
            } else if (passwordValidCode == 7) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_seven));
                isValid = false;
            } else if (passwordValidCode == 8) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.login_error_password_message_eight));
                isValid = false;
            }
        } else {
            mViewBinding.passwordTextInputLayout.setError(null);
            mViewBinding.passwordTextInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }
    /***********************************************************************************************
     *******************************************Open Activity***************************************
     **********************************************************************************************/
    private void openAfterLoginActivity(Provider provider) {
        sharedPreferencesHelper.setRemember(true);

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
            activityNavigator
                    .clearBackStack()
                    .startActivity(AfterLoginActivity.class);
        } else {
            showShortToast("Your account is currently not active.");
        }
    }

    private void getDeviceToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        String token = task.getResult();
                        Log.e("token", token);
                        getApplicationContext().sendBroadcast(new Intent(SEND_BROADCAST));
                        storeToken(token);
                    }
                });
    }

    private void storeToken(String fcmToken) {
        sharedPreferencesHelper.setFcmToken(fcmToken);
    }
}