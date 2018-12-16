package aharner11.cloudsurfer;

	import android.graphics.Bitmap;
	import android.graphics.Canvas;
	import android.location.Location;
	import android.util.Log;


	public class Clouds {


		private Bitmap bitmap; //the actual btimap
		private Position loc;
		private boolean touched; //if surfer is picked up or being dragged
	//	private Position relativeMove;
		private Position[] locs; //array of locations of all the clouds of this type (norm)
		private Acceleration accel;
		private Acceleration normal;
		private boolean isCloud;
		
		public Clouds(Bitmap bitmap, Position[] locations,  Acceleration norm){
			this.bitmap = bitmap;
			
			//here is where to load the bitmap and set it...
			this.locs = locations;
			this.normal = norm;
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
		
		
		public Acceleration getAcceleration(){
			return accel;
		}
		public void setAcceleration(Acceleration newAccel){
			this.accel = newAccel;
		}
//		public void setVelocity(){
		//	return vel;
//		}
		
		
		public void updateRelativeLocations(Velocity secondtemp){
			
			double changeX;
			double changeY;
			int i =0;
			changeX = secondtemp.getVelocityX();
			changeY = secondtemp.getVelocityY();
			while (i < locs.length){
				locs[i].setX((int) (locs[i].getX()+changeX));
				locs[i].setY((int) (locs[i].getY()+changeY));
				i++;
			}
			//should move all clouds by the amount relative to the surfer player
			
		}
		
		public void draw(Canvas canvas, Acceleration accel){
			
			int i =0;
			
			while (i < locs.length){
				canvas.drawBitmap(bitmap, locs[i].getX() - (bitmap.getWidth()/2), locs[i].getY()-(bitmap.getHeight()), null);
				i++;
			}
			
			//updateRelativeLocations();
		//	canvas.drawBitmap(bitmap, loc.getX() - (bitmap.getWidth()/2), loc.getY()-(bitmap.getHeight()), null);
			
		}
		
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
		
		public void movement(Acceleration gravity){
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
			// TODO Auto-generated method stub
		//	if (vel.getVelocityY() < 15){
		//	vel.setVelocityY(vel.getVelocityY()+4);
		//	}
		}
		public Acceleration getNormal() {
			// TODO Auto-generated method stub
			return this.normal;
		}
		
		public Position[] getPositions(){
			return this.locs;
			
		}
		
		public void applyNormal(Acceleration normal2) {
			// TODO Auto-generated method stub
	//////		this.accel = new Acceleration(normal2.getX()+this.accel.getX(),normal2.getY()+this.accel.getY());
		}
	}
