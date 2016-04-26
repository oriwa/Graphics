package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.DM.Math.Point;

public class Sphere extends Surface {

	public double Radius;
	public Point Center;
	
	public Sphere(Point center, double radius)
	{
		Center=center;
		Radius=radius;
	}

	@Override
	public Intersection findIntersection(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
