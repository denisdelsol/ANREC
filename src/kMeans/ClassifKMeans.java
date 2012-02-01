/**
 * 
 */
package kMeans;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

import distance.Distance;

/**
 * @author Julien and Denis
 *
 */
public class ClassifKMeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FileInputStream fstream;
		try {
			// Initialisation
			fstream = new FileInputStream("ListeDesMoyennes.txt");
			DataInputStream dataInputStream = new DataInputStream(fstream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			String line = bufferedReader.readLine();
			StringTokenizer strtok = new StringTokenizer(line);
			// On r�cup�re la valeur de k souhait�e
			int k = Integer.parseInt(strtok.nextToken(" "));

			// On r�cup�re le nombre d'�l�ments
			int nbElem = Integer.parseInt(strtok.nextToken(" "));

			LinkedList<Point> pointList = new LinkedList<Point>();
			// Pr�mices : On enregistre la liste des points du fichier pour pouvoir les manipuler apr�s
			Point currentPoint;
			while ((line = bufferedReader.readLine()) != null) {
				strtok = new StringTokenizer(line, "	");
				LinkedList<Float> coordList = new LinkedList<Float>();
				while (strtok.hasMoreTokens()) {
					coordList.add(Float.parseFloat(strtok.nextToken()));
				}
				currentPoint = new Point(coordList.toArray(new Float[coordList.size()]));
				pointList.add(currentPoint);
			} // Tous les points sont enregistr�s

			// Il ne dot rester que k points (ou clusters, pareil)
			while (pointList.size()>k) {
				System.out.println(pointList.size());
				Float[][] distanceMatrix = new Float[pointList.size()][pointList.size()];
				Float min = Float.POSITIVE_INFINITY;
				int minI=0;
				int minJ=0;
				// g�n�ration de la matrice de distance initiale + captation du min
				for (int i=0; i<pointList.size(); i++) {
					for (int j=0; j<=i; j++) {
						distanceMatrix[i][j] = Distance.distance(pointList.get(i), pointList.get(j));
						distanceMatrix[j][i] = Distance.distance(pointList.get(i), pointList.get(j));
						if (distanceMatrix[i][j] < min) {
							min = distanceMatrix[i][j];
							minI = i;
							minJ = j;
						}
					}
				}
				// On g�n�re les coordonn�es du cluster
				Float[] clusterCoord = new Float[nbElem];
				for (int i=0; i<nbElem; i++) {
					clusterCoord[i] = (pointList.get(minJ).getCoord()[i] + pointList.get(minI).getCoord()[i])/2;
				}
				Point cluster = new Point(clusterCoord);
				pointList.add(cluster);
				pointList.remove(minJ);
				pointList.remove(minI);	
			}
			// la pointlist est une liste de points repr�sentant les k clusters







			//			// On fusionne les 2 �l�ments les plus proches
			//			List<Float[]> list = new ArrayList<Float[]>(Arrays.asList(distanceMatrix));
			//			list.remove(minI);
			//			for (int j=0; j<distanceMatrix.length; j++) {
			//				List<Float> listColonne = new ArrayList<Float>(Arrays.asList(list.get(j)));
			//				listColonne.remove(minJ);
			//				list.set(j, listColonne.toArray(list.get(j)));
			//			}
			//			distanceMatrix = list.toArray(distanceMatrix);
			//			// On a vir� une dimension � la matrice
			//			
			//			

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
