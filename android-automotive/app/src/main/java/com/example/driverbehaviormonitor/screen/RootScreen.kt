package com.example.driverbehaviormonitor.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.driverbehaviormonitor.screen.dashboard.DashboardScreen
import com.example.driverbehaviormonitor.screen.info.InfoScreen
import com.example.driverbehaviormonitor.screen.login.LoginScreen
import com.example.driverbehaviormonitor.screen.user.UserScreen
import com.example.driverbehaviormonitor.VehicleState
import com.example.driverbehaviormonitor.R
import com.example.driverbehaviormonitor.SessionManager

@Composable
fun RootScreen(state: VehicleState) {
    var currentScreen by remember { mutableStateOf("dashboard") }

    Column {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { currentScreen = "dashboard" }) { Text("Dashboard") }
            Button(onClick = { currentScreen = "info" }) { Text("Info") }

            if (!SessionManager.isSet) {
                IconButton(
                    onClick = { currentScreen = "login" },
                    modifier = Modifier.sizeIn(minWidth = 250.dp, minHeight = 40.dp),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.web_neutral_rd_si),
                            contentDescription = null
                        )
                    })
                if ( currentScreen == "user" )
                    currentScreen = "login"
            } else {
                Button(onClick = { currentScreen = "user" }) { Text("User") }
                if ( currentScreen == "login" )
                    currentScreen = "user"
            }
        }
        when (currentScreen) {
            "dashboard" -> DashboardScreen(state)
            "info" -> InfoScreen(state)
            "login" -> LoginScreen()
            "user" -> UserScreen()
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1408dp,height=792dp,dpi=160",
    name = "AAOS 1024x768 Preview"
)
@Composable
fun PreviewRootScreen() {
    val mockState = VehicleState(
        speed = 0,
        rpm = 0,
        fuelLevel = 100,
        engineTemp = 90,
        odometer = 0,
        gear = "P",
        seatbeltOn = true,
        leftBlinkerOn = false,
        rightBlinkerOn = false
    )
    DashboardScreen(state = mockState)
}
