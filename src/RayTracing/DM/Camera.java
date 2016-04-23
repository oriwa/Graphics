package RayTracing.DM;

import RayTracing.DM.Math.Vector;

public class Camera {
	
	public Vector Position;
	public Vector LookAtPoint;
	public Vector Up;
	public double ScreenDistance;
	public double ScreenWidth;
	
	public Camera(Vector position, Vector lookAtPoint, Vector up, double screenDistance, double screenWidth) 
	{
		Position = position;
		LookAtPoint = lookAtPoint;
		Up = up;
		ScreenDistance = screenDistance;
		ScreenWidth = screenWidth;
	}
}
