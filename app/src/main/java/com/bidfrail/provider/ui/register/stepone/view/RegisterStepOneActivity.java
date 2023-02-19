package com.bidfrail.provider.ui.register.stepone.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.databinding.ActivityRegisterStepOneBinding;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureListener;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureModalBottomSheet;
import com.bidfrail.provider.model.SentOTP;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.bidfrail.provider.ui.register.stepone.viewmodel.RegisterStepOneViewModel;
import com.bidfrail.provider.ui.register.steptwo.view.RegisterStepTwoActivity;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.ValidationUtils;
import com.library.utilities.permissionutils.ManagePermission;
import com.library.utilities.permissionutils.PermissionName;
import com.library.utilities.string.StringUtils;
import dagger.hilt.android.AndroidEntryPoint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import com.library.imagecompressor.Compressor;
import com.library.imagecompressor.CompressorFolderException;
import com.library.utilities.ImageAndVideoUtils;
import com.library.utilities.ImplicitIntentUtils;
import com.library.utilities.file.FileProviderUtils;
import com.library.utilities.file.FileUtils;
import com.library.utilities.file.MediaFileUtils;
import com.library.utilities.file.MemoryUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.file.StorageUtils;
import com.library.utilities.listener.AlertDialogListener;
import com.library.utilities.permissionutils.PermissionDialog;
import java.io.File;

@AndroidEntryPoint
public class RegisterStepOneActivity extends BaseActivity<ActivityRegisterStepOneBinding, RegisterStepOneViewModel> {

