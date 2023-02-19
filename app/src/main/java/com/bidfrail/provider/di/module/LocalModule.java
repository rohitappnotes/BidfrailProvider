package com.bidfrail.provider.di.module;

import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.di.qualifier.local.RoomDatabaseName;
import com.bidfrail.provider.di.qualifier.local.SharedPreferencesName;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class LocalModule {

    @Provides
    @SharedPreferencesName
    @Singleton
    String provideSharedPreferencesFileName() {
        return AppConstants.SharedPreferences.SHARED_PREFERENCES_FILE_NAME;
    }

    @Provides
    @RoomDatabaseName
    @Singleton
    String provideRoomDatabaseFileName() {
        return  AppConstants.Database.ROOM_DATABASE_FILE_NAME;
    }
}
