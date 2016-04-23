package RayTracing;

import RayTracing.DM.Color;
import RayTracing.DM.Math.Point;

public class Intersection {
	private Point point;
	private Color color;
	
	public Intersection(Point point, Color color){
		this.point = point;
		this.color = color;
	}
	
	public Intersection(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
