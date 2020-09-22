package com.example.accelerationsample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SensorTestActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    DBHelper db;
    private TextView textViewXAxis;




    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @Override
    public void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);
        textViewXAxis = findViewById(R.id.value_z_axis);


        db=new DBHelper(this);
        db.deleteData();


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);






    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);

        }

    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];
        textViewXAxis.setText(String.format(Locale.getDefault(), "%.2f", z));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str=sdf.format(cal.getTime());
        String [] ind=str.split(":");
        int sFinal=Integer.parseInt(ind[2]);
        String  f= df2.format(z);
        float zFinal=Float.parseFloat(f);
        /*Log.i("LL","Seconds Count="+sFinal);
        Log.i("Tt","Z Count="+zFinal);*/
        db.insertData(sFinal, zFinal);
       db.getData();
       /* Log.i("LL","Final ="+result);*/
        /*if(resultFinal>=1.5000){
            Toast.makeText(getApplicationContext(),"Detected",Toast.LENGTH_SHORT).show();
        }*/


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

}