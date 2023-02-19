package com.bidfrail.provider.ui.register.stepthree.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseArray;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.Category;
import com.bidfrail.provider.model.SubCategory;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import org.json.JSONObject;
import java.util.ArrayList;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

@HiltViewModel
public class RegisterStepThreeViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<ArrayList<Category>> categorySuccess = new MutableLiveData<>();
    private MutableLiveData<String> categoryError = new MutableLiveData<>();

    private MutableLiveData<ArrayList<SubCategory>> subCategorySuccess = new MutableLiveData<>();
    private MutableLiveData<String> subCategoryError = new MutableLiveData<>();

    private MutableLiveData<String> registerProviderSuccess = new MutableLiveData<>();
    private MutableLiveData<String> registerProviderError = new MutableLiveData<>();

    @Inject
    public RegisterStepThreeViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<ArrayList<Category>> categorySuccess() {
        return categorySuccess;
    }

    public MutableLiveData<String> categoryError() {
        return categoryError;
    }

    public void category() {
        remoteRepository.categoryCall().enqueue(new ApiCallback<ApiResponseArray<Category>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<Category>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            categoryError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                categoryError.setValue(response.body().getMessage());
                            } else {
                                categorySuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        categoryError.setValue(response.body().getMessage());
                    }
                } else {
                    categoryError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                categoryError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<SubCategory>> subCategorySuccess() {
        return subCategorySuccess;
    }

    public MutableLiveData<String> subCategoryError() {
        return subCategoryError;
    }

    public void subCategory(int subCategoryId) {
        remoteRepository.subCategoryCall(subCategoryId).enqueue(new ApiCallback<ApiResponseArray<SubCategory>>() {
            @Override
            public void onSuccess(Response<ApiResponseArray<SubCategory>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            subCategoryError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                subCategoryError.setValue(response.body().getMessage());
                            } else {
                                subCategorySuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        subCategoryError.setValue(response.body().getMessage());
                    }
                } else {
                    subCategoryError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                subCategoryError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> registerProviderSuccess() {
        return registerProviderSuccess;
    }

    public MutableLiveData<String> registerProviderError() {
        return registerProviderError;
    }

    public void registerProvider(MultipartBody.Part profilePicture,
                                 RequestBody name,
                                 RequestBody phoneNumber,
                                 RequestBody email,
                                 RequestBody password,
                                 RequestBody latitude,
                                 RequestBody longitude,
                                 RequestBody latLonAddress,
                                 RequestBody categoryId,
                                 RequestBody subCategoryId,
                                 RequestBody aboutYou,
                                 MultipartBody.Part aadhaarCardFront,
                                 MultipartBody.Part aadhaarCardBack,
                                 MultipartBody.Part panCard) {
        remoteRepository.registerProviderCall(profilePicture,
                name,
                phoneNumber,
                email,
                password,
                latitude,
                longitude,
                latLonAddress,
                categoryId,
                subCategoryId,
                aboutYou,
                aadhaarCardFront,
                aadhaarCardBack,
                panCard).enqueue(new ApiCallback<ResponseBody>() {
            @Override
            public void onSuccess(Response<ResponseBody> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(response.body().string());

                            boolean status = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");

                            if (status) {
                                registerProviderError.setValue(message);
                            } else {
                                registerProviderSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        registerProviderError.setValue(response.message());
                    }
                } else {
                    registerProviderError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                registerProviderError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
