package com.example.driverbehaviormonitor.screen.login

import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
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

    Column(
        modifier = Modifier
            .background(androidx.compose.ui.graphics.Color.Black)
            .fillMaxSize()
    ) {
        when (val state = qrState) {
            is LoginViewModel.QrState.Loading -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()){
                    CircularProgressIndicator(color = androidx.compose.ui.graphics.Color.White)
                }
            }

            is LoginViewModel.QrState.Success -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()){
                    Text(
                        text = state.verification_url,
                        color = androidx.compose.ui.graphics.Color.Blue,
                        fontSize = 60.sp
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()){
                    QRCodeImage(content = state.qrData)
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()){
                    Text(
                        text = state.userCode,
                        color = androidx.compose.ui.graphics.Color.Blue,
                        fontSize = 80.sp
                    )
                }
            }

            is LoginViewModel.QrState.Error -> {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()){
                    Text(
                        text = "Error: ${state.message}",
                        color = androidx.compose.ui.graphics.Color.Red,
                        fontSize = 16.sp
                    )
                }
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
                    setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.GRAY)
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
