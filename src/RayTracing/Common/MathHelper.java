package RayTracing.Common;

import RayTracing.DM.Math.*;

public class MathHelper {

	public static double[] solveQuadraticEquation(double a, double b, double c){
		double roots[] = new double[2];
		double temp = Math.sqrt(Math.pow(b, 2) - 4*a*c);
	    roots[0] = (-b + temp) / (2*a);
	    roots[1] = (-b - temp) / (2*a);
	    return roots;
	}
	
	public static Vector getNormalizeVector(Point from,Point to)
	{
		Vector vector=new Vector(from.getCoordinate(0)-to.getCoordinate(0), from.getCoordinate(1)-to.getCoordinate(1), from.getCoordinate(2)-to.getCoordinate(2));
		double norm =norm( vector);
		vector.scalarMult(1/norm);
		return vector;
	}
	
	public static double norm(Vector vector)
	{
		return Math.sqrt((Math.pow(vector.getCoordinate(0), 2)+Math.pow(vector.getCoordinate(1), 2)+Math.pow(vector.getCoordinate(2), 2)));
	}
}
