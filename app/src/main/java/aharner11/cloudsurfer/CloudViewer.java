package aharner11.cloudsurfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

public class CloudViewer extends SurfaceView implements 
	SurfaceHolder.Callback{

	private static final String TAG = cloudthread.class.getSimpleName();
	private cloudthread thread;
	private Player surfer;
	//private Clouds clouds;
	private Object[] objects;
	private TextView status;
	//init bitmaps
	private Bitmap bm;
	public Bitmap pressMenuButton;
	public Bitmap internetErrorBitmap;
	public Bitmap cloudsurferlogo;
	
	//menu images:
	public Bitmap cloudlogo;
	public Bitmap tutorialimage;
	public Bitmap picklevel;
	public Bitmap aehlogo;
	
	
	
	public Bitmap WINBIT;
	public int yyy;
	public int neg;
	public int yyy2;
	public int neg2;
	public int yyy3;
	public int neg3;
	
	
	Hashtable<String, Bitmap> bitmaps;
	private static final double GRAVITY = 1;
	private static final double JETS = -2;
	private static final boolean DEBUG = false;
	
	private boolean LOADING; 
	private Acceleration cloudfirm;
	private Acceleration jetAccel;
	public float xx, yy, zz;
	public float updown;
	public int count = 0;
	public String testName;
	public Position startingLocation;
	int PreviousState;
	
	public JSONArray jArray;
    public JSONObject json_data;
    public JSONArray jArrayObj;
    public JSONObject json_dataObj;
    
    public int addedscore;
    public int scoreToWin;
    public boolean FIRST;
    //memory reuse
    Object[] retarray;
    Object newlocation;
    
    
    //state machine:
    
	public int state;
	
	//dynamically load the image from my server
	//will eventually allow people to create and upload their own players
	String image_URL= "http://andrewharner.com/surfer/images/cloudsurfer1.png";
	//eventually make the player's image into a set of 5 sprites to give it animation
	public Position[] cloudPos;
	public Acceleration gravity;
	
	public CloudViewer(Context context) {
		super(context);
		state = 0;
		
		PreviousState = -1;
		LOADING = false; 
		gravity = new Acceleration(0,GRAVITY);
		cloudfirm = new Acceleration(0,-1*GRAVITY);
		jetAccel = new Acceleration(0,-1*JETS);
		bitmaps = new Hashtable<String, Bitmap>();
		//set gravity
		updown = (float) GRAVITY;
		getHolder().addCallback(this);
		FIRST = true;
		scoreToWin = 25;
		yyy = 0;
		neg = 1;
		yyy2 = 0;
		neg2 = 1;
		yyy3 = 0;
		neg3 = 1;
		
		//load the images for the menu
		
		internetErrorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.active);
		pressMenuButton = BitmapFactory.decodeResource(getResources(), R.drawable.pushplay);
		cloudsurferlogo = BitmapFactory.decodeResource(getResources(), R.drawable.cloudlogo);
		WINBIT = BitmapFactory.decodeResource(getResources(), R.drawable.youwon);
		
		cloudlogo = BitmapFactory.decodeResource(getResources(), R.drawable.cloudsurferlogo);
		tutorialimage = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial);
		picklevel = BitmapFactory.decodeResource(getResources(), R.drawable.picklevel);
		aehlogo = BitmapFactory.decodeResource(getResources(), R.drawable.aeh);
		
		//status = (TextView) findViewById(R.id.status);
	//	context.setStatus("loading...");
		
		
	//	Bitmap bitmap, Position[] locations, Position relativemove, Acceleration norm
		
		thread = new cloudthread(getHolder(), this);
		
		//makes the cloud view have focus.. to handle events later
		setFocusable(true);
	}
	
	private Bitmap LoadImage(String URL, BitmapFactory.Options options)
	   {       
	    Bitmap bitmap = null;
	    InputStream in = null;       
	       try {
	           in = OpenHttpConnection(URL);
	           bitmap = BitmapFactory.decodeStream(in, null, options);
	           in.close();
	       } catch (IOException e1) {
	       }
	       return bitmap;               
	   }
	
	private InputStream OpenHttpConnection(String strURL) throws IOException{
		 InputStream inputStream = null;
		 URL url = new URL(strURL);
		 URLConnection conn = url.openConnection();

		 try{
		  HttpURLConnection httpConn = (HttpURLConnection)conn;
		  httpConn.setRequestMethod("GET");
		  httpConn.connect();

		  if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		   inputStream = httpConn.getInputStream();
		  }
		 }
		 catch (Exception ex)
		 {
		 }
		 return inputStream;
		}

		
	
	@Override 
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
		//TODO
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder){
		//TODO
		thread.setRunning(true);
		thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		Log.d(TAG, "Surface is being destroyed");

		boolean retry = true;
		while (retry){
			try{
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				}
		}//keeps trying to shutdown teh thread
	}
	
	
	
	@Override
	 public boolean onTouchEvent(MotionEvent event) {
	  
		//menu state:
			if(state == 0){
				 ConnectivityManager cm = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
			     NetworkInfo netInfo = cm.getActiveNetworkInfo();
			     
			     if (netInfo != null && netInfo.isConnected()) {
								 	
								
								if (event.getAction() == MotionEvent.ACTION_DOWN) {
										
										if(!LOADING){				
											loadLevel();
											this.changeState(2);
										}
									if (event.getY() > getHeight() - 50) {
									   	thread.setRunning(false);
								    ((Activity)getContext()).finish();
								   }
									
								}
			     }
			     else{
			    	 state = 9;
			     }
					
			
			
		}
		
		//win state:

	if(state == 10){
	
	
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//reset score!
				this.addedscore = 0;
				this.changeState(0);
			}
			
	   
		
	}
	
	
	

		
		
		
		
		//game state:
	
		if(state == 2){
			
			
			
				   if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
				   // delegating event handling to the droid
					  surfer.handleActionDown((int)event.getX(), (int)event.getY());
			
				   // check if in the lower part of the screen we exit
				   if (event.getY() > getHeight() - 50) {
					   	
					   
					   state = 0;
					   //reset scoring as well
					   this.addedscore = 0;
				    
				   } else {
					   if (DEBUG)  Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
				    ;
				   }
				  } if (event.getAction() == MotionEvent.ACTION_MOVE) {
					  surfer.jetson();   
					  updown = (float) JETS;			
					  if (DEBUG)  Log.d(TAG, "I got touched");
				  } if (event.getAction() == MotionEvent.ACTION_UP) {
				   // touch was released
					   surfer.setTouched(false);
					   updown = (float) GRAVITY;
					   surfer.jetsoff();
				  }
	  
		}
		
		
		//no internet state
		if(state == 9){
			 ConnectivityManager cm = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		     NetworkInfo netInfo = cm.getActiveNetworkInfo();
		     
		     if (netInfo != null && netInfo.isConnected()) {
							 	
				this.changeState(0);
					
		     }
		     else{
		    	 state = 9;
		     }
				
		
		
	}
	  
	  
	  
	  return true;
	 }
	
	int lostCollision = 1;
	
	
	
	public boolean update(){
		
		
				
		
		if(state == 2){
			//Log.d("tests","SHOULD NOT");
					//check for collisions with walls.....
					//not used anymore, but will be changed to make player die at a certain point
					
			//Log.d(TAG, "score is:" + addedscore + "| collects :"+ scoreToWin);
			if(scoreToWin > 0){
				
				if (addedscore >= scoreToWin){
					state = 10;
				}
			}
			
			
			
			
				if (surfer.getX() > getWidth()){
						surfer.reverseX();
					} else if (surfer.getX() < 0){
							surfer.reverseX();
					}
					if (surfer.getY() > getHeight()){
						surfer.reverseY();
					} else if (surfer.getY() < 0){
							surfer.reverseY();
					}
					//check for collison with clouds
					//need to support object array
					 if (DEBUG) Log.d(TAG, "succkckks11111");
					
					if(surfer.collision(objects)){
						//Velocity secondtemp = new Velocity(0,0);
						 if (DEBUG) Log.d(TAG, "succckkkkks22222");
						Velocity secondtemp = surfer.getVelocity();
						//dont move on hit (0,0)
						//clouds.updateRelativeLocations(secondtemp);
						updateAllObjects(secondtemp);
					}else{			
						Velocity temp = surfer.getRelativeMove();
						//otherwise, move based on player
						//clouds.updateRelativeLocations(temp);
						updateAllObjects(temp);
					}
		}
		
		return true;
		
	}
	
	

	public void updateAllObjects(Velocity velnum){
		int j = objects.length;
		int i = 0;
		while(i<j){
				objects[i].updateRelativeLocations(surfer);
				//objects[i].movement();
				i++;
		}
		
	}
	
	public void drawAllObjects(Canvas canvas){
		int j = objects.length;
		int i = 0;
		addedscore = 0;
		scoreToWin = 0;
		while(i<j){
				objects[i].draw(canvas);
				addedscore = addedscore + objects[i].getScore();
				scoreToWin = scoreToWin + objects[i].getPointValue();
				
				i++;
		}
		
		
		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas){
		//if state is -1 we know that it is the first time through the game...
		//Log.d("tests","state="+ state);
		if(state==0){
			//Log.d("tests","prevstate="+ PreviousState);
			//if(PreviousState != 0){
				//Log.d("tests","wTF=");
				this.drawMainMenu(canvas);
				//Log.d("tests","FUCK THIS LOGGER7");
				PreviousState = 0;
				
			//}
			return;
		}
		else if(state==10){
					
					this.drawWin(canvas);
					PreviousState = 10;
					
				}
		else if(state==9){
			
			this.drawInternetError(canvas);
			PreviousState = 9;
			
		}
		
		else if(state == 2){
			//Log.d("tests","SHOULDNT BE OPEN");
					//first update all the locations
					update();
					//now redraw everything
					canvas.drawColor(Color.rgb(135,206,250));
					//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button1), 0, getHeight()-150, null);
					Paint paint = new Paint(); 
					
					paint.setColor(Color.GRAY); 
					paint.setTextSize(35); 
					canvas.drawText("Score: " + addedscore, 15, 35, paint);
					
					paint.setColor(Color.BLACK); 
					paint.setTextSize(20); 
					//write the accelerations to the game screen for testing purposed
					//////canvas.drawText("Accel: " + xx +"x "  + updown+"y", getWidth()-250, getHeight()-150, paint);
					
					
					canvas.drawText("to return to Menu, swipe down at very bottom", 20, getHeight()-15, paint);
					surfer.draw(canvas, new Acceleration(xx,updown));
					
					drawAllObjects(canvas);
					PreviousState = 2;
					
		}
	}


	public void updateAccels(float x, float y, float z) {
		// TODO Auto-generated method stub
		xx= x;
		yy=y;
		zz=z;
	}
	
	
	 public void getLevel() throws IllegalStateException, IOException, JSONException{
			String result = "";
			InputStream is = null;
			testName = "old";
			getContext();
			
	
		 
			//data to send
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("year","1980"));
			Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS");
			
			try{
			//http post send
		
					HttpClient httpclient = new DefaultHttpClient();
					Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS222");
					HttpPost httppost = new HttpPost("http://andrewharner.com/surfer/androidPullLevel.php");
					Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS333");
					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS444");
					HttpResponse response = httpclient.execute(httppost);
					Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS555");
					HttpEntity entity = response.getEntity();
					Log.d(TAG, "ATTEMPING TO DOWNLOAD LEVELS666");
					is = entity.getContent();
		
			
			//convert response to a string
		
				    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			        StringBuilder sb = new StringBuilder();
			        String line = null;
			        while ((line = reader.readLine()) != null) {
			                sb.append(line + "\n");
			        }
			        is.close();
			 
			        result=sb.toString();
			
			        //show no network error
			        if(result == null){
			        	Log.d(TAG, "NO NETWORKS AVAILABLE");
			        	state = 9;
			        }
			        
			        jArray = new JSONArray(result);
			       //hacked
			        json_data = jArray.getJSONObject(1);
			}catch(Exception e){
				Log.d(TAG, "NO NETWORKS AVAILABLE3");
				state = 9;
				
			}
			        
			        /*
			         * 
			         * 
			        //use this for parsing out each level into an array
			        for(int i=0;i<jArray.length();i++){
			                JSONObject json_data = jArray.getJSONObject(i);			         
			                
			                if (DEBUG) Log.d("log_tag","lvlID: "+json_data.getInt("lvlID")+
			                		", lvlName: "+json_data.getString("lvlName")+
			                		", CreatedBy: "+json_data.getString("CreatedBy")+
			                		", DateCreated: "+json_data.getString("DateCreated")+
			                		", Character: "+json_data.getString("Character")+
			                		", Locations: "+json_data.getString("Locations")+
			                		", StartingLocationX: "+json_data.getString("StartingLocationX")+
			                		", StartingLocationY: "+json_data.getString("StartingLocationY")
			                );
			        }*/
			
			
	   
	    }

	public void loadLevel() {
		
						//context.setResult("Levels Loading...");
						LOADING = true; 
					//	Log.d(TAG, "onLOAD: starting service");
					//    getContext().startService(new Intent());
						//Body of your click handler/
				//		Thread trd = new Thread(new Runnable(){
				//		  @Override
				//		  public void run(){
						    //code to do the HTTP request
						
						
						//this updates the 'testName' field with the string for the level we loaded
						try {
							getLevel();
						} catch (IllegalStateException e1) {
							// TODO Auto-generated catch block
							
							e1.printStackTrace();
							state = 9;
							return;
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							state = 9;
							e1.printStackTrace();
							return;
						} catch (JSONException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
							state = 9;
							//use the following function to create new data to account for the internet not being there so that the rest of it doesn't crash
							//trigger 'return' ??
							falseDataInitiate();
							return;
						}
						
						
						
						 //i is the level number picked to load
				      
				        	//where I set the variables and things for each level
						if (!(json_data.isNull("Locations"))){
						
				        	try {
								testName = json_data.getString("Locations");
								image_URL = json_data.getString("Character");
								startingLocation = 
									new Position(
											Integer.parseInt(json_data.getString("StartingLocationX")),
											Integer.parseInt(json_data.getString("StartingLocationY")));
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								
								e.printStackTrace();
								state = 9;
								return;
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								
								e.printStackTrace();
								state = 9;
								return;
							}
				        
						}
						
						
						
						BitmapFactory.Options bmOptions;
					    bmOptions = new BitmapFactory.Options();
					    bmOptions.inSampleSize = 1;
					    bm = LoadImage(image_URL, bmOptions);
					 // bmImage.setImageBitmap(bm);
						//BitmapFactory.decodeResource(getResources(), R.drawable.cloudsurfer1)
						surfer = new Player(bm, startingLocation, new Velocity(0,0), gravity);
						// clouds = new Clouds(BitmapFactory.decodeResource(getResources(), R.drawable.cloudbigger), cloudPos, cloudfirm);
								   
						
						//testName has a JSON structure loaded into it with a list of each object in the 
						//format:   {object1},{object2},....,{objectN}
					    objects = getObjectsFromString(testName);
					    
					
					    Log.d(TAG, "onLOAD: stopping srvice");
					   // getContext().stopService(new Intent());
						//done adding all the objects i
					    LOADING = false; 
						//context.setStatus("Levels Loaded Successfully, choose one below...");
						//Toast.makeText(context.getApplicationContext(), "Levels Loaded...", 1000).show();
						
				//		  }
				//		});
						
				//		trd.run();
					    Log.d(TAG, "onLOAD: stopping srdfsdfdfdfsvice");
					    
	}
	
	
	
	
	private void falseDataInitiate() {
		// TODO Auto-generated method stub
		
		//use a fake string that is teh JSON response
		
		
		
		
	}

	private Object[] getObjectsFromString(String testName2) {
		//TODO
		
		
		int start, start2, start3, end, end2, end3;
		String xval, yval;
		String imgurlval = "";
		String movement = "";
		String itemtype = "";
		int i = 0;
		
		try{
	        jArrayObj = new JSONArray("["+testName2+"]");
	        
	        
		}catch(JSONException e){
	        Log.e("log_tag", "Error TO SJSOSLDSAD data "+e.toString());
		}
		
		
		int u =0;
		retarray = new Object[jArrayObj.length()];
		
		while(u<jArrayObj.length()){
			
			
				
				try {
					json_dataObj = jArrayObj.getJSONObject(u);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		
				Position temp3 = new Position(0,0);
				try {
					temp3 = new Position(
								Integer.parseInt(json_dataObj.getString("xpos")),
								Integer.parseInt(json_dataObj.getString("ypos")));
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				try {
					imgurlval = json_dataObj.getString("imgurl");
					movement = json_dataObj.getString("movement");
					itemtype = json_dataObj.getString("type");
					Log.d("test", movement);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//store bitmaps when they are loaded, then destroy later
				Bitmap bitmaploaded; 
				
				
				if(bitmaps.containsKey(imgurlval)){
					bitmaploaded = bitmaps.get(imgurlval);
					
				}else{
					BitmapFactory.Options bmOptions;
				    bmOptions = new BitmapFactory.Options();
				    bmOptions.inSampleSize = 1;
			
				    String objects_image_URL = imgurlval;
				    bitmaploaded = LoadImage(objects_image_URL, bmOptions);
				    bitmaps.put(imgurlval, bitmaploaded);
				    //Log.d("test","inserted " + imgurlval + " into the hash!");
				}
				
				
		
			    
			    
			    newlocation= new Object("nameee", itemtype, bitmaploaded, temp3, new Acceleration(0,0), new MotionPath(movement));
			    //Log.d("test", "getURL:"+newlocation.getX());
			    
			    retarray[u] = newlocation;
			    //Log.d("test", "getX:"+retarray[u].getX());
			    u++;
		}
		
		Log.d("test","Level Loaded!");
		return retarray;
	}
	
	
	public void changeState(int nextState){
		
	    Log.d(TAG, "onLOAD: canged statee");
		state = nextState;
		
	}
	
	
	

	public int countOccurrencesOf(String word, Character ch) {
	
		    int pos = word.indexOf(ch);  
		    return pos == -1 ? 0 : 1 + countOccurrencesOf(word.substring(pos+1),ch);  
		    
	}
	    
	private void drawMainMenu(Canvas canvas) {
		// TODO Auto-generated method stub
		
		// Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pushplay);
		// canvas.drawColor(Color.BLACK); 
		 canvas.drawColor(Color.rgb(169, 189, 233)); //http://drpeterjones.com/colorcalc/
		
	//	 cloudlogo;
		 //tutorialimage;
	//	picklevel;
		 
		//  canvas.drawBitmap(pressMenuButton, (getWidth()/2)-pressMenuButton.getWidth()/2, (getHeight()/2)-pressMenuButton.getHeight()/2, null);
	      canvas.drawBitmap(cloudlogo, -32, -10, null);
	      
	   
		  canvas.drawBitmap(tutorialimage, yyy, getHeight()/5, null);
		  //(getHeight()/2)-tutorialimage.getHeight()/2
	      
		  yyy = yyy+ (neg*3);
	      if(yyy > getWidth()-tutorialimage.getWidth()){
	    	  	neg = -1;
	    	  	yyy = getWidth() -(tutorialimage.getWidth()) -1;
	      }
	      if(yyy < -10){
	    	  	neg = 1;
	    	  	yyy = -8;
	      }
	      
	      canvas.drawBitmap(picklevel, yyy2, getHeight()/2, null);
	      
	      yyy2 = yyy2 + (neg2*2);
	      if(yyy2 > getWidth()-picklevel.getWidth()){
	    	  	neg2 = -1;
	    	  	yyy2 = getWidth() -(picklevel.getWidth()) -1;
	      }
	      if(yyy2 < -15){
	    	  	neg2 = 1;
	    	  	yyy2 = -14;
	      }
	      
	      canvas.drawBitmap(aehlogo, getWidth()-150, getHeight()-150, null);
	      
	      
	      
		
	      
        // cloudsurferlogo
         
			//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button1), 0, getHeight()-150, null);
			Paint paint = new Paint(); 
			paint.setColor(Color.WHITE); 
			paint.setTextSize(50); 
			//write the accelerations to the game screen for testing purposed
			canvas.drawText("Swipe Down to Start", 15, getHeight()-200, paint);
			paint.setTextSize(20); 
			canvas.drawText("to exit game swipe down at the very bottom, or press back", 20, getHeight()-15, paint);
			paint.setColor(Color.GREEN); 
			canvas.drawText("sometimes a 'not responding' msg appears", 20, getHeight()-100, paint);
			canvas.drawText("ignore it please -- click WAIT :)", 20, getHeight()-60, paint);
			canvas.drawText("it is caused by a slow connection to the database", 20, getHeight()-80, paint);
         
			
			//addlevels
         
		
	}	
	
	private void drawInternetError(Canvas canvas) {
		// TODO Auto-generated method stub
		
		 
         canvas.drawBitmap(internetErrorBitmap, (getWidth()/2)-internetErrorBitmap.getWidth()/2, (getHeight()/2)-internetErrorBitmap.getHeight()/2, null);
		
	}
	
	private void drawWin(Canvas canvas) {
		// TODO Auto-generated method stub
		
		// Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pushplay);
		 canvas.drawColor(Color.BLACK); 
		// canvas.drawColor(Color.rgb(170, 215, 170)); //http://drpeterjones.com/colorcalc/
		
		 
		  canvas.drawBitmap(WINBIT, yyy, (getHeight()/2)-WINBIT.getHeight()/2, null);
	      
		  yyy = yyy+ (neg*4);
	      if(yyy > getWidth()-WINBIT.getHeight()){
	    	  	neg = -1;
	    	  	yyy = getWidth() -(WINBIT.getHeight()) -1;
	      }
	      if(yyy < 5){
	    	  	neg = 1;
	    	  	yyy = 6;
	      }
	      
		//  canvas.drawBitmap(pressMenuButton, (getWidth()/2)-pressMenuButton.getWidth()/2, (getHeight()/2)-pressMenuButton.getHeight()/2, null);
	      canvas.drawBitmap(cloudsurferlogo, (getWidth()/2)-cloudsurferlogo.getWidth()/2, 10, null);
        // cloudsurferlogo
         
			//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button1), 0, getHeight()-150, null);
			Paint paint = new Paint(); 
			paint.setColor(Color.WHITE); 
			paint.setTextSize(50); 
			//write the accelerations to the game screen for testing purposed
			canvas.drawText("Swipe Down to return to Menu", 15, getHeight()-200, paint);
			//paint.setTextSize(20); 
			//canvas.drawText("to exit game swipe down at the very bottom, or press back", 20, getHeight()-15, paint);
			//paint.setColor(Color.GREEN); 
			//canvas.drawText("sometimes a 'not responding' msg appears", 20, getHeight()-100, paint);
			//canvas.drawText("ignore it please -- click WAIT :)", 20, getHeight()-60, paint);
			//canvas.drawText("it is caused by a slow connection to the database", 20, getHeight()-80, paint);
         
			
			//addlevels
         
		
	}
	public cloudthread getThread(){
		
		return thread;
	}

	public int getState() {
		// TODO Auto-generated method stub
		return state;
	}	
		

}
