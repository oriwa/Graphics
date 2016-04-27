package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.Common.MathHelper;
import RayTracing.DM.Math.Base3DComponent;
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

	@Override
	public Intersection findIntersection(Ray ray) {
		double a = 1;
		Vector v = ray.getSource().substruct(Center);
		double b = 2 * ray.getDirection().dotProduct(v);
		double c = v.dotProduct(v) - Radius * Radius;
		
		double[] solution = MathHelper.solveQuadraticEquation(a, b, c);
		
		Point p1 = (Point) ray.getSource().add(ray.getDirection().scalarMult(solution[0]));
		Point p2 = (Point) ray.getSource().add(ray.getDirection().scalarMult(solution[1]));

		double distance1 = ray.getSource().calcDistance(p1);
		double distance2 = ray.getSource().calcDistance(p2);
		
		Point intersectionPoint = distance1 > distance2 ? p2 : p1;
		
		return new Intersection(intersectionPoint,this);
	}
}
