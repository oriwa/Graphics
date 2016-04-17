package RayTracing.DM;

import RayTracing.DM.Math.Point;
import RayTracing.DM.Math.Vector;

public class Cylinder extends Surface {
	 
	public Point Center;
	public double Length;
	public double Radius;
	public Vector Rotation;
	
	public Cylinder(Point center, double length, double radius, Vector rotation)
	{
		Center=center;
		Length=length;
		Radius=radius;
		Rotation=rotation;
	}
}
