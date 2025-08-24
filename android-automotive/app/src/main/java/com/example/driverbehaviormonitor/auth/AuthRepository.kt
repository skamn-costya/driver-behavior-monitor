package com.example.driverbehaviormonitor.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class AuthRepository(
    private val baseUrl: String = "https://example.com",
    private val client: OkHttpClient = OkHttpClient()
) {
    suspend fun waitForAuthorization(
        deviceId: String,
        attempts: Int = 30,          // ≈2.5 мин при 5с интервале
        intervalMs: Long = 5_000
    ): Boolean {
        repeat(attempts) {
            if (checkAuthStatus(deviceId)) return true
            delay(intervalMs)
        }
        return false
    }

    private suspend fun checkAuthStatus(deviceId: String): Boolean =
        withContext(Dispatchers.IO) {
            val req = Request.Builder()
                .url("$baseUrl/api/device/$deviceId/auth-status")
                .get()
                .build()

            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return@use false
                val body = resp.body?.string().orEmpty()
                body.contains("\"authorized\": true")
            }
        }
}
