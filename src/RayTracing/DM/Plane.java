package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.DM.Math.Point;
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

		double a= -(Normal.dotProduct(ray.getSource()) - Offset);
		double b=Normal.dotProduct(ray.getDirection());
		double t=a/b;
		Point interPoint=ray.getPointOnRay(t);
		return new Intersection(interPoint,this,t);
	}
	
}
