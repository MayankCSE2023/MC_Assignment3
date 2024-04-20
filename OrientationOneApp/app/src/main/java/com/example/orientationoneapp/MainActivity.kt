package com.example.orientationoneapp
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orientationoneapp.model.OrientationDatabase
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
    val context = LocalContext.current
    val database = OrientationDatabase.getDatabase(context)
    val viewModel: OrientationViewModel = viewModel { OrientationViewModel(database.orientationDao()) }

    LaunchedEffect(context) {
        viewModel.startOrientationUpdates(context)
    }

    Column {
        OrientationDisplay(viewModel.orientationData.value)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { context.startActivity(Intent(context, DataActivity::class.java)) }) {
            Text(text = "View Saved Data")
        }
    }
}