package com.bidfrail.provider.data.local.sharedpreferences;

import android.content.Context;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.di.qualifier.local.SharedPreferencesName;
import com.library.sharedpreferences.SharedPreferenceBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SharedPreferencesHelper extends SharedPreferenceBuilder {

    @Inject
    public SharedPreferencesHelper(@ApplicationContext Context context, @SharedPreferencesName String sharedPreferencesFileName) {
        super(context, sharedPreferencesFileName);
    }

    public void setCurrentTheme(int value) {
        putInt(AppConstants.SharedPreferences.CURRENT_THEME, value);
    }

    public int getCurrentTheme() {
        return getInt(AppConstants.SharedPreferences.CURRENT_THEME, 0);
    }

    public void setIsLanguageSelect(boolean value) {
        putBoolean(AppConstants.SharedPreferences.IS_LANGUAGE_SELECT, value);
    }

    public boolean getIsLanguageSelect() {
        return getBoolean(AppConstants.SharedPreferences.IS_LANGUAGE_SELECT, false);
    }

    public String getCurrentLanguage() {
        return getString(AppConstants.SharedPreferences.CURRENT_LANGUAGE, "en");
    }

    public void setCurrentLanguage(String value) {
        putString(AppConstants.SharedPreferences.CURRENT_LANGUAGE, value);
    }

    public void setIsShowAppIntro(boolean value) {
        putBoolean(AppConstants.SharedPreferences.IS_SHOW_APP_INTRO, value);
    }

    public boolean getIsShowAppIntro() {
        return getBoolean(AppConstants.SharedPreferences.IS_SHOW_APP_INTRO, true);
    }

    public void setRemember(boolean value) {
        putBoolean(AppConstants.SharedPreferences.REMEMBER_ME, value);
    }

    public boolean getRemember() {
        return getBoolean(AppConstants.SharedPreferences.REMEMBER_ME, false);
    }

    public void setProviderId(int value) {
        putInt(AppConstants.SharedPreferences.PROVIDER_ID, value);
    }

    public int getProviderId() {
        return getInt(AppConstants.SharedPreferences.PROVIDER_ID, 0);
    }

    public void setOS(String value) {
        putString(AppConstants.SharedPreferences.OS, value);
    }

    public String getOS() {
        return getString(AppConstants.SharedPreferences.OS, null);
    }

    public void setPicture(String value) {
        putString(AppConstants.SharedPreferences.PICTURE, value);
    }

    public String getPicture() {
        return getString(AppConstants.SharedPreferences.PICTURE);
    }

    public void setName(String value) {
        putString(AppConstants.SharedPreferences.NAME, value);
    }

    public String getName() {
        return getString(AppConstants.SharedPreferences.NAME);
    }

    public void setPhoneNumber(String value) {
        putString(AppConstants.SharedPreferences.PHONE_NUMBER, value);
    }

    public String getPhoneNumber() {
        return getString(AppConstants.SharedPreferences.PHONE_NUMBER);
    }

    public void setEmail(String value) {
        putString(AppConstants.SharedPreferences.EMAIL, value);
    }

    public String getEmail() {
        return getString(AppConstants.SharedPreferences.EMAIL);
    }

    public void setPassword(String value) {
        putString(AppConstants.SharedPreferences.PASSWORD, value);
    }

    public String getPassword() {
        return getString(AppConstants.SharedPreferences.PASSWORD);
    }

    public void setLatitude(String value) {
        putString(AppConstants.SharedPreferences.LATITUDE, value);
    }

    public String getLatitude() {
        return getString(AppConstants.SharedPreferences.LATITUDE);
    }

    public void setLongitude(String value) {
        putString(AppConstants.SharedPreferences.LONGITUDE, value);
    }

    public String getLongitude() {
        return getString(AppConstants.SharedPreferences.LONGITUDE);
    }

    public void setLatLonAddress(String value) {
        putString(AppConstants.SharedPreferences.LAT_LON_ADDRESS, value);
    }

    public String getLatLonAddress() {
        return getString(AppConstants.SharedPreferences.LAT_LON_ADDRESS);
    }

    public void setCategoryId(int value) {
        putInt(AppConstants.SharedPreferences.CATEGORY_ID, value);
    }

    public int getCategoryId() {
        return getInt(AppConstants.SharedPreferences.CATEGORY_ID);
    }

    public void setCategoryName(String value) {
        putString(AppConstants.SharedPreferences.CATEGORY_NAME, value);
    }

    public String getCategoryName() {
        return getString(AppConstants.SharedPreferences.CATEGORY_NAME);
    }

    public void setSubCategoryId(String value) {
        putString(AppConstants.SharedPreferences.SUB_CATEGORY_ID, value);
    }

    public String getSubCategoryId() {
        return getString(AppConstants.SharedPreferences.SUB_CATEGORY_ID);
    }

    public void setSubCategoryName(String value) {
        putString(AppConstants.SharedPreferences.SUB_CATEGORY_NAME, value);
    }

    public String getSubCategoryName() {
        return getString(AppConstants.SharedPreferences.SUB_CATEGORY_NAME);
    }

    public void setAboutYou(String value) {
        putString(AppConstants.SharedPreferences.ABOUT_YOU, value);
    }

    public String getAboutYou() {
        return getString(AppConstants.SharedPreferences.ABOUT_YOU);
    }

    public void setGallery(String value) {
        putString(AppConstants.SharedPreferences.GALLERY, value);
    }

    public String getGallery() {
        return getString(AppConstants.SharedPreferences.GALLERY);
    }

    public void setAadhaarCardFront(String value) {
        putString(AppConstants.SharedPreferences.AADHAAR_CARD_FRONT, value);
    }

    public String getAadhaarCardFront() {
        return getString(AppConstants.SharedPreferences.AADHAAR_CARD_FRONT);
    }

    public void setAadhaarCardBack(String value) {
        putString(AppConstants.SharedPreferences.AADHAAR_CARD_BACK, value);
    }

    public String getAadhaarCardBack() {
        return getString(AppConstants.SharedPreferences.AADHAAR_CARD_BACK);
    }

    public void setPanCard(String value) {
        putString(AppConstants.SharedPreferences.PAN_CARD, value);
    }

    public String getPanCard() {
        return getString(AppConstants.SharedPreferences.PAN_CARD);
    }

    public void setIsActive(int value) {
        putInt(AppConstants.SharedPreferences.IS_ACTIVE, value);
    }

    public int getIsActive() {
        return getInt(AppConstants.SharedPreferences.IS_ACTIVE);
    }

    public void setIsOnline(int value) {
        putInt(AppConstants.SharedPreferences.IS_ONLINE, value);
    }

    public int getIsOnline() {
        return getInt(AppConstants.SharedPreferences.IS_ONLINE);
    }

    public void setCredit(int value) {
        putInt(AppConstants.SharedPreferences.CREDIT, value);
    }

    public int getCredit() {
        return getInt(AppConstants.SharedPreferences.CREDIT);
    }

    public void setFcmToken(String value) {
        putString(AppConstants.SharedPreferences.FCM_TOKEN, value);
    }

    public String getFcmToken() {
        return getString(AppConstants.SharedPreferences.FCM_TOKEN);
    }

    public void setCreatedAt(String value) {
        putString(AppConstants.SharedPreferences.CREATED_AT, value);
    }

    public String getCreatedAt() {
        return getString(AppConstants.SharedPreferences.CREATED_AT);
    }

    public void setUpdatedAt(String value) {
        putString(AppConstants.SharedPreferences.UPDATED_AT, value);
    }

    public String getUpdatedAt() {
        return getString(AppConstants.SharedPreferences.UPDATED_AT);
    }
}
