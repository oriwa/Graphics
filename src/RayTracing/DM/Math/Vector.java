package RayTracing.DM.Math;

public class Vector extends Base3DComponent{

	public Vector() {
		super();
	}

	@Override
	protected Base3DComponent newInstance() {
		return new Vector();
	}
	
	public Vector(double x,double y,double z) {
		super(x,y,z);
	}
	
	public double dotProduct(Base3DComponent other){
		double result = 0;
		
		for (int i = 0; i < 3; i++){
			result += this.getCoordinate(i) * other.getCoordinate(i);
		}
		
		return result;
	}
	

	public double norm()
	{
		return Math.sqrt((Math.pow(this.getCoordinate(0), 2)+Math.pow(this.getCoordinate(1), 2)+Math.pow(this.getCoordinate(2), 2)));
	}
	
	public Vector normalize()
	{
		scalarMult(1/norm());
		return this;
	}
	
}
