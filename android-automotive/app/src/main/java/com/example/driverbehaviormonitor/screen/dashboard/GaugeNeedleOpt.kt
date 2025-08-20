package com.example.driverbehaviormonitor.screen.dashboard

import androidx.compose.ui.graphics.Color

data class GaugeNeedleOpt (
    val background: Int = 0,
    val minAngle: Float = -180f,
    val maxAngle: Float = 0f,
    val maxVal: Float = 100f,
    val color: Color = Color.White
)