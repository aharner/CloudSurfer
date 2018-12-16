package aharner11.cloudsurfer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.location.Location;
import android.util.Log;


public class Player {


	private Bitmap bitmap; //the actual btimap
	private Position loc;
	private Velocity relativeMovee;
	private boolean touched; //if surfer is picked up or being dragged
	private static final double GRAVITY = 1;
	boolean jetLock = false;
	boolean wallLock = false;
	private Velocity vel;
	private Acceleration accel;
	private int counted;
	private Acceleration normal;
	private boolean isCloud;
	private Position originalLoc;
	private TiltAngles getThem;
	private static final String TAG = cloudthread.class.getSimpleName();
	private static final boolean DEBUG = false;
	
	//values for accelerometer in movement after update is called
	float valX;
	float valY;
	static boolean bounceLock;
	
	//String whichSideHit;
	
	
	int ox;
	int oy;
	Bitmap bitmap2;
	int xcoord;
	int ycoord;
	String hittype;
	
	public Player(Bitmap bitmap, Position location, Velocity velo, Acceleration accell){
		this.bitmap = bitmap;
		this.loc =location;
		this.vel = velo;	
		this.accel = accell;
		this.isCloud=false;
		this.relativeMovee = new Velocity(0,0);
		this.originalLoc = location;
		getThem = new TiltAngles();
		Log.d(TAG, "Started player 1");
		//this one used
	}
	
	public Player(Bitmap decodeResource, Position location, Velocity velo, Acceleration accell, Velocity relativeDraw) {
		this.bitmap = bitmap;
		this.loc =location;
		this.vel = velo;	
		this.accel = accell;
		this.relativeMovee = relativeDraw;
		this.originalLoc = location;
		 getThem = new TiltAngles();
		 Log.d(TAG, "Started a player 2");
	}

	public Bitmap getBitmap(){
		return bitmap;
	}
	
	public void setBitmap(Bitmap bitmap) { 
		this.bitmap = bitmap;
	}
	
