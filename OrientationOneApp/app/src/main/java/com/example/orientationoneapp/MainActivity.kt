package com.example.orientationoneapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orientationoneapp.screens.OrientationDisplay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrientationApp()
        }
    }
}

@Composable
fun OrientationApp() {
    val viewModel: OrientationViewModel = viewModel()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Real-time Orientation:")
        OrientationDisplay(viewModel.orientationData.value)
    }

    val context = LocalContext.current
    LaunchedEffect(context) {
        viewModel.startOrientationUpdates(context)
    }
}