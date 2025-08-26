package com.example.driverbehaviormonitor.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import android.util.Log
import org.json.JSONObject
import android.content.Context
import com.google.gson.Gson
import com.example.driverbehaviormonitor.SessionManager

class AuthRepository(
    private val baseUrl: String = "https://aaos.sorokolit.net",
    private val client: OkHttpClient = OkHttpClient()
) {
    suspend fun waitForAuthorization(
        session_id: String,
        attempts: Int = 30,          // ≈2.5 мин при 5с интервале
        intervalMs: Long = 5_000
    ): Boolean {
        repeat(attempts) {
            if (checkAuthStatus(session_id)) return true
            delay(intervalMs)
        }
        return false
    }

        private suspend fun checkAuthStatus(session_id: String ): Boolean =
        withContext(Dispatchers.IO) {
            val req = Request.Builder()
                .url("$baseUrl/auth/status/$session_id")
                .get()
                .build()

            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return@use false
                val body = resp.body?.string().orEmpty()
                Log.d("DBM", "resp.body $body")

                val gson = Gson()
                val authResponse = gson.fromJson(body, AuthResponse::class.java)

                if (authResponse.status.equals("approved", true) && authResponse.tokens != null) {
//                    val access = authResponse.tokens.access_token.orEmpty()
//                    val refresh = authResponse.tokens.refresh_token.orEmpty()

                    // Save tokens
                    SessionManager.saveTokens(
                        authResponse.tokens.access_token.orEmpty(),
                        authResponse.tokens.refresh_token.orEmpty())
//                    val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
//                    prefs.edit()
//                        .putString("access_token", access)
//                        .putString("refresh_token", refresh)
//                        .apply()
//                    Log.d("DBM", "Access token saved: $access")
                    true
                } else {
                    false
                }
            }
        }
}
