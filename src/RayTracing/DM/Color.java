package RayTracing.DM;

public class Color {
	
	private double r;
	private double g;
	private double b;
	
	public Color()
	{
		r=0.0;
		g=0.0;
		b=0.0;
	}
	
	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = Math.min(1.0, r);
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = Math.min(1.0, g);
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = Math.min(1.0, b);
	}

	public Color(double r,double g,double b)
	{
		setR(r); 
		setG(g); 
		setB(b); 
	}
	
	public Color addColor(Color color)
	{
		return new Color(r+color.r,g+color.g,b+color.b);	
	}
	
	public Color mult(double factor)
	{
		return new Color(r*factor,g*factor,b*factor);	
	}
	
	public Color multColor(Color color)
	{
		return new Color(r*color.r,g*color.g,b*color.b);	
	}
	 
	
	public byte getRInByte()
	{
		return (byte)(Math.min(1.0, r) *255);
	}
	
	public byte getGInByte()
	{
		return (byte)(Math.min(1.0, g)*255);
	}
	
	public byte getBInByte()
	{
		return (byte)(Math.min(1.0, b)*255);
	}
}
