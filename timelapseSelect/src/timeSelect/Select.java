package timeSelect;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Select {
	
	private ObjectProperty<File> file = new SimpleObjectProperty<>();
	private IntegerProperty plage = new SimpleIntegerProperty();
	private IntegerProperty decalage = new SimpleIntegerProperty();
	private IntegerProperty debut = new SimpleIntegerProperty();
	
	
	public Select(ObjectProperty<File> file, IntegerProperty plage,
			IntegerProperty delcalage, IntegerProperty debut) {
		super();
		this.file = file;
		this.plage = plage;
		this.decalage = delcalage;
		this.debut = debut;
		
	}
	
	public Select() {;
	}


	public ObjectProperty<File> fileProperty() {
		return file;
	}


	
	public File getFile() {
		return file.get();
	}


	public void setFile(File file) {
		this.file.set(file);
	}
	
	public IntegerProperty plageProperty() {
		return plage;
	}

	public int getPlage() {
		return plage.get();
	}


	public void setPlage(int plage) {
		this.plage.set(plage);
	}


	public IntegerProperty decalageProperty() {
		return decalage;
	}
	

	public int getDelcalage() {
		return decalage.get();
	}

	public void setDecalage(int delcalage) {
		this.decalage.set(delcalage);
	}
	
	public IntegerProperty debutProperty() {
		return debut;
	}
	

	public int getDebut() {
		return debut.get();
	}

	public void setDebut(int debut) {
		this.debut.set(debut);
	}
	
	

}
