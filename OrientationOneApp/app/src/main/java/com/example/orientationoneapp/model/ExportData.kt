package com.example.orientationoneapp.model

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException

// Step 1: Retrieve Data from Room Database
fun getOrientationDataFromDatabase(orientationDao: OrientationDao): List<OrientationData> {
    return orientationDao.get500OrientationData().toList()
}

// Step 2: Convert Data to Text Format
fun convertDataToTextFormat(data: List<OrientationData>): String {
    val stringBuilder = StringBuilder()
    data.forEach { orientationData ->
        // Format data as text, e.g., CSV format
        stringBuilder.append("${orientationData.id},${orientationData.timestamp},${orientationData.roll},${orientationData.pitch},${orientationData.yaw}\n")
    }
    return stringBuilder.toString()
}

// Step 3: Write Data to File
fun writeDataToFile(context: Context, textData: String, fileName: String) {
    try {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, fileName)
        file.writeText(textData)
        // Success message
        Log.d("ExportData", "Data exported successfully to: ${file.absolutePath}")
    } catch (e: IOException) {
        // Error message
        Log.e("ExportData", "Error exporting data: ${e.message}")
    }
}
