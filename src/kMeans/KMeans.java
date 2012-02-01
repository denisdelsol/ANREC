/**
 * 
 */
package kMeans;

import initialisation.RegularInit;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

import sortie.Fichier;
import sortie.Graphe;
import distance.Distance;

/**
 * @author Julien and Denis
 *
 */
public class KMeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FileInputStream fstream;
		try {
			// Initialisation
			fstream = new FileInputStream("exemple1.txt");
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
			
			KMeans.analysis(k, nbElem, pointList);
//			KMeans.agglomerativeMethod(k, nbElem, pointList);
			dataInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @param k
	 * @param nbElem
	 * @param pointList
	 */
	private static void agglomerativeMethod(int k, int nbElem,
			LinkedList<Point> pointList) {
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
					
					Fichier fichier = new Fichier(pointList);
	}

	/**
	 * @param k
	 * @param nbElem
	 * @param pointList
	 */
	private static void analysis(int k, int nbElem, LinkedList<Point> pointList) {
		// On g�n�re la liste des centres d'it�ration 0
					LinkedList<Center> centerList = RegularInit.generateInitialCenters(pointList, k, nbElem);
					// Calculer la somme actuelle des �carts
					float gapSum = Float.POSITIVE_INFINITY;
					float newGapSum = gapSum;
					// On it�re tant que la somme des �carts diminue � chaque it�ration
					do {
						gapSum = newGapSum;
						// Algorithme du k-means FTW


						// Phase 1 : Attribuer chaque point au centre dont il est le plus proche, au sens de la distance choisie
						// On it�re sur l'ensemble des points
						for (int i=0; i<pointList.size(); i++) {
							Float minDistance = Float.POSITIVE_INFINITY;
							Float distance;
							int minCenterIndex = -1;
							// On it�re sur l'ensemble des centres
							for (int j=0; j<centerList.size(); j++) {
								distance = Distance.distance(pointList.get(i), centerList.get(j));
								if (distance < minDistance) { // Le nouveau centre est plus proche du point consid�r� que l'ancien centre
									minDistance = distance;
									minCenterIndex = j;
								}
								// On supprime ce point de son ancien centre
								for (int m=0; m<centerList.size(); m++) {
									if (centerList.get(m).contains(pointList.get(i))) {
										centerList.get(m).removePoint(pointList.get(i));
									}
								}
								// On attribue ce point au nouveau centre
								if (minCenterIndex != -1) {
									centerList.get(minCenterIndex).addPoint(pointList.get(i));							
								} else {
									System.out.println("Distance mesur�e sup�rieure � l'infini : Check.");
								}
							} // Fin de traitement du point
						} // Fin de la Phase 1

						// Phase 2 : on repositionne les centres par rapport au nuage de points attribu�
						// On it�re sur les centres
						for (int i=0; i<centerList.size(); i++) {
							Float[] newCenterCoord = new Float[nbElem];
							LinkedList<Point> currentCenterPointList = centerList.get(i).getPointList();
							// On it�re sur les points du centre actuel
							if(!currentCenterPointList.isEmpty()) {
								for (int j=0; j<currentCenterPointList.size(); j++) {
									// On it�re sur les dimensions
									for (int m=0; m<nbElem; m++) {
										// On ajoute la m-i�me coordonn�e du j-i�me point du i-i�me centre
										if (newCenterCoord[m] != null) { 
											newCenterCoord[m] += currentCenterPointList.get(j).getCoord()[m];
										} else {
											newCenterCoord[m] = currentCenterPointList.get(j).getCoord()[m];
										}
									}
								}
								// On doit rediviser chaque coordonn�e du nouveau centre par le nombre de points
								for (int m=0; m<nbElem; m++) {
									if (currentCenterPointList.size() > 0) {
										newCenterCoord[m] = newCenterCoord[m]/currentCenterPointList.size();
									} else {
										newCenterCoord[m] = 0.0f;
									}
								}
								// On modifier les coordonn�es du centre
								centerList.get(i).setCoord(newCenterCoord);
							}
						} // Fin de la Phase 2
						// On recalcule newGapSum
						newGapSum = 0;
						// On it�re sur les centres
						for (int i=0; i<centerList.size(); i++) {
							// On it�re sur les points du centre en cours
							if(centerList.get(i).getPointList() != null) {
								for (int j=0; j<centerList.get(i).getPointList().size(); j++) {
									// on calcule la distance au carr� du point au centre et on l'ajoute � newGapSum
									newGapSum += Math.pow(Distance.distance(centerList.get(i).getPointList().get(j), centerList.get(i)), 2);
								}
							}
						}
										System.out.println(centerList.get(1).getCoord()[0]);
					} while (newGapSum < gapSum);
					
					//Lance le dessin du graphe
					Graphe graphe = new Graphe();
					graphe.dessine(centerList);

		
	}
}
