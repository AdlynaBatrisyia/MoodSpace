package com.example.a216765_wan_lab1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class ShakeDetector(
    context: Context,
    private val onShake: () -> Unit
) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastUpdate = 0L
    private var isFirstReading = true
    private var lastShakeTime = 0L

    private val SHAKE_THRESHOLD = 500   // lower = more sensitive
    private val SHAKE_COOLDOWN = 2000L  // ms between triggers

    fun start() {
        isFirstReading = true
        if (accelerometer != null) {
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val currentTime = System.currentTimeMillis()

        // skip first reading to set baseline
        if (isFirstReading) {
            lastX = event.values[0]
            lastY = event.values[1]
            lastZ = event.values[2]
            lastUpdate = currentTime
            isFirstReading = false
            return
        }

        val timeDiff = currentTime - lastUpdate
        if (timeDiff < 100) return  // only check every 100ms

        lastUpdate = currentTime

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val deltaX = abs(x - lastX)
        val deltaY = abs(y - lastY)
        val deltaZ = abs(z - lastZ)

        val speed = (deltaX + deltaY + deltaZ) / timeDiff * 10000

        lastX = x
        lastY = y
        lastZ = z

        if (speed > SHAKE_THRESHOLD) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTime > SHAKE_COOLDOWN) {
                lastShakeTime = now
                onShake()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}