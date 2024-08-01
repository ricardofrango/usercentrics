package com.ricardo.usercentrics.di

import com.ricardo.usercentrics.BuildConfig
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UsercentricsSDKModule {

    companion object {
        @Singleton
        @Provides
        @UsercentricsSettingsKey
        fun provideUsercentricsSettingsKey(): String = BuildConfig.USERCENTRICS_SETTINGS_KEY

        @Singleton
        @Provides
        fun provideUsercentricsOptions(
            @UsercentricsSettingsKey usercentricsSettingsKey: String
        ) = UsercentricsOptions(
            settingsId = usercentricsSettingsKey,
            loggerLevel = UsercentricsLoggerLevel.ERROR
        )
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UsercentricsSettingsKey
