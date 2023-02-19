package com.bidfrail.provider.data.remote;

import com.bidfrail.provider.AppConstants;

public class ApiConfiguration {

    public static final int CUSTOM_HTTP_CONNECT_TIMEOUT_IN_SECONDS              = 2 * 60; /* 2 minutes */
    public static final int CUSTOM_HTTP_WRITE_TIMEOUT_IN_SECONDS                = 25; /* 25 seconds */
    public static final int CUSTOM_HTTP_READ_TIMEOUT_IN_SECONDS                 = 40; /* 40 seconds */

    public static final String CUSTOM_OK_HTTP_CACHE_FILE_NAME                   = "customOkHttpClientCache";
    public static final long CUSTOM_OK_HTTP_CACHE_SIZE                          = 20 * 1024 * 1024; /* 20 MB Cache size */

    public static final int CUSTOM_CACHE_DURATION_WITH_NETWORK_IN_SECONDS       = 10;
    public static final int CUSTOM_CACHE_DURATION_WITHOUT_NETWORK_IN_SECONDS    = 14 * 24 * 60 * 60; /* Expired in two week. */

    public static final String BASE_URL                     =  AppConstants.AppConfig.IS_DEBUG ? AppConstants.ApiInfo.Development.BASE_URL : AppConstants.ApiInfo.Production.BASE_URL;
    public static final String API_KEY                      =  AppConstants.AppConfig.IS_DEBUG ? AppConstants.ApiInfo.Development.BASE_URL : AppConstants.ApiInfo.Production.BASE_URL;
    public static final String BEARER_AUTHENTICATION_TOKEN  =  AppConstants.AppConfig.IS_DEBUG ? AppConstants.ApiInfo.Development.BASE_URL : AppConstants.ApiInfo.Production.BASE_URL;
    public static final String IMAGE_URL                    =  "https://admin.bidfrail.com/DevTeam/";

    /*
     * End points
     */
    public static final String PROVIDER                                     = "provider";
    public static final String UPDATE_PROVIDER                              = "update_provider";
    public static final String CHECK_DETAILS_PROVIDER                       = "check_details_provider";
    public static final String CATEGORY                                     = "category";
    public static final String SUB_CATEGORY                                 = "sub_category";
    public static final String REGISTER_PROVIDER                            = "register_provider";
    public static final String LOGIN_PROVIDER                               = "login_provider";
    public static final String FORGOT_PASSWORD_PROVIDER                     = "forgot_password_provider";

    public static final String LEADS                                        = "leads";
    public static final String LEAD_DETAILS                                 = "lead_details";

    public static final String BID_ON_LEAD                                  = "bid_on_lead";
    public static final String ACCEPT_OR_REJECT_LEAD                        = "accept_or_reject_lead";
    public static final String ACCEPT_OR_REJECT_FINAL_OFFER                 = "accept_final_offer";
    public static final String SET_PROVIDER_STATUS                          = "set_provider_status";
    public static final String RAISE_BILL                                   = "raise_bill";

    public static final String CONTACT_US                                   = "contact_us";
    public static final String ABOUT_US                                     = "about_us";
}
