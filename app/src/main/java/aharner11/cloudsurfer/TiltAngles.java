package aharner11.cloudsurfer;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
 
public class TiltAngles extends Activity {
	private TextView result;
	private SensorManager sensorManager;
	private Sensor sensor;
	private static final String TAG = cloudthread.class.getSimpleName();
	private float x, y, z;
 
	/*
	public TiltAngles(){
		Log.d(TAG, "Started acce3er");
		//super();
		Log.d(TAG, "Started acc7ometer");
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
 
	//	sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	//	sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
 
		//result = (TextView) findViewById(R.id.result);
		//result.setText("No result yet");
	//Log.d(TAG, "Started accelerometer");
	}
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
 
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
 
		//result = (TextView) findViewById(R.id.result);
		//result.setText("No result yet");
		Log.d(TAG, "Started accelerometer");
	}
 
	public float getXaccel(){
		return x;
	}
	
	private void refreshDisplay() {
		String output = String
				.format("x is: %f / y is: %f / z is: %f", x, y, z);
		//result.setText(output);
		Log.d(TAG, output);
	}
 
	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(accelerationListener, sensor,
				SensorManager.SENSOR_DELAY_GAME);
	}
 
	@Override
	protected void onStop() {
		sensorManager.unregisterListener(accelerationListener);
		super.onStop();
	}
 
	private SensorEventListener accelerationListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int acc) {
		}
 
		@Override
		public void onSensorChanged(SensorEvent event) {
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			refreshDisplay();
		}
 
	};
}