package RayTracing.Common;

import RayTracing.DM.Math.*;

public class MathHelper {

	public final static double EPSILON=0.0001;
	
	public static double[] solveQuadraticEquation(double a, double b, double c){
		double roots[] = new double[2];
		double delta=Math.pow(b, 2) - 4*a*c;
		if(delta<0)
			return null;
		double temp = Math.sqrt(delta);
	    roots[0] = (-b + temp) / (2*a);
	    roots[1] = (-b - temp) / (2*a);
	    return roots;
	}
	
	public static Vector getNormalizeVector(Point from,Point to)
	{
		Vector vector=new Vector(to.getCoordinate(0)-from.getCoordinate(0), to.getCoordinate(1)-from.getCoordinate(1), to.getCoordinate(2)-from.getCoordinate(2));
		double norm =vector.norm();
		return (Vector) vector.scalarMult(1/norm);
	}
	
	
	public static Vector crossProduct(Vector vector,Vector vector2)
	{
		double x=vector.getCoordinate(1)*vector2.getCoordinate(2)-vector.getCoordinate(2)*vector2.getCoordinate(1);
		double y=vector.getCoordinate(2)*vector2.getCoordinate(0)-vector.getCoordinate(0)*vector2.getCoordinate(2);
		double z=vector.getCoordinate(0)*vector2.getCoordinate(1)-vector.getCoordinate(1)*vector2.getCoordinate(0);				
		return new Vector(x,y,z);
	}
}
