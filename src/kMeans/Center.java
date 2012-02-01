/**
 * 
 */
package kMeans;

import java.util.LinkedList;

/**
 * @author Julien
 *
 */
public class Center extends Point {
	
	private LinkedList<Point> pointList = new LinkedList<Point>();

	public Center(Float[] coord) {
		super(coord);
	}
	
	public Float[] getCoord() {
		return coord;
	}

	public LinkedList<Point> getPointList() {
		return pointList;
	}
	
	public void addPoint(Point point) {
		pointList.add(point);
	}
	
	public void removePoint(Point point) {
		pointList.remove(pointList.indexOf(point));
	}

	public boolean contains(Point point) {
		return pointList.contains(point);
	}

	public void setCoord(Float[] coord) {
		this.coord = coord;
	}
}
