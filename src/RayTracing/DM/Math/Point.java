package RayTracing.DM.Math;

public class Point extends Base3DComponent{
	
	public Point() {
		super();
	}

	public Point(double x,double y,double z) {
		super(x,y,z);
	}

	@Override
	protected Base3DComponent newInstance() {
		return new Point();
	}
	
	public double calcDistance(Point other){
		double distance = 0;
		
		for (int i = 0; i < 3; i++){
			double temp = this.getCoordinate(i) - other.getCoordinate(i);
			distance += temp * temp;
		}
		
		return Math.sqrt(distance);
	}
}
