package com.caralarm.service;



import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class VibrateService extends Service {
	private  SensorManager sm;
	public float X;
	public float Y;
	public float Z;
	public float V;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		 int sensorType = Sensor.TYPE_ACCELEROMETER;
		 sm.registerListener(myAccelerometerListener,sm.getDefaultSensor(sensorType),SensorManager.SENSOR_DELAY_NORMAL);
			
	}

	final SensorEventListener myAccelerometerListener = new SensorEventListener(){
	    public void onSensorChanged(SensorEvent sensorEvent){
	    		if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
	    		
	    				X = sensorEvent.values[0];
	    				Y = sensorEvent.values[1];
	    				Z = sensorEvent.values[2];
	    				
	    				//V =(float) Math.sqrt(X * X + Y * Y + Z * Z);
	    			    Intent intent=new Intent();
	    			    intent.putExtra("X", X);
	    			    intent.putExtra("Y", Y);
	    			    intent.putExtra("Z", Z);
	    			    intent.setAction("com.caralarm.service.VibrateService");
	    			    sendBroadcast(intent);
	    			   


	    		}
	    	}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub	
		}
		  };
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


}
