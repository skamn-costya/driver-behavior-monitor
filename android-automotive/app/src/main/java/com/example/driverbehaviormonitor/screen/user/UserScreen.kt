package com.example.driverbehaviormonitor.screen.user

import androidx.compose.ui.platform.LocalContext
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.example.driverbehaviormonitor.auth.*
import com.example.driverbehaviormonitor.screen.dashboard.cflcdFontFamily
import com.example.driverbehaviormonitor.userData.UserViewModel
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.example.driverbehaviormonitor.SessionManager

@Composable
fun UserScreen(viewModel: UserViewModel = UserViewModel()) {
    val user = viewModel.user

    LaunchedEffect(Unit) {
        if ( !SessionManager.accessToken.isNullOrEmpty() )
            viewModel.loadUser(SessionManager.accessToken!!)
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user == null) {
            CircularProgressIndicator(color = Color.White)
        } else {
            Text(
                text = "Welcome ${user.name}",
                color = Color.White,
                fontSize = 42.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            AsyncImage(
                model = user.picture,
                contentDescription = "User Photo",
                modifier = Modifier.size(400.dp).clip(CircleShape)
            )

            Button(onClick = {
                SessionManager.deleteTokens()
            }) { Text("Logout") }
        }
    }
}
