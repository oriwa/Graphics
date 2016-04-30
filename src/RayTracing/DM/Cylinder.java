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
		
		Vector axis = new Vector(0,0,1);
		axis = axis.rotateByAngleX(Rotation.getCoordinate(0)).rotateByAngleY(Rotation.getCoordinate(1)).rotateByAngleZ(Rotation.getCoordinate(2));
		Point centerTop = (Point) Center.add(axis.scalarMult(Length/2));
		Point centerBottom = (Point) Center.substruct(axis.scalarMult(Length/2));
		
		Plane topPlane = new Plane(axis, axis.dotProduct(centerTop));
		Plane bottomPlane = new Plane(axis, axis.dotProduct(centerBottom));
		
		Intersection topPlaneIntersection = topPlane.findIntersection(ray);
		Intersection bottomPlaneIntersection = bottomPlane.findIntersection(ray);
		
		if (topPlaneIntersection != null && topPlaneIntersection.getPoint().calcDistance(centerTop) > Radius)
			topPlaneIntersection = null;
		if (bottomPlaneIntersection != null && bottomPlaneIntersection.getPoint().calcDistance(centerBottom) > Radius)
			bottomPlaneIntersection = null;
		
		Intersection minIntersection = topPlaneIntersection;	
		if (topPlaneIntersection == null || (bottomPlaneIntersection != null && minIntersection.getDistance() > bottomPlaneIntersection.getDistance()))
				minIntersection = bottomPlaneIntersection;
		
		if (minIntersection == null)
			return null;
		
		minIntersection.setSurface(this);
		return minIntersection;
	}

	@Override
	public Vector getNormal(Point point,Vector ray) {
		Vector axis = new Vector(0,0,1);
		return axis.rotateByAngleX(Rotation.getCoordinate(0)).rotateByAngleY(Rotation.getCoordinate(1)).rotateByAngleZ(Rotation.getCoordinate(2));
	}
}
