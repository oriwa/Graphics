package RayTracing.DM;

public class Color {
	
	public double R;
	public double G;
	public double B;
	
	public Color()
	{
		R=0.0;
		G=0.0;
		B=0.0;
	}
	
	public Color(double r,double g,double b)
	{
		R=r;
		G=g;
		B=b;
	}
	
	
	
	public byte getRInByte()
	{
		return (byte)(Math.min(1.0, R) *255);
	}
	
	public byte getGInByte()
	{
		return (byte)(Math.min(1.0, G)*255);
	}
	
	public byte getBInByte()
	{
		return (byte)(Math.min(1.0, B)*255);
	}
}
