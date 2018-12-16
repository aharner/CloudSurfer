package aharner11.cloudsurfer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CloudBasic {

	private Bitmap bitmap; //the actual btimap
	private int x;
	private int y;
	private boolean touched; //if surfer is picked up or being dragged
	private Velocity vel;
	private TiltAngles accels;
	
	public CloudBasic(Bitmap bitmap, int x, int y){
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.vel = new Velocity();
		this.accels = new TiltAngles();			
	}
	
	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) { 
		this.bitmap = bitmap;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	public boolean isTouched(){
		return touched;
	}
	
	public void setTouched(boolean touched){
		this.touched = touched;
	}
	
	public Velocity getVelocity(){
		return vel;
	}
	
	public void draw(Canvas canvas, float gravity, Position move){
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/2), y-(bitmap.getHeight()), null);
		movement(gravity, move);
	}
	
	
	public void handleActionDown(int eventX, int eventY){
		if(eventX >= (x-bitmap.getWidth()/2) && (eventX <= (x +bitmap.getWidth()/2))){
			if(eventY >= (y-bitmap.getHeight()/2) && (eventY <= (y +bitmap.getHeight()/2))){
				//surfer is touched
				setTouched(true);
		}else {
			setTouched(false);
		}
		}else{
			setTouched(false);
		}	
	}
	
	public void movement(float gravity, Position move){
	
		//calculate gravity first
		if(vel.getVelocityY() > -6){
			vel.setVelocityY(vel.getVelocityY()+gravity);
		}
		//determine accel for x velocity change
		
	//	vel.setVelocityX(accels.getXaccel());
		
		//change location based on velocity:
		this.x = (int) (x + vel.getVelocityX());
		this.y = (int) (y - vel.getVelocityY());
		this.x = (int) (x + move.getX());
		this.y = (int) (y - move.getY());
		
		
		
	}
	
	public void reverseX(){
		this.vel.setVelocityX(vel.getVelocityX()*-1);
	}
	public void reverseY(){
		this.vel.setVelocityY(vel.getVelocityY()*-1);
	}

	public void jets() {
		// TODO Auto-generated method stub
		if (vel.getVelocityY() < 15){
		vel.setVelocityY(vel.getVelocityY()+4);
		}
	}
}
