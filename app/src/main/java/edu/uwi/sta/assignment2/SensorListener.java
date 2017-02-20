package edu.uwi.sta.assignment2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * Created by M.hayes on 4/12/2016.
 */
public class SensorListener implements SensorEventListener {
    Context context;

    public SensorListener(Context context) {
        this.context = context;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Toast.makeText(
                context,
                "Received Value of " + event.values[0] + " from the sensor: " +event.sensor.getStringType(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
