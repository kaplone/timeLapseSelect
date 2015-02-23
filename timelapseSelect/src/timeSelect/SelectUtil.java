package timeSelect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectUtil {
	
	
	
	public static  ObservableList<File> select(Select s, File txt) {
		
		int i = 0;
		int pre = 0;
		
		boolean flag_debut = true;
		boolean flag_passed = true;
		boolean flag_wait = false;
        boolean flag_decompte = false;
        boolean flag_suite = false;
		
		int annee = 2014;
		int mois = 1;
		int jour = 1;
		int heure = 1;
		int minute = 0;
		
		int compte = 0;
		
		File curFile;
		
		FileWriter write_txt = null;

		try {
			write_txt = new FileWriter(txt);
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
		
		ObservableList<File> aff = FXCollections.observableArrayList();
		
		if (s.getFile() == null){
			System.out.println("s.getFile == null");
			return null;
		}

		
		ArrayList<File> fichiersAL = new ArrayList<>();
		   	
		File[] fichiers = s.getFile().listFiles();
		Arrays.sort(fichiers);
		
		ArrayList<File> fichs = new ArrayList<File>(Arrays.asList(fichiers));
		
		Iterator cur = fichs.iterator();
		
		int avance = s.getDebut();
		
		
		
		while (cur.hasNext()){
			
			// demarrage
			
			if (flag_debut){
				
			    curFile = (File) cur.next();
			    System.out.println("demarrage : " + curFile);
	
	    		if (curFile.getName().endsWith("jpg")
	    			&& Integer.parseInt(curFile.getName().split("_")[3].split("h")[0]) == avance){
	    			
	    			flag_debut = false;
	    			flag_decompte = true;
	    			flag_suite = true;
	    			
	    			fichiersAL.add(curFile);
	    		    try {
						write_txt.write(curFile.toString() + "\n");
					} catch (IOException e) {
						// TODO Bloc catch généré automatiquement
						e.printStackTrace();
					}
	    			compte ++;
	    			
	    			annee = Integer.parseInt(curFile.getName().split("_")[0]);
	    			mois = Integer.parseInt(curFile.getName().split("_")[1]);
	    			jour = Integer.parseInt(curFile.getName().split("_")[2]);
	    			heure = Integer.parseInt(curFile.getName().split("_")[3].split("h")[0]);
	    			minute = Integer.parseInt(curFile.getName().split("_")[3].split("h")[1].split("m")[0]);
	    			
	    		}
	    	}	
			
			
			// decompte
			
		
			if (flag_decompte && compte < s.getPlage()){
				
				curFile = (File) cur.next();
				System.out.println("decompte : " + curFile);
				
				annee = Integer.parseInt(curFile.getName().split("_")[0]);
    			mois = Integer.parseInt(curFile.getName().split("_")[1]);
    			jour = Integer.parseInt(curFile.getName().split("_")[2]);
    			heure = Integer.parseInt(curFile.getName().split("_")[3].split("h")[0]);
    			minute = Integer.parseInt(curFile.getName().split("_")[3].split("h")[1].split("m")[0]);
				
				fichiersAL.add(curFile);
				try {
					write_txt.write(curFile.toString() + "\n");
				} catch (IOException e) {
					// TODO Bloc catch généré automatiquement
					e.printStackTrace();
				}
				compte ++;	
			}
			else {
				flag_decompte = false;
				flag_suite = true;
				compte = 0;
			}
			
			
			// suite 
			
			if (flag_suite){
				
			    curFile = (File) cur.next();
			    //System.out.println("suite : " + curFile);
	
	    		if (curFile.getName().endsWith("jpg")
	    		    && (Integer.parseInt(curFile.getName().split("_")[2])> jour 
	    		    	|| (Integer.parseInt(curFile.getName().split("_")[2]) == 1 
	    		    		&& (jour == 30
	    		    		    || jour == 31)))
	    			&& Integer.parseInt(curFile.getName().split("_")[3].split("h")[0]) >= heure 
	    			&& Integer.parseInt(curFile.getName().split("_")[3].split("h")[1].split("m")[0]) > minute - s.getDelcalage()){
	    			
	    			System.out.println("suite retenue : " + curFile);

	    			flag_decompte = true;
					flag_suite = false;
	    			
	    			fichiersAL.add(curFile);
	    			try {
						write_txt.write(curFile.toString() + "\n");
					} catch (IOException e) {
						// TODO Bloc catch généré automatiquement
						e.printStackTrace();
					}
	    			compte ++;
	    			
	    		}
	    	}
		}
		
		try {
			write_txt.close();
		} catch (IOException e) {
			// TODO Bloc catch généré automatiquement
			e.printStackTrace();
		}
	
    	aff.addAll(fichiersAL);
    	
    	return aff;
    	
    	
    }

}
