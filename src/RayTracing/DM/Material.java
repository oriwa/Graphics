package RayTracing.DM;

public class Material {

	public Color Diffuse;
	public Color Specular;
	public Color Reflection;
	public double Phong;
	public double Transparency;
	
	public Material(Color diffuse, Color specular, Color reflection, double phong, double transparency)
	{
		Diffuse = diffuse;
		Specular = specular;
		Phong = phong;
		Reflection = reflection;
		Transparency = transparency;
	}
	
	
}
