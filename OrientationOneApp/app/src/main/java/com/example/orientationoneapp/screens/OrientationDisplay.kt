package com.example.orientationoneapp.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun OrientationDisplay(orientation: Triple<Float, Float, Float>) {
    Column {
        Text(text = "X: ${orientation.first.format(1)}")
        Text(text = "Y: ${orientation.second.format(1)}")
        Text(text = "Z: ${orientation.third.format(1)}")
        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun Float.format(digits: Int) = String.format("%.1f", this)