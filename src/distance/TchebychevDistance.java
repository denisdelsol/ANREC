package distance;

import kMeans.Point;

public class TchebychevDistance {

	/**
	 * @param point
	 * @param center
	 * @return
	 */
	public static Float distance(Point point, Point center) {
		Float distance = 0.0f;
		// On itère sur les dimensions
		for (int i=0; i<point.getCoord().length; i++) {
			distance = Math.max((float) Math.abs(point.getCoord()[i]-center.getCoord()[i]), distance);
		}
		return distance;
	}
}
