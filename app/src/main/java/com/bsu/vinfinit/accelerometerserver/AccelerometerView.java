package com.bsu.vinfinit.accelerometerserver;

import android.app.Activity;
import android.widget.TextView;

import com.bsu.vinfinit.accelerometerserver.utils.Observer;

/**
 * Created by uladzimir on 9/26/17.
 */

public class AccelerometerView implements Observer {
    private TextView x;
    private TextView y;
    private TextView z;

    public AccelerometerView(Activity activity) {
        x = activity.findViewById(R.id.x);
        y = activity.findViewById(R.id.y);
        z = activity.findViewById(R.id.z);
    }

    @Override
    public void update(float x, float y, float z) {
        this.x.setText(Float.toString(x));
        this.y.setText(Float.toString(y));
        this.z.setText(Float.toString(z));
    }
}
