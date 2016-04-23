package RayTracing.DM;

import RayTracing.Intersection;
import RayTracing.Ray;

public abstract class Surface {


	public Material Material;
	
	public abstract Intersection findIntersection(Ray ray);
	
}
