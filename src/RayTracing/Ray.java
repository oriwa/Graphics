package RayTracing;

import RayTracing.DM.Math.Point;
import RayTracing.DM.Math.Vector;

public class Ray {

	private Point source;
	private Vector direction;

	public Ray(Point source, Vector direction) {
		this.source = source;
		this.direction = direction;
	}

	public void setSource(Point source) {
		this.source = source;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public Point getSource() {
		return source;
	}

	public Point getPointOnRay(double t) {
		return (Point) source.add(direction.scalarMult(t));
	}

}
