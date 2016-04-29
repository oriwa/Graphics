package RayTracing.DM;

import RayTracing.DM.Math.Point;

public class Light {

	public Point Position;
	public Color Color;
	public double SpecularIntensity;
	public double ShadowIntensity;
	public double Radius;
	
	public Light(Point position, RayTracing.DM.Color color, double specularIntensity, double shadowIntensity,
			double radius) 
	{
		Position = position;
		Color = color;
		SpecularIntensity = specularIntensity;
		ShadowIntensity = shadowIntensity;
		Radius = radius;
	}
	
	
}
