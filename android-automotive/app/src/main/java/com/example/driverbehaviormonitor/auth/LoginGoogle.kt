//package com.example.driverbehaviormonitor.auth
//
//import kotlin.text.get
//import androidx.lifecycle.*
//
//class LoginGoogle {
//    private fun startLoginFlow() {
//        // 1. Call backend to request device_code
//        ifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val response = backendRequestDeviceCode()
//                deviceCode = response.device_code
//
//                // 2. Generate QR with verification_url + user_code
//                val qrUrl = "${response.verification_url}?user_code=${response.user_code}"
//                val bitmap = generateQrCode(qrUrl)
//
//                withContext(Dispatchers.Main) {
//                    qrImage.setImageBitmap(bitmap)
//                }
//
//                // 3. Start polling for token
//                startPollingForToken(response.interval)
//
//            } catch (e: Exception) {
//                Log.e("LoginActivity", "Error: ${e.message}")
//            }
//        }
//    }
//
//    private fun generateQrCode(text: String): Bitmap {
//        val writer = QRCodeWriter()
//        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
//        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
//        for (x in 0 until 512) {
//            for (y in 0 until 512) {
//                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
//            }
//        }
//        return bmp
//    }
//
//    private fun startPollingForToken(interval: Int) {
//        pollingJob?.cancel()
//        pollingJob = lifecycleScope.launch(Dispatchers.IO) {
//            while (isActive && deviceCode != null) {
//                delay(interval * 1000L)
//
//                val tokenResponse = backendRequestAccessToken(deviceCode!!)
//                if (tokenResponse != null && tokenResponse.access_token != null) {
//                    saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
//
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(this@LoginActivity, "Login Success!", Toast.LENGTH_SHORT).show()
//                        // Navigate to main screen
//                    }
//
//                    cancel() // stop polling
//                }
//            }
//        }
//    }
//
//    private fun saveToken(accessToken: String, refreshToken: String?) {
//        val prefs = getSharedPreferences("auth", Context.MODE_PRIVATE)
//        prefs.edit()
//            .putString("access_token", accessToken)
//            .putString("refresh_token", refreshToken)
//            .apply()
//    }
//
//    // --- Dummy backend calls ---
//    private fun backendRequestDeviceCode(): DeviceCodeResponse {
//        // Normally you call your backend via Retrofit
//        // Backend talks to Google and returns response
//        return DeviceCodeResponse(
//            device_code = "xyz",
//            user_code = "ABCD-EFGH",
//            verification_url = "https://www.google.com/device",
//            interval = 5
//        )
//    }
//
//    private fun backendRequestAccessToken(deviceCode: String): TokenResponse? {
//        // Call your backend again (backend polls Google)
//        // Return null if still pending
//        return null
//    }
//}