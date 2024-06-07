package com.example.useoflightsensorinjc

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.useoflightsensorinjc.ui.theme.UseOfLightSensorInJCTheme
import java.io.IOException

class MainActivity : ComponentActivity(),SensorEventListener {

    private var sensor: Sensor? = null
    private var sensorManager: SensorManager? = null

    private var backgroundColor by mutableStateOf(Color.Yellow)
    private var isImageVisible by mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UseOfLightSensorInJCTheme {
              Surface (modifier = Modifier.fillMaxSize(),
                  color = Color.Yellow
                  ){
                  MyApp(isImageVisible)
              }
            }
            sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
            sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        try {
            if(event!!.values[0] < 30){
                 // how to change image visibility to image Invisibility
                // ans set background dark
                Log.d(TAG, "onSensorChanged: + ${event.values[0]}")
                backgroundColor = Color.DarkGray
                isImageVisible = false
            }else{
           // image visibility is visible
                backgroundColor = Color.Yellow
                isImageVisible = true
            }
        }catch (e:IOException){
            Log.d(TAG, "onSensorChanged: + ${e.message}")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        // Register a listener for the sensor.
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}

@Composable
fun MyApp(isImageVisible: Boolean) {
    Column(modifier = Modifier.padding(40.dp))
    {
        Text(text = "Put Object in front of your device Sensors(Right to the FrontCamera), SEE the changes",
            modifier = Modifier.padding(top = 30.dp))

        if(isImageVisible) {
            val source = painterResource(id = R.drawable.img)
            Image(
                painter = source, contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.padding(top = 50.dp)
            )
        }
    }
}


