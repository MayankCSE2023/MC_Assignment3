package com.example.orientationoneapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrientationDisplay(orientation: Triple<Float, Float, Float>) {
    Column {
        Text(text = "X: ${orientation.first}")
        Text(text = "Y: ${orientation.second}")
        Text(text = "Z: ${orientation.third}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OrientationDisplay(Triple(0f, 0f, 0f))
}