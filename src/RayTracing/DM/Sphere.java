package RayTracing.DM;

import RayTracing.DM.Math.Point;

public class Sphere extends Surface {

	public double Radius;
	public Point Center;
	
	public Sphere(Point center, double radius)
	{
		Center=center;
		Radius=radius;
	}
	
}
