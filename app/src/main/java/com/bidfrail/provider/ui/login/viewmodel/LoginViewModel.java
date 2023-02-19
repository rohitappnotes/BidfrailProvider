package com.bidfrail.provider.ui.login.viewmodel;

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
public class LoginViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<Provider> loginProviderSuccess = new MutableLiveData<>();
    private MutableLiveData<String> loginProviderError = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<Provider> loginProviderSuccess() {
        return loginProviderSuccess;
    }

    public MutableLiveData<String> loginProviderError() {
        return loginProviderError;
    }

    public void loginProvider(String email, String password, String fcmToken) {
        remoteRepository.loginProviderCall(email, password, fcmToken).enqueue(new ApiCallback<ApiResponseObject<Provider>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Provider>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            loginProviderError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                loginProviderError.setValue(response.body().getMessage());
                            } else {
                                loginProviderSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        loginProviderError.setValue(response.body().getMessage());
                    }
                } else {
                    loginProviderError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                loginProviderError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
