package com.bsu.vinfinit.accelerometerserver;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.bsu.vinfinit.accelerometerserver.utils.Observer;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by uladzimir on 9/25/17.
 */

public class AccelerometerSensor implements SensorEventListener {
    private float[] ac = new float[3];
    private List<Observer> observers = new ArrayList<>();

    public AccelerometerSensor(Activity activity) {
        SensorManager mSensorManager = (SensorManager)activity.getSystemService(SENSOR_SERVICE);
        Sensor mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void add(Observer o) {
        this.observers.add(o);
    }

    private void execute(float x, float y, float z) {
        for (Observer observer : observers) {
            observer.update(x, y, z);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        ac[0] = sensorEvent.values[0];
        ac[1] = sensorEvent.values[1];
        ac[2] = sensorEvent.values[2];
        this.execute(ac[0], ac[1], ac[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
