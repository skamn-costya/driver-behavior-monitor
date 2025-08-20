package com.example.driverbehaviormonitor.backend

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import com.example.driverbehaviormonitor.auth.*

interface AuthApi {
    @GET("auth/qr")
    suspend fun getQr(): QrResponse

    @POST("auth/device")
    suspend fun requestDeviceCode(): DeviceCodeResponse

    @POST("auth/token")
    suspend fun pollToken(@Body body: Map<String, String>): TokenResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body body: Map<String, String>): TokenResponse
}
