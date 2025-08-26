package com.example.driverbehaviormonitor.auth

data class AuthResponse(
    val deviceCode: String?,
    val userCode: String?,
    val verificationUrl: String?,
    val status: String?,
    val tokens: Tokens?
)

data class Tokens(
    val access_token: String?,
    val expires_in: Int?,
    val refresh_token: String?,
    val scope: String?,
    val token_type: String?,
    val id_token: String?
)
data class TokenResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val idToken: String?,
    val scope: String?,
    val tokenType: String?,
    val expiresIn: Int?
)