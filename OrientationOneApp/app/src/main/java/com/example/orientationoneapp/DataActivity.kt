package com.example.orientationoneapp

import android.graphics.Point
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.Column

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.orientationoneapp.model.OrientationDao

import com.example.orientationoneapp.model.OrientationDatabase
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed



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
            text = "Orientation Data List",
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        if (orientationDataList.isEmpty()) {
            Text(text = "No data available")
        } else {

            LazyColumn {
                itemsIndexed(orientationDataList) { index, data ->
                    Text(
                        text = "ID: ${data.id}, Timestamp: ${data.timestamp}, " +
                                "Roll: ${data.roll}, Pitch: ${data.pitch}, Yaw: ${data.yaw}",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}



