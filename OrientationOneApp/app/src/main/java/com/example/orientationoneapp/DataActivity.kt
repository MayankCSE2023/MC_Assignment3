package com.example.orientationoneapp

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.ui.platform.LocalContext
import com.example.orientationoneapp.model.OrientationDatabase
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Path
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.orientationoneapp.model.OrientationDao
import com.example.orientationoneapp.model.OrientationData
import java.io.File
import java.io.IOException


class DataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val database = OrientationDatabase.getDatabase(context)
            DataDisplay(database.orientationDao())
        }
    }
}


@Composable
fun DataDisplay(orientationDao: OrientationDao) {
    val orientationDataList by orientationDao.getAllOrientationData().observeAsState(emptyList())

    Column {
        Text(
            text = "Orientation Data Graph",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        if (orientationDataList.isEmpty()) {
            Text(text = "No data available")
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawLineGraph(orientationDataList, this)
                }
            }
        }
    }
}

private fun DrawScope.drawLineGraph(
    orientationDataList: List<OrientationData>,
    canvas: DrawScope
) {
    val maxId = orientationDataList.maxOf { it.id }
    val minId = orientationDataList.minOf { it.id }
    val idRange = maxId - minId

    val maxYaw = orientationDataList.maxOf { it.yaw }
    val minYaw = orientationDataList.minOf { it.yaw }
    val yawRange = maxYaw - minYaw

    val maxRoll = orientationDataList.maxOf { it.roll }
    val minRoll = orientationDataList.minOf { it.roll }
    val rollRange = maxRoll - minRoll

    val maxPitch = orientationDataList.maxOf { it.pitch }
    val minPitch = orientationDataList.minOf { it.pitch }
    val pitchRange = maxPitch - minPitch

    val scaleX = size.width / idRange.toFloat()
    val scaleY = size.height

    val pathYaw = Path()
    val pathRoll = Path()
    val pathPitch = Path()

    orientationDataList.forEachIndexed { index, data ->
        val x = (data.id - minId).toFloat() * scaleX
        val yYaw = (data.yaw - minYaw) / yawRange * scaleY
        val yRoll = (data.roll - minRoll) / rollRange * scaleY
        val yPitch = (data.pitch - minPitch) / pitchRange * scaleY

        if (index == 0) {
            pathYaw.moveTo(x, yYaw)
            pathRoll.moveTo(x, yRoll)
            pathPitch.moveTo(x, yPitch)
        } else {
            pathYaw.lineTo(x, yYaw)
            pathRoll.lineTo(x, yRoll)
            pathPitch.lineTo(x, yPitch)
        }
    }

    drawPath(
        path = pathYaw,
        color = Color.Red,
        style = Stroke(width = 2f)
    )
    drawPath(
        path = pathRoll,
        color = Color.Green,
        style = Stroke(width = 2f)
    )
    drawPath(
        path = pathPitch,
        color = Color.Blue,
        style = Stroke(width = 2f)
    )

}

//fun exportOrientationDataToFile(orientationDao: OrientationDao) {
//    // Retrieve the orientation data from the database
//    val orientationDataList = orientationDao.getAllOrientationData()
//
//    // Convert the orientation data into a text format
//    val textData = buildString {
//        orientationDataList.forEach { data ->
//            append("${data.id},${data.timestamp},${data.roll},${data.pitch},${data.yaw}\n")
//        }
//    }
//
//    // Write the text data to a file on external storage
//    try {
//        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        val file = File(directory, "orientation_data.txt")
//        file.writeText(textData)
//        // Success message
//        println("Orientation data exported successfully to: ${file.absolutePath}")
//    } catch (e: IOException) {
//        // Error message
//        println("Error exporting orientation data: ${e.message}")
//    }
//}

//@Composable
//fun DataDisplay(orientationDao: OrientationDao) {
//    val orientationDataList by orientationDao.getAllOrientationData().observeAsState(emptyList())
//
//    Column {
//        Text(
//            text = "Orientation Data List",
//            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
//        )
//        if (orientationDataList.isEmpty()) {
//            Text(text = "No data available")
//        } else {
//
//            LazyColumn {
//                itemsIndexed(orientationDataList) { index, data ->
//                    Text(
//                        text = "ID: ${data.id}, Timestamp: ${data.timestamp}, " +
//                                "Roll: ${data.roll}, Pitch: ${data.pitch}, Yaw: ${data.yaw}",
//                        modifier = Modifier.padding(8.dp)
//                    )
//                }
//            }
//        }
//    }
//}



