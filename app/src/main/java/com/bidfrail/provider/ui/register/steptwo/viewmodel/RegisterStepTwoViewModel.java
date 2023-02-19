package com.bidfrail.provider.ui.register.steptwo.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.SentOTP;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class RegisterStepTwoViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<SentOTP> checkDetailsProviderSuccess = new MutableLiveData<>();
    private MutableLiveData<String> checkDetailsProviderError = new MutableLiveData<>();

    @Inject
    public RegisterStepTwoViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<SentOTP> checkDetailsProviderSuccess() {
        return checkDetailsProviderSuccess;
    }

    public MutableLiveData<String> checkDetailsProviderError() {
        return checkDetailsProviderError;
    }

    public void checkDetailsProvider(String phoneNumber, String email) {
        remoteRepository.checkDetailsProviderCall(phoneNumber, email).enqueue(new ApiCallback<ApiResponseObject<SentOTP>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<SentOTP>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            checkDetailsProviderError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                checkDetailsProviderError.setValue(response.body().getMessage());
                            } else {
                                checkDetailsProviderSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        checkDetailsProviderError.setValue(response.body().getMessage());
                    }
                } else {
                    checkDetailsProviderError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                checkDetailsProviderError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
