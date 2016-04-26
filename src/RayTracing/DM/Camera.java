package RayTracing.DM;

import RayTracing.Common.MathHelper;
import RayTracing.DM.Math.*;

public class Camera {
	
	public Point Position;
	public Point LookAtPoint;
	public Vector Up;
	public Vector Direction;
	public double ScreenDistance;
	public double ScreenWidth;
	public double ScreenHeight;
	
	public Camera(Point position, Point lookAtPoint, Vector up, double screenDistance, double screenWidth) 
	{
		Position = position;
		LookAtPoint = lookAtPoint;
		Up = up;
		ScreenDistance = screenDistance;
		ScreenWidth = screenWidth;
		InitCamera();
	}

	private void InitCamera() 
	{
		Direction=MathHelper.getNormalizeVector(Position, LookAtPoint);		
	}
}
