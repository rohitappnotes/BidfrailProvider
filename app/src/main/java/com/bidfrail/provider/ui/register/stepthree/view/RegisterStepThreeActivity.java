package com.bidfrail.provider.ui.register.stepthree.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.remote.RequestUtils;
import com.bidfrail.provider.databinding.ActivityRegisterStepThreeBinding;
import com.bidfrail.provider.dialogplacepicker.AutoCompletePlacePickerDialog;
import com.bidfrail.provider.dialogplacepicker.AutoCompletePlacePickerListener;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureListener;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureModalBottomSheet;
import com.bidfrail.provider.model.Category;
import com.bidfrail.provider.model.SubCategory;
import com.bidfrail.provider.dialogmultiselect.MultiSelect;
import com.bidfrail.provider.dialogmultiselect.MultiSelectDialog;
import com.bidfrail.provider.dialogmultiselect.MultiSelectListener;
import com.bidfrail.provider.dialogsingleselect.SingleSelect;
import com.bidfrail.provider.dialogsingleselect.SingleSelectDialog;
import com.bidfrail.provider.dialogsingleselect.SingleSelectListener;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.bidfrail.provider.ui.register.stepthree.viewmodel.RegisterStepThreeViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.library.asynctask.alternative.AsyncTaskAlternative;
import com.library.imagecompressor.Compressor;
import com.library.imagecompressor.CompressorFolderException;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ImageAndVideoUtils;
import com.library.utilities.ImplicitIntentUtils;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.file.FileProviderUtils;
import com.library.utilities.file.FileUtils;
import com.library.utilities.file.MediaFileUtils;
import com.library.utilities.file.MemoryUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.file.StorageUtils;
import com.library.utilities.listener.AlertDialogListener;
import com.library.utilities.permissionutils.ManagePermission;
import com.library.utilities.permissionutils.PermissionDialog;
import com.library.utilities.permissionutils.PermissionName;
import com.library.utilities.string.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class RegisterStepThreeActivity extends BaseActivity<ActivityRegisterStepThreeBinding, RegisterStepThreeViewModel> {

    private ManagePermission managePermission;
    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    public static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.READ_EXTERNAL_STORAGE,
                    PermissionName.WRITE_EXTERNAL_STORAGE,
                    PermissionName.CAMERA,
            };

    public static final String EXTRA_PROFILE_PICTURE = "PROFILE_PICTURE";
    public static final String EXTRA_NAME = "NAME";
    public static final String EXTRA_PHONE_NUMBER = "PHONE_NUMBER";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static final String EXTRA_PASSWORD = "PASSWORD";

    private String profilePictureCompressFilePath;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String latitude;
    private String longitude;
    private String latLonAddress;
    private String categoryId;
    private String subCategoryIds;
    private String aboutYou;

    private Uri aadhaarCardFrontUri;
    private String aadhaarCardFrontOriginalFilePath;
    private String aadhaarCardFrontCompressFilePath;

    private Uri aadhaarCardBackUri;
    private String aadhaarCardBackOriginalFilePath;
    private String aadhaarCardBackCompressFilePath;

    private Uri panCardUri;
    private String panCardOriginalFilePath;
    private String panCardCompressFilePath;

    private ArrayList<Category> categoryArrayList = new ArrayList<Category>();
    private ArrayList<SubCategory> subSubCategoryArrayList = new ArrayList<SubCategory>();

    @Override
    protected String getActivityClassName() {
        return RegisterStepThreeActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(RegisterStepThreeActivity.this);
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
    }

    @Override
    protected RegisterStepThreeViewModel getViewModel() {
        return viewModelProvider(RegisterStepThreeViewModel.class);
    }

    @Override
    protected ActivityRegisterStepThreeBinding getViewBinding(LayoutInflater inflater) {
        return ActivityRegisterStepThreeBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setupToolBar(mViewBinding.toolbar, ResourcesUtils.getColor(getApplicationContext(), R.color.colorToolbarBackground), R.drawable.ic_left_arrow, R.string.register_step_three_toolbar_title);
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
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> categoryErrorObserver = new Observer<String>() {
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
        mViewModel.categoryError().observe(this, categoryErrorObserver);

        final Observer<ArrayList<Category>> categorySuccessObserver = new Observer<ArrayList<Category>>() {
            @Override
            public void onChanged(ArrayList<Category> categories) {
                hideProgressDialog();
                categoryArrayList = categories;
            }
        };
        mViewModel.categorySuccess().observe(this, categorySuccessObserver);
        showProgressDialog();
        mViewModel.category();

        Observer<String> subCategoryErrorObserver = new Observer<String>() {
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
        mViewModel.subCategoryError().observe(this, subCategoryErrorObserver);

        final Observer<ArrayList<SubCategory>> subCategorySuccessObserver = new Observer<ArrayList<SubCategory>>() {
            @Override
            public void onChanged(ArrayList<SubCategory> subCategories) {
                hideProgressDialog();
                subSubCategoryArrayList = subCategories;
            }
        };
        mViewModel.subCategorySuccess().observe(this, subCategorySuccessObserver);

        Observer<String> registerProviderErrorObserver = new Observer<String>() {
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
        mViewModel.registerProviderError().observe(this, registerProviderErrorObserver);

        final Observer<String> registerProviderSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                showShortToast(successMessage);
                activityNavigator
                        .clearBackStack()
                        .startActivity(LoginActivity.class);
            }
        };
        mViewModel.registerProviderSuccess().observe(this, registerProviderSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        mViewBinding.areaRegionTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompletePlacePickerDialog autoCompletePlacePickerDialog = new AutoCompletePlacePickerDialog(RegisterStepThreeActivity.this);
                autoCompletePlacePickerDialog.setAutoCompletePlacePickerListener(new AutoCompletePlacePickerListener() {
                    @Override
                    public void onPlaceSelected(@NonNull Place place) {
                        hideKeyboard();
                        autoCompletePlacePickerDialog.dismiss();
                        latLonAddress = place.getAddress();
                        updateUI(place.getLatLng());
                    }
                });
                autoCompletePlacePickerDialog.show(ResourcesUtils.getString(getApplicationContext(), R.string.register_step_three_hint_area_region));
            }
        });

        mViewBinding.categoryTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryArrayList.size() != 0)
                {
                    SingleSelectDialog singleSelectDialog = new SingleSelectDialog(RegisterStepThreeActivity.this);
                    singleSelectDialog.enableSearchBar(true);
                    singleSelectDialog.enableItemImage(false);
                    singleSelectDialog.enableRadioButton(false);
                    singleSelectDialog.setMultiSelectListener(new SingleSelectListener() {
                        @Override
                        public void onSingleSelected(int position, SingleSelect singleSelect, String id, String name) {
                            System.out.println("====POSITION===="+position);
                            System.out.println("====COMMA SEPARATED IDS===="+id);
                            System.out.println("====COMMA SEPARATED NAMES===="+name);

                            categoryId = id;
                            mViewBinding.categoryTextInputEditText.setText(name);

                            showProgressDialog();
                            mViewModel.subCategory(Integer.parseInt(categoryId));
                        }
                    });
                    singleSelectDialog.setSingleSelectArrayList(getCategorySingleSelectArrayList(categoryArrayList));
                    singleSelectDialog.show(ResourcesUtils.getString(getApplicationContext(), R.string.register_step_three_hint_category));
                }
            }
        });

        mViewBinding.subCategoryTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subSubCategoryArrayList.size() != 0)
                {
                    MultiSelectDialog multiSelectDialog = new MultiSelectDialog(RegisterStepThreeActivity.this);
                    multiSelectDialog.setMultiSelectListener(new MultiSelectListener() {
                        @Override
                        public void onMultiSelected(int position, MultiSelect multiSelect, String commaSeparatedIds, String commaSeparatedNames) {
                            System.out.println("====POSITION===="+position);
                            System.out.println("====COMMA SEPARATED IDS===="+commaSeparatedIds);
                            System.out.println("====COMMA SEPARATED NAMES===="+commaSeparatedNames);

                            subCategoryIds = commaSeparatedIds;
                            mViewBinding.subCategoryTextInputEditText.setText(commaSeparatedNames);
                        }
                    });
                    multiSelectDialog.enableItemImage(true);
                    multiSelectDialog.enableSearchBar(true);
                    multiSelectDialog.setMultiSelectArrayList(getSubCategoryMultiSelectArrayList(subSubCategoryArrayList));
                    multiSelectDialog.show(ResourcesUtils.getString(getApplicationContext(), R.string.register_step_three_hint_sub_category));
                }
            }
        });

        mViewBinding.aadhaarCardFrontMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectOrCaptureModalBottomSheet modalBottomSheet = new ImageSelectOrCaptureModalBottomSheet(new ImageSelectOrCaptureListener() {
                    @Override
                    public void fromGallery() {
                        Intent imageFromGalleryIntent = ImplicitIntentUtils.actionPickIntent(1);
                        aadhaarCardFrontImageFromGalleryActivityResultLauncher.launch(imageFromGalleryIntent);
                    }

                    @Override
                    public void fromCamera() {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            Log.e(mTag, "permission already granted");
                            String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                            String fileName = MediaFileUtils.getRandomFileName(1);
                            String extension = "jpg";

                            Intent captureImageIntent = createAadhaarCardFrontImageFile(getApplicationContext(), RegisterStepThreeActivity.this, customDirectoryName, fileName, extension);
                            aadhaarCardFrontImageFromCameraActivityResultLauncher.launch(captureImageIntent);
                        } else {
                            Log.e(mTag, "permission is not granted, request for permission");
                            ActivityCompat.requestPermissions(
                                    RegisterStepThreeActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                modalBottomSheet.show(fragmentManager, modalBottomSheet.getTag());
            }
        });

        mViewBinding.aadhaarCardBackMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectOrCaptureModalBottomSheet modalBottomSheet = new ImageSelectOrCaptureModalBottomSheet(new ImageSelectOrCaptureListener() {
                    @Override
                    public void fromGallery() {
                        Intent imageFromGalleryIntent = ImplicitIntentUtils.actionPickIntent(1);
                        aadhaarCardBackImageFromGalleryActivityResultLauncher.launch(imageFromGalleryIntent);
                    }

                    @Override
                    public void fromCamera() {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            Log.e(mTag, "permission already granted");
                            String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                            String fileName = MediaFileUtils.getRandomFileName(1);
                            String extension = "jpg";

                            Intent captureImageIntent = createAadhaarCardBackImageFile(getApplicationContext(), RegisterStepThreeActivity.this, customDirectoryName, fileName, extension);
                            aadhaarCardBackImageFromCameraActivityResultLauncher.launch(captureImageIntent);
                        } else {
                            Log.e(mTag, "permission is not granted, request for permission");
                            ActivityCompat.requestPermissions(
                                    RegisterStepThreeActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                modalBottomSheet.show(fragmentManager, modalBottomSheet.getTag());
            }
        });

        mViewBinding.panCardMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectOrCaptureModalBottomSheet modalBottomSheet = new ImageSelectOrCaptureModalBottomSheet(new ImageSelectOrCaptureListener() {
                    @Override
                    public void fromGallery() {
                        Intent imageFromGalleryIntent = ImplicitIntentUtils.actionPickIntent(1);
                        panCardImageFromGalleryActivityResultLauncher.launch(imageFromGalleryIntent);
                    }

                    @Override
                    public void fromCamera() {
                        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                            Log.e(mTag, "permission already granted");
                            String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                            String fileName = MediaFileUtils.getRandomFileName(1);
                            String extension = "jpg";

                            Intent captureImageIntent = createPanCardImageFile(getApplicationContext(), RegisterStepThreeActivity.this, customDirectoryName, fileName, extension);
                            panCardImageFromCameraActivityResultLauncher.launch(captureImageIntent);
                        } else {
                            Log.e(mTag, "permission is not granted, request for permission");
                            ActivityCompat.requestPermissions(
                                    RegisterStepThreeActivity.this,
                                    MULTIPLE_PERMISSIONS,
                                    MULTIPLE_PERMISSION_REQUEST_CODE);
                        }
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                modalBottomSheet.show(fragmentManager, modalBottomSheet.getTag());
            }
        });

        mViewBinding.submitMaterialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();

                aboutYou = mViewBinding.aboutYouTextInputEditText.getText().toString().trim();
                if (submitButtonValidation(latLonAddress, categoryId, subCategoryIds, aboutYou, aadhaarCardFrontCompressFilePath, aadhaarCardBackCompressFilePath, panCardCompressFilePath)) {
                    showProgressDialog();

                    MultipartBody.Part profilePictureMultipartBody;
                    if (StringUtils.isEmpty(profilePictureCompressFilePath)) {
                        profilePictureMultipartBody = null;
                    } else {
                        profilePictureMultipartBody = RequestUtils.createMultipartBody("profile_picture", profilePictureCompressFilePath);
                    }

                    RequestBody nameRequestBody = RequestUtils.createRequestBodyForString(name);
                    RequestBody phoneNumberRequestBody = RequestUtils.createRequestBodyForString(phoneNumber);
                    RequestBody emailRequestBody = RequestUtils.createRequestBodyForString(email);
                    RequestBody passwordRequestBody = RequestUtils.createRequestBodyForString(password);

                    RequestBody latitudeRequestBody = RequestUtils.createRequestBodyForString(latitude);
                    RequestBody longitudeRequestBody = RequestUtils.createRequestBodyForString(longitude);
                    RequestBody latLonAddressRequestBody = RequestUtils.createRequestBodyForString(latLonAddress);
                    RequestBody categoryIdRequestBody = RequestUtils.createRequestBodyForString(categoryId);
                    RequestBody subCategoryIdsRequestBody = RequestUtils.createRequestBodyForString(subCategoryIds);
                    RequestBody aboutYouRequestBody = RequestUtils.createRequestBodyForString(aboutYou);

                    MultipartBody.Part aadhaarCardFrontMultipartBody;
                    if (StringUtils.isEmpty(aadhaarCardFrontCompressFilePath)) {
                        aadhaarCardFrontMultipartBody = null;
                    } else {
                        aadhaarCardFrontMultipartBody = RequestUtils.createMultipartBody("aadhaar_card_front", aadhaarCardFrontCompressFilePath);
                    }

                    MultipartBody.Part aadhaarCardBackMultipartBody;
                    if (StringUtils.isEmpty(aadhaarCardBackCompressFilePath)) {
                        aadhaarCardBackMultipartBody = null;
                    } else {
                        aadhaarCardBackMultipartBody = RequestUtils.createMultipartBody("aadhaar_card_back", aadhaarCardBackCompressFilePath);
                    }

                    MultipartBody.Part panCardMultipartBody;
                    if (StringUtils.isEmpty(panCardCompressFilePath)) {
                        panCardMultipartBody = null;
                    } else {
                        panCardMultipartBody = RequestUtils.createMultipartBody("pan_card", panCardCompressFilePath);
                    }

                    mViewModel.registerProvider(profilePictureMultipartBody, nameRequestBody, phoneNumberRequestBody, emailRequestBody, passwordRequestBody, latitudeRequestBody, longitudeRequestBody, latLonAddressRequestBody, categoryIdRequestBody, subCategoryIdsRequestBody, aboutYouRequestBody, aadhaarCardFrontMultipartBody, aadhaarCardBackMultipartBody, panCardMultipartBody);
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
                                RegisterStepThreeActivity.this,
                                MULTIPLE_PERMISSIONS,
                                MULTIPLE_PERMISSION_REQUEST_CODE);
                    }
                }
            });
    /***********************************************************************************************
     *******************************************Validations*****************************************
     **********************************************************************************************/
    private boolean submitButtonValidation(String latLonAddress, String categoryId, String subCategoryIds, String aboutYou, String aadhaarCardFrontFilePath, String aadhaarCardBackFilePath, String panCardFilePath) {
        boolean isValid = true;

        if (StringUtils.isEmpty(latLonAddress)) {
            mViewBinding.areaRegionTextInputLayout.setError(getString(R.string.register_step_three_error_select_area_one));
            isValid = false;
        } else {
            mViewBinding.areaRegionTextInputLayout.setError(null);
            mViewBinding.areaRegionTextInputLayout.setErrorEnabled(false);
        }

        if (StringUtils.isEmpty(categoryId)) {
            mViewBinding.categoryTextInputLayout.setError(getString(R.string.register_step_three_error_select_category_one));
            isValid = false;
        } else {
            mViewBinding.categoryTextInputLayout.setError(null);
            mViewBinding.categoryTextInputLayout.setErrorEnabled(false);
        }

        if (StringUtils.isEmpty(subCategoryIds)) {
            mViewBinding.subCategoryTextInputLayout.setError(getString(R.string.register_step_three_error_select_sub_category_one));
            isValid = false;
        } else {
            mViewBinding.subCategoryTextInputLayout.setError(null);
            mViewBinding.subCategoryTextInputLayout.setErrorEnabled(false);
        }

        if (StringUtils.isEmpty(aboutYou)) {
            mViewBinding.aboutYouTextInputLayout.setError(getString(R.string.register_step_three_error_about_you_one));
            isValid = false;
        } else {
            mViewBinding.aboutYouTextInputLayout.setError(null);
            mViewBinding.aboutYouTextInputLayout.setErrorEnabled(false);
        }

        if (StringUtils.isEmpty(aadhaarCardFrontFilePath)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepThree,
                    getString(R.string.register_step_three_hint_upload_aadhaar_card_front),
                    null,
                    null
            );
            isValid = false;
        }

        if (StringUtils.isEmpty(aadhaarCardBackFilePath)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepThree,
                    getString(R.string.register_step_three_hint_upload_aadhaar_card_back),
                    null,
                    null
            );
            isValid = false;
        }

        if (StringUtils.isEmpty(panCardFilePath)) {
            showShortSnackBar(
                    mViewBinding.activityRegisterStepThree,
                    getString(R.string.register_step_three_hint_upload_pan_card),
                    null,
                    null
            );
            isValid = false;
        }
        return isValid;
    }
    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    public ArrayList<SingleSelect> getCategorySingleSelectArrayList(ArrayList<Category> categoryArrayList) {
        ArrayList<SingleSelect> categorySingleSelectArrayList = new ArrayList<SingleSelect>();
        if (categoryArrayList != null) {
            for (Category category : categoryArrayList) {
                System.out.println("ID : " + category.getId() + " NAME : " + category.getName()+ " IMAGE : " + category.getPoster());
                categorySingleSelectArrayList.add(new SingleSelect(String.valueOf(category.getId()), category.getName(), category.getPoster()));
            }
        }
        return categorySingleSelectArrayList;
    }

    public ArrayList<MultiSelect> getSubCategoryMultiSelectArrayList(ArrayList<SubCategory> subCategoryArrayList) {
        ArrayList<MultiSelect> categoryMultiSelectArrayList = new ArrayList<MultiSelect>();
        if (subCategoryArrayList != null) {
            for (SubCategory subCategory : subCategoryArrayList) {
                System.out.println("ID : " + subCategory.getId() + " NAME : " + subCategory.getName()+ " IMAGE : " + subCategory.getGif());
                categoryMultiSelectArrayList.add(new MultiSelect(String.valueOf(subCategory.getId()), subCategory.getName(), subCategory.getGif()));
            }
        }
        return categoryMultiSelectArrayList;
    }

    private Intent createAadhaarCardFrontImageFile(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            aadhaarCardFrontOriginalFilePath = mediaFile.getAbsolutePath();
            aadhaarCardFrontUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, aadhaarCardFrontUri, activity);
    }

    ActivityResultLauncher<Intent> aadhaarCardFrontImageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            aadhaarCardFrontUri = data.getData();
                            if (aadhaarCardFrontUri != null) {
                                aadhaarCardFrontOriginalFilePath = RealPathUtils.getRealPath(getApplicationContext(), aadhaarCardFrontUri);

                                File uncompressFile = new File(aadhaarCardFrontOriginalFilePath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    aadhaarCardFrontCompressFilePath = compressFile.getPath();

                                    Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                                    if (bitmap != null) {
                                        mViewBinding.aadhaarCardFrontImageView.setImageBitmap(bitmap);
                                    }
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> aadhaarCardFrontImageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {

                        File uncompressFile = new File(aadhaarCardFrontOriginalFilePath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            aadhaarCardFrontCompressFilePath = compressFile.getPath();

                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                            if (bitmap != null) {
                                mViewBinding.aadhaarCardFrontImageView.setImageBitmap(bitmap);
                            }
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private Intent createAadhaarCardBackImageFile(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            aadhaarCardBackOriginalFilePath = mediaFile.getAbsolutePath();
            aadhaarCardBackUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, aadhaarCardBackUri, activity);
    }

    ActivityResultLauncher<Intent> aadhaarCardBackImageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            aadhaarCardBackUri = data.getData();
                            if (aadhaarCardBackUri != null) {
                                aadhaarCardBackOriginalFilePath = RealPathUtils.getRealPath(getApplicationContext(), aadhaarCardBackUri);

                                File uncompressFile = new File(aadhaarCardBackOriginalFilePath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    aadhaarCardBackCompressFilePath = compressFile.getPath();

                                    Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                                    if (bitmap != null) {
                                        mViewBinding.aadhaarCardBackImageView.setImageBitmap(bitmap);
                                    }
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> aadhaarCardBackImageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {

                        File uncompressFile = new File(aadhaarCardBackOriginalFilePath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            aadhaarCardBackCompressFilePath = compressFile.getPath();

                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                            if (bitmap != null) {
                                mViewBinding.aadhaarCardBackImageView.setImageBitmap(bitmap);
                            }
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private Intent createPanCardImageFile(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            panCardOriginalFilePath = mediaFile.getAbsolutePath();
            panCardUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, panCardUri, activity);
    }

    ActivityResultLauncher<Intent> panCardImageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            panCardUri = data.getData();
                            if (panCardUri != null) {
                                panCardOriginalFilePath = RealPathUtils.getRealPath(getApplicationContext(), panCardUri);

                                File uncompressFile = new File(panCardOriginalFilePath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    panCardCompressFilePath = compressFile.getPath();

                                    Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                                    if (bitmap != null) {
                                        mViewBinding.panCardImageView.setImageBitmap(bitmap);
                                    }
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> panCardImageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {

                        File uncompressFile = new File(panCardOriginalFilePath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            panCardCompressFilePath = compressFile.getPath();

                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            Bitmap bitmap = BitmapFactory.decodeFile(compressFile.getAbsolutePath());
                            if (bitmap != null) {
                                mViewBinding.panCardImageView.setImageBitmap(bitmap);
                            }
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @RequiresPermission("android.permission.INTERNET")
    private String getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            geocoder = new Geocoder(context, Locale.getDefault());
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                if (addresses.size() != 0) {
                    Address returnedAddress = addresses.get(0);
                    for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                        stringBuilder.append(returnedAddress.getAddressLine(i)).append("\n");
                    }
                } else {
                    Log.e(mTag, "addresses size 0");
                }
            } else {
                Log.e(mTag, "addresses null");
            }
        } catch (Exception exception) {
            Log.e(mTag, "Can not get Address : " + exception);
        }
        return stringBuilder.toString();
    }

    private class LatLngToAddressTask extends AsyncTaskAlternative<LatLng, Integer, String> {

        public String FULL_ADDRESS = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(LatLng latLng) {
            if (isCancelled()) {
                onCancelled();
                return null;
            }

            FULL_ADDRESS = getAddress(getApplicationContext(), latLng.latitude, latLng.longitude);
            return FULL_ADDRESS;
        }

        @Override
        protected void onPostExecute(String address) {
            if (address != null) {
                latLonAddress = address;
                mViewBinding.areaRegionTextInputEditText.setText(latLonAddress);
            }
        }

        @Override
        protected void onBackgroundError(Exception e) {
        }
    }

    private void updateUI(LatLng point) {
        latitude = String.valueOf(point.latitude);
        longitude = String.valueOf(point.longitude);
        LatLngToAddressTask latLngToAddressTask = new LatLngToAddressTask();
        latLngToAddressTask.execute(point);
    }
}