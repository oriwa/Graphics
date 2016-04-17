package RayTracing.DM;

import RayTracing.DM.Math.Vector;

public class Plane extends Surface {
	
	public double Offset;
	public Vector Normal;
	
	public Plane(Vector normal, double offset)
	{
		Normal=normal;
		Offset=offset;
	}
	
}
