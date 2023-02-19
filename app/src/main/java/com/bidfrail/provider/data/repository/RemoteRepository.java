package com.bidfrail.provider.data.repository;

import com.bidfrail.provider.data.remote.ApiResponseArray;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.data.remote.api.ApiService;
import com.bidfrail.provider.fcm.model.NotificationRequestForSingleDevice;
import com.bidfrail.provider.fcm.model.NotificationResponse;
import com.bidfrail.provider.model.AboutUs;
import com.bidfrail.provider.model.Category;
import com.bidfrail.provider.model.ContactUs;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.model.Provider;
import com.bidfrail.provider.model.SentOTP;
import com.bidfrail.provider.model.SubCategory;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;

@Singleton
public class RemoteRepository {

    private final ApiService apiService;

    @Inject
    public RemoteRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public Call<ApiResponseObject<Provider>> providerCall(int providerId) {
        return apiService.provider(providerId);
    }

    public Call<ApiResponseObject<SentOTP>> checkDetailsProviderCall(String phoneNumber, String email) {
        return apiService.checkDetailsProvider(phoneNumber, email);
    }

    public Call<ApiResponseArray<Category>> categoryCall() {
        return apiService.category();
    }

    public Call<ApiResponseArray<SubCategory>> subCategoryCall(int categoryId) {
        return apiService.subCategory(categoryId);
    }

    public Call<ResponseBody> registerProviderCall(MultipartBody.Part profilePicture,
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
        return apiService.registerProvider(
                profilePicture,
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
                panCard);
    }

    public Call<ApiResponseObject<Provider>> loginProviderCall(String email, String password, String fcmToken) {
        return apiService.loginProvider(email, password, fcmToken);
    }

    public Call<ResponseBody> forgotPasswordProviderCall(String email) {
        return apiService.forgotPasswordProvider(email);
    }

    public Call<ApiResponseArray<Lead>> leadsCall(int providerId) {
        return apiService.leads(providerId);
    }

    public Call<ApiResponseObject<Lead>> leadDetailsCall(int orderId, int providerId) {
        return apiService.leadDetails(orderId, providerId);
    }

    public Call<ResponseBody> bidOnLeadCall(int providerId,
                                            int orderId,
                                            int bidAmount,
                                            String message,
                                            String userFCMToken) {
        return apiService.bidOnLead(providerId, orderId, bidAmount, message, userFCMToken);
    }

    public Call<ResponseBody> acceptOrRejectLeadCall(int orderId,
                                                     int providerId,
                                                     String status,
                                                     String by,
                                                     int byId,
                                                     String userFCMToken) {
        return apiService.acceptOrRejectLead(orderId, providerId, status, by, byId, userFCMToken);
    }

    public Call<ResponseBody> acceptOrRejectFinalOfferCall(int orderId,
                                                           int providerId,
                                                           String status,
                                                           String userFCMToken) {
        return apiService.acceptOrRejectFinalOffer(orderId, providerId, status, userFCMToken);
    }

    public Call<ResponseBody> setProviderStatusCall(int orderId,
                                                    int providerId,
                                                    String providerStatus,
                                                    String userFCMToken) {
        return apiService.setProviderStatus(orderId, providerId, providerStatus, userFCMToken);
    }

    public Call<ResponseBody> raiseBillCall(int orderId,
                                            int extraCharge,
                                            String description,
                                            String userFCMToken) {
        return apiService.raiseBill(orderId, extraCharge, description, userFCMToken);
    }

    public Call<ApiResponseObject<AboutUs>> aboutUsCall() {
        return apiService.aboutUs();
    }

    public Call<ApiResponseObject<ContactUs>> contactUsCall() {
        return apiService.contactUs();
    }

    public Call<NotificationResponse> sendNotificationCall(NotificationRequestForSingleDevice notificationRequestForSingleDevice) {
        return apiService.sendNotification(notificationRequestForSingleDevice);
    }
}
