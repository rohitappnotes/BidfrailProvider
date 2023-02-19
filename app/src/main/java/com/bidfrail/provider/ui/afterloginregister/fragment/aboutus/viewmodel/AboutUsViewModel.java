package com.bidfrail.provider.ui.afterloginregister.fragment.aboutus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.AboutUs;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class AboutUsViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<AboutUs> aboutUsSuccess = new MutableLiveData<>();
    private MutableLiveData<String> aboutUsError = new MutableLiveData<>();

    @Inject
    public AboutUsViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<AboutUs> aboutUsSuccess() {
        return aboutUsSuccess;
    }

    public MutableLiveData<String> aboutUsError() {
        return aboutUsError;
    }

    public void aboutUs() {
        remoteRepository.aboutUsCall().enqueue(new ApiCallback<ApiResponseObject<AboutUs>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<AboutUs>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            aboutUsError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                aboutUsError.setValue(response.body().getMessage());
                            } else {
                                aboutUsSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        aboutUsError.setValue(response.body().getMessage());
                    }
                } else {
                    aboutUsError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                aboutUsError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
