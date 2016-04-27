package RayTracing;

import RayTracing.DM.Surface;
import RayTracing.DM.Math.Point;

public class Intersection {
	private Point point;
	private Surface surface;
	
	public Intersection(Point point, Surface surface){
		this.setPoint( point);
		this.setSurface(surface);
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
}
