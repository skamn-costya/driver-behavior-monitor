package com.example.driverbehaviormonitor.screen.login

import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.example.driverbehaviormonitor.auth.*
import com.example.driverbehaviormonitor.screen.dashboard.cflcdFontFamily

@Composable
fun LoginScreen(viewModel: LoginViewModel = LoginViewModel()) {
    val context = LocalContext.current
    val qrState by viewModel.qrCodeState.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadQr()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(androidx.compose.ui.graphics.Color.Black)
            .fillMaxSize()
    ) {
        when (val state = qrState) {
            is LoginViewModel.QrState.Loading -> {
                CircularProgressIndicator(color = androidx.compose.ui.graphics.Color.White)
            }

            is LoginViewModel.QrState.Success -> {
                QRCodeImage(content = state.qrData)
            }

            is LoginViewModel.QrState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = androidx.compose.ui.graphics.Color.Red,
                    fontSize = 16.sp
                )
                Toast.makeText(context, "QR code error: ${state.message}", Toast.LENGTH_SHORT).show()
            }

            null -> {}
        }
    }
}

@Composable
fun QRCodeImage(content: String) {
    val bitmap = remember(content) {
        val size = 512
        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).apply {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = "QR Code",
        modifier = Modifier.size(256.dp)
    )
}
