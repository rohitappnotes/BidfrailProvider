package com.bidfrail.provider.ui.chat.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.fcm.model.NotificationRequestForSingleDevice;
import com.bidfrail.provider.fcm.model.NotificationResponse;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Response;

@HiltViewModel
public class ChatViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<Lead> leadSuccess = new MutableLiveData<>();
    private MutableLiveData<String> leadError = new MutableLiveData<>();

    private MutableLiveData<String> sendNotificationSuccess = new MutableLiveData<>();
    private MutableLiveData<String> sendNotificationError = new MutableLiveData<>();

    @Inject
    public ChatViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }

    public MutableLiveData<Lead> leadSuccess() {
        return leadSuccess;
    }

    public MutableLiveData<String> leadError() {
        return leadError;
    }

    public void leadDetails(int orderId, int providerId) {
        remoteRepository.leadDetailsCall(orderId, providerId).enqueue(new ApiCallback<ApiResponseObject<Lead>>() {
            @Override
            public void onSuccess(Response<ApiResponseObject<Lead>> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().isError()) {
                            leadError.setValue(response.body().getMessage());
                        } else {
                            if (response.body().getData() == null) {
                                leadError.setValue(response.body().getMessage());
                            } else {
                                leadSuccess.setValue(response.body().getData());
                            }
                        }
                    } else {
                        leadError.setValue(response.body().getMessage());
                    }
                } else {
                    leadError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                leadError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> sendNotificationSuccess() {
        return sendNotificationSuccess;
    }

    public MutableLiveData<String> sendNotificationError() {
        return sendNotificationError;
    }

    public void sendNotification(NotificationRequestForSingleDevice notificationRequestForSingleDevice) {
        remoteRepository.sendNotificationCall(notificationRequestForSingleDevice).enqueue(new ApiCallback<NotificationResponse>() {
            @Override
            public void onSuccess(Response<NotificationResponse> response) {
                if (response.body() != null) {
                    if (response.isSuccessful()) {
                        if (response.body().getSuccess() > 0) {
                            sendNotificationSuccess.setValue("Message successfully sent.");
                        } else {
                            sendNotificationError.setValue(response.body().getResults().get(0).getError());
                        }
                    } else {
                        sendNotificationError.setValue("isSuccessful() is false : " + response.message());
                    }
                } else {
                    sendNotificationError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                sendNotificationError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
