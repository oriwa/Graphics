package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.Common.MathHelper;
import RayTracing.DM.Math.Point;
import RayTracing.DM.Math.Vector;

public class Sphere extends Surface {

	public double Radius;
	public Point Center;

	public Sphere(Point center, double radius) {
		Center = center;
		Radius = radius;
	}

	@Override
	public Intersection findIntersection(Ray ray) {
		double a = 1;
		Vector v = ray.getSource().substruct(Center);
		double b = 2 * ray.getDirection().dotProduct(v);
		double c = v.dotProduct(v) - Radius * Radius;

		double[] solution = MathHelper.solveQuadraticEquation(a, b, c);

		Point p1 = (Point) ray.getSource().add(
				ray.getDirection().scalarMult(solution[0]));
		Point p2 = (Point) ray.getSource().add(
				ray.getDirection().scalarMult(solution[1]));

		double distance1 = ray.getSource().calcDistance(p1);
		double distance2 = ray.getSource().calcDistance(p2);
		Point intersectionPoint = null;
		double distance;
		if (distance1 > distance2) {
			intersectionPoint = p2;
			distance = distance2;
		} else {
			intersectionPoint = p1;
			distance = distance1;
		}
		return new Intersection(intersectionPoint, this, distance);
	}

}
