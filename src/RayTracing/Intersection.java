package RayTracing;

import RayTracing.DM.Surface;
import RayTracing.DM.Math.Point;

public class Intersection {
	private Point point;
	private Surface surface;
	private double distance;
	
	public Intersection(Point point, Surface surface,double distance){
		this.setPoint(point);
		this.setSurface(surface);
		this.setDistance(distance);
	}
	

	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}

	public Surface getSurface() {
		return surface;
	}

	public void setSurface(Surface surface) {
		this.surface = surface;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}
}
