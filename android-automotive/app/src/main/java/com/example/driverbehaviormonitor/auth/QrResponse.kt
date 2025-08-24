package com.example.driverbehaviormonitor.auth

data class QrResponse(
    val session_id: String,
    val verification_url: String,
    val userCode: String,
    val qrData: String,
)
