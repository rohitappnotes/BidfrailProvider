package com.bidfrail.provider.ui.register.steptwo.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.R;
import com.bidfrail.provider.databinding.ActivityRegisterStepTwoBinding;
import com.bidfrail.provider.model.SentOTP;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.register.stepthree.view.RegisterStepThreeActivity;
import com.bidfrail.provider.ui.register.steptwo.viewmodel.RegisterStepTwoViewModel;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.string.StringUtils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterStepTwoActivity extends BaseActivity<ActivityRegisterStepTwoBinding, RegisterStepTwoViewModel> {

    public static final String EXTRA_PROFILE_PICTURE = "PROFILE_PICTURE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";
    public static final String EXTRA_SENT_OTP = "SENT_OTP";

    private CountDownTimer countDownTimerFor1Minute;

    private String profilePictureCompressFilePath;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;

    private SentOTP sentOTPObject;

    private String messageOTP;
    private String inputOTP;

    @Override
    protected String getActivityClassName() {
        return RegisterStepTwoActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(RegisterStepTwoActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
        Bundle bundle = activityNavigator.getBundle();

        profilePictureCompressFilePath = bundle.getString(EXTRA_PROFILE_PICTURE);
        name = bundle.getString(EXTRA_NAME);
        phoneNumber = bundle.getString(EXTRA_PHONE_NUMBER);
        email = bundle.getString(EXTRA_EMAIL);
        password = bundle.getString(EXTRA_PASSWORD);

        sentOTPObject = bundle.getParcelable(EXTRA_SENT_OTP);
        messageOTP = sentOTPObject.getSentOTP();
    }

    @Override
    protected RegisterStepTwoViewModel getViewModel() {
        return viewModelProvider(RegisterStepTwoViewModel.class);
    }

    @Override
    protected ActivityRegisterStepTwoBinding getViewBinding(LayoutInflater inflater) {
        return ActivityRegisterStepTwoBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setupToolBar(mViewBinding.toolbar, ResourcesUtils.getColor(getApplicationContext(), R.color.colorToolbarBackground), R.drawable.ic_left_arrow, R.string.register_step_two_toolbar_title);
    }

    @Override
    protected void init() {
        counter1Minute();
    }

    @Override
    protected void initView() {
        mViewBinding.sentToPhoneNumberTextView.setText("+91" + phoneNumber);
    }

    @Override
    protected void addTextChangedListener() {
        mViewBinding.pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(mTag, "onTextChanged() called with: s = [" + s + "], start = [" + start + "], before = [" + before + "], count = [" + count + "]");
                inputOTP = s.toString();
                if (verifyButtonValidation(inputOTP)) {
                    hideKeyboard();
                    openRegisterStepThreeActivity();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> checkDetailsProviderErrorObserver = new Observer<String>() {
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
        mViewModel.checkDetailsProviderError().observe(this, checkDetailsProviderErrorObserver);

        final Observer<SentOTP> checkDetailsProviderSuccessObserver = new Observer<SentOTP>() {
            @Override
            public void onChanged(SentOTP sentOTP) {
                counter1Minute();
                hideProgressDialog();
                sentOTPObject = sentOTP;
                messageOTP = sentOTPObject.getSentOTP();
            }
        };
        mViewModel.checkDetailsProviderSuccess().observe(this, checkDetailsProviderSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        mViewBinding.verifyMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                if (verifyButtonValidation(inputOTP)) {
                    openRegisterStepThreeActivity();
                }
            }
        });

        mViewBinding.reSendCodeLinkOrTimerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewBinding.pinView.setText("");
                hideKeyboard();
                if (mViewBinding.reSendCodeLinkOrTimerTextView.getText() == getString(R.string.register_step_two_link_resend_code)) {
                    showProgressDialog();
                    mViewModel.checkDetailsProvider(phoneNumber, email);
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
        countDownTimerFor1Minute.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean verifyButtonValidation(String inputOTP) {
        if (StringUtils.isEmpty(inputOTP)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepTwo,
                    getString(R.string.register_step_two_error_otp_one),
                    null,
                    null
            );
            return false;
        } else if (!inputOTP.equals(messageOTP)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepTwo,
                    getString(R.string.register_step_two_error_otp_two),
                    null,
                    null
            );
            return false;
        }
        return true;
    }
    /***********************************************************************************************
     *******************************************Open Activity***************************************
     **********************************************************************************************/
    private void openRegisterStepThreeActivity() {
        Bundle bundle = new Bundle();
        bundle.putString(RegisterStepThreeActivity.EXTRA_PROFILE_PICTURE, profilePictureCompressFilePath);
        bundle.putString(RegisterStepThreeActivity.EXTRA_NAME, name);
        bundle.putString(RegisterStepThreeActivity.EXTRA_PHONE_NUMBER, phoneNumber);
        bundle.putString(RegisterStepThreeActivity.EXTRA_EMAIL, email);
        bundle.putString(RegisterStepThreeActivity.EXTRA_PASSWORD, password);
        activityNavigator
                .setBundle(bundle)
                .startActivity(RegisterStepThreeActivity.class);
    }
    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    private void counter1Minute() {
        long secondsIn1Minute = 60;

        mViewBinding.reSendCodeLinkOrTimerTextView.setText(60 + "s");
        mViewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_resend_code_in));

        countDownTimerFor1Minute = new CountDownTimer(/*convert second to milliseconds*/1000 * secondsIn1Minute, 1000) {
            public void onTick(long millisUntilFinished) {

                NumberFormat numberFormat = new DecimalFormat("00");
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);

                String hms = numberFormat.format(hours) + ":" + numberFormat.format(minutes) + ":" + numberFormat.format(seconds);
                System.out.println("===========HMS==========" + hms);
                mViewBinding.reSendCodeLinkOrTimerTextView.setText(seconds + "s");
                mViewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_resend_code_in));
            }

            public void onFinish() {
                System.out.println("=============FINISH==========");
                mViewBinding.reSendCodeLinkOrTimerTextView.setText(getString(R.string.register_step_two_link_resend_code));
                mViewBinding.reSendCodeMessageTextView.setText(getString(R.string.register_step_two_label_did_not_get_the_code));
            }
        };
        countDownTimerFor1Minute.start();
    }
}