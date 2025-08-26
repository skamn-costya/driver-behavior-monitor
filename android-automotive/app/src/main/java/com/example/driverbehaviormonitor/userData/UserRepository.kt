package com.example.driverbehaviormonitor.userData

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.withContext


data class GoogleUser(
    val id: String,
    val email: String,
    val name: String,
    val given_name: String,
    val picture: String
)

class UserRepository {
    private val client = OkHttpClient()

    suspend fun getUserInfo(accessToken: String): GoogleUser? =
        withContext(Dispatchers.IO) {
            val req = Request.Builder()
                .url("https://www.googleapis.com/oauth2/v2/userinfo")
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return@use null
                val body = resp.body?.string().orEmpty()
                return@use Gson().fromJson(body, GoogleUser::class.java)
            }
        }
}
