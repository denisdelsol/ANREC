package sortie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import kMeans.Point;

/**
 * Permet de sortir les données dans un fichier texte
 * 
 * @author Denis
 *
 */
public class Fichier {
	
	BufferedWriter output;
	
	public Fichier(LinkedList<Point> listePoint){
		try {
			FileWriter fw = new FileWriter("resultat.txt", true);
			output = new BufferedWriter(fw);
			for (Point point : listePoint){
				ecrire(point.getCoord());
			}
			
		} catch (IOException ie){
			ie.getMessage();
		}
	}
	
	private void ecrire(Float[] donnee){
		try {
			for (int i  =0; i< donnee.length; i++){
				output.write(String.valueOf(donnee[i])+" ");
			}
			output.flush();
		} catch (IOException io){
			io.getMessage();
		}
	}
	
	

}
