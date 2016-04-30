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
	
	public Vector matrixMult(Base3DComponent[] matrix){
		Vector result = new Vector();
		
		for (int i = 0; i < 3; i++){
			Vector row = new Vector(matrix[0].getCoordinate(i), matrix[1].getCoordinate(i), matrix[2].getCoordinate(i));
			double value = dotProduct(row);
			result.setCoordinate(i, value);
		}
		
		return result;
	}
	
	public Vector rotateByAngleX(double angle){
		angle = Math.toRadians(angle);
		Vector[] rotationMatrix = new Vector[3];
		rotationMatrix[0] = new Vector(1, 0, 0);
		rotationMatrix[1] = new Vector(0, Math.cos(angle), Math.sin(angle));
		rotationMatrix[2] = new Vector(0, -Math.sin(angle), Math.cos(angle));
		return matrixMult(rotationMatrix);
	
	}
	public Vector rotateByAngleY(double angle){
		angle = Math.toRadians(angle);
		Vector[] rotationMatrix = new Vector[3];
		rotationMatrix[0] = new Vector(Math.cos(angle), 0, -Math.sin(angle));
		rotationMatrix[1] = new Vector(0, 1, 0);
		rotationMatrix[2] = new Vector(Math.sin(angle), 0, Math.cos(angle));
		return matrixMult(rotationMatrix);
	
	}
	public Vector rotateByAngleZ(double angle){
		angle = Math.toRadians(angle);
		Vector[] rotationMatrix = new Vector[3];
		rotationMatrix[0] = new Vector(Math.cos(angle), Math.sin(angle), 0);
		rotationMatrix[1] = new Vector(-Math.sin(angle), Math.cos(angle), 0);
		rotationMatrix[2] = new Vector(0, 0, 1);
		return matrixMult(rotationMatrix);
	
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
