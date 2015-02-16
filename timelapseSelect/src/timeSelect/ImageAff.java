package timeSelect;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ImageAff {
	
	private IntegerProperty pos = new SimpleIntegerProperty();
	private ObjectProperty<File> file = new SimpleObjectProperty<>();
	
	
	
	public ImageAff(IntegerProperty pos, ObjectProperty<File> file) {
		super();
		this.pos = pos;
		this.file = file;
	}
	public ImageAff(){
		
	}
	
	public IntegerProperty posProperty() {
		return pos;
	}
	public Integer getPos() {
		return pos.get();
	}
	public void setPos(IntegerProperty pos) {
		this.pos = pos;
	}
	public ObjectProperty<File> fileProperty() {
		return file;
	}
	public File getFile() {
		return file.get();
	}
	public void setFile(ObjectProperty<File> file) {
		this.file = file;
	}

}
