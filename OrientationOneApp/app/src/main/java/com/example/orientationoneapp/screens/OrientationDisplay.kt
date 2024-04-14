package com.example.orientationoneapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OrientationDisplay(orientation: Triple<Float, Float, Float>) {
    Column {
        Text(text = "X: ${orientation.first}")
        Text(text = "Y: ${orientation.second}")
        Text(text = "Z: ${orientation.third}")
        Spacer(modifier = Modifier.height(16.dp)) // Add some space

        // Add visual indicators for orientation
        OrientationIndicator(orientation = orientation)
    }
}

@Composable
fun OrientationIndicator(orientation: Triple<Float, Float, Float>) {
    // Create a 3D model or animated arrows based on the orientation data
    // For simplicity, let's create a basic representation using Compose graphics
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.LightGray)
    ) {
        // Example: Draw arrow for pitch (tilt forward/backward)
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(
                    rotationX = orientation.first // Rotate along X-axis for pitch
                )
                .background(Color.Red)
        )

        // Example: Draw arrow for roll (tilt sideways)
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(
                    rotationY = orientation.second // Rotate along Y-axis for roll
                )
                .background(Color.Green)
        )

        // Example: Draw arrow for yaw (rotation around vertical axis)
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer(
                    rotationZ = orientation.third // Rotate along Z-axis for yaw
                )
                .background(Color.Blue)
        )
    }
}
