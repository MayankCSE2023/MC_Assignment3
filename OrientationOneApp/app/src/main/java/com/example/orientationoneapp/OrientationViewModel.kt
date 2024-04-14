package com.example.orientationoneapp

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class OrientationViewModel : ViewModel() {
    val orientationData = mutableStateOf(Triple(0f, 0f, 0f))

    private var sensorManager: SensorManager? = null
    private var accelerometer: Sensor? = null
    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                orientationData.value = Triple(x, y, z)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // Ignoring for now
        }
    }

    @SuppressLint("MissingPermission")
    fun startOrientationUpdates(context: Context) {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let { accel ->
            sensorManager?.registerListener(sensorEventListener, accel, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager?.unregisterListener(sensorEventListener)
    }
}