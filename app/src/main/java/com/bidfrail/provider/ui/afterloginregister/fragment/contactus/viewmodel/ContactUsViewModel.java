package com.bidfrail.provider.ui.afterloginregister.fragment.contactus.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.ContactUs;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class ContactUsViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<ContactUs> contactUsSuccess = new MutableLiveData<>();
    private MutableLiveData<String> contactUsError = new MutableLiveData<>();

    @Inject
    public ContactUsViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<ContactUs> contactUsSuccess() {
        return contactUsSuccess;
    }

    public MutableLiveData<String> contactUsError() {
        return contactUsError;
    }

    public void contactUs() {
        remoteRepository.contactUsCall().enqueue(new ApiCallback<ApiResponseObject<ContactUs>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<ContactUs>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            contactUsError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                contactUsError.setValue(response.body().getMessage());
                            } else {
                                contactUsSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        contactUsError.setValue(response.body().getMessage());
                    }
                } else {
                    contactUsError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                contactUsError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
