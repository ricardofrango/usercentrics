package com.ricardo.usercentrics.domain

import com.usercentrics.sdk.Usercentrics
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class UsercentricsSdkReadyUseCase @Inject constructor() {

    suspend operator fun invoke(): Boolean {
        return isReady()
    }

    private suspend fun isReady(): Boolean =
        suspendCoroutine { continuation ->
            Usercentrics.isReady(
                onSuccess = {
                    continuation.resume(true)
                },
                onFailure = {
                    continuation.resume(true)
                }
            )
        }
}