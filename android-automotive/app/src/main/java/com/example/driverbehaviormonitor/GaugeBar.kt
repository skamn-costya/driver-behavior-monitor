package com.example.driverbehaviormonitor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GaugeBar(value: Float, maxValue: Float, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(
            progress = value / maxValue,
            color = color,
            modifier = Modifier
                .width(160.dp)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text("$label: ${value.toInt()}", fontSize = 18.sp, color = Color.White)
    }
}
