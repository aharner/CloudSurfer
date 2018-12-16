package aharner11.cloudsurfer;

public class Position {
	
	private int x;
	private int y;
	
	public Position(){
		this.x=0;
		this.y=0;
	}
	
	public Position(int posx, int posy){
		this.x=posx;
		this.y=posy;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int posx){
		this.x = posx;
	}
	
	public void setY(int posy){
		this.y = posy;
	}
	
	
}
