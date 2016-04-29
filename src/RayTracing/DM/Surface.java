package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;
import RayTracing.DM.Math.Point;
import RayTracing.DM.Math.Vector;

public abstract class Surface {


	public Material Material;
	
	public abstract Intersection findIntersection(Ray ray);
	
	public abstract Vector getNormal(Point point,Vector ray);
	
}
