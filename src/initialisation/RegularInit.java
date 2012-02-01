/**
 * 
 */
package initialisation;

import java.util.LinkedList;

import kMeans.Center;
import kMeans.Point;

/**
 * @author Julien
 *
 */
public class RegularInit {

	/**
	 * @param pointList
	 * @return
	 */
	public static LinkedList<Center> generateInitialCenters(
			LinkedList<Point> pointList, int k, int nbElem) {
		LinkedList<Center> listCenter = new LinkedList<Center>();
		// On parcourt la liste pour r�cup�rer les extrema de chaque dimension
		Float[] listMax = new Float[nbElem];
		Float[] listMin = new Float[nbElem];
		float value;
		// On parcourt tous les points
		for (int v=0; v<pointList.size(); v++) {
			// On cherche un extremum sur chaque dimension de la ligne
			for (int i=0; i<nbElem; i++) {
				value = pointList.get(v).getCoord()[i];
				// Si la valeur lue est sup�rieure au max pr�c�demment r�pertori�, on le remplace
				if (listMax[i] != null && listMin[i] != null) {
					if (value > listMax[i]) {
						listMax[i] = value;
					} else if (value < listMin[i]) { // Pareil pour le min
						listMin[i] = value;
					}
				} else {
					listMax[i] = value;
					listMin[i] = value;
				}
			}
		} // On a tous les extrema
		
		// On g�n�re la liste des centres de mani�re r�guli�re sur chaque dimension entre les extrema
		Float[] centerCoord = new Float[nbElem];
		// On va cr�er k centres
		for (int i=0; i<k; i++) {
			// Pour chaque dimension
			for (int j=0; j<nbElem; j++) {
				// On g�n�re le tableau de coordonn�es
				centerCoord[j] = listMin[j] + i*((Math.abs(listMax[j]-listMin[j]))/k);
			}
			Float[] resultCoord = centerCoord.clone();
			listCenter.add(new Center(resultCoord));
		}
		return listCenter;
	}


}
