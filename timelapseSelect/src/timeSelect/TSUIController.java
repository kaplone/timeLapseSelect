package timeSelect;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TSUIController implements Initializable {
	
	@FXML
	private ChoiceBox<String> source;
	@FXML
	private ChoiceBox<String> plage;
	@FXML
	private ChoiceBox<String> decalage;
	@FXML
	private ChoiceBox<String> debut;
	@FXML
	private ImageView view;
	@FXML
	private Slider slider;
	@FXML
	private Label label;
	
	
	
	private ObservableList<String> sources = FXCollections.observableArrayList();
	private ObservableList<File> sourcesF = FXCollections.observableArrayList();
	private ObservableList<String> plages = FXCollections.observableArrayList();
	private ObservableList<String> decalages = FXCollections.observableArrayList();
	private ObservableList<String> debuts = FXCollections.observableArrayList();
	
	private ObservableList<File> aff = FXCollections.observableArrayList();
	
	private StringProperty nom = new SimpleStringProperty();
	private IntegerProperty num = new SimpleIntegerProperty();
	
	private ObjectProperty<Image> sbi = new SimpleObjectProperty<>();
	
	private Image tmpImage;
	
	private Select sel = new Select();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		File rep = new File("/home/autor/Desktop/tests_select");
		
		sources.add("selectionner un repertoire");
		sourcesF.addAll(rep.listFiles());
		
		for (File f : sourcesF){
			String [] elem = f.toString().split("/");
			sources.add(elem[elem.length -2] + "/" + elem[elem.length -1]);
		}
		
		source.setItems(sources);
		source.getSelectionModel().select(0);
		source.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	            }
		});
		
		
		plages.addAll(new String [] {"selectionner une plage",
                                     "1 image",
                                     "2 images",
                                     "5 images",
                                     "10 images",
                                     "15 images",
                                     "20 images",
                                     "30 images"});
		plage.setItems(plages);
		plage.getSelectionModel().select(0);
		plage.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setPlage(Integer.parseInt(plages.get((int) new_value).split(" ")[0]));
	                	    maj();
	                	}
	            }
		});
		
		decalages.addAll(new String [] {"selectionner un decalage",
                "1 minute",
                "5 minute",
                "10 minute",
                "15 minute",
                "20 minute",
                "30 minute",
                "45 minute",
                "60 minute"
                });
       decalage.setItems(decalages);
       decalage.getSelectionModel().select(0);
       decalage.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setDecalage(Integer.parseInt(decalages.get((int) new_value).split(" ")[0]));
	                	    maj();
	                	}
	            }
		});
       
       debuts.addAll(new String [] {"selectionner un debut",
               "5h00",
               "6h00",
               "7h00",
               "8h00",
               "9h00",
               "10h00",
               "11h00",
               "12h00",
               "13h00",
               "14h00",
               "15h00",
               "16h00",
               "17h00",
               "18h00",
               "19h00",
               "20h00",
               "21h00"               
               });
      debut.setItems(debuts);
      debut.getSelectionModel().select(0);
      debut.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	if (new_value.intValue()!= 0){
	                	    sel.setDebut(Integer.parseInt(debuts.get(new_value.intValue()).split("h")[0]));
	                	    maj();
	                	}
	            }
		});
              
       StringBinding sb = new StringBinding() {
    	   {
              super.bind(slider.valueProperty());
    	   }
	
		@Override
		protected String computeValue() {
			
			if (aff.size() > 0){
			
				num.set((int) slider.valueProperty().get());
				
				String tmp = aff.get(num.get()).toString();
				
				nom.set(num.get() + "    " + tmp);
				System.out.println(tmp);
				
				tmpImage = new Image("file://" + tmp);
				
				sbi.set(tmpImage);
			}
			else System.out.println("aff.size() = " + aff.size() );
				
				return nom.get();
			}
		};

       
		source.getSelectionModel().selectedIndexProperty().addListener(new
	            ChangeListener<Number>() {
	                public void changed(ObservableValue ov,
	                    Number value, Number new_value) {
	                	
	                	sel.setFile(sourcesF.get((int) new_value - 1));
	                	
	                	maj();
	            }
		});
       
       label.textProperty().unbind();
   	
       label.textProperty().bind(sb);

   	   view.imageProperty().bind(sbi);

	}
	
	protected void maj(){
    	
    	aff = SelectUtil.select(sel); 
    	
    	if (aff != null && aff.size() > 0){
    		slider.setMax(aff.size() -1);
    	}
    	slider.setMin(0);
    	slider.isSnapToTicks();
		
	}
	
	
	

}