	public Position getLocation(){
		return loc;
	}
	public int getX(){
		return loc.getX();
	}
	public int getY(){
		return loc.getY();
	}
	
	
	public void setLocation(Position locc){
		this.loc = locc;
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
	public Acceleration getAcceleration(){
		return accel;
	}
	public void setAcceleration(Acceleration newAccel){
		this.accel = newAccel;
	}
//	public void setVelocity(){
	//	return vel;
//	}
	
	public void draw(Canvas canvas, Acceleration accel){
		canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth()/2), loc.getY()-(bitmap.getHeight()), null);
		movement(accel);
	}
	
	
	public void handleActionDown(int eventX, int eventY){
		if(eventX >= (loc.getX()-bitmap.getWidth()/2) && (eventX <= (loc.getX() +bitmap.getWidth()/2))){
			if(eventY >= (loc.getY()-bitmap.getHeight()/2) && (eventY <= (loc.getY() +bitmap.getHeight()/2))){
				//surfer is touched
				setTouched(true);
				}else {
					setTouched(false);
				}
		}else{
			setTouched(false);
		}	
	}
	
	
	public void movement(Acceleration accel){
		/*
		//kinda hacked?
	if(wallLock){
		valX = (float)0;
		valY = (float)0;
		
	}else{
			
	*/
		valX = (float) accel.getX();
		valY = (float) accel.getY();
////////	  Log.d(TAG, "wallLock is" + wallLock);
	//}
	 if (DEBUG) Log.d(TAG, "NEW Accel party!");
	//	this.
	//	this.vel.setXYvelocity(0, 0);
			//vel.setXYvelocity(vel.getVelocityX()+accel.getX(), vel.getVelocityY()+accel.getY());
			///.//////OLD - loc.setX(((int)this.getVelocity().getVelocityX())+this.getX());
		//was loc - this is where loc saved
	////	loc.setY(((int)this.getVelocity().getVelocityY())+this.getY());
	/////	loc.setX((int) (this.getX()+valX));
		
		if(vel.getVelocityY() >= 7){
			vel.setVelocityY(7); 
		}
		relativeMovee.setVelocityY(((int)this.getVelocity().getVelocityY())-valY);
		relativeMovee.setVelocityX((int)(this.getVelocity().getVelocityX()+valX));
		
////////////////		Log.d(TAG, "relatMove = "+ relativeMovee.getVelocityX() + "|" + relativeMovee.getVelocityY());
		//String temppp = "vels: " + relativeMovee.getVelocityX() +"X"+relativeMovee.getVelocityY()+"Y";
		// if (DEBUG) Log.d(TAG, temppp);
		
		//compare old location to new for calculation of clouds movement
	////////	compareOldLocToNew();
		
		
		//calculate gravity first
		if(vel.getVelocityY() > -6){
			vel.setVelocityY(vel.getVelocityY() - accel.getY()); 
		}
		
		//if(vel.getVelocityY() > 9){
	//		vel.setVelocityY(vel.getVelocityY() - accel.getY()); 
	//	}
		//determine accel for x velocity change
		
		//vel.setVelocityX(getThem.());
		
		//change location based on velocity:
	//	this.x = (int) (x + vel.getVelocityX());
	//	this.y = (int) (y - vel.getVelocityY());
		
	}

	//returns true is the position it is given is within its boundaries
	//make take entire list of objects....
	public boolean withinBoundsHuh(Position other){
		int ox = other.getX();
		int oy = other.getY();
		boolean tempbool = false;
		
		if((ox >= (loc.getX()-bitmap.getWidth()/2) && (ox <= (loc.getX() +bitmap.getWidth()/2)))){
			if(oy >= (loc.getY()-bitmap.getHeight()/2) && (oy <= (loc.getY() +bitmap.getHeight()/2))){
				 Log.d("hello", "Hit between objects");
				 	relativeMovee.setVelocityX(0);
					relativeMovee.setVelocityY(0);
				return true;
			}
		}
		
		return tempbool;
		
		
	}
	
	public void reverseX(){
		this.vel.setVelocityX(vel.getVelocityX()*-1);
	}
	public void reverseY(){
		this.vel.setVelocityY(vel.getVelocityY()*-1);
	}

	public Acceleration getNormal() {
		// TODO Auto-generated method stub
		return this.normal;
	}
	public void applyNormal(Acceleration normal2) {
		// TODO Auto-generated method stub
		
		this.vel.setXYvelocity(this.vel.getVelocityX()+normal2.getX(), this.vel.getVelocityY()+normal2.getY());
		relativeMovee.setVelocityX(this.vel.getVelocityX()+normal2.getX());
		relativeMovee.setVelocityY( this.vel.getVelocityY()+normal2.getY());
		//////= new Acceleration(this.accel.getX(),0);
		//else
		//	this.accel = new Acceleration(this.accel.getX(),1);
		
	}
	//use this to set the relativeMove variable for the clouds.....
	public void compareOldLocToNew(){
		relativeMovee.setVelocityX(originalLoc.getX()-loc.getX());
		relativeMovee.setVelocityY(originalLoc.getY()-loc.getY());
	//	loc.setX(originalLoc.getX());
	//	loc.setY(originalLoc.getX());
				
	}
	
	public Velocity getRelativeMove(){
		
		if( relativeMovee.getVelocityY()==-1){
				relativeMovee.setVelocityY(0);
			return this.relativeMovee;
		}
		
		
		return this.relativeMovee;
		
	}

	public boolean collision(Object[] objects) {
		// TODO Auto-generated method stub
		
		boolean hadAHit = false;
		//need to make hte algorithm find ALL collisions and combine effects of them... might cause unforeseen glitches
		int i =0;
		 ox = this.getX();
		 oy = this.getY();
		 
		String whichSideHit = "";
		
		Position positions;
		int x = 0;
	//	int z = objects.length;
		///Log.d("test", "length is= " + objects.length);
		while(x<objects.length){
			bitmap2 = objects[x].getBitmap();
			positions = objects[x].getPosition();
				if(!(objects[x].isHidden())){
						xcoord = positions.getX();
						ycoord = positions.getY();
						
						whichSideHit = isCollisionDetected(getBitmap(), bitmap2, ox,oy,getBitmap().getWidth(), getBitmap().getHeight(), xcoord, ycoord, bitmap2.getWidth(),bitmap2.getHeight());
								
						if(!(whichSideHit.equals(""))){	
							
									hittype = objects[x].getType();	
									objects[x].onPlayerHit(this, whichSideHit);
					/////				Log.d("hello", "Hit a " + hittype + "!");
									//onHitCloud();
									
									hadAHit = true;
							
						}
				}	
			x++;
		}	
		//	locs[i].setX(locs[i].getX()-changeX);
		//	locs[i].setY(locs[i].getY()-changeY);
		
		return hadAHit;
			
		
	}



	//not needed anymore I dont think....
	
