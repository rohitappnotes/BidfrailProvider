package com.bidfrail.provider.ui.splash.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.Provider;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class SplashViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<Provider> providerSuccess = new MutableLiveData<>();
    private MutableLiveData<String> providerError = new MutableLiveData<>();

    @Inject
    public SplashViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<Provider> providerSuccess() {
        return providerSuccess;
    }

    public MutableLiveData<String> providerError() {
        return providerError;
    }

    public void providerDetails(int providerId) {
        remoteRepository.providerCall(providerId).enqueue(new ApiCallback<ApiResponseObject<Provider>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Provider>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            providerError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                providerError.setValue(response.body().getMessage());
                            } else {
                                providerSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        providerError.setValue(response.body().getMessage());
                    }
                } else {
                    providerError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                providerError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
