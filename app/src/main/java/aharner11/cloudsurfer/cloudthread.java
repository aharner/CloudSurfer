package aharner11.cloudsurfer;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class cloudthread extends Thread{

	
	
		private static final String TAG = cloudthread.class.getSimpleName();
		
		//desired frame rate -fps
		private final static int MAX_FPS = 50;
		private final static int MAX_FRAME_SKIPS = 5;
		private final static int FRAME_PERIOD = 1000/MAX_FPS;
	//use boolean to pause the game's state
	private boolean running;
	private SurfaceHolder surfaceHolder;
	private CloudViewer gamePanel;
	
	
	
	
	public void setRunning(boolean running){
		this.running = running;
	}
	
	
//constructor
	public cloudthread(SurfaceHolder surfaceHolder, CloudViewer gamePanel){
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}
	

	@Override
	public void run(){
		Canvas canvas;
		
		Log.d(TAG, "Starting game loo00p");
		
		long beginTime;
		long timeDiff;
		int sleepTime;
		int framesSkipped;
		
		sleepTime = 0;		
		
		while(running){
			canvas = null;
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder){
					beginTime = System.currentTimeMillis();
					framesSkipped = 0; //resetting the frames skipped
					//update game state
					if(this.gamePanel.getState() == 2){
						this.gamePanel.update();
					}
					//draws the canvas on the panel
					this.gamePanel.onDraw(canvas);
					//this.gamePanel.drawAllObjects(canvas);
					//calculate time for cycle
					timeDiff = System.currentTimeMillis() - beginTime;
					//calculate sleeping time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);
					
					if (sleepTime > 0){
						try{
							//send the thread to sleep for a short period of time, useful for battery saving
							Thread.sleep(sleepTime);
							
						}catch (InterruptedException e) {}
					}
					
					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
						//need to catch up
						//update without rendering to screen
						this.gamePanel.update();
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}
					
					
					
					
				}
			}finally{
				//in case of an exception the surface is not left in an inconsistent state
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
			//tickCount++;
			//update game state
			//update state to screen
			
			}
	//	Log.d(TAG, "Game loop executed " +tickCount + " times");
		
	}
	
}
