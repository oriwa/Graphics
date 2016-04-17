package RayTracing.DM;

public class Material {

	public Color Diffuse;
	public Color Specular;
	public double Phong;
	public Color Reflection;
	public double Transparency;
	
	public Material(Color diffuse, Color specular, double phong, Color reflection, double transparency)
	{
		Diffuse = diffuse;
		Specular = specular;
		Phong = phong;
		Reflection = reflection;
		Transparency = transparency;
	}
	
	
}
