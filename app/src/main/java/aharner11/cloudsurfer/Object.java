package aharner11.cloudsurfer;

	import android.graphics.Bitmap;
	import android.graphics.Canvas;
	import android.location.Location;
	import android.util.Log;


	public class Object {

		private String name; //name of the class of object
		private Bitmap bitmap; //the actual btimap
		private Position loc;
		private String type;
		private boolean touched; //if surfer is picked up or being dragged
		
		//eventually add:
		//defined motions... array of them
		
	//	private Position relativeMove;
		private Position locs; //array of locations of all the clouds of this type (norm)
		private Acceleration accel;
		private Acceleration normal;
		private MotionPath motions;
		double changeX;
		double changeY;
		boolean hidden;
		int score;
		//private boolean isCloud;
		
		public Object(String name, String type, Bitmap bitmap, Position locations,  Acceleration norm, MotionPath motion){
			
			
			this.bitmap = bitmap;
			 changeX =0.0;
			 changeY=0.0;
			//here is where to load the bitmap and set it...
			this.locs = locations;
			this.normal = norm;
			this.motions = motion;
			this.type = type;
			score = 0;
			hidden = false;
			
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
			 Log.d("test", "onLOAD: got X service");
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
		
		
		public Acceleration getAcceleration(){
			return accel;
		}
		public void setAcceleration(Acceleration newAccel){
			this.accel = newAccel;
		}
//		public void setVelocity(){
		//	return vel;
//		}
		
		
		public void updateRelativeLocations(Player secondtemp){
			
			
			
			changeX = secondtemp.getRelativeMove().getVelocityX();
			changeY = secondtemp.getRelativeMove().getVelocityY();
			
				locs.setX((int) (locs.getX()+changeX));
				locs.setY((int) (locs.getY()+changeY));
				
		
			//should move all clouds by the amount relative to the surfer player
			
		}
		
		public void draw(Canvas canvas){
			if(!hidden){
				canvas.drawBitmap(bitmap, locs.getX(), locs.getY(), null);
				}
			}
			
			//updateRelativeLocations();
		//	canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth()/2), loc.getY()-(bitmap.getHeight()), null);

	
		
		/*
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
		*/
		
		public void movement(){
			
			
			locs.setX((int) (locs.getX()+motions.nextMovement()));
				//only goes left/right atm
				//locs.setY((int) (locs.getY()+changeY));
			
			
			
			
			/*
			if(isCloud){
				//vel.setXYvelocity(vel.getVelocityX()+accel.getX(), vel.getVelocityY()+accel.getY());
				loc.setX((int) (this.getX()-accel.getX()));
				loc.setY((int) (this.getY()-accel.getY()));
			}
			
			
			/*
			//calculate gravity first
			if(vel.getVelocityY() > -6){
				vel.setVelocityY(vel.getVelocityY()-gravity);
			}
			//determine accel for x velocity change
			
			vel.setVelocityX(accels.getXaccel());
			
			//change location based on velocity:
			this.x = (int) (x + vel.getVelocityX());
			this.y = (int) (y - vel.getVelocityY());
			
			*/
		}
		//returns true is the position it is given is within its boundaries
		/*
		  public boolean withinBoundsHuh(Position other){
		 
			int ox = other.getX();
			int oy = other.getY();
			boolean tempbool = false;
			
			if((ox >= (loc.getX()-bitmap.getWidth()/2) && (ox <= (loc.getX() +bitmap.getWidth()/2)))){
				if(oy >= (loc.getY()-bitmap.getHeight()/2) && (oy <= (loc.getY() +bitmap.getHeight()/2))){
					Log.d("hello", "Hit between cloudss");
					return true;
				}
			}
			
			return tempbool;
			
			
		}
		*/
		public void reverseX(){
		//	this.vel.setVelocityX(vel.getVelocityX()*-1);
		}
		public void reverseY(){
		//	this.vel.setVelocityY(vel.getVelocityY()*-1);
		}

		public void jets() {
			
		}
		public Acceleration getNormal() {
			// TODO Auto-generated method stub
			return this.normal;
		}
		
		public Position getPosition(){
			return this.locs;
			
		}
		
		
		public void changeLocation(Position newlocation){
			 //   Position[] addedArray = new Position[locs.length + 1];
			  //  System.arraycopy(locs, 0, addedArray, 0, locs.length);
			 //   addedArray[locs.length] = newlocation;
			    locs = newlocation;
		}
		
		
		
		public void applyNormal(Acceleration normal2) {
			// TODO Auto-generated method stub
	//////		this.accel = new Acceleration(normal2.getX()+this.accel.getX(),normal2.getY()+this.accel.getY());
			
			
		}

		public String getType() {
			// TODO Auto-generated method stub
			
			return this.type;
			
		}
		
		public int getScore() {
			// TODO Auto-generated method stub
			
			return this.score;
			
		}

		public void onPlayerHit(Player thePlayer, String whichPart) {
			// TODO Auto-generated method stub
			
			//Log.d("test", "COLLECTED-"+type);
			if(!hidden){
				//Log.d("test", "hidasdadadsaddw -" + this.type);
				if(type.equals("collectable")){
					//Log.d("test", "hidign object now -" + this.type);
					this.hideObject();
					score = 1;	
				}	
			}
			
			if(!hidden){
				//Log.d("test", "hidasdadadsaddw -" + this.type);
				if(type.equals("wall")){
					//Log.d("test", "hidign object now -" + this.type);
					if (whichPart.equals("right")){
						thePlayer.bounceLeft();
					}
					else if (whichPart.equals("left")){
						thePlayer.bounceRight();
					}
					
					
					thePlayer.hittingWall();
				}	
			}
			
			//land on platform, or fall off it...
			if(!hidden){
				//Log.d("test", "hidasdadadsaddw -" + this.type);
				if(type.equals("platform")){
					//Log.d("test", "hidign object now -" + this.type);
					if (whichPart.equals("right")){
						thePlayer.bounceLeft();
					}
					else if (whichPart.equals("left")){
						thePlayer.bounceRight();
					}
					else if (whichPart.equals("top")){
						thePlayer.bounceDown();
					}
					else if (whichPart.equals("bottom")){
						thePlayer.hittingGround();
					}
					
					
					thePlayer.hittingWall();
				}	
			}
			
			if(!hidden){
				//Log.d("test", "hidasdadadsaddw -" + this.type);
				if(type.equals("ground")){
					//Log.d("test", "hidign object now -" + this.type);
					if (whichPart.equals("top")){
						thePlayer.bounceDown();
					}
					if (whichPart.equals("middle")){
						thePlayer.bounceDown();
					}
					else{
					thePlayer.hittingGround();
					}
				}	
			}
			
			if(type.equals("death")){
					//Log.d("test", "hidign object now -" + this.type);
					thePlayer.death();
			}
			
			
			return;
			
			
		}

		public void hideObject() {
			// TODO Auto-generated method stub
			this.hidden = true;
		}
		
		public boolean isHidden(){
			
			
			return hidden;
		}

		public int getPointValue() {
			// TODO Auto-generated method stub
			
			if(this.type.equalsIgnoreCase("collectable")){
				
				return 1;
			}
			else{
				return 0;
			}
			}
		
	}
