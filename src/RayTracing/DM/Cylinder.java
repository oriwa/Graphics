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

	Vector axis;
	Point centerTop;
	Point centerBottom;
	Plane topPlane;
	Plane bottomPlane;

	public Cylinder(Point center, double length, double radius, Vector rotation) {
		Center = center;
		Length = length;
		Radius = radius;
		Rotation = rotation;

		axis = new Vector(0, 0, 1);
		axis = axis.rotateByAngleX(Rotation.getCoordinate(0)).rotateByAngleY(Rotation.getCoordinate(1))
				.rotateByAngleZ(Rotation.getCoordinate(2));
		centerTop = (Point) Center.add(axis.scalarMult(Length / 2));
		centerBottom = (Point) Center.substruct(axis.scalarMult(Length / 2));
		topPlane = new Plane(axis, axis.dotProduct(centerTop));
		bottomPlane = new Plane(axis, axis.dotProduct(centerBottom));
	}

	@Override
	public Intersection findIntersection(Ray ray) {

		Point relativeTop = (Point) centerTop.substruct(centerBottom);

		Vector toBottom = ((Point) ray.getSource().substruct(centerBottom)).toVector();
		
		Vector aVector = (Vector) ray.getDirection().substruct(axis.scalarMult(ray.getDirection().dotProduct(axis)));
		double a = Math.pow(aVector.norm(),2);
		
		Vector bVector = (Vector) toBottom.substruct(axis.scalarMult(toBottom.dotProduct(axis)));
		double b = 2 * aVector.dotProduct(bVector);
		
		double c = Math.pow(bVector.norm(),2) - Math.pow(Radius,2);
		
		
		Point intersectionPoint = null;
		double distance = 0;
		double[] solution = MathHelper.solveQuadraticEquation(a, b, c);

		if (solution != null) {
			Point p1 = (Point) ray.getSource().add(ray.getDirection().scalarMult(solution[0]));
			Point p2 = (Point) ray.getSource().add(ray.getDirection().scalarMult(solution[1]));

			double distance1 = ray.getSource().calcDistance(p1);
			double distance2 = ray.getSource().calcDistance(p2);

			if (distance1 > distance2) {
				intersectionPoint = p2;
				distance = distance2;
			} else {
				intersectionPoint = p1;
				distance = distance1;
			}
		}

		Intersection cylinderIntersection = null;
		if (intersectionPoint != null) {
			double t = -(centerBottom.substruct(intersectionPoint)).dotProduct(relativeTop)
					/ relativeTop.dotProduct(relativeTop);
			if (t >= 0 && t <= 1)
				cylinderIntersection = new Intersection(intersectionPoint, this, distance);

		}
		
		Intersection topPlaneIntersection = topPlane.findIntersection(ray);
		Intersection bottomPlaneIntersection = bottomPlane.findIntersection(ray);

		if (topPlaneIntersection != null && topPlaneIntersection.getPoint().calcDistance(centerTop) > Radius)
			topPlaneIntersection = null;
		if (bottomPlaneIntersection != null && bottomPlaneIntersection.getPoint().calcDistance(centerBottom) > Radius)
			bottomPlaneIntersection = null;

		Intersection minIntersection = topPlaneIntersection;
		if (topPlaneIntersection == null || (bottomPlaneIntersection != null
				&& minIntersection.getDistance() > bottomPlaneIntersection.getDistance()))
			minIntersection = bottomPlaneIntersection;

		if (minIntersection == null || (cylinderIntersection != null && minIntersection.getDistance() > cylinderIntersection.getDistance()))
			return cylinderIntersection;

		minIntersection.setSurface(this);
		return minIntersection;
	}

	@Override
	public Vector getNormal(Point point, Vector ray) {
		// if intersection is on one of the planes
		if (Math.abs(topPlane.Normal.dotProduct(point)- topPlane.Offset)<MathHelper.EPSILON)
			return axis.normalize();
		if (Math.abs(bottomPlane.Normal.dotProduct(point) - bottomPlane.Offset)<MathHelper.EPSILON)
			return (Vector) axis.normalize().scalarMult(-1);

		Vector vector = MathHelper.getNormalizeVector(Center, point);
		if (Math.acos(vector.dotProduct(ray)) > 0.5 * Math.PI)
			return (Vector) vector.scalarMult(-1);

		return vector;
		/*Point relativeTop = (Point) centerTop.substruct(centerBottom);

		double t = -(centerBottom.substruct(point)).dotProduct(relativeTop)
				/ relativeTop.dotProduct(relativeTop);

		return ((Point) centerBottom.add(relativeTop.scalarMult(t))).toVector().normalize();*/
	}
}
