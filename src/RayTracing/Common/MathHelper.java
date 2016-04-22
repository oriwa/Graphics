package RayTracing.Common;

public class MathHelper {

	public static double[] solveQuadraticEquation(double a, double b, double c){
		double roots[] = new double[2];
		double temp = Math.sqrt(Math.pow(b, 2) - 4*a*c);
	    roots[0] = (-b + temp) / (2*a);
	    roots[1] = (-b - temp) / (2*a);
	    return roots;
	}
}
