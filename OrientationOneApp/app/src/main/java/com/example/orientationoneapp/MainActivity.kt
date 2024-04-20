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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.orientationoneapp.model.OrientationDao
import com.example.orientationoneapp.model.OrientationDatabase
import com.example.orientationoneapp.model.convertDataToTextFormat
import com.example.orientationoneapp.model.writeDataToFile
import com.example.orientationoneapp.screens.OrientationDisplay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            Text(text = "Show Graph")
        }
        ExportButton(database.orientationDao())
    }
}


@Composable
fun ExportButton(orientationDao: OrientationDao) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    Button(
        onClick = {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                val orientationDataList = orientationDao.get500OrientationData().toList()
                val textData = convertDataToTextFormat(orientationDataList)
                writeDataToFile(context, textData, "orientation_data.txt")
            }
        }
    ) {
        Text("Export Data")
    }
}
