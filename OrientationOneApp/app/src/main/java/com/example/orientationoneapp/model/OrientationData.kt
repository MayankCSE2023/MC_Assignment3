package com.example.orientationoneapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orientation_data")
data class OrientationData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val roll: Float,
    val pitch: Float,
    val yaw: Float
)



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


