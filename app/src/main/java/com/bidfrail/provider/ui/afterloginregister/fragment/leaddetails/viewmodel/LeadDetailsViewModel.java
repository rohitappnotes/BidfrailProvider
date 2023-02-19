package com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.bidfrail.provider.data.remote.ApiCallback;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.exception.NetworkException;
import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;

import org.json.JSONObject;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.ResponseBody;
import retrofit2.Response;

@HiltViewModel
public class LeadDetailsViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    private MutableLiveData<Lead> leadSuccess = new MutableLiveData<>();
    private MutableLiveData<String> leadError = new MutableLiveData<>();

    private MutableLiveData<String> bidOnLeadSuccess = new MutableLiveData<>();
    private MutableLiveData<String> bidOnLeadError = new MutableLiveData<>();

    private MutableLiveData<String> acceptOrRejectLeadSuccess = new MutableLiveData<>();
    private MutableLiveData<String> acceptOrRejectLeadError = new MutableLiveData<>();

    private MutableLiveData<String> acceptOrRejectFinalOfferSuccess = new MutableLiveData<>();
    private MutableLiveData<String> acceptOrRejectFinalOfferError = new MutableLiveData<>();

    private MutableLiveData<String> setProviderStatusSuccess = new MutableLiveData<>();
    private MutableLiveData<String> setProviderStatusError = new MutableLiveData<>();

    private MutableLiveData<String> raiseBillSuccess = new MutableLiveData<>();
    private MutableLiveData<String> raiseBillError = new MutableLiveData<>();

    @Inject
    public LeadDetailsViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
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

    public MutableLiveData<String> bidOnLeadSuccess() {
        return bidOnLeadSuccess;
    }

    public MutableLiveData<String> bidOnLeadError() {
        return bidOnLeadError;
    }

    public void bidOnLead(int providerId, int orderId, int bidAmount, String message, String userFCMToken) {
        remoteRepository.bidOnLeadCall(providerId, orderId, bidAmount, message, userFCMToken).enqueue(new ApiCallback<ResponseBody>() {
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
                                bidOnLeadError.setValue(message);
                            } else {
                                bidOnLeadSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        bidOnLeadError.setValue(response.message());
                    }
                } else {
                    bidOnLeadError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                bidOnLeadError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> acceptOrRejectLeadSuccess() {
        return acceptOrRejectLeadSuccess;
    }

    public MutableLiveData<String> acceptOrRejectLeadError() {
        return acceptOrRejectLeadError;
    }

    public void acceptOrRejectLead(int orderId, int providerId, String status, String by, int byId, String userFCMToken) {
        remoteRepository.acceptOrRejectLeadCall(orderId, providerId, status, by, byId, userFCMToken).enqueue(new ApiCallback<ResponseBody>() {
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
                                acceptOrRejectLeadError.setValue(message);
                            } else {
                                acceptOrRejectLeadSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        acceptOrRejectLeadError.setValue(response.message());
                    }
                } else {
                    acceptOrRejectLeadError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                acceptOrRejectLeadError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> acceptOrRejectFinalOfferSuccess() {
        return acceptOrRejectFinalOfferSuccess;
    }

    public MutableLiveData<String> acceptOrRejectFinalOfferError() {
        return acceptOrRejectFinalOfferError;
    }

    public void acceptOrRejectFinalOffer(int orderId, int providerId, String status, String userFCMToken) {
        remoteRepository.acceptOrRejectFinalOfferCall(orderId, providerId, status, userFCMToken).enqueue(new ApiCallback<ResponseBody>() {
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
                                acceptOrRejectFinalOfferError.setValue(message);
                            } else {
                                acceptOrRejectFinalOfferSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        acceptOrRejectFinalOfferError.setValue(response.message());
                    }
                } else {
                    acceptOrRejectFinalOfferError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                acceptOrRejectFinalOfferError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> setProviderStatusSuccess() {
        return setProviderStatusSuccess;
    }

    public MutableLiveData<String> setProviderStatusError() {
        return setProviderStatusError;
    }

    public void setProviderStatus(int orderId, int providerId, String providerStatus, String userFCMToken) {
        remoteRepository.setProviderStatusCall(orderId, providerId, providerStatus, userFCMToken).enqueue(new ApiCallback<ResponseBody>() {
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
                                setProviderStatusError.setValue(message);
                            } else {
                                setProviderStatusSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        setProviderStatusError.setValue(response.message());
                    }
                } else {
                    setProviderStatusError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                setProviderStatusError.setValue(networkException.getDisplayMessage());
            }
        });
    }

    public MutableLiveData<String> raiseBillSuccess() {
        return raiseBillSuccess;
    }

    public MutableLiveData<String> raiseBillError() {
        return raiseBillError;
    }

    public void raiseBill(int orderId, int extraCharge, String description, String userFCMToken) {
        remoteRepository.raiseBillCall(orderId, extraCharge, description, userFCMToken).enqueue(new ApiCallback<ResponseBody>() {
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
                                raiseBillError.setValue(message);
                            } else {
                                raiseBillSuccess.setValue(message);
                            }
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        raiseBillError.setValue(response.message());
                    }
                } else {
                    raiseBillError.setValue(null);
                }
            }

            @Override
            public void onFailure(NetworkException networkException) {
                raiseBillError.setValue(networkException.getDisplayMessage());
            }
        });
    }
}