    private ManagePermission managePermission;
    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    public static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.READ_EXTERNAL_STORAGE,
                    PermissionName.WRITE_EXTERNAL_STORAGE,
                    PermissionName.CAMERA,
            };

    private Uri profilePictureUri;
    private String profilePictureOriginalFilePath;
    private String profilePictureCompressFilePath;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String confirmPassword;

    @Override
    protected String getActivityClassName() {
        return RegisterStepOneActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(RegisterStepOneActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected RegisterStepOneViewModel getViewModel() {
        return viewModelProvider(RegisterStepOneViewModel.class);
    }

    @Override
    protected ActivityRegisterStepOneBinding getViewBinding(LayoutInflater inflater) {
        return ActivityRegisterStepOneBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setupToolBar(mViewBinding.toolbar, ResourcesUtils.getColor(getApplicationContext(), R.color.colorToolbarBackground), R.drawable.ic_left_arrow, R.string.register_step_one_toolbar_title);
    }

    @Override
    protected void init() {
        managePermission = new ManagePermission(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void addTextChangedListener() {
        mViewBinding.passwordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    mViewBinding.passwordTextInputLayout.setErrorEnabled(true);
                    mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_one));
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
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_one));
                    } else if (passwordValidCode == 2) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_two));
                    } else if (passwordValidCode == 3) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_three));
                    } else if (passwordValidCode == 4) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_four));
                    } else if (passwordValidCode == 5) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_five));
                    } else if (passwordValidCode == 6) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_six));
                    } else if (passwordValidCode == 7) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_seven));
                    } else if (passwordValidCode == 8) {
                        mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_eight));
                    }
                }
            }
        });

        mViewBinding.confirmPasswordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

                if (text.length() < 1) {
                    mViewBinding.confirmPasswordTextInputLayout.setErrorEnabled(true);
                    mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_one));
                }
                if (text.length() > 0) {
                    mViewBinding.confirmPasswordTextInputLayout.setError(null);
                    mViewBinding.confirmPasswordTextInputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int confirmPasswordValidCode = ValidationUtils.isValidConfirmPassword(mViewBinding.passwordTextInputEditText.getText().toString().trim(), mViewBinding.confirmPasswordTextInputEditText.getText().toString().trim());
                if (confirmPasswordValidCode > 0) {
                    if (confirmPasswordValidCode == 1) {
                        mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_one));
                    }
                    if (confirmPasswordValidCode == 2) {
                        mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_one));
                    }
                    if (confirmPasswordValidCode == 3) {
                        mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_two));
                    }
                    if (confirmPasswordValidCode == 4) {
                        mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_three));
                    }
                }
            }
        });
    }

    @Override
    protected void setOnFocusChangeListener() {
        mViewBinding.nameTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.e(mTag, "If view having focus");
                } else {
                    Log.e(mTag, "If view not having focus. You can validate here");
                    String input = mViewBinding.nameTextInputEditText.getText().toString();
                    if (input.length() < 1) {
                        mViewBinding.nameTextInputLayout.setErrorEnabled(true);
                        mViewBinding.nameTextInputLayout.setError(getString(R.string.register_step_one_error_name_one));
                    } else {
                        mViewBinding.nameTextInputLayout.setError(null);
                        mViewBinding.nameTextInputLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        mViewBinding.phoneNumberTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.e(mTag, "If view having focus");
                } else {
                    Log.e(mTag, "If view not having focus. You can validate here");
                    String input = mViewBinding.phoneNumberTextInputEditText.getText().toString();
                    int phoneValidCode = ValidationUtils.isPhoneNumberValid(input);
                    if (phoneValidCode > 0) {
                        if (phoneValidCode == 1) {
                            mViewBinding.phoneNumberTextInputLayout.setError(getString(R.string.register_step_one_error_phone_number_one));
                        } else if (phoneValidCode == 2) {
                            mViewBinding.phoneNumberTextInputLayout.setError(getString(R.string.register_step_one_error_phone_number_two));
                        }
                    } else {
                        mViewBinding.phoneNumberTextInputLayout.setError(null);
                        mViewBinding.phoneNumberTextInputLayout.setErrorEnabled(false);
                    }
                }
            }
        });

        mViewBinding.emailTextInputEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.e(mTag, "If view having focus");
                } else {
                    Log.e(mTag, "If view not having focus. You can validate here");
                    String input = mViewBinding.emailTextInputEditText.getText().toString();
                    int emailValidCode = ValidationUtils.isValidEmail(input);
                    if (emailValidCode > 0) {
                        if (emailValidCode == 1) {
                            mViewBinding.emailTextInputLayout.setError(getString(R.string.register_step_one_error_email_one));
                        } else if (emailValidCode == 2) {
                            mViewBinding.emailTextInputLayout.setError(getString(R.string.register_step_one_error_email_two));
                        }
                    } else {
                        mViewBinding.emailTextInputLayout.setError(null);
                        mViewBinding.emailTextInputLayout.setErrorEnabled(false);
                    }
                }
            }
        });
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
                hideProgressDialog();
                openRegisterStepTwoActivity(sentOTP);
            }
        };
        mViewModel.checkDetailsProviderSuccess().observe(this, checkDetailsProviderSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        mViewBinding.imageFromFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectOrCaptureModalBottomSheet modalBottomSheet = new ImageSelectOrCaptureModalBottomSheet(new ImageSelectOrCaptureListener() {
                    @Override
                    public void fromGallery() {
                        Intent imageFromGalleryIntent = ImplicitIntentUtils.actionPickIntent(1);
                        imageFromGalleryActivityResultLauncher.launch(imageFromGalleryIntent);
                    }

                    @Override
                    public void fromCamera() {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            Log.e(mTag, "permission already granted");
                            String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                            String fileName = MediaFileUtils.getRandomFileName(1);
                            String extension = "jpg";

                            Intent captureImageIntent = createProfilePictureImageFile(getApplicationContext(), RegisterStepOneActivity.this, customDirectoryName, fileName, extension);
                            imageFromCameraActivityResultLauncher.launch(captureImageIntent);
                        } else {
                            Log.e(mTag, "permission is not granted, request for permission");
                            ActivityCompat.requestPermissions(
                                    RegisterStepOneActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                modalBottomSheet.show(fragmentManager, modalBottomSheet.getTag());
            }
        });

        mViewBinding.registerMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                name = mViewBinding.nameTextInputEditText.getText().toString().trim();
                phoneNumber = mViewBinding.phoneNumberTextInputEditText.getText().toString().trim();
                email = mViewBinding.emailTextInputEditText.getText().toString().trim();
                password = mViewBinding.passwordTextInputEditText.getText().toString().trim();
                confirmPassword = mViewBinding.confirmPasswordTextInputEditText.getText().toString().trim();

                if (registerButtonValidation(profilePictureCompressFilePath, name, phoneNumber, email, password, confirmPassword)) {
                    showProgressDialog();
                    mViewModel.checkDetailsProvider(phoneNumber, email);
                }
            }
        });

        mViewBinding.loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityNavigator
                        .clearBackStack()
                        .startActivity(LoginActivity.class);
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
     ********************************************Permission*****************************************
     **********************************************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];

                            if (permission.equalsIgnoreCase(PermissionName.READ_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "READ_EXTERNAL_STORAGE permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "READ_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide select image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.WRITE_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "WRITE_EXTERNAL_STORAGE permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "WRITE_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide select image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.CAMERA)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "CAMERA permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "CAMERA denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_camera,
                                            "Camera Permission",
                                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide capture image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }
                        }
                    }
                    Log.e(mTag, "all permission granted, do the task");
                } else {
                    Log.e(mTag, "Unknown Error");
                }
                break;
            default:
                throw new RuntimeException("unhandled permissions request code: " + requestCode);
        }
    }

    ActivityResultLauncher<Intent> permissionFromSettingActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                        Log.e(mTag, "permission already granted");
                    } else {
                        Log.e(mTag, "permission is not granted, request for permission");
                        ActivityCompat.requestPermissions(
                                RegisterStepOneActivity.this,
                                MULTIPLE_PERMISSIONS,
                                MULTIPLE_PERMISSION_REQUEST_CODE);
                    }
                }
            });

    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean registerButtonValidation(String profilePictureFilePath, String name, String phoneNumber, String email, String password, String confirmPassword) {
        boolean isValid = true;

        int phoneValidCode = ValidationUtils.isPhoneNumberValid(phoneNumber);
        int emailValidCode = ValidationUtils.isValidEmail(email);

        if (StringUtils.isEmpty(profilePictureFilePath)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepOne,
                    getString(R.string.register_step_one_error_profile_picture_one),
                    null,
                    null
            );
            isValid = false;
        }

        if (StringUtils.isEmpty(name)) {
            mViewBinding.nameTextInputLayout.setError(getString(R.string.register_step_one_error_name_one));
            isValid = false;
        } else {
            mViewBinding.nameTextInputLayout.setError(null);
            mViewBinding.nameTextInputLayout.setErrorEnabled(false);
        }

        if (phoneValidCode > 0) {
            if (phoneValidCode == 1) {
                mViewBinding.phoneNumberTextInputLayout.setError(getString(R.string.register_step_one_error_phone_number_one));
                isValid = false;
            } else if (phoneValidCode == 2) {
                mViewBinding.phoneNumberTextInputLayout.setError(getString(R.string.register_step_one_error_phone_number_two));
                isValid = false;
            }
        } else {
            mViewBinding.phoneNumberTextInputLayout.setError(null);
            mViewBinding.phoneNumberTextInputLayout.setErrorEnabled(false);
        }

        if (emailValidCode > 0) {
            if (emailValidCode == 1) {
                mViewBinding.emailTextInputLayout.setError(getString(R.string.register_step_one_error_email_one));
                isValid = false;
            } else if (emailValidCode == 2) {
                mViewBinding.emailTextInputLayout.setError(getString(R.string.register_step_one_error_email_two));
                isValid = false;
            }
        } else {
            mViewBinding.emailTextInputLayout.setError(null);
            mViewBinding.emailTextInputLayout.setErrorEnabled(false);
        }

        if (!passwordValidation(password, confirmPassword)) {
            isValid = false;
        }

        return isValid;
    }

    private boolean passwordValidation(String password, String confirmPassword) {
        boolean isValid = true;

        int passwordValidCode = ValidationUtils.isValidPassword(password);
        int confirmPasswordValidCode = ValidationUtils.isValidConfirmPassword(password, confirmPassword);

        if (passwordValidCode > 0) {
            if (passwordValidCode == 1) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_one));
                isValid = false;
            } else if (passwordValidCode == 2) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_two));
                isValid = false;
            } else if (passwordValidCode == 3) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_three));
                isValid = false;
            } else if (passwordValidCode == 4) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_four));
                isValid = false;
            } else if (passwordValidCode == 5) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_five));
                isValid = false;
            } else if (passwordValidCode == 6) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_six));
                isValid = false;
            } else if (passwordValidCode == 7) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_seven));
                isValid = false;
            } else if (passwordValidCode == 8) {
                mViewBinding.passwordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_eight));
                isValid = false;
            }
        } else {
            mViewBinding.passwordTextInputLayout.setError(null);
            mViewBinding.passwordTextInputLayout.setErrorEnabled(false);
        }

        if (confirmPasswordValidCode > 0) {
            if (confirmPasswordValidCode == 1) {
                mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_password_message_one));
                isValid = false;
            } else if (confirmPasswordValidCode == 2) {
                mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_one));
                isValid = false;
            } else if (confirmPasswordValidCode == 3) {
                mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_two));
                isValid = false;
            } else if (confirmPasswordValidCode == 4) {
                mViewBinding.confirmPasswordTextInputLayout.setError(getString(R.string.register_step_one_error_confirm_password_message_three));
                isValid = false;
            }
        } else {
            mViewBinding.confirmPasswordTextInputLayout.setError(null);
            mViewBinding.confirmPasswordTextInputLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    /***********************************************************************************************
     *******************************************Open Activity***************************************
     **********************************************************************************************/
    private void openRegisterStepTwoActivity(SentOTP sentOTP) {
        Bundle bundle = new Bundle();
        bundle.putString(RegisterStepTwoActivity.EXTRA_PROFILE_PICTURE, profilePictureCompressFilePath);
        bundle.putString(RegisterStepTwoActivity.EXTRA_NAME, name);
        bundle.putString(RegisterStepTwoActivity.EXTRA_PHONE_NUMBER, phoneNumber);
        bundle.putString(RegisterStepTwoActivity.EXTRA_EMAIL, email);
        bundle.putString(RegisterStepTwoActivity.EXTRA_PASSWORD, password);
        activityNavigator
                .setBundle(bundle)
                .setParcelable(RegisterStepTwoActivity.EXTRA_SENT_OTP, sentOTP)
                .startActivity(RegisterStepTwoActivity.class);
    }

    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    private Intent createProfilePictureImageFile(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            profilePictureOriginalFilePath = mediaFile.getAbsolutePath();
            profilePictureUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, profilePictureUri, activity);
    }

    ActivityResultLauncher<Intent> imageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            profilePictureUri = data.getData();
                            if (profilePictureUri != null) {
                                //mViewBinding.profilePictureCircleImageView.setImageURI(imageUri);

                                profilePictureOriginalFilePath = RealPathUtils.getRealPath(getApplicationContext(), profilePictureUri);

                                File uncompressFile = new File(profilePictureOriginalFilePath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    profilePictureCompressFilePath = compressFile.getPath();

                                    Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                                    if (bitmap != null) {
                                        mViewBinding.profilePictureCircleImageView.setImageBitmap(bitmap);
                                    }
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        //showShortToast("User cancelled image from gallery");
                    } else {
                        //showShortToast("Sorry! Failed to image from gallery");
                    }
                }
            });

    ActivityResultLauncher<Intent> imageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        //mViewBinding.profilePictureCircleImageView.setImageURI(imageUri);

                        File uncompressFile = new File(profilePictureOriginalFilePath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            profilePictureCompressFilePath = compressFile.getPath();

                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                            if (bitmap != null) {
                                mViewBinding.profilePictureCircleImageView.setImageBitmap(bitmap);
                            }
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        //showShortToast("User cancelled image from camera");
                    } else {
                        //showShortToast("Sorry! Failed to image from camera");
                    }
                }
            });
}