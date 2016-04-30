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

		Vector v = (Vector) Center.substruct(ray.getSource());

		double vd = v.dotProduct(ray.getDirection());
		if (vd < 0) {
			return null;
		}

		double v2_vd2 = v.dotProduct(v) - Math.pow(vd, 2);
		if (v2_vd2 > Math.pow(Radius, 2)) {
			return null;
		}

		double det = Math.sqrt(Math.pow(Radius, 2) - v2_vd2);
		double distance = vd - det;
		Point p = (Point) ray.getSource().add(ray.getDirection().scalarMult(distance));
		return new Intersection(p, this, distance);
	}

	@Override
	public Vector getNormal(Point point, Vector ray) {
		Vector vector = MathHelper.getNormalizeVector(Center, point);
		if (Math.acos(vector.dotProduct(ray)) > 0.5 * Math.PI)
			return (Vector) vector.scalarMult(-1);

		return vector;
	}

}