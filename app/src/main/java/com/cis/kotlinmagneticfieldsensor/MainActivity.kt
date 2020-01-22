package com.cis.kotlinmagneticfieldsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
// 마그네틱 필드를 이용하면 주변 자기장 값을 읽어올 수 있다.
// 가속도 센서와 조합해서 사용하면 방위(동서남북)를 읽을 수 있다.
class MainActivity : AppCompatActivity() {
    var manager : SensorManager? = null
    var listener : SensorListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        listener = SensorListener()

        startBtn.setOnClickListener {
            val sensor = manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
            val chk = manager?.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)

            if (chk == false) {
                tv.text = "마그네틱 필드 센서를 지원하지 않습니."
            }

        }

        stopBtn.setOnClickListener {
            manager?.unregisterListener(listener)
        }
    }

    inner class SensorListener : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
                tv.text = "y 축 주변 자기장 : ${event?.values[0]}\n"
                tv.append("x 축 주변 자기장 : ${event?.values[1]}\n")
                tv.append("z 축 주변 자기장 : ${event?.values[2]}")
            }
        }

    }
}
