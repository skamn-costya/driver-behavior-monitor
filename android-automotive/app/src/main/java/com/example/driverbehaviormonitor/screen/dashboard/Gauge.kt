package com.example.driverbehaviormonitor.screen.dashboard

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
import androidx.compose.material3.Text
import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.driverbehaviormonitor.R

val cflcdFontFamily = FontFamily(
    Font(R.font.lcd, FontWeight.Normal)
)

@Composable
fun GaugeNeedle(value: Int, opt: GaugeNeedleOpt) {
    val angle = (value / opt.maxVal) * opt.maxAngle + opt.minAngle
    val animatedAngle = animateFloatAsState(targetValue = angle, animationSpec = tween(500))

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(300.dp)) {

        Image(
            painter = painterResource(id = opt.background),
            contentDescription = "Gauge Background",
            modifier = Modifier.fillMaxSize()
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = this.center
            val radius = size.minDimension / 2.5f
            val rad = Math.toRadians(animatedAngle.value.toDouble())
            val end = Offset(
                x = center.x + radius * cos(rad).toFloat(),
                y = center.y + radius * sin(rad).toFloat()
            )
            drawLine(opt.color, start = center, end = end, strokeWidth = 8f, cap = StrokeCap.Round)
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.size(150.dp)
        ) {
            Text(
                text = value.toString(),
                fontFamily = cflcdFontFamily,
                fontSize = 28.sp,
                color = Color.Cyan,
            )
        }
    }
}

@Preview
@Composable
fun PreviewGaugeNeedle() {
//    val speed = GaugeNeedleOpt(R.drawable.speed_200,  -225.5f, 270.5f, 200f, Color.Green)
    val speed = GaugeNeedleOpt(R.drawable.tach01,  -213f, 246f, 10f, Color.Green)
    GaugeNeedle(100, speed);
}
