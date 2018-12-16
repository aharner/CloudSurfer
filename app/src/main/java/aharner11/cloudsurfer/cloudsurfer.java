package aharner11.cloudsurfer;
//Andrew Harner -2012

//import cloud.surfer.cloud.AccelerometerListener;
//import cloud.surfer.cloud.AccelerometerManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class cloudsurfer extends Activity implements AccelerometerListener {
	
	 private static Context CONTEXT;
	private static final String TAG = cloudsurfer.class.getSimpleName();
	private static final int MENU_START = 1;
//	private static final double GRAVITY = 1;
	
//	public Acceleration gravity;
	private ViewGroup viewgroup;
	private TextView result;
	private TextView status;
	private Spinner levelselector;
	private SensorManager sensorManager;
	private Sensor sensor;
	public float x, y, z;
	public String testName;
	public CloudViewer myCloudView;
	//public accels = new Acceleration(x,y,z);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//gravity = new Acceleration(0,GRAVITY);
    	//set gravity
    	super.onCreate(savedInstanceState);
    	/////setContentView(R.layout.main);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	//sets the main content viewer
    	setContentView(R.layout.main);
    	 final Button button = (Button) findViewById(R.id.button1);
    	 final Button loadbutton = (Button) findViewById(R.id.loadbutton);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
		
		//create the cloud viewer
		myCloudView = new CloudViewer(this);
		result = (TextView) findViewById(R.id.result);
		status = (TextView) findViewById(R.id.status);
		levelselector = (Spinner) findViewById(R.id.levelselector);
		
		//gets the level name
	
		
		
		result.setText("Cloud Surfer Running. Press the button to load the level.");
		status.setText("DOWNLOAD THE LEVELS FIRST - takes about 5 seconds");
		
		playGame();
//		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	//            this, R.string.level_prompt, android.R.layout.simple_spinner_item);
	 //   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 //   levelselector.setAdapter(adapter);
		
		//result.setText("No result yett "+testName);
	    CONTEXT = this;
      //  TiltAngles accelss = new TiltAngles();
        
	    button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //   Perform action on click
            	playGame();
            }
	    });
	    
	    //for the game level loading.... of all levels
        loadbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                  //   Perform action on click
                	loadGame();
                }  
        });
        
        
            
     
    	
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
            menu.add(0, MENU_START, 0, R.string.MENU_FIRST);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
	   	// do something with the clicks
	   	case MENU_START:
		// TODO: Define what to do on click
	   		backToMenu();
	   		break;
	   		//playGame();
	   	//	onStop();
	   //	if(item.equals(R.string.MENU_FIRST)){
	   //		playGame();
	   //	}
	 }
	 return true;
    }
 
    
    @Override
    protected void onStop(){
    	
    	Log.d(TAG, "Stopping...in the name of love...");
    	this.onDestroy();
    	super.onStop();
    	((Activity)getContext()).finish();
    }
    
    public void playGame(){
        //request to turn the title OFFFF

    	//make it fullscreen
    	
    	//set cloudviewer as the main panel to view
    	//myCloudView = new cloudviewer(this);
    	setContentView(myCloudView);
    	//myCloudView.loadLevel(this);
    	Log.d(TAG, "View Added");
        }
    
    public void loadGame(){
        //request to turn the title OFFFF

    	//make it fullscreen
    	
    	//set cloudviewer as the main panel to view
    	//myCloudView = new cloudviewer(this);
    	Log.d(TAG, "game loading");
    	
    //	myCloudView.loadLevel(this);
    	//add context back in maybe?
    	
        }
        
    
    
    
    public void backToMenu(){
        //request to turn the title OFFFF

    	//make it fullscreen
    	
    	myCloudView.changeState(0);
    	
		//set cloudviewer as the main panel to view
    	//myCloudView = new cloudviewer(this);
    //	viewgroup.removeAllViews();
    //	setContentView(R.layout.main);
    	//endGame();
    //	Log.d(TAG, "Back to Menu");
        }
    public void endGame(){
        //request to turn the title OFFFF

    	//make it fullscreen
    	//set cloudviewer as the main panel to view
    	//myCloudView = new cloudviewer(this);
    	//myCloudView.setVisibility(INVISIBLE);
    	//Log.d(TAG, "View Added");
    	Log.d(TAG, "Ending...in the name of love...");
    	this.onDestroy();
    	
    	super.onStop();
    	((Activity)getContext()).finish();
        }
        
    
        
    
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported()) {
            AccelerometerManager.startListening(this);
        }
    }
 
    protected void onPause() {
    	Log.d(TAG, "Game Paused... but killing the app in the name of love...");
    	this.onDestroy();
    	if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
    	
    	myCloudView.getThread().setRunning(false);
    	///in future.... make this false thing be the only thing here, then make onResume have it as well :)
    	
    	super.onStop();
    	((Activity)getContext()).finish();
    }
 
    protected void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
       // thread.setRunning(false);
	    ((Activity)getContext()).finish();
        
 
    }
 
 
	private void refreshDisplay() {
		String output = String
				.format("x is: %f / y is: %f / z is: %f", x, y, z);
		result.setText(output);
		//getLevel();
	}
	
	private void updateAccels() {
	//	R.id.x
	///	R.id.y = (int) y;
	//	R.id.z = (int) z;
		
		
		//result.setText(output);
	}
 /*
	private SensorEventListener accelerationListener = new SensorEventListener() {
		@Override
		public void onAccuracyChanged(Sensor sensor, int acc) {
		}
 
		@Override
		public void onSensorChanged(SensorEvent event) {
			
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			x=1;
			y=1;
			z=1;
			myCloudView.updateAccels(x,y,z);
			//refreshDisplay();
		}
 
	};
*/
	@Override
	public void onAccelerationChanged(float x, float y, float z) {
		// TODO Auto-generated method stub
		myCloudView.updateAccels(x,y,z);
	}

	@Override
	public void onShake(float force) {
		// TODO Auto-generated method stub
		
	}

	public static Context getContext() {
		// TODO Auto-generated method stub
		return CONTEXT;
	}
    
public void setStatus(String newString){
		
		status.setText(newString);
		
		
	}

public void setResult(String newString){
	
	result.setText(newString);
	
}

public void setLevelChooser(String newString){
	
	result.setText(newString);
//	 Spinner spinner = (Spinner) findViewById(R.id.spinner);
}

    
}



