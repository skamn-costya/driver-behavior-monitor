package com.example.driverbehaviormonitor.auth

data class DeviceCodeResponse(
    val deviceCode: String,
    val userCode: String,
    val verificationURL: String,
    val expiresIn: Int,
    val interval: Int
)