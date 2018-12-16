package aharner11.cloudsurfer;

public class Velocity {

	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_LEFT 	= -1;
	public static final int DIRECTION_UP 	= -1;
	public static final int DIRECTION_DOWN 	= 1;
	
	private double xvel; //1 pixel per game loop
	private double yvel;

	public Velocity(){
		this.xvel = 0;
		this.yvel = 0;
	}

	public Velocity(int xx, int yy){
		this.xvel = xx;
		this.yvel = yy;
	}
	

	public double getVelocityX(){
		return xvel;
	}
	public double getVelocityY(){
		return yvel;
	}
	
	public void setVelocityX(double number){
		xvel = number;
	}
	public void setVelocityY(double d){
		yvel = d;
	}
	public void setXYvelocity(double d, double e){
		xvel = d;
		yvel = e;
	}
	
}
