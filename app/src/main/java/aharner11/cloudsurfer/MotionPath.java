package aharner11.cloudsurfer;

import java.lang.Math;

public class MotionPath {
	
	
	String exactMovement;
	int path;
	int path3;
	int oldpath3;
	int moveXstep;
	int moveYstep;
	int i;
	public MotionPath(String movement) {
		// TODO Auto-generated constructor stub
		// take in something like 'move left 50'
		//or 'move left 50, move down 50, repeat....
		
		//for now, simplicity is just 1 number, and we'll make it go back and forth that width
		path = Integer.parseInt(movement);
		
		path3 = 0;
		i = 0;
		if(movement == ""){
			path3 = 0;
		}
		
	}
	
	
	public int nextMovement(){
		
	//	if(path3 == 0){
	//		return 0;
	//		
	//	}
		//path= 50;
	 if ( i > 0){
		path3 = 4;			
	 }
     if ( i >= path){
			path3 = -4;			
	 }
     i = i +8;
	if ( i >= path * 2){
			i = 0;			
	}
			
		
		
		
		//have it take 10 'steps' to get to the location
		//if(path3 == -9999){
		//	path3 = (path/10);
		//	oldpath3 = path3;
		//}
		//if(Math.abs(path3) > Math.abs(path)){
			//reverse the direction...
		//	path = -1 * path;
		//	path3 = -9999;
		//}
		
		//oldpath3 = oldpath3+ path3;
		
		
		
		return path3;
		
	
		
	}

	
	
	
	
	
}
