package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.DM.Math.Vector;

public class Plane extends Surface {
	
	public double Offset;
	public Vector Normal;
	
	public Plane(Vector normal, double offset)
	{
		Normal=normal;
		Offset=offset;
	}

	@Override
	public Intersection findIntersection(Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
