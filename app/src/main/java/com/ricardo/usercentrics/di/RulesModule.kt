package com.ricardo.usercentrics.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class RulesModule {

    @JvmSuppressWildcards
    @Provides
    @Costs
    fun provideServicesCosts() = mapOf(
        "Configuration of app settings" to 1.0,
        "IP address" to 2.0,
        "User behaviour" to 2.0,
        "User agent" to 3.0,
        "App crashes" to -2.0,
        "Browser information" to 3.0,
        "Credit and debit card number" to 4.0,
        "First name" to 6.0,
        "Geographic location" to 7.0,
        "Date and time of visit" to 1.0,
        "Advertising identifier" to 2.0,
        "Bank details" to 5.0,
        "Purchase activity" to 6.0,
        "Internet service provider" to 4.0,
        "JavaScript support" to -1.0,
    )

    @Provides
    @RuleOneBankingSnoopy
    fun provideRuleOneBankingSnoopy() = listOf(
        "Purchase activity",
        "Bank details",
        "Credit and debit card number"
    )

    @Provides
    @RuleTwoWhyDoYouCare
    fun provideRuleTwoWhyDoYouCare() = listOf(
        "Search terms",
        "Geographic location",
        "IP Address"
    )

    @Provides
    @RuleThreeTheGoodCitizenLimit
    fun provideRuleThreeTheGoodCitizenLimit() = 4
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Costs

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RuleOneBankingSnoopy

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RuleTwoWhyDoYouCare

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RuleThreeTheGoodCitizenLimit
