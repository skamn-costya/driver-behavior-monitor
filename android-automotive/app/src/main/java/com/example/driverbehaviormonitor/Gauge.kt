package com.example.driverbehaviormonitor

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun GaugeNeedle(value: Float, maxValue: Float, label: String, color: Color) {
    val angle = (value / maxValue) * 270f - 135f
    val animatedAngle = animateFloatAsState(targetValue = angle, animationSpec = tween(500))

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.DarkGray,
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = 12f)
            )
            val center = this.center
            val radius = size.minDimension / 2.5f
            val rad = Math.toRadians(animatedAngle.value.toDouble())
            val end = androidx.compose.ui.geometry.Offset(
                x = center.x + radius * cos(rad).toFloat(),
                y = center.y + radius * sin(rad).toFloat()
            )
            drawLine(color, start = center, end = end, strokeWidth = 8f, cap = StrokeCap.Round)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("${value.toInt()}", fontSize = 24.sp, color = Color.White)
            Text(label, fontSize = 16.sp, color = color)
        }
    }
}
