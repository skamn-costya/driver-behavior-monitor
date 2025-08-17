package com.example.driverbehaviormonitor

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GaugeNeedle(value: Float, maxValue: Float, color: Color, d_id: Int) {
    val angle = (value / maxValue) * 249f - 214f
    val animatedAngle = animateFloatAsState(targetValue = angle, animationSpec = tween(500))

    // Используем Box для наложения слоев
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {

        // Слой 1: Фоновая подложка. Помещаем ее первой, чтобы она была внизу.
        // Замените R.drawable.dashboard_bg на имя вашего файла
        Image(
            painter = painterResource(id = d_id),
            contentDescription = "Gauge Background",
            modifier = Modifier.fillMaxSize()
        )

        // Слой 2: Стрелка, нарисованная на Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = this.center
            val radius = size.minDimension / 2.5f
            val rad = Math.toRadians(animatedAngle.value.toDouble())
            val end = androidx.compose.ui.geometry.Offset(
                x = center.x + radius * cos(rad).toFloat(),
                y = center.y + radius * sin(rad).toFloat()
            )
            drawLine(color, start = center, end = end, strokeWidth = 8f, cap = StrokeCap.Round)
        }
    }
}

@Preview
@Composable
fun PreviewGaugeNeedle() {
    GaugeNeedle(110f, 240f, Color.Red, R.drawable.speed01);
}
