package com.example.driverbehaviormonitor.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log

object ApiClient {
    private const val BASE_URL = "https://aaos.sorokolit.net/"

    val authApi: AuthApi by lazy {
        Log.d("ApiClient", "Creating AuthApi")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(AuthApi::class.java)
    }
}
