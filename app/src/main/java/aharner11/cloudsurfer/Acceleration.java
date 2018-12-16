package aharner11.cloudsurfer;

public class Acceleration {
	private double x;
	private double y;
	
	public Acceleration(){
		this.x=0;
		this.y=0;
	}
	
	public Acceleration(double accx, double accy){
		this.x=accx;
		this.y=accy;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setX(double accx){
		this.x = accx;
	}
	
	public void setY(double accy){
		this.y = accy;
	}
	
}
