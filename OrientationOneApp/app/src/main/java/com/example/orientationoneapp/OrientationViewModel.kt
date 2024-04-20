package com.example.orientationoneapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orientationoneapp.model.OrientationDao
import com.example.orientationoneapp.model.OrientationData
import com.example.orientationoneapp.model.OrientationDatabase
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

class OrientationViewModel(private val orientationDao: OrientationDao) : ViewModel() {

    val orientationData = mutableStateOf(Triple(0f, 0f, 0f))


    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private val sensorEventListener = object : SensorEventListener {
        private var lastSavedTime = 0L

        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastSavedTime >= 500) {
                    try {
                        val timestamp = System.currentTimeMillis()
                        val x = event.values[0]
                        val y = event.values[1]
                        val z = event.values[2]
                        val roll = calculateRoll(x, y, z)
                        val pitch = calculatePitch(x, y, z)
                        val yaw = calculateYaw(x, y, z)

                        orientationData.value = Triple(roll, pitch, yaw)

                        // Save the orientation data to the database
                        viewModelScope.launch {
                            orientationDao.insert(
                                OrientationData(
                                    timestamp = timestamp,
                                    roll = roll,
                                    pitch = pitch,
                                    yaw = yaw
                                )
                            )
                        }

                        lastSavedTime = currentTime // Update last saved time
                    } catch (e: Exception) {
                        Log.e("OrientationViewModel", "Error processing sensor data: ${e.message}")
                    }
                }
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Ignoring for now
        }
    }

    @SuppressLint("MissingPermission")
    fun startOrientationUpdates(context: Context) {
        try {
            sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            accelerometer?.let { accel ->
                sensorManager?.registerListener(sensorEventListener, accel, SensorManager.SENSOR_DELAY_NORMAL)
            }
        } catch (e: Exception) {
            Log.e("OrientationViewModel", "Error starting orientation updates: ${e.message}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        try {
            sensorManager?.unregisterListener(sensorEventListener)
        } catch (e: Exception) {
            Log.e("OrientationViewModel", "Error unregistering sensor listener: ${e.message}")
        }
    }

    private fun calculateRoll(x: Float, y: Float, z: Float): Float {
        return Math.toDegrees(atan2(y, z).toDouble()).toFloat()
    }

    private fun calculatePitch(x: Float, y: Float, z: Float): Float {
        return Math.toDegrees(atan2(-x, sqrt(y.pow(2) + z.pow(2))).toDouble()).toFloat()
    }

    private fun calculateYaw(x: Float, y: Float, z: Float): Float {
        return Math.toDegrees(atan2(y, x).toDouble()).toFloat()
    }
}