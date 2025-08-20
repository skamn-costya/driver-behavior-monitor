package com.example.driverbehaviormonitor.auth

data class TokenResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val idToken: String?,
    val scope: String?,
    val tokenType: String?,
    val expiresIn: Int?
)