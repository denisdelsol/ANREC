package sortie;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Permet de sortir les données dans un fichier texte
 * 
 * @author Denis
 *
 */
public class Fichier {
	
	BufferedWriter output;
	
	public Fichier(){
		try {
			FileWriter fw = new FileWriter("resultat.txt", true);
			output = new BufferedWriter(fw);
			
		} catch (IOException ie){
			ie.getMessage();
		}
	}
	
	public void ecrire(String donnee){
		try {
		output.write(donnee);
		output.flush();
		} catch (IOException io){
			io.getMessage();
		}
	}
	
	

}
