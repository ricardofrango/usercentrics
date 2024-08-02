package com.ricardo.usercentrics

import android.app.Application
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class UsercentricsApplication : Application() {

    @Inject lateinit var usercentricsOptions: UsercentricsOptions

    override fun onCreate() {
        super.onCreate()

        initUsercentrics()
    }

    private fun initUsercentrics() {
        Usercentrics.initialize(this, usercentricsOptions)
    }
}