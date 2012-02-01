/**
 * 
 */
package distance;

import kMeans.Point;

/**
 * @author Julien
 *
 */
public class PowerTwoSumDistance {

	/**
	 * @param point
	 * @param center
	 * @return
	 */
	public static Float distance(Point point, Point center) {
		Float distance = 0.0f;
		// On itère sur les dimensions
		for (int i=0; i<point.getCoord().length; i++) {
			distance += (float) Math.pow(point.getCoord()[i]-center.getCoord()[i], 2);
		}
		distance = (float) Math.sqrt(distance);
		return distance;
	}
	
}
