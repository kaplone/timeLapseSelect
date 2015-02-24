package timeSelect;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Select {
	
	private ObjectProperty<File> preview = new SimpleObjectProperty<>();
	private ObjectProperty<File> full = new SimpleObjectProperty<>();
	
	private IntegerProperty plage = new SimpleIntegerProperty();
	private IntegerProperty decalage = new SimpleIntegerProperty();
	private IntegerProperty debut = new SimpleIntegerProperty();
	
	private ObjectProperty<LocalDate> date_in = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> date_out = new SimpleObjectProperty<>();
	
	
	public Select(ObjectProperty<File> preview,ObjectProperty<File> full, IntegerProperty plage,
			IntegerProperty delcalage, IntegerProperty debut, ObjectProperty<LocalDate> d_in, ObjectProperty<LocalDate> d_out) {
		super();
		this.preview = preview;
		this.full = full;
		this.plage = plage;
		this.decalage = delcalage;
		this.debut = debut;
		this.date_in = d_in;
		this.date_out = d_out;
		
	}
	
	public Select() {;
	}


	public ObjectProperty<File> previewProperty() {
		return preview;
	}

	public File getPreview() {
		return preview.get();
	}

	public void setPreview(File file) {
		this.preview.set(file);
	}
	
	public ObjectProperty<File> fullProperty() {
		return full;
	}

	public File getFull() {
		return full.get();
	}

	public void setFull(File file) {
		this.full.set(file);
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
	
	public ObjectProperty<LocalDate> date_inProperty() {
		return date_in;
	}
	
	public LocalDate getDate_in() {
		return date_in.get();
	}
	
	public String getDateString_in() {
		System.out.println("getDateString_in() " + date_in.get().toString());
		return Arrays.asList(date_in.get().toString().split("-")).stream().collect(Collectors.joining("_"));
	}

	public void setDate_in(LocalDate d) {
		this.date_in.set(d);
	}
	
	public ObjectProperty<LocalDate> date_outProperty() {
		return date_out;
	}
	
	public LocalDate getDate_out() {
		return date_out.get();
	}
	
	public String getDateString_out() {
		System.out.println("getDateString_out() " + date_out.get().toString());
		return Arrays.asList(date_out.get().toString().split("-")).stream().collect(Collectors.joining("_"));
	}

	public void setDate_out(LocalDate d) {
		this.date_out.set(d);
	}
	
	

}
