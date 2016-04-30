package RayTracing.DM.Math;

public abstract class Base3DComponent {
	
	private double[] coordinates;
	
	public Base3DComponent()
	{
		coordinates = new double[3];
		coordinates[0]=0.0;
		coordinates[1]=0.0;
		coordinates[2]=0.0;
	}

	public Base3DComponent(double x,double y,double z)
	{
		this();
		
		coordinates[0]=x;
		coordinates[1]=y;
		coordinates[2]=z;
	}

    protected abstract Base3DComponent newInstance();

	public Base3DComponent scalarMult(double scalar){
		Base3DComponent result = this.newInstance();
		
		for (int i = 0; i < 3; i++){
			double value = this.getCoordinate(i) * scalar;
			result.setCoordinate(i, value);
		}
		
		return result;
	}
	
	public Base3DComponent add(Base3DComponent other){
		Base3DComponent result = this.newInstance();
		
		for (int i = 0; i < 3; i++){
			double value = this.getCoordinate(i) + other.getCoordinate(i);
			result.setCoordinate(i, value);
		}
		
		return result;
	}
	
	public Base3DComponent substruct(Base3DComponent other){
		Base3DComponent result = this.newInstance();
		
		for (int i = 0; i < 3; i++){
			double value = this.getCoordinate(i) - other.getCoordinate(i);
			result.setCoordinate(i, value);
		}
		
		return result;
	}
	
	public boolean equals(Base3DComponent other)
	{
		for (int i = 0; i < 3; i++){
			if(this.getCoordinate(i) != other.getCoordinate(i))
					return false;
		}
		return true;
	}
	
	public double dotProduct(Base3DComponent other){
		double result = 0;
		
		for (int i = 0; i < 3; i++){
			result += this.getCoordinate(i) * other.getCoordinate(i);
		}
		
		return result;
	}

	
	public double getCoordinate(int index){
		return coordinates[index];
	}
	public void setCoordinate(int index, double value){
		coordinates[index] = value;
	}
}
