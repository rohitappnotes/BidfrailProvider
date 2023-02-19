package com.bidfrail.provider.data.remote.api;

import com.bidfrail.provider.data.remote.ApiConfiguration;
import com.bidfrail.provider.data.remote.ApiResponseArray;
import com.bidfrail.provider.data.remote.ApiResponseObject;
import com.bidfrail.provider.fcm.model.NotificationRequestForSingleDevice;
import com.bidfrail.provider.fcm.model.NotificationResponse;
import com.bidfrail.provider.model.AboutUs;
import com.bidfrail.provider.model.Category;
import com.bidfrail.provider.model.ContactUs;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.model.Provider;
import com.bidfrail.provider.model.SentOTP;
import com.bidfrail.provider.model.SubCategory;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @FormUrlEncoded
    @POST(ApiConfiguration.PROVIDER)
    Call<ApiResponseObject<Provider>> provider(@Field("provider_id") int userId);

    @Multipart
    @POST(ApiConfiguration.UPDATE_PROVIDER)
    Call<ApiResponseObject<Provider>> updateProfile(@Part("provider_id") RequestBody providerId,
                                                    @Part MultipartBody.Part profilePicture, // "profile_picture"
                                                    @Part("name") RequestBody name,
                                                    @Part("phone_number") RequestBody phoneNumber,
                                                    @Part("email") RequestBody email,
                                                    @Part("password") RequestBody password,
                                                    @Part("latitude") RequestBody latitude,
                                                    @Part("longitude") RequestBody longitude,
                                                    @Part("lat_lon_address") RequestBody latLonAddress,
                                                    @Part("category_id") RequestBody categoryId,
                                                    @Part("sub_category_id") RequestBody subCategoryId,
                                                    @Part("about_you") RequestBody aboutYou,
                                                    @Part MultipartBody.Part gallery, // "gallery"
                                                    @Part MultipartBody.Part aadhaarCardFront, // "aadhaar_card_front"
                                                    @Part MultipartBody.Part aadhaarCardBack, // "aadhaar_card_back"
                                                    @Part MultipartBody.Part panCard, // "pan_card"
                                                    @Part("fcm_token") RequestBody fcmToken);

    @FormUrlEncoded
    @POST(ApiConfiguration.CHECK_DETAILS_PROVIDER)
    Call<ApiResponseObject<SentOTP>> checkDetailsProvider(@Field("phone_number") String phoneNumber,
                                                          @Field("email") String email);

    @GET(ApiConfiguration.CATEGORY)
    Call<ApiResponseArray<Category>> category();

    @FormUrlEncoded
    @POST(ApiConfiguration.SUB_CATEGORY)
    Call<ApiResponseArray<SubCategory>> subCategory(@Field("category_id") int categoryId);

    @Multipart
    @POST(ApiConfiguration.REGISTER_PROVIDER)
    Call<ResponseBody> registerProvider(@Part MultipartBody.Part profilePicture,
                                        @Part("name") RequestBody name,
                                        @Part("phone_number") RequestBody phoneNumber,
                                        @Part("email") RequestBody email,
                                        @Part("password") RequestBody password,
                                        @Part("latitude") RequestBody latitude,
                                        @Part("longitude") RequestBody longitude,
                                        @Part("lat_lon_address") RequestBody latLonAddress,
                                        @Part("category_id") RequestBody categoryId,
                                        @Part("sub_category_id") RequestBody subCategoryId,
                                        @Part("about_you") RequestBody aboutYou,
                                        @Part MultipartBody.Part aadhaarCardFront,
                                        @Part MultipartBody.Part aadhaarCardBack,
                                        @Part MultipartBody.Part panCard);

    @FormUrlEncoded
    @POST(ApiConfiguration.LOGIN_PROVIDER)
    Call<ApiResponseObject<Provider>> loginProvider(@Field("email") String email,
                                                    @Field("password") String password,
                                                    @Field("fcm_token") String fcmToken);

    @FormUrlEncoded
    @POST(ApiConfiguration.FORGOT_PASSWORD_PROVIDER)
    Call<ResponseBody> forgotPasswordProvider(@Field("email") String email /* sent password reset link to email */);


    @FormUrlEncoded
    @POST(ApiConfiguration.LEADS)
    Call<ApiResponseArray<Lead>> leads(@Field("provider_id") int providerId);

    @FormUrlEncoded
    @POST(ApiConfiguration.LEAD_DETAILS)
    Call<ApiResponseObject<Lead>> leadDetails(@Field("order_id") int orderId, @Field("provider_id") int providerId);

    @FormUrlEncoded
    @POST(ApiConfiguration.BID_ON_LEAD)
    Call<ResponseBody> bidOnLead(@Field("provider_id") int providerId,
                                 @Field("order_id") int orderId,
                                 @Field("bid_amount") int bidAmount,
                                 @Field("message") String message,
                                 @Field("user_fcm_token") String userFCMToken);

    @FormUrlEncoded
    @POST(ApiConfiguration.ACCEPT_OR_REJECT_LEAD)
    Call<ResponseBody> acceptOrRejectLead(@Field("order_id") int orderId,
                                          @Field("provider_id") int providerId,
                                          @Field("status") String status /* accept or reject */,
                                          @Field("by") String by,
                                          @Field("by_id") int byId,
                                          @Field("user_fcm_token") String userFCMToken);

    @FormUrlEncoded
    @POST(ApiConfiguration.ACCEPT_OR_REJECT_FINAL_OFFER)
    Call<ResponseBody> acceptOrRejectFinalOffer(@Field("order_id") int orderId,
                                          @Field("provider_id") int providerId,
                                          @Field("status") String status /* accept or reject */,
                                          @Field("user_fcm_token") String userFCMToken);

    /*
     * user status : 'posted','confirmed','started','completed','pay_now','payment_done'
     * provider status : 'accept','start','raise_bill','payment_received'
     *
     * first step :-
     *
     * user post job, in this case user_status "posted" and provider_status "null"
     *
     * in user side job status posted
     * in provider side "Accept" and "Reject" button show
     *
     * second step :-
     *
     * if provider "Reject" the job, then job not show to that provider
     * if provider "Accept" the job, then job not show to other provider
     *
     * if provider "Accept" the job, in this case provider_status is "accept" and user_status is "confirmed"
     *
     * in user side job status confirmed
     * in provider side "Start" button show
     *
     * third step :-
     *
     * if provider goes to user location and click on "Start" button
     *
     * if provider click on "Start" button, in this case provider_status is "start" and user_status is "started"
     *
     * in user side job status started
     * in provider side "Raise Bill" button show
     *
     * fourth step :-
     *
     * if provider done its work then click on "Raise Bill" button
     *
     * if provider click on "Raise Bill" button, bottom sheet show provider enter amount and
     * description and click on "Send Rates" button, then amount and description send to user.
     *
     * in this case provider_status is "raise_bill" and user_status is "pay_now"
     *
     * in user side job status completed and "Pay Now" button, amount, description, otp show
     * in provider side after click on "Send Rates" button, new bottom sheet open with otp input
     * get otp from user side and fill.
     *
     * fifth step :-
     *
     * if user payment done, user rate to provider and provider get otp from user side and fill and click on "Submit"
     * button new screen open where provider rate to user and click on "Submit" button.
     *
     * in this case provider_status is "payment_received" and user_status is "payment_done"
     */
    @FormUrlEncoded
    @POST(ApiConfiguration.SET_PROVIDER_STATUS)
    Call<ResponseBody> setProviderStatus(@Field("order_id") int orderId,
                                         @Field("provider_id") int providerId,
                                         @Field("provider_status") String providerStatus /* 'accept','start','raise_bill','payment_received' */,
                                         @Field("user_fcm_token") String userFCMToken);

    @FormUrlEncoded
    @POST(ApiConfiguration.RAISE_BILL)
    Call<ResponseBody> raiseBill(@Field("order_id") int orderId,
                                 @Field("extra_charge") int extraCharge,
                                 @Field("description") String description,
                                 @Field("user_fcm_token") String userFCMToken);

    @GET(ApiConfiguration.ABOUT_US)
    Call<ApiResponseObject<AboutUs>> aboutUs();

    @GET(ApiConfiguration.CONTACT_US)
    Call<ApiResponseObject<ContactUs>> contactUs();

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAoM_qaNM:APA91bEy1Guey8TCDLHqYi-nlMG33FtKukHEXQY0d8VsYycdHQvUcjJLd2SS6vPmrXq8RsSC-KYSDqkcHl4wi29h48AJDFeDgigX7IpDS3RVCijyCsFW5aQXP839vJO1-WGZqMcj94ns"
            }
    )
    @POST("https://fcm.googleapis.com/fcm/send")
    Call<NotificationResponse> sendNotification(@Body NotificationRequestForSingleDevice notificationRequestForSingleDevice);
}