//each object type has own hit events that control the player within 'PLAYER"
	private void onHitCloud() {
		// TODO Auto-generated method stub
		//dont move on hit (0,0)
	//	this.setAcceleration(acctwo);
// not working right	applyNormal(normal2);
	//set velocity to 0-X 0-Y instead on a hit
	/////this.vel.setVelocityX(0);
			if(!jetLock){	
					this.vel.setVelocityY(0);
			}
	}
	

	public void jetson() {
		// TODO Auto-generated method stub
		//if (vel.getVelocityY() < 15){
		if(!bounceLock){
			vel.setVelocityY(vel.getVelocityY()+4);
			wallLock = false;
			jetLock = true;
		}
		//wait(100);
		//accel.setY(-8);
		//}
	}
	public void jetsoff() {
		// TODO Auto-generated method stub
		//if (vel.getVelocityY() < 15){
		//vel.setVelocityY(vel.getVelocityY()+4);
		jetLock = false;
		//wait(100);
		//accel.setY(-8);
		//}
	}

	public void hittingWall() {
		// TODO Auto-generated method stub
		
		//do something that makes hte character not go through the walls... kinda hacked
		if(!bounceLock){
			// TODO Auto-generated method stub
			//if (vel.getVelocityY() < 15){
			vel.setVelocityX(0);
			wallLock = true;
		//	jetLock = true;
			//wait(100);
			//accel.setY(-8);
			//}
		}
		
	}
	
	public void hittingGround() {
		// TODO Auto-generated method stub
		
		//do something that makes hte character not go through the walls... kinda hacked
		if(!bounceLock){
			// TODO Auto-generated method stub
			//if (vel.getVelocityY() < 15){
			vel.setVelocityY(0);
		}
		//	jetLock = true;
			//wait(100);
			//accel.setY(-8);
			//}
		
	}

	public void death() {
		// TODO Auto-generated method stub
		
		//DO A DEATH SEQUENCE....
		
		
	}
	
	
	//bitmap pixel perfect collision detection stuff:
	public static String isCollisionDetected(Bitmap sprite1, Bitmap sprite2, int ox2, int oy2, int width, int height,
			int xcoord2, int ycoord2, int width2, int height2){
		
		
	    Rect bounds1 = new Rect(ox2, oy2, ox2+width, oy2+height);
	    Rect bounds2 = new Rect(xcoord2+width/2, ycoord2+height, xcoord2+width2+width/2, ycoord2+height2+height);

	 
	    
	    
	    if( Rect.intersects(bounds1, bounds2) ){
	    //	return true;
	 	  /* Log.d(TAG,"bottom:"+ bounds1.left + ","
	   	    		+ bounds1.top + ","
	   	    		+ bounds1.right + ","
	   	    		+ bounds1.bottom + ",");
	   	    
	    	
	   	    Log.d(TAG,"bottom2:"+ bounds2.left + ","
	   	    		+ bounds2.top + ","
	   	    		+ bounds2.right + ","
	   	    		+ bounds2.bottom + ",");
	    	*/
	    	
	    	
	        Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
	        for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
	            for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
	                int sprite1Pixel = getBitmapPixel(sprite1, i, j, ox2, oy2);
	                int sprite2Pixel = getBitmapPixel(sprite2, i, j, xcoord2+width/2, ycoord2+height); 
	                if( isFilled(sprite1Pixel) && isFilled(sprite2Pixel)) {
	                	//add a way to tell which side is being hit on the object... so that if player hits his
	                	//head on a 'ground' he doesn't stick to it and go through it... and ricochets
	                	//TODO
	                	//return which side based on percents of surfer i/j is at right now?
	                	
	                	
	                	//definitely a top or bottom collision
	                	if((collisionBounds.left==ox2)&&((collisionBounds.right==ox2+width))){
	                		if(collisionBounds.top<oy2 +(height/2)){
		                		Log.d(TAG, "top is hitting");
		                		return "top";
		                	}
		                	if(collisionBounds.bottom>oy2 +(height/2)){
		                		Log.d(TAG, "bottom hitting" + i +"," +collisionBounds.left+","+ collisionBounds.right+","+collisionBounds.top+","+ collisionBounds.bottom+"," + j +"widthis"+(3*width)/4);
		                		return "bottom";
		                	}
	                	}
	                	//definitely a left or right collision
	                	else if((collisionBounds.top==oy2)&&((collisionBounds.bottom==oy2+height))){
	                		if(collisionBounds.left<ox2 +(width/2)){
		                		Log.d(TAG, "left is hitting");
		                		return "left";
		                	}
		                	if(collisionBounds.right>ox2 +(width/2)){
		                		Log.d(TAG, "right hitting" + i +"," +collisionBounds.left+","+ collisionBounds.right+","+collisionBounds.top+","+ collisionBounds.bottom+"," + j +"widthis"+(3*width)/4);
		                		return "right";
		                	}
	                	}
	                	
	                	
	                	else if((collisionBounds.left<215)&&((collisionBounds.right!=ox2+width))){
	                		Log.d(TAG, "left side is hitting");
	                		return "left";
	                	}
	                	else if((collisionBounds.right>255)&&((collisionBounds.left!=200))){
	                		Log.d(TAG, "right side hitting");
	                		return "right";
	                	}
	                	
	                	else{
	                		Log.d(TAG, "wellllp, that sucks...somehow got wedged in it");
	                		return "middle";
	                	}
	                    
	                 //    200, 275, 370, 375
	                }
	            }
	        }
	       
	    }
	    bounceLock = false;
	    return "";
	}

	private static int getBitmapPixel(Bitmap sprite, int i, int j, int xcoord, int ycoord) {
		 return sprite.getPixel(i-xcoord, j-ycoord);
	
	}

	private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
	    int left = (int) Math.max(rect1.left, rect2.left);
	    int top = (int) Math.max(rect1.top, rect2.top);
	    int right = (int) Math.min(rect1.right, rect2.right);
	    int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
	    return new Rect(left, top, right, bottom);
	}

	private static boolean isFilled(int pixel) {
	    return pixel != Color.TRANSPARENT;
	}
	
	
	
	private boolean isAHit(int ox, int oy, int width, int height,
			int xcoord, int ycoord, int width2, int height2) {
		// TODO Auto-generated method stub
		
		
		if((ox >= xcoord) && (ox <= (xcoord + width2))){
			if((oy >= ycoord) && (oy <= (ycoord + height2))){
				
				return true;
			}
		}
		
		
		
		
		return false;
	}

	

	public void bounceLeft() {
		//vel.setVelocityX(-4);
		//this.vel.setVelocityX((vel.getVelocityX()*-1)-10);
		relativeMovee.setVelocityX(3);
	//	loc.setX(loc.getX()-5);
		//bounceLock = true;
	}
	public void bounceRight() {
		//relativeMovee.setVelocityY(((int)this.getVelocity().getVelocityY())-valY);
		//relativeMovee.setVelocityX(5);
		//this.vel.setVelocityX((vel.getVelocityX()*-1)-10);
		relativeMovee.setVelocityX(-3);
	//	loc.setX(loc.getX()+5);
		//vel.setVelocityX(4);
		//bounceLock = true;
	}

	public void bounceDown() {
		// TODO Auto-generated method stub
		relativeMovee.setVelocityY(-3);
		//loc.setY(loc.getY()-5);
		//bounceLock = true;
	}
	
}
