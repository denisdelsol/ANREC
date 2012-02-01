package distance;

import kMeans.Point;

public class Distance {
	
	public static Float distance(Point point, Point center) {
		return PowerTwoSumDistance.distance(point, center);
		//return ManhattanDistance.distance(point, center);
		//return TchebychevDistance.distance(point, center);
	}

}
